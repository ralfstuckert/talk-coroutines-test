package talk.code

import api.User
import api.UserService
import coroutines.coAssertThrows
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

fun CoroutineScope.loadUserAsync(backend: UserService): Deferred<User> = async {
    withTimeout(30_000) {
        backend.load()
    }
}

class Script_3_2_TimeoutAsync {

    private val user = User("Herbert")
    private val backend: UserService = mockk()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `testing async in time`() = runBlockingTest {
        coEvery { backend.load() } coAnswers {
            delay(29_999)
            user
        }
        val deferred = loadUserAsync(backend)
        // waiting will advance coroutine
        val loaded = deferred.await()
        assertSame(user, loaded)
    }


    @Test
    fun `testing async timeout`() = runBlockingTest {
        coEvery { backend.load() } coAnswers {
            delay(30_000)
            user
        }
        coAssertThrows<TimeoutCancellationException> {
            loadUserAsync(backend).await()
        }
    }

    @Test
    fun `testing async timeout with standard assert`() {
        assertThrows<TimeoutCancellationException> {
            runBlockingTest {
                coEvery { backend.load() } coAnswers {
                    delay(30_000)
                    user
                }

                loadUserAsync(backend).await()
            }
        }
    }


}