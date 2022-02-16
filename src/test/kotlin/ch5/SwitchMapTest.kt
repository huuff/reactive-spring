package ch5

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class SwitchMapTest {

    @Test
    fun switchMapWithLookaheads() {
        val source = Flux
            .just("re", "rea", "reac", "reactive")
            .delayElements(Duration.ofMillis(100))
            .switchMap(this::lookup)
            .log()

        StepVerifier
            .create(source)
            .expectNext("reactive -> reactive")
            .verifyComplete()
    }

    private fun lookup(word: String): Flux<String> {
        return Flux.just("$word -> reactive")
            .delayElements(Duration.ofMillis(500))
    }
}