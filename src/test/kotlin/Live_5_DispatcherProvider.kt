package talk.script

import api.User
import api.UserService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * API
 */
suspend fun loadUserIO(backend: UserService): User =
    withContext(Dispatchers.IO) {
        backend.load()
    }


/**
 * In future releases it might be possible to test code directly using [Dispatchers.IO] etc,
 * see issue [1365](https://github.com/Kotlin/kotlinx.coroutines/issues/1365).
 */
@ExperimentalCoroutinesApi
class Live_5_DispatcherProvider {

    private val user = User("Herbert")

    @Test
    fun `use a DispatcherProvider`() = runBlockingTest {

        val backend: UserService = mockk()
        coEvery { backend.load() } coAnswers {
            // delay in order to check auto-advance of TestDispatcher
            delay(30_000)
            user
        }

        val loaded = loadUserIO(backend)
        Assertions.assertSame(user, loaded)
    }


}