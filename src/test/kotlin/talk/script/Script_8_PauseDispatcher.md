# Pause Dispatcher

## pauseDispatcher()

- let example run and explain
- introduce `runDispatcher()` and let it run:
```kotlin
    pauseDispatcher()
    launch {
```
- explain non eager execution, adapt test
```kotlin
    launch {
        ...
    }
    // not started yet
    assertEquals(0, state)
```

## resumeDispatcher()       
- replace `runCurrent()` with `resumeDispatcher()` and let it run
- explains that it advances until idle
```kotlin
    // not started yet
    assertEquals(0, state)

    resumeDispatcher()
    // advance until idle after resumeDispatcher()
    assertEquals(3, state)
```
    
## pauseDispatcher {}      
- replace pause/resumeDispatcher with scoped pauseDispatcher
```kotlin
    pauseDispatcher {
        launch {
        ...
        }

        assertEquals(0, state)
    }

    // advance until idle after pauseDispatcher() block
    assertEquals(3, state)
```
