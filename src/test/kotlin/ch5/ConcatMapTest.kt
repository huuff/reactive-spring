package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class ConcatMapTest {

    private fun delayReplyFor(i: Int, delay: Long): Flux<Int> {
       return Flux.just(i).delayElements(Duration.ofMillis(delay))
    }

    @Test
    fun concatMap() {
        val data = Flux
            .just(Pair(1, 300L), Pair(2, 200L), Pair(3, 100L))
            .log()
            .concatMap { id -> this.delayReplyFor(id.first, id.second) }

        StepVerifier
            .create(data)
            .expectNext(1, 2, 3)
            .verifyComplete()
    }
}