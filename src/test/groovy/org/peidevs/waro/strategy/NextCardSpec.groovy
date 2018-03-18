package org.peidevs.waro.strategy

import spock.lang.*

import java.util.stream.IntStream

class NextCardSpec extends Specification {

    def "next card strategy selects closest card to prize"() {
        def strategy = new NextCard()
        def maxCard = 50
        def prizeCard = 10
        def handSize = 20
        def identity = { i -> i }
        IntStream hand = IntStream.range(1,handSize+1).boxed().mapToInt(identity)
        
        when:
            def result = strategy.selectCard(prizeCard, hand, maxCard)
      
        then:
            1 == result
    }

}
