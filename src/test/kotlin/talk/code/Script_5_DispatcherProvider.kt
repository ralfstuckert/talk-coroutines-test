package talk.code

import api.User
import api.UserService
import com.rickbusarow.dispatcherprovider.DispatcherProvider
import com.rickbusarow.dispatcherprovider.dispatcherProvider
import com.rickbusarow.dispatcherprovider.test.TestDispatcherProvider
import com.rickbusarow.dispatcherprovider.test.runBlockingTestProvided
import com.rickbusarow.dispatcherprovider.withIO
import coroutines.testDispatcher
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.coroutines.coroutineContext

suspend fun loadUserProvidedDispatcher(backend: UserService, dispatcherProvider: DispatcherProvider): User =
    withContext(dispatcherProvider.io) {
        backend.load()
    }

suspend fun loadUserContextIO(backend: UserService): User =
    withContext(coroutineContext.dispatcherProvider.io) {
        backend.load()
    }

suspend fun loadUserWithIO(backend: UserService): User =
    withIO {
        backend.load()
    }

/**
 * In future releases it might be possible to test code directly using [Dispatchers.IO] etc,
 * see issue [1365](https://github.com/Kotlin/kotlinx.coroutines/issues/1365).
 */
@ExperimentalCoroutinesApi
class Script_5_DispatcherProvider {

    private val user = User("Herbert")
    private val backend: UserService = mockk()

    @BeforeEach
    fun setUp() {
        clearAllMocks()

        coEvery { backend.load() } coAnswers {
            // delay a bit in order to check auto-advance of TestDispatcher
            delay(5_000)
            user
        }
    }

    @Test
    fun `inject or pass DispatcherProvider`() = runBlockingTest {
        val dispatcherProvider = TestDispatcherProvider(testDispatcher)

        val loaded = loadUserProvidedDispatcher(backend, dispatcherProvider)
        Assertions.assertSame(user, loaded)
    }

    @Test
    fun `pass DispatcherProvider via coroutine context`() = runBlockingTestProvided {
        val loaded = loadUserContextIO(backend)
        Assertions.assertSame(user, loaded)
    }

    @Test
    fun `convenience function withIO()`() = runBlockingTestProvided {
        val loaded = loadUserWithIO(backend)
        Assertions.assertSame(user, loaded)
    }


}