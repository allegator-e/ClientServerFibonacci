package Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSender {
    private String host;
    private int port;

    public ClientSender(String host, int port) {
        this.host = host; this.port = port;
    }

    void send(Integer number){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(number);
            byte[] data = baos.toByteArray();

            Socket socket = new Socket(host, port);
            socket.getOutputStream().write(data);
            ClientReceiver receiver = new ClientReceiver(socket);

            receiver.receive();

            socket.close();
            oos.close();
            baos.close();
        }catch (IOException e){
            System.out.println("Error connection with server...");
        }
    }

}
