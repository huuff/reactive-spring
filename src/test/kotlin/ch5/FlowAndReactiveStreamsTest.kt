package ch5

import org.junit.jupiter.api.Test
import org.reactivestreams.FlowAdapters
import reactor.adapter.JdkFlowAdapter
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FlowAndReactiveStreamsTest {

    @Test
    fun convert() {
        val original = Flux.range(1, 10)

        val asJDKFlow = FlowAdapters.toFlowPublisher(original)
        val asReactiveStream = FlowAdapters.toPublisher(asJDKFlow)

        StepVerifier.create(original).expectNextCount(10).verifyComplete()
        StepVerifier.create(asReactiveStream).expectNextCount(10).verifyComplete()

        val asReactorFluxAgain = JdkFlowAdapter.flowPublisherToFlux(asJDKFlow)
        StepVerifier.create(asReactorFluxAgain).expectNextCount(10).verifyComplete()
    }
}