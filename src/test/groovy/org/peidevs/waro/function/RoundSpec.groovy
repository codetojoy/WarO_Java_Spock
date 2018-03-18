package org.peidevs.waro.function

import org.peidevs.waro.player.*
import org.peidevs.waro.strategy.*
import org.peidevs.waro.table.*

import spock.lang.*

import static java.util.stream.Collectors.toList

class RoundSpec extends Specification {
    def hand1 = new Hand([1,2,3])
    def hand2 = new Hand([4,5,6])
    def hand3 = new Hand([7,8,9])

    def strategy = new MaxCard()
    def players = new ArrayList<>()

    def numCards = 12
    def maxCard = numCards

    def "can play a basic round"() {
        def p1 = new Player("p1", strategy, maxCard, hand1)
        def p2 = new Player("p2", strategy, maxCard, hand2)
        def p3 = new Player("p3", strategy, maxCard, hand3)
        
        players.add(p1)
        players.add(p2)
        players.add(p3)
        
        def prizeCard = 10
        
        when:
            def newPlayers = new Round(prizeCard).apply(players.stream()).collect(toList())
        
        then:
            3 == newPlayers.size()
            newPlayers.each { 2 == it.numCardsInHand }

            def predicateNumRoundsWon = { val, p -> p.playerStats.numRoundsWon == val }
            1 == newPlayers.stream().filter(predicateNumRoundsWon.curry(1)).count() 
            2 == newPlayers.stream().filter(predicateNumRoundsWon.curry(0)).count() 

            def predicateTotal = { val, p -> p.playerStats.total == val }
            1 == newPlayers.stream().filter(predicateTotal.curry(10)).count() 
            2 == newPlayers.stream().filter(predicateTotal.curry(0)).count() 
    }

    def "can collect bids from players"() {
        def round = new Round()
        
        Player p1 = new Player("p1", strategy, maxCard, hand1)
        Player p2 = new Player("p2", strategy, maxCard, hand2)
        Player p3 = new Player("p3", strategy, maxCard, hand3)
        
        players.add(p1)
        players.add(p2)
        players.add(p3)
        
        when:
            def result = round.getAllBids(players.stream(), 10)
        
        then:
            3 == result.size()
    }
    
    def "can find winning bid"() { 
        def round = new Round()
        def maxCard = 50
        
        def p1 = new Player("Beethoven", strategy, maxCard, new Hand())
        def p2 = new Player("Chopin", strategy, maxCard, new Hand())
        def p3 = new Player("Mozart", strategy, maxCard, new Hand())
        
        def prizeCard = 20
        def bids = []
        bids.add( new Bid(prizeCard, 10, p1) )
        bids.add( new Bid(prizeCard, 12, p2) )
        bids.add( new Bid(prizeCard, 14, p3) )
        
        when:
            def result = round.findWinningBid(bids)
        
        then:
            "Mozart" == result.getBidder().getName()
    }
}
