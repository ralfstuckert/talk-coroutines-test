# Exception Handling

## uncaught exceptions
- let example run and compare to `runBlockingTest()`
- introduce `uncaughtExceptions` and let it run:
```kotlin
    // executed eagerly, so we can handle the exception right here
    assertEquals(1, uncaughtExceptions.size)
    assertThat(uncaughtExceptions[0], isA<IOException>())

    ...
    assertEquals(2, uncaughtExceptions.size)
    assertThat(uncaughtExceptions[1], isA<IllegalArgumentException>())
```
- `uncaughtExceptions` in TestCoroutineScope is delegated to TestCoroutineExceptionHandler


## Propagation in SupervisorScope
- let it run with `runBlocking()`
- now with `runBlockingTest()` and explain
- introduce dummy exception handler
```kotlin
   runBlockingTest {
               withContext(DummyCoroutineExceptionHandler) {
```
- dummy exception handler does not work if passed in `runBlockingTest()`
  as it is not a `TestCoroutineExceptionHandler`
- introduce custom test exception handler
```kotlin
   runBlockingTest(SilentTestCoroutineExceptionHandler()) {
```

