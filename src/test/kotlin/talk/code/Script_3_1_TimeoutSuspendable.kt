package talk.code

import api.User
import api.UserService
import coroutines.coAssertThrows
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

//
// API classes
//

suspend fun loadUser(backend: UserService): User =
    withTimeout(30_000) {
        backend.load()
    }

//
// Tests
//

@UseExperimental(ExperimentalCoroutinesApi::class)
class Script_3_1_TimeoutSuspendable {

    private val user = User("Herbert")

    @Test
    fun `testing timeout with a mockk service`() = runBlockingTest {
        val backend = mockk<UserService>()
        coEvery { backend.load() } coAnswers {
            delay(30_000)
            user
        }
        coAssertThrows<TimeoutCancellationException> {
            loadUser(backend)
        }
    }


    // derive from previous
    @Test
    fun `testing in time with a mockk service`() = runBlockingTest {
        val backend = mockk<UserService>()
        coEvery { backend.load() } coAnswers {
            delay(29_999)
            user
        }
        val loaded = loadUser(backend)
        assertSame(user, loaded)
    }


}




