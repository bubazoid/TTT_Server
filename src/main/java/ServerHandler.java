import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Sergey on 21.10.2016.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("New player joined");
        channels.add(ctx.channel());
        sendPackage(ctx.channel(), new Package("Hello new player"), false);
        Game game = GameController.addNewPlayer(ctx.channel().id());
        if (game != null) {
            startNewGame(game);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        closeGame(ctx.channel());
    }

    private void startNewGame(Game game) {
        for (Channel channel : channels) {
            if (channel.id() == game.getP1()) {
                channel.writeAndFlush(new Package("You first"));
                channel.writeAndFlush(new Package(Package.YOUR_STEP));
            }
            if (channel.id() == game.getP2()) {
                channel.writeAndFlush(new Package("You second"));
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Package pack = (Package) msg;
        switch (pack.getType()) {
            case Package.STEP:
                step(ctx, pack);
                break;
            case Package.CLOSE_GAME:
                closeGame(ctx.channel());
                break;
        }
    }

    private void step(ChannelHandlerContext ctx, Package pack) {
        Game game;
        Channel opponentChannel;
        game = GameController.findGame(ctx.channel().id());
        if (game != null) {
            if (game.makeStep(pack.getStep(), ctx.channel().id())) {
                ChannelId opponentId = game.getOpponentId(ctx.channel().id());
                if (opponentId != null) {
                    opponentChannel = getChannelById(opponentId);
                    if (opponentChannel != null) {
                        sendPackage(opponentChannel, new Package(Package.STEP, pack.getStep()), true);
                        switch (game.getStatus()) {
                            case Logic.DRAW:
                                Package answer = new Package(Package.CLOSE_GAME, "Ничья");
                                sendPackage(opponentChannel, answer, false);
                                sendPackage(ctx.channel(), answer, false);
                                break;
                            case Logic.NEXT:
                                sendPackage(opponentChannel, new Package(Package.YOUR_STEP), false);
                                break;
                            case Logic.WIN:
                                sendPackage(opponentChannel, new Package(Package.CLOSE_GAME, "Вы проиграли"), false);
                                sendPackage(ctx.channel(), new Package(Package.CLOSE_GAME, "Вы выиграли"), false);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void sendPackage(Channel channel, Package pack, boolean sync) {
        if (sync) {
            try {
                channel.writeAndFlush(pack).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            channel.writeAndFlush(pack);
        }
    }

    private void closeGame(Channel channel) {
        Game game = GameController.findGame(channel.id());
        if (game != null) {
            ChannelId opponentId = game.getOpponentId(channel.id());
            GameController.closeGame(game);
            if (opponentId != null) ;
            {
                Channel opponentChannel = getChannelById(opponentId);
                if (opponentChannel != null) {
                    sendPackage(opponentChannel, new Package(Package.CLOSE_GAME, "Соперник вышел"), true);
                    closeChannel(channel);
                    closeChannel(opponentChannel);
                }
            }
        }
    }

    private void closeChannel(Channel channel) {
        channels.remove(channel);
        channel.close();
    }

    private Channel getChannelById(ChannelId id) {
        for (Channel channel : channels) {
            if (channel.id() == id) {
                return channel;
            }
        }
        return null;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        closeChannel(ctx.channel());
    }
}
