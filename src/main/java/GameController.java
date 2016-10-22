import io.netty.channel.ChannelId;

import java.util.ArrayList;

/**
 * Created by bubnyshev on 21.10.2016.
 */
public class GameController {
    private  static ArrayList<Game> games = new ArrayList<Game>();

    public static synchronized Game addNewPlayer(ChannelId channelId){

        if(games.size() == 0) {
            games.add(new Game());
        }
        Game lastGame = games.get(games.size() - 1);
        if (lastGame.addPlayer(channelId)){
            games.add(new Game());
            return lastGame;
        } else {
            return null;
        }
    }


    public static Game findGame(ChannelId id){
        for (Game game:games){
            if (game.getP1() == id || game.getP2() == id);
            return game;
        }
        return null;
    }

    public static void closeGame(Game game) {
        games.remove(game);
    }
}
