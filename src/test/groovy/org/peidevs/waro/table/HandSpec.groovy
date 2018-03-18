package org.peidevs.waro.table

import spock.lang.*

import java.util.stream.IntStream
import static java.util.stream.Collectors.toList

class HandSpec extends Specification {
    
    def "can select a card"() {
        def cards = IntStream.range(1,10+1).boxed().collect(toList())
        def hand = new Hand(cards)
        
        when:
            def result = hand.select(5)
        
        then:
            9 == result.cardsAsIntStream().boxed().count()
    }
    
    def "will disallow illegal selection"() {
        def cards = IntStream.range(1,10+1).boxed().collect(toList())
        def hand = new Hand(cards)
        
        when:
            def result = hand.select(18)

        then:
            thrown(IllegalArgumentException)
    }
}
