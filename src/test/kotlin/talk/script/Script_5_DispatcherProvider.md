# Dispatcher Provider

- let it run and explain missing time control due to dispatcher
- introduce `DispatcherProvider` interface
```kotlin
    suspend fun loadUserIO(backend: UserService, dispatcherProvider: DispatcherProvider): User =
        withContext(dispatcherProvider.io) {
   

    fun `use a DispatcherProvider`() = runBlockingTest {
       val dispatcherProvider = TestDispatcherProvider(testDispatcher)
```
- introduce library

## runBlocking(Test)Provided
- transform to using provider in context
```kotlin
    suspend fun loadUserIO(backend: UserService): User =
        withContext(coroutineContext.dispatcherProvider.io) {

    fun `use a DispatcherProvider`() = runBlockingTestProvided {
```

## withIO
- show utility withIO
```kotlin
    suspend fun loadUserIO(backend: UserService): User =
       withIO {
```
