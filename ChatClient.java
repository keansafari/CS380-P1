
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.Reader;
import java.io.PrintStream;


public final class ChatClient extends Thread {

    static Socket socket;
    ChatClient(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("codebank.xyz", 38001)) {
            //for the username input
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userName;
            String address = socket.getInetAddress().getHostAddress();

            
            //Recieve
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            //How to send
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");

            //Gets user name from user
            System.out.print("Enter a username: ");
            userName = stdIn.readLine();

            //Sends user name to server
            out.println(userName);

            //Starts thread to constantly recieve output from server
            (new Thread(new ChatClient(socket))).start();
            
            while (true) {
                userName = stdIn.readLine();
                if (userName.equals("-exit")) {
                    socket.close();
                    stdIn.close();
                    is.close();
                    br.close();
                }
                out.println(userName);
            }    
        }
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while (true)
                System.out.println(br.readLine());
        } catch (Exception e) {
            System.out.println("Exception@(!#*)#");
        }
        
    }

}











