package server;

import creature.*;
import router.InvaderControlRouter;
import world.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiServer {
    private ServerSocket serverSocket;
    private static World world;
    private static Map<String, Integer> map;
    public void start(int port, World world) throws IOException {
        map = new HashMap<>();
        map.put("Blinky",0);
        map.put("Clyde",0);
        map.put("Pinky",0);
        map.put("Inky",0);
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
                        inputLine = in.readLine();
                        if(login(map,inputLine))
                            out.println("registered successfully!");
                        else{
                            out.println("Failed.No such role or the role has been occupied!");
                        }

                    }
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean login(Map<String ,Integer> map,String role){
            if(!map.containsKey(role)){
                return false;
            }else{
                if(map.get(role) > 0){
                    return false;
                }
                else{
                    map.put(role,1);
                    List<Creature> creatureList = world.getCreatures();
                    world.unlockWorld();
                    for(Creature c:creatureList){
                        if(c.getTitle().equals(role) && role.equals("Blinky")){
                            ((BlinkyAI) (GhostAI) c.getAI()).assumeRouter(new InvaderControlRouter());
                        }
                        else if(c.getTitle().equals(role) && role.equals("Pinky")){
                            ((PinkyAI) (GhostAI) c.getAI()).assumeRouter(new InvaderControlRouter());
                        }
                        else if(c.getTitle().equals(role) && role.equals("Inky")){
                            ((InkyAI) (GhostAI) c.getAI()).assumeRouter(new InvaderControlRouter());
                        }
                        else if(c.getTitle().equals(role) && role.equals("Clyde")){
                            ((ClydeAI) (GhostAI) c.getAI()).assumeRouter(new InvaderControlRouter());
                        }
                    }
                    return true;
                }
            }
        }
    }
}
