package Client;

import java.nio.channels.UnresolvedAddressException;
import java.util.Scanner;

public class Client {
    private Scanner reader;
    private ClientSender sender;

    public Client(String host, int port) {
        reader = new Scanner(System.in);
        sender = new ClientSender(host, port);
    }

    public void clientConnect() {
        try {
            while (true) {
                String numberString = reader.nextLine().trim();
                if (numberString.equals("")) {
                    System.exit(1);
                } else {
                    try {
                        int number = Integer.parseInt(numberString);
                        if (number >= 0) {
                            sender.send(number);
                        } else {
                            System.out.println("Number can't be less than 0. Write again");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect input. Write int number");
                    }
                }
            }
        } catch (UnresolvedAddressException e){
            System.out.println("Exception of initialization host");
        }
    }
}
