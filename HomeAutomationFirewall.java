import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HomeAutomationFirewall {

    private static List<String> blockedIPs = new ArrayList<>();

    public static void main(String[] args) {
        blockedIPs.add("192.168.1.100"); // Example blocked IP

        int port = 8080; // Example port number to monitor

        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Firewall started on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    String clientIP = clientSocket.getInetAddress().getHostAddress();
                    System.out.println("Connection from: " + clientIP);

                    if (isBlocked(clientIP)) {
                        System.out.println("Connection from " + clientIP + " blocked by firewall");
                        clientSocket.close();
                    } else {
                        System.out.println("Connection from " + clientIP + " accepted");
                        // Simulate handling the connection (in a real system, perform the appropriate
                        // actions)
                        handleConnection(clientSocket);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isBlocked(String ip) {
        return blockedIPs.contains(ip);
    }

    private static void handleConnection(Socket clientSocket) {
        // Simulated handling of the connection
        // In a real system, perform the appropriate actions based on the security
        // requirements
        // For the example, simply log the connection and close it
        try {
            System.out.println("Handling the connection...");
            // Close the connection after handling (simulated action)
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
