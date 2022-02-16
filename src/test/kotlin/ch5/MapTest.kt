package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class MapTest {

    @Test
    fun maps() {
        val data = Flux.just("a", "b", "c")
            .map(String::uppercase)

        StepVerifier
            .create(data)
            .expectNext("A", "B", "C")
            .verifyComplete()
    }
}