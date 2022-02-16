package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.ReplayProcessor
import reactor.core.publisher.Sinks
import reactor.test.StepVerifier

class ReplayProcessorTest {

    @Test
    fun replayProcessor() {
        val sink = Sinks.many().replay().limit<String>(2)
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
        for (i in 1..5) {
            StepVerifier
                .create(publisher)
                .expectNext("2")
                .expectNext("3")
                .verifyComplete()
        }
    }
}