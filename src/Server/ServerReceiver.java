package Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ServerReceiver {
    private SelectionKey key;
    private Integer number;
    private ByteBuffer buffer = ByteBuffer.allocate(4096);
    public ServerReceiver(SelectionKey key) {
        this.key = key;
    };
    public int read() throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count = socketChannel.read(buffer);
        if (count > -1) {
            byte[] bytes = buffer.array();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            number = (Integer) ois.readObject();
            ois.close();
            bais.close();
            buffer.clear();
            key.interestOps(SelectionKey.OP_WRITE);
        }
        if (count == -1) {
            key.cancel();
        }
        return number;
    }
}
