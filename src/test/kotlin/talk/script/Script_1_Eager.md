# Eager Execution

- show example and let it run
- use `runBlockingTest()` and remove `join()` 
```kotlin
              runBlockingTest() {
        ...     
             
        launch {
            called = true
        }
        // runBlockingTest() uses eager executing dispatcher
        assertTrue(called)
```

## eager execution stops at yield/delay
- add `yield()` and let it run
```kotlin
        launch {
            yield()
            called = true
        }
```
- adapt unit test
```kotlin
        // eager execution ends at yield...
        assertFalse(called)
```
- and introduce `runCurrent()`
```kotlin
        // ...so continue manually
        runCurrent()
        assertTrue(called)
    }
```

