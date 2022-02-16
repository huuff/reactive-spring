package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.test.StepVerifier

// XXX: Adapted to Sinks.many since EmitterProcessor is deprecated
class EmitterProcessorTest {

    @Test
    fun emitterProcessor() {
        val sink = Sinks.many().multicast().onBackpressureBuffer<String>()
        produce(sink)
        consume(sink.asFlux())
    }

    private fun produce(sink: Sinks.Many<String>) {
        sink.emitNext("1", Sinks.EmitFailureHandler.FAIL_FAST)
        sink.emitNext("2", Sinks.EmitFailureHandler.FAIL_FAST)
        sink.emitNext("3", Sinks.EmitFailureHandler.FAIL_FAST)
        sink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST)
    }

    private fun consume(publisher: Flux<String>) {
        StepVerifier
            .create(publisher)
            .expectNext("1")
            .expectNext("2")
            .expectNext("3")
            .verifyComplete()
    }
}