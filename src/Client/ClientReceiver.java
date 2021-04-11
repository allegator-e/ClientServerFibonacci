package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiver {
    private Socket socket;

    public ClientReceiver(Socket socket){
        this.socket = socket;
    }

    public void receive() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Integer answer = (Integer) ois.readObject();
            System.out.println(answer);
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in response from server");
        }
    }
}
