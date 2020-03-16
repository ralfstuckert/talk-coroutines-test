package talk.live

import api.Confirmation
import api.UI
import coroutines.testDispatcher
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * API
 */
suspend fun confirmDone(ui: UI): Confirmation =
    withContext(Dispatchers.Main) {
        ui.waitForUserConfirm("Press OK to continue")
    }


@ExperimentalCoroutinesApi
class Live_4_MainDispatcher {

    @Test
    fun `use dispatcher of test scope for main`() = runBlockingTest {

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