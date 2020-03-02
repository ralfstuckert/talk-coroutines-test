package talk.script

import api.User
import api.UserService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test


/**
 * API
 */
suspend fun loadUser(backend: UserService): User =
    withTimeout(30_000) {
        backend.load()
    }


@UseExperimental(ExperimentalCoroutinesApi::class)
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

