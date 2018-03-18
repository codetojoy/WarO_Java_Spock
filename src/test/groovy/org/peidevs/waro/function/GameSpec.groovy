package org.peidevs.waro.function

import org.peidevs.waro.player.Player
import org.peidevs.waro.strategy.NextCard
import org.peidevs.waro.table.*

import spock.lang.*

import static java.util.stream.Collectors.toList

class GameSpec extends Specification {
    def numCards = 12
    def maxCard = numCards
    def strategy = new NextCard()
    def players = []

    def "can play a basic game"() {
        players = [
            new Player("p1", strategy, maxCard, new Hand()),
            new Player("p2", strategy, maxCard, new Hand()),
            new Player("p3", strategy, maxCard, new Hand())
        ]
        
        when:
            def newPlayers = new Game(numCards, false).apply(players)
        
        then:
            3 == newPlayers.size()
            newPlayers.each { 0 == it.numCardsInHand }

            def predicateNumGamesWon = { val, p -> p.playerStats.numGamesWon == val }
            1 == newPlayers.stream().filter(predicateNumGamesWon.curry(1)).count() 
            2 == newPlayers.stream().filter(predicateNumGamesWon.curry(0)).count() 

            def predicateTotal = { val, p -> p.playerStats.total <= val }
            def maxTotal = maxCard + (maxCard - 1) + (maxCard - 2)
            3 == newPlayers.stream().filter(predicateTotal.curry(maxTotal)).count() 

            def predicateNumRoundsWon = { it.playerStats.numRoundsWon }
            def toInt = { it as Integer }
            3 == newPlayers.stream().map(predicateNumRoundsWon).mapToInt(toInt).sum()
    }

    def "can play another basic game"() {
        players = [
            new Player("p1", strategy, maxCard, new Hand([1,5,9])),
            new Player("p2", strategy, maxCard, new Hand([4,8,6])),
            new Player("p3", strategy, maxCard, new Hand([7,2,3]))
        ]
        
        def kitty = new Hand([10,11,12])
        
        when:
            def newPlayers = new Game(numCards, false).play(kitty, players.stream()).collect(toList())
        
        then:
            3 == newPlayers.size()
            newPlayers.each { 0 == it.numCardsInHand }

            def predicateNumRoundsWon = { val, p -> p.playerStats.numRoundsWon == val }
            3 == newPlayers.stream().filter(predicateNumRoundsWon.curry(1)).count()

            def predicateTotal = { val, p -> p.playerStats.total == val }
            1 == newPlayers.stream().filter(predicateTotal.curry(10)).count()
            1 == newPlayers.stream().filter(predicateTotal.curry(11)).count()
            1 == newPlayers.stream().filter(predicateTotal.curry(12)).count()
    }
}
