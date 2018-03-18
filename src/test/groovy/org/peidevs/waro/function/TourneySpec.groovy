package org.peidevs.waro.function

import org.peidevs.waro.player.Player
import org.peidevs.waro.strategy.NextCard
import org.peidevs.waro.table.*

import spock.lang.*

class TourneySpec extends Specification {
    def "can play a tourney"() {
        def numGames = 3
        def numCards = 12
        def maxCard = numCards
        def strategy = new NextCard()
        def players = [
            new Player("p1", strategy, maxCard, new Hand()),
            new Player("p2", strategy, maxCard, new Hand()),
            new Player("p3", strategy, maxCard, new Hand())
        ]
        
        when:
            def newPlayers = new Tourney(numCards, numGames, false).apply(players)
        
        then:
            3 == newPlayers.size()
            newPlayers.each { 0 == it.numCardsInHand }

            def predicateNumGamesWon = { it.playerStats.numGamesWon }
            def toInt = { it as Integer }
            3 == newPlayers.stream().map(predicateNumGamesWon).mapToInt(toInt).sum()
    }
}
