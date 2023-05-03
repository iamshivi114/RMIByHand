import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10314);
        
            while (true) {
                Socket socket = serverSocket.accept();
                try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
        
                    String method = (String) ois.readObject();
                    int result = 0;
                    switch (method) {
                        case "add":
                            result = add(ois.readInt(), ois.readInt());
                            break;
                        case "divide":
                            result = divide(ois.readInt(), ois.readInt());
                            break;
                        case "echo":
                            oos.writeObject(ois.readObject());
                            oos.flush();
                            continue;
                    }
        
                    oos.writeInt(result);
                    oos.flush();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}