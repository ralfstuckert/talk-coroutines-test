package talk.code

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@UseExperimental(ExperimentalCoroutinesApi::class)
class Script_1_Eager {

    @Test
    fun `no eager excecution in runBlocking`() = runBlocking {
        var called = false
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
        var called = false
        launch {
            called = true
        }
        // runBlockingTest() uses eager executing dispatcher
        assertTrue(called)
    }

    // aus vorhergehendem Beispiel ableiten
    @Test
    fun `eager execution until delay or yield`() = runBlockingTest {
        var called = false
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