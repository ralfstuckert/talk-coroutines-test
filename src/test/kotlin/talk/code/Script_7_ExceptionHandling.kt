package talk.code

import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isA
import coroutines.SilentTestCoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class Script_7_ExceptionHandling {

    @Test
    fun `TestExceptionHandler used by runBlockingTest() catches all exceptions`() {
        assertThrows<IOException> {
            runBlockingTest() {

                launch() {
                    throw IOException()
                }
                // executed eagerly, so we can handle the exception right here
                assertEquals(1, uncaughtExceptions.size)
                assertThat(uncaughtExceptions[0], isA<IOException>())

                launch() {
                    throw IllegalArgumentException()
                }
                assertEquals(2, uncaughtExceptions.size)
                assertThat(uncaughtExceptions[1], isA<IllegalArgumentException>())
            }
        }
    }

    @Test
    fun `TestCoroutineExceptionHandler will propagate exceptions of supervised children`() =
        runBlockingTest() {

            supervisorScope() {
                val child1 = launch() {
                    throw IOException()
                }
                val child2 = launch() {
                    delay(1000)
                }
                assertThat(uncaughtExceptions, anyElement(isA<IOException>()))
            }
        }


    @Test
    fun `use a custom exception handler in runBlockingTest() for testing supervisor behaviour`() =
        runBlockingTest(SilentTestCoroutineExceptionHandler()) {

            supervisorScope() {
                val child1 = launch() {
                    throw IOException()
                }

                val child2 = launch() {
                    delay(1000)
                }
            }
            assertThat(uncaughtExceptions, anyElement(isA<IOException>()))
        }




}