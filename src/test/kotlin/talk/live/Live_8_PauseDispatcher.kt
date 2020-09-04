package talk.live

import coroutines.AtomicInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Live_8_PauseDispatcher {

    @Test
    fun `pause dispatcher`() = runBlockingTest {
        var state by AtomicInt(0)

        launch {
            state = 1
            yield()
            state = 2
            delay(1000)
            state = 3
        }
        assertEquals(1, state)

        // runCurrent() advances all actions until current (virtual) time.
        runCurrent()
        assertEquals(2, state)

        advanceUntilIdle()
        assertEquals(3, state)
    }
}