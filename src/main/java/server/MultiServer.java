package server;

import screen.player.PlayScreen;
import world.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    private ServerSocket serverSocket;
    private static World world;
    public void start(int port, World world) throws IOException {
        serverSocket = new ServerSocket(port);
        this.world = world;
        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    if ("creatures".equals(inputLine)) {
                        out.println(world.saveCreaturesAsString());
                    }else if("player".equals(inputLine)){
                        out.println(world.savePlayerAsString());
                    }else if("world".equals(inputLine)){
                        out.println(world.saveWorldAsString());
                    }else if("role".equals(inputLine)){

                    }
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
