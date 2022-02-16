package ch5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.concurrent.atomic.AtomicBoolean

class TransformTest {

    @Test
    fun transform() {
        val finished = AtomicBoolean()
        val letters = Flux.just("A", "B", "C")
            .transform { stringFlux ->
                stringFlux.doFinally { signal -> finished.set(true) }
            }

        StepVerifier.create(letters).expectNextCount(3).verifyComplete()

        Assertions.assertTrue(finished.get())
    }
}