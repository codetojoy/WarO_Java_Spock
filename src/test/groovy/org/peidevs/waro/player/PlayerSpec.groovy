package org.peidevs.waro.player

import org.peidevs.waro.strategy.*
import org.peidevs.waro.table.Hand

import spock.lang.*
import java.util.stream.IntStream
import static java.util.stream.Collectors.toList

class PlayerTest extends Specification {
    def strategy = new MaxCard()
    def maxCard = 40
    def prizeCard = 10

    def buildHand(min,max) {
        def cards = IntStream.range(min,max).boxed().collect(toList())
        new Hand(cards)
    }

    def "reset should put things back to zero"() {
        def hand = buildHand(1,5)
        def player = new Player("Randy", strategy, maxCard, hand)
        def bid = player.getBid(prizeCard)

        player = player.winsRound(bid)
        3 == player.getNumCardsInHand()
        1 == player.getPlayerStats().getNumRoundsWon()
        
        when:
            def newHand = buildHand(6,8+1)
            def result = player.reset(newHand)
        
        then:
            3 == result.getNumCardsInHand()
            0 == result.getPlayerStats().getNumRoundsWon()
    }
   
    def "get bid from player"() {
        def hand = buildHand(1,5)
        def player = new Player("Randy", strategy, maxCard, hand)

        when:
            def bid = player.getBid(prizeCard)

        then:
            player == bid.bidder
            4 == bid.offer
            prizeCard == bid.prizeCard 
   }
}
