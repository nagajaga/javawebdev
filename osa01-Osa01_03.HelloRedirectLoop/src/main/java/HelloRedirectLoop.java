
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelloRedirectLoop {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        int reqs = 1;
        while (true) {
            Socket req = server.accept();
            System.out.println("Request " + reqs++);
            Scanner s = new Scanner(req.getInputStream());
            String p = s.nextLine();
            if (p.contains("quit")) {
                break;
            }
            PrintWriter pw = new PrintWriter(req.getOutputStream());
            pw.println("HTTP/1.1 302 Found");
            pw.println("Location: http://localhost:8080/");
            pw.println("");
            pw.flush();
            pw.close();
            req.close();
            s.close();

        }
    }
}
