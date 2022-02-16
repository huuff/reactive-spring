package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.ReplayProcessor
import reactor.test.StepVerifier

// TODO: Use Sinks.many
class ReplayProcessorTest {

    @Test
    fun replayProcessor() {
        val processor = ReplayProcessor.create<String>(
            2, false
        )
        produce(processor.sink())
        consume(processor)
    }

    private fun produce(sink: FluxSink<String>) {
        sink.next("1")
        sink.next("2")
        sink.next("3")
        sink.complete()
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