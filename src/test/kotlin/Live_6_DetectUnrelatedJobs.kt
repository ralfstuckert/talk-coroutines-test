package talk.script

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@UseExperimental(ExperimentalCoroutinesApi::class)
class Live_6_DetectUnrelatedJobs {


    @Test
    fun `runBlockingTest() detects uncompleted Jobs`() = runBlockingTest() {
        val job = GlobalScope.launch() {
            delay(2000)
        }
        job.join()
        println("We will never see this")
    }



}