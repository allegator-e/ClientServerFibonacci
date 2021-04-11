package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSender {
    private Integer answer;
    private SelectionKey key;
    static Logger LOGGER;
    static {
        LOGGER = Logger.getLogger(ServerSender.class.getName());
    }
    public ServerSender(SelectionKey key) {
        this.answer = (Integer)key.attachment();
        this.key = key;
    };

    public void write() {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(answer);
            ByteBuffer buffer = ByteBuffer.wrap(bos.toByteArray());
            socketChannel.write(buffer);
            oos.close();
            bos.close();
            buffer.clear();
            key.cancel();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception in send answer" );
        }
    }
}
