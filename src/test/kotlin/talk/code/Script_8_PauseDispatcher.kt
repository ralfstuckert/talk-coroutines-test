package talk.code

import coroutines.AtomicInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Script_8_PauseDispatcher {

    @Test
    fun `paused dispatcher does not execute eager`() = runBlockingTest {
        var state by AtomicInt(0)

        pauseDispatcher()
        launch {
            state = 1
            yield()
            state = 2
            delay(1000)
            state = 3
        }
        // not started yet
        assertEquals(0, state)

        // runCurrent() advances all actions until current (virtual) time.
        runCurrent()
        assertEquals(2, state)

        advanceUntilIdle()
        assertEquals(3, state)
    }

    @Test
    fun `resumeDispatcher() advances until idle`() = runBlockingTest {
        var state by AtomicInt(0)

        pauseDispatcher()
        launch {
            state = 1
            yield()
            state = 2
            delay(1000)
            state = 3
        }
        // not started yet
        assertEquals(0, state)

        resumeDispatcher()
        // advance until idle after resumeDispatcher()
        assertEquals(3, state)
    }


    @Test
    fun `pauseDispatcher {} resumes after executing block`() = runBlockingTest {
        var state by AtomicInt(0)

        pauseDispatcher {
            launch {
                state = 1
                yield()
                state = 2
                delay(1000)
                state = 3
            }

            assertEquals(0, state)
        }

        // advance until idle after pauseDispatcher() block
        assertEquals(3, state)
    }


}