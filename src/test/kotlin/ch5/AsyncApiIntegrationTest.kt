package ch5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.test.StepVerifier
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class AsyncApiIntegrationTest {
    val executor: ExecutorService = Executors.newFixedThreadPool(10)

    private fun launch(integerFluxSink: FluxSink<Int>, count: Int) {
        executor.submit {
            val integer = AtomicInteger()
            Assertions.assertNotNull(integerFluxSink)
            while (integer.get() < count) {
                val random = Math.random()
                integerFluxSink.next(integer.incrementAndGet())
                Thread.sleep(random.toLong() * 1_000)
            }
            integerFluxSink.complete()
        }
    }

    @Test
    fun async() {
        val integers: Flux<Int> = Flux.create { emitter -> launch(emitter,5) }

        StepVerifier
            .create(integers.doFinally { signal -> this.executor.shutdown() })
            .expectNextCount(5)
            .verifyComplete()
    }
}