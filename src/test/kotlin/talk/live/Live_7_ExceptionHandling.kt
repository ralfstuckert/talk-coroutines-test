package talk.live

import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isA
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import java.lang.IllegalArgumentException

@UseExperimental(ExperimentalCoroutinesApi::class)
class Live_7_ExceptionHandling {

    @Test
    fun `TestCoroutineExceptionHandler used by runBlockingTest() catches all exceptions`() {
        assertThrows<IOException> {
            runBlocking() {

                launch() {
                    throw IOException()
                }

                launch() {
                    throw IllegalArgumentException()
                }

            }
        }
    }


    @Test
    fun `TestCoroutineExceptionHandler propagates exception`() =
        runBlocking() {

            supervisorScope() {
                val child1 = launch() {
                    throw IOException()
                }
                val child2 = launch() {
                    delay(1000)
                }
            }
        }



}