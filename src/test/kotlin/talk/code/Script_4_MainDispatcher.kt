package talk.code

import api.Confirmation
import api.UI
import coroutines.MainDispatcherExtension
import coroutines.testDispatcher
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

suspend fun confirmDone(ui: UI): Confirmation =
    withContext(Dispatchers.Main) {
        ui.waitForUserConfirm("Press OK to continue")
    }

@ExtendWith(MainDispatcherExtension::class)
@ExperimentalCoroutinesApi
class Script_4_MainDispatcher {

    @AfterEach
    fun tearDown() {
        // reset main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `use dispatcher of test scope for main`() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)

        val uiMock: UI = mockk()
        coEvery { uiMock.waitForUserConfirm(any()) } coAnswers {
            delay(10_000)
            Confirmation.OK
        }

        val confirmation = confirmDone(uiMock)

        assertEquals(Confirmation.OK, confirmation)

        // main dispatcher will be reset in tearDown()
    }


    @Test
    fun `provide test dispatcher via JUnit extension (aka Rule)`(dispatcher: TestCoroutineDispatcher) =
        dispatcher.runBlockingTest {

            val uiMock: UI = mockk()
            coEvery { uiMock.waitForUserConfirm(any()) } coAnswers {
                delay(10_000)
                Confirmation.OK
            }

            val confirmation = confirmDone(uiMock)
            // since the test dispatcher is used for main, time will be auto-advanced
            assertEquals(Confirmation.OK, confirmation)
        }

}