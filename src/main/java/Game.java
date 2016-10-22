import io.netty.channel.ChannelId;

/**
 * Created by bubnyshev on 21.10.2016.
 */
public class Game {
    private ChannelId p1;
    private ChannelId p2;
    private Integer[] desk = {0,0,0,0,0,0,0,0,0};
    private Integer status = Logic.NEXT;


    public boolean addPlayer(ChannelId id) {
        if (p1 == null){
            p1 = id;
        } else if (p2 == null){
            p2 = id;
        }
        return p1 != null && p2 != null;
    }
    public boolean isFull(){
        return p1 != null && p2 != null;
    }

    public ChannelId getP1() {
        return p1;
    }

    public ChannelId getP2() {
        return p2;
    }

    public boolean makeStep(int step, ChannelId id) {
        int player = getPlayerNumberById(id);
        if (player != 0 && desk[step] == 0){
            desk[step] = player;
            status = Logic.checkDesk(desk);
            return true;
        } else {
            return false;
        }
    }


    private int getPlayerNumberById(ChannelId id){
        if (p1 == id ){
            return 1;
        }
        if (p2 == id ){
            return 2;
        }
        return 0;
    }

    public Integer getStatus() {
        return status;
    }

    public ChannelId getOpponentId(ChannelId id) {
        int player = getPlayerNumberById(id);
        return player == 1? p2:p1;
    }
}
