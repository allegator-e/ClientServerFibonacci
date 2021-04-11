import Client.Client;
import Server.Server;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("The program has ended")));
        try {
            String hostName = args[1];
            int port = Integer.parseInt(args[2]);
            if (port > 65535 || port < 1) {
                System.out.println("Port's number must be between 1 and 65535");
                System.exit(-1);
            }
            if (args[0].equals("-client")) {
                Client client = new Client(hostName, port);
                client.clientConnect();
            } else if (args[0].equals("-server")) {
                Server server = new Server(hostName, port);
                server.serverConnect();
            } else {
                System.out.println("Incorrect input. Write key (-client or -server and host and port in args");
                System.exit(-1);
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Incorrect input. Write key (-client or -server and host and port in args");
            System.exit(-1);
        }
    }
}
