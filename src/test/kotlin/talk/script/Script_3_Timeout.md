# Timeout

## timeout
- show function loadUser() with injected service
- explain test and let it run
- introduce delay and expect exception
```kotlin
        coEvery { backend.load() } coAnswers {
            delay(30_000)
...
        coAssertThrows<TimeoutCancellationException> {
            loadUser(backend)
        }
```

## in time boundary
- copy test and rename
```kotlin
            delay(29_999)
            user
        }
        val loaded = loadUser(backend)
        assertSame(user, loaded)
```

## async example (optional)
- copy timeout test and rename
```kotlin
        coAssertThrows<TimeoutCancellationException> {
            loadUserAsync(backend).await()
        }
```
