package server;

import creature.*;
import creature.router.InvaderControlRouter;
import world.World;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NIOMultiServer {
    private static final String POISON_PILL = "POISON_PILL";
    private World world;
    public void start(World world) throws IOException {
        this.world = world;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 28848));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }
                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }
    }
    private void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println(new String(buffer.array()).trim());
            System.out.println("Not accepting client messages anymore");
        }
        else {
            buffer.flip();
            client.write(buffer);
            String cmd = new String(buffer.array()).trim();
            processCmd(cmd);
            System.out.println(new String(buffer.array()).trim());
            buffer.clear();
            buffer.put(new byte[256]);
            buffer.clear();

        }
    }

    private  void processCmd(String cmd){
        String temp[] = cmd.split("\\s+");
        String role = temp[0];
        String movement = temp[1];
        List<Creature> list = world.getCreatures();
        world.unlockWorld();
        for(Creature c :list){
            if(c.getTitle().equals(role)&&role.equals("Blinky")){
                InvaderControlRouter  router = (InvaderControlRouter) ((BlinkyAI) (GhostAI) c.getAI()).getRouter();
                router.receive(movement);
            }
            else if(c.getTitle().equals(role)&&role.equals("Pinky")){
                InvaderControlRouter  router = (InvaderControlRouter) ((PinkyAI) (GhostAI) c.getAI()).getRouter();
                router.receive(movement);
            }
            else if(c.getTitle().equals(role)&&role.equals("Inky")){
                InvaderControlRouter  router = (InvaderControlRouter) ((InkyAI) (GhostAI) c.getAI()).getRouter();
                router.receive(movement);
            }
            else if(c.getTitle().equals(role)&&role.equals("Clyde")){
                InvaderControlRouter  router = (InvaderControlRouter) ((ClydeAI) (GhostAI) c.getAI()).getRouter();
                router.receive(movement);
            }
        }
    }

    private  void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

}
