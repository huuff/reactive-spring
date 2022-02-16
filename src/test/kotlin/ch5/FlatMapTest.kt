package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class FlatMapTest {

    @Test
    fun flatMap() {
        val data = Flux
            .just(Pair(1, 300L), Pair(2, 200L), Pair(3, 100L))
            .log()
            .flatMap { id -> delayReplyFor(id.first, id.second) }

        StepVerifier
            .create(data)
            .expectNext(3, 2, 1)
            .verifyComplete()
    }

    private fun delayReplyFor(i: Int, delay: Long): Flux<Int> {
        return Flux.just(i).delayElements(Duration.ofMillis(delay))
    }
}