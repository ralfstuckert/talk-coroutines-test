package talk.live

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class Live_7_ExceptionHandling {

    @Test
    fun `TestCoroutineExceptionHandler used by runBlockingTest() catches all exceptions`() {
            runBlocking() {

                launch() {
                    throw IOException()
                }

                launch() {
                    throw IllegalArgumentException()
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