package talk.code

import coroutines.AtomicBoolean
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class Script_1_Eager {

    @Test
    fun `no eager excecution in runBlocking`() = runBlocking {
        var called by AtomicBoolean(false)
        val job = launch {
            called = true
        }
        // job might not be executed yet, so need to join
        job.join()
        assertTrue(called)
    }

    // aus vorhergehendem Beispiel ableiten
    @Test
    fun `eager excecution in runBlockingTest`() = runBlockingTest {
        var called by AtomicBoolean(false)
        launch {
            called = true
        }
        // runBlockingTest() uses eager executing dispatcher
        assertTrue(called)
    }

    // aus vorhergehendem Beispiel ableiten
    @Test
    fun `eager execution until delay or yield`() = runBlockingTest {
        var called by AtomicBoolean(false)
        launch {
            yield()
            called = true
        }
        // eager execution ends at yield...
        assertFalse(called)
        // ...so continue manually
        runCurrent()
        assertTrue(called)
    }


}