package talk.script

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@UseExperimental(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class Live_2_AdvanceTime {

    @Test
    fun `time control`() = runBlocking() {
            delay(5_000)
    }













    @Test
    fun `no auto-advance in launched coroutine`() = runBlockingTest {
        var called = false
        launch {
            delay(1000)
            called = true
        }
        // eager execution stops due to delay...
        assertFalse(called)
    }


}