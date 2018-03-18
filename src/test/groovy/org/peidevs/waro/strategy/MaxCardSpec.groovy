package org.peidevs.waro.strategy

import spock.lang.*

import java.util.stream.IntStream

class MaxCardSpec extends Specification {

    def "max card strategy selects highest card"() {
        def strategy = new MaxCard()
        def maxCard = 50
        def prizeCard = 10
        def handSize = 20
        def identity = { i -> i }
        IntStream hand = IntStream.range(1,handSize+1).boxed().mapToInt(identity)
        
        when:
            def result = strategy.selectCard(prizeCard, hand, maxCard)
      
        then:
            handSize == result
    }

}
