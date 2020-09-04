package talk.live

import coroutines.AtomicBoolean
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Live_1_Eager {

    @Test
    fun `eager excecution`() = runBlocking {
        var called by AtomicBoolean(false)

        val job = launch {
            called = true
        }
        // need to join in order to make sure job is done
        job.join()
        assertTrue(called)
    }


}