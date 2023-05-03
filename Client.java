import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        try {
            return (int) callRemote("add", lhs, rhs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static Integer divide(int num, int denom) {
        Object result = callRemote("divide", num, denom);
        if (result instanceof ArithmeticException) {
            throw (ArithmeticException) result;
        }
        return (Integer) result;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        try {
            return (String) callRemote("echo", message);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Object callRemote(String method, Object... args) {
        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
    
            oos.writeObject(method);
            for (Object arg : args) {
                oos.writeObject(arg);
            }
            oos.flush();
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello") == "You said Hello!")
            System.out.print(".");
        else
            System.out.print("X");
        
        System.out.println(" Finished");
    }
}