# Detect Unrelated Jobs

- `runBlockingTest()` makes assumptions on time control
and therefore detects uncompleted jobs by counting
- usually a hint on using non-TestCoroutineDispatcher
- show usage of `testDispatcher`

- copy example and alter to using async-await:
```kotlin
    @Test
    fun `runBlockingTest() detects using non-TestDispatcher`() = runBlockingTest() {
        val value = async(Dispatchers.IO) {
            delay(2000)
            17
        }.await()
        println("We will never see value ${value}")
    }
```
