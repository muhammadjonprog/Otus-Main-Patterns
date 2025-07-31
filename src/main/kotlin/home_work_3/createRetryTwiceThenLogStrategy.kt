package home_work_3

fun createRetryTwiceThenLogStrategy(): ExceptionHandler {
    return RetryExceptionHandler(
        maxAttempts = 2,
        fallbackHandler = LoggingExceptionHandler()
    )
}
