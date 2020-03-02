package talk.code

import coroutines.testDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@UseExperimental(ExperimentalCoroutinesApi::class)
class Script_6_DetectUnrelatedJobs {

    @Test
    fun `runBlockingTest() detects uncompleted Jobs`() = runBlockingTest() {
        val job = GlobalScope.launch() {
            delay(2000)
        }
        job.join()
        println("We will never see this")
    }


    @Test
    fun `runBlockingTest() detects using non-TestDispatcher`() = runBlockingTest() {
        val value = async(Dispatchers.IO) {
            delay(2000)
            17
        }.await()
        println("We will never see value ${value}")
    }

}
