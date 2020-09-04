package talk.live

import api.User
import api.UserService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Test


/**
 * API
 */
suspend fun loadUser(backend: UserService): User =
    withTimeout(30_000) {
        backend.load()
    }


class Live_3_Timeout {

    private val user = User("Herbert")

    @Test
    fun `testing timeout`() = runBlockingTest {
        val backend = mockk<UserService>()
        coEvery { backend.load() } coAnswers {
            user
        }

        loadUser(backend)
    }


}

/**
 * Another API
 */
fun CoroutineScope.loadUserAsync(backend: api.UserService): Deferred<User> = async {
    withTimeout(30_000) {
        backend.load()
    }
}

