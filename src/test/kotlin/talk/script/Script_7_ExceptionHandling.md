# Exception Handling

## uncaught exceptions
- let example run and compare to runBlocking()
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


## Propagation in Supe`rvisorScope
- let it run with `runBlocking()`
- now with `runBlockingTest()` and explain
- introduce analyzing exceptions with `uncaughtExceptions`
```kotlin
    assertThat(uncaughtExceptions, anyElement(isA<IOException>()))
```
- introduce custom exception handler
```kotlin
    SilentTestCoroutineExceptionHandler()
```

