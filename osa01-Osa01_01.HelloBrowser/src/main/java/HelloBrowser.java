
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HelloBrowser {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a URL: ");
        String url = input.nextLine();
        Socket socket = new Socket(url, 80);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("GET / HTTP/1.1");
        out.println("Host: " + url);
        out.println();
        out.flush();
        Scanner in = new Scanner(socket.getInputStream());
        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(line);
        }
    }
}
