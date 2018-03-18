package org.peidevs.waro.table

import spock.lang.*

import static java.util.stream.Collectors.toList

import org.peidevs.waro.player.*
import org.peidevs.waro.strategy.*

class DealerSpec extends Specification {
    def dealer = new Dealer()

    def "dealer can deal cards to players at table"() {
        def numCards = 12
        def maxCard = numCards
        def strategy = new MaxCard()
        def players = new ArrayList<>()
        
        def h1 = new Hand([1,2,3])
        def p1 = new Player("p1", strategy, maxCard, h1)

        def h2 = new Hand([4,5,6])
        def p2 = new Player("p2", strategy, maxCard, h2)

        def h3 = new Hand([7,8,9])
        def p3 = new Player("p3", strategy, maxCard, h3)
        
        players.add(p1)
        players.add(p2)
        players.add(p3)

        when:
            def table = dealer.deal(20, players)

        then:
            5 == table.getKitty().cardsAsIntStream().count()
            5 == table.getPlayers().get(0).numCardsInHand
            5 == table.getPlayers().get(1).numCardsInHand
            5 == table.getPlayers().get(2).numCardsInHand
    }

    def "dealer deals proper # cards to table"() {
        def numCards = 40
        def numPlayers = 4

        when:
            def hands = dealer.deal(numCards, numPlayers)
        
        then:
            def handList = hands.collect(toList())
            handList.each { hand -> 
                8 == hand.cardsAsIntStream().count()
            }
    }

    def "dealer won't allow uneven cards"() {
        def numCards = 42
        def numPlayers = 4

        when: 
            dealer.deal(numCards, numPlayers)        

        then:
            thrown(IllegalArgumentException)
    }

    def "can shuffle cards"() {
        def numCards = 4
        
        when:
           def result = dealer.buildShuffledDeck(numCards)
        
        then:
            4 ==  result.size()
            result.contains(1)
            result.contains(2)
            result.contains(3)
            result.contains(4)
    }
}
