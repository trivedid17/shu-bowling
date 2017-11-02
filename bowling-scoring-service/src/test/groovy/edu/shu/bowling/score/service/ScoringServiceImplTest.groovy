package edu.shu.bowling.score.service

import edu.shu.bowling.score.model.Frame
import edu.shu.bowling.score.model.Game
import edu.shu.bowling.score.model.LastFrame
import edu.shu.bowling.score.model.Player
import edu.shu.bowling.score.exception.ValidationException
import edu.shu.bowling.score.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ScoringServiceImplTest extends Specification {

    @Autowired
    ScoreService scoreService

    @Autowired
    GameRepository gameRepository


    def "Start a new Game with 6 players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)

        for (int i = 0; i < 6; i++) {
            def player = new Player()
            player.setPlayerName("Player" + i)
            game.getPlayers().add(player)
        }

        when: "game is started"
        def result = scoreService.startGame(game)
        then: "should return new gameId"
        result != "" && result != null
    }

    def "Start a new Game with 1 players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)

        def player = new Player()
        player.setPlayerName("Player")
        game.getPlayers().add(player)

        when: "game is started"
        def result = scoreService.startGame(game)
        then: "should return new gameId"
        result != "" && result != null
    }

    def "Start a new Game without players"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)


        when: "game is started"
        scoreService.startGame(game)
        then: "should throw exception"
        final ValidationException exception = thrown()
        final message = exception.message
        message == "The number of players in a game must be between 1 and 6\n"
    }


    def "Play throw for a game which does not exist"() {

        given: "A pins"
        def gameId = "Not exist"
        def pins = 6
        when: "game is started"
        scoreService.computeScore(gameId,pins)
        then: "should throw exception"
        thrown(ValidationException)
    }

    def "Roll a ball for a game which is not active"() {

        given: "A Game"
        def game = new Game()
        game.setLaneId(1)
        game.setStartTime(new Date())
        game.complete()

        def player = new Player()
        player.setPlayerName("Player")
        player.setSeqNo(1)
        game.getPlayers().add(player)

        def result = gameRepository.saveAndFlush(game)
        def gameId = result.getGameId()
        def pins = 4

        when: "game is started"
        scoreService.computeScore(gameId, pins)
        then: "should throw exception"
        thrown(ValidationException)
    }






    def "Calculate Score for a game with no Player" () {

        given: "A Game object"
            def game = new Game()
        when: "score is calculated"
            scoreService.computeScore(game)
        then: "Should throw an exception"
            final ValidationException exception = thrown()
            final message = exception.message
            message == "Game should have at least one player"
    }


    def "Single player score when no frame played"(){

        given: "A Game"
            def game = new Game()
            List<Player> players = game.getPlayers()
        Player player = new Player()
            players.add(player)
        when: "score is calculated"
            scoreService.computeScore(game)
        then: "Total score of player should be 0"
            player.getScore() == 0
    }





    def "Single player score when strike in rows"(int framePlayed,int result){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < framePlayed && framePlayed < 11; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(10)
                if(frame instanceof LastFrame)
                {
                    frame.setRoll2(10)
                    frame.setRoll3(10)
                }
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
            scoreService.computeScore(game)
        then: "Total score of player should match result"
        player.getScore() == result
        where:
        framePlayed | result
        1   |   0
        2   |   0
        3   |   30
        4   |   60
        5   |   90
        6   |   120
        7   |   150
        8   |   180
        9   |   210
        10  |   300

    }


    def "Single player score when spares in rows"(int framePlayed,int result){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < framePlayed && framePlayed < 11; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(4)
                frame.setRoll2(6)
                if(frame instanceof LastFrame)
                {
                    frame.setRoll3(5)
                }
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should match result"
        player.getScore() == result
        where:
        framePlayed | result
        1   |   0
        2   |   14
        3   |   28
        4   |   42
        5   |   56
        6   |   70
        7   |   84
        8   |   98
        9   |   112
        10  |   141


    }


    def "Single player score when no spares and no strikes"(int framePlayed,int result){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < framePlayed && framePlayed < 11; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(4)
                frame.setRoll2(5)
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should match result"
        player.getScore() == result
        where:
        framePlayed | result
        1   |   9
        2   |   18
        3   |   27
        4   |   36
        5   |   45
        6   |   54
        7   |   63
        8   |   72
        9   |   81
        10  |   90
    }



    def "Single player score when some strikes, spares and individual score"(int framePlayed,int result){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < framePlayed && framePlayed < 11; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setSeqNo(i + 1)
                frames.add(frame)
               switch(i)
               {
                   case 0:
                       frame.setRoll1(0)
                       frame.setRoll2(5)
                       break
                   case 1:
                       frame.setRoll1(6)
                       frame.setRoll2(2)
                       break
                   case 2:
                       frame.setRoll1(10)
                       break
                   case 3:
                       frame.setRoll1(2)
                       frame.setRoll2(4)
                       break
                   case 4:
                       frame.setRoll1(6)
                       frame.setRoll2(4)
                       break
                   case 5:
                       frame.setRoll1(3)
                       frame.setRoll2(4)
                       break
                   case 6:
                       frame.setRoll1(0)
                       frame.setRoll2(0)
                       break
                   case 7:
                       frame.setRoll1(4)
                       frame.setRoll2(6)
                       break
                   case 8:
                       frame.setRoll1(10)
                       break
                   case 9:
                       frame.setRoll1(3)
                       frame.setRoll2(2)
                       break

               }

            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should match result"
        player.getScore() == result
        where:
        framePlayed | result
        1   |   5
        2   |   13
        3   |   13
        4   |   35
        5   |   35
        6   |   55
        7   |   55
        8   |   55
        9   |   75
        10  |   95

    }


    def "Single player first two rolls score with no strike or spare"(){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < 1; i++) {
                Frame frame = new Frame()
                frame.setRoll1(3)
                frame.setRoll2(4)
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should be 7"
        player.getScore() == 7
    }

    def "Single player perfect score"(){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < 10; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(10)
                if(frame instanceof LastFrame)
                {
                    frame.setRoll2(10)
                    frame.setRoll3(10)
                }
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should be 300"
        player.getScore() == 300
    }


    def "Multi player perfect score"(){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player1 = new Player()
        players.add(player1)
        Player player2 = new Player()
        players.add(player2)
        for (Player player : players) {
            List<Frame> frames = player.getFrames()
            for (int i = 0; i < 10; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(10)
                if(frame instanceof LastFrame)
                {
                    frame.setRoll2(10)
                    frame.setRoll3(10)
                }
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should be 300"
        player1.getScore() == 300 &&  player2.getScore() == 300
    }


    def "Single player with no score"(){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player = new Player()
        players.add(player)
        for (Player player1 : players) {
            List<Frame> frames = player1.getFrames()
            for (int i = 0; i < 10; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(0)
                if(frame instanceof LastFrame)
                {
                    frame.setRoll2(0)
                    frame.setRoll3(0)
                }
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should be 0"
        player.getScore() == 0
    }


    def "Multi player with no score"(){

        given: "A Game"
        def game = new Game()
        List<Player> players = game.getPlayers()
        Player player1 = new Player()
        players.add(player1)
        Player player2 = new Player()
        players.add(player2)
        for (Player player : players) {
            List<Frame> frames = player.getFrames()
            for (int i = 0; i < 10; i++) {

                Frame frame = i < 9 ? new Frame(): new LastFrame()
                frame.setRoll1(0)
                if(frame instanceof LastFrame)
                {
                    frame.setRoll2(0)
                    frame.setRoll3(0)
                }
                frame.setSeqNo(i + 1)
                frames.add(frame)
            }
        }
        when: "score is calculated"
        scoreService.computeScore(game)
        then: "Total score of player should be 0"
        player1.getScore() == 0 &&  player2.getScore() == 0
    }

}
