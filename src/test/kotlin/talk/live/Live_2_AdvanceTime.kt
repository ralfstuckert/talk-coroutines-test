package talk.live

import coroutines.AtomicBoolean
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class Live_2_AdvanceTime {

    @Test
    fun `time control`() = runBlocking() {
            delay(5_000)
    }













    @Test
    fun `no auto-advance in launched coroutine`() = runBlockingTest {
        var called by AtomicBoolean(false)
        launch {
            delay(1000)
            called = true
        }
        // eager execution stops due to delay...
        assertFalse(called)
    }


}