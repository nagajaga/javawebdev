import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelloServer {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);

        while (true)  {
            Socket req = server.accept();
            Scanner s = new Scanner(req.getInputStream());
            String p = s.nextLine();
            if (p.contains("quit")) {
                break;
            }
            PrintWriter pw = new PrintWriter(req.getOutputStream());
            pw.println("HTTP/1.1 200 OK");
            pw.println("");
            Files.lines(Paths.get("index.html")).forEach(line -> pw.println(line));
            pw.flush();
            pw.close();
            req.close();
            s.close();
            
        
        }

    }
}
