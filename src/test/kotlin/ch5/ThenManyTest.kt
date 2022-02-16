package ch5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.concurrent.atomic.AtomicInteger

class ThenManyTest {

    @Test
    fun thenMany() {
        val letters = AtomicInteger()
        val numbers = AtomicInteger()

        val lettersPublisher = Flux.just("a", "b", "c")
            .log()
            .doOnNext { letters.getAndIncrement() }
        val numbersPublisher = Flux.just(1, 2, 3)
            .log()
            .doOnNext { numbers.getAndIncrement() }
        val thisBeforeThat = lettersPublisher.thenMany(numbersPublisher)

        StepVerifier.create(thisBeforeThat).expectNext(1, 2, 3).verifyComplete()
        Assertions.assertEquals(letters.get(), 3)
        Assertions.assertEquals(numbers.get(), 3)
    }
}