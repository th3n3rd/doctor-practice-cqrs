package com.example.doctorpractice.scheduling.application.queries

class QueryDispatcher(val queryHandlers: QueryHandlers) {
    inline fun <reified T : Any> dispatch(query: Any): T {
        return queryHandlers.handle(query)
    }
}
