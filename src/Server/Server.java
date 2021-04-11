package Server;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private String host;
    private int port;
    private Fibonacci fibonacci = new Fibonacci();

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void serverConnect() {
        Logger LOGGER = Logger.getLogger(Server.class.getName());
        try {
            Selector selector = Selector.open();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(host, port));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.isOpen()) {
                int count = selector.select();
                if (count == 0) {
                    continue;
                }
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) {
                        LOGGER.log(Level.INFO, "Connection established");
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        ServerReceiver receiver = new ServerReceiver(key);
                        int number = receiver.read();
                        LOGGER.log(Level.INFO, "Received number " + number);
                        key.attach(fibonacci.calculateFibonacci(number));
                    } else if (key.isWritable()) {
                        ServerSender sender = new ServerSender(key);
                        sender.write();
                        LOGGER.log(Level.INFO, "End of connection. The result was sent to te client");
                    }
                    keyIterator.remove();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception in server. Program has stopped");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }
}
