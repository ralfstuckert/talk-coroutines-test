# Main Dispatcher

- let example run -> main dispatcher is missing
- introduce setMain() function
```kotlin
    @AfterEach
    fun tearDown() {
        // reset main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    fun `main dispatcher`() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
```

## MainDispatcher rule
```kotlin
@ExtendWith(MainDispatcherExtension::class)
  ...

    fun `main dispatcher`(dispatcher: TestCoroutineDispatcher) =
        dispatcher.runBlockingTest {

```
