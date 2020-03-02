# Advance Time

- run example with `runBlocking()`
- show it takes 5 seconds
- try with `runBlockingTest()` and measure time
- show virtual time
```kotlin
            val virtualStart = currentTime
            delay(100_000)
            val virtualDuration = currentTime - virtualStart
            println("virtualDuration: $virtualDuration")
```

## no auto-advance in launch
- show example and let it run
- explain auto advance
- show time control:
```kotlin
        // ...so advance virtual time
        advanceTimeBy(1000)
        assertTrue(called)
```
 
## show advance is reliable
```kotlin
        // control of virtual time is reliable
        advanceTimeBy(999)
        assertFalse(called)
        advanceTimeBy(1)
        assertTrue(called)
```
