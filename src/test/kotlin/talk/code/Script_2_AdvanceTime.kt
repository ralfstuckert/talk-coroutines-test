package talk.code

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.greaterThanOrEqualTo
import coroutines.AtomicBoolean
import coroutines.coAssertExecutesInLessThan
import coroutines.coAssertExecutionTakesAtLeast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.time.seconds

class Script_2_AdvanceTime {

    @Test
    fun `using runBlocking() may take some time`() = runBlocking() {
        coAssertExecutionTakesAtLeast(5.seconds) {
            delay(5_000)
        }
    }

    @Test
    fun `runBlockingTest() auto-advances virtual time`() = runBlockingTest {
        coAssertExecutesInLessThan(2.seconds) {
            val virtualStart = currentTime
            delay(100_000)
            val virtualDuration = currentTime - virtualStart
            assertThat(virtualDuration, greaterThanOrEqualTo(100_000L))
        }
    }

    @Test
    fun `does not auto-advance time in launched coroutine`() = runBlockingTest {
        var called by AtomicBoolean(false)
        launch {
            delay(1000)
            called = true
        }
        // eager execution stops due to delay...
        assertFalse(called)
        // ...so advance virtual time
        advanceTimeBy(1000)
        assertTrue(called)
    }

    // aus vorhergehendem Beispiel ableiten
    @Test
    fun `advance time is reliable`() = runBlockingTest {
        var called by AtomicBoolean(false)
        launch {
            delay(1000)
            called = true
        }
        // control of virtual time is reliable
        advanceTimeBy(999)
        assertFalse(called)
        advanceTimeBy(1)
        assertTrue(called)
    }



}