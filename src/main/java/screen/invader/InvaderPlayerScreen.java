package screen.invader;

import asciiPanel.AsciiPanel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import creature.Creature;
import creature.Player;
import messages.Messages;
import screen.Screen;
import serializer.CreatureDeserializer;
import serializer.PlayerDeserializer;
import server.NIOClient;
import world.World;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class InvaderPlayerScreen implements Screen {
    public final static int DIM = 49;
    private World world;
    private Player player;
    private int screenWidth;
    private int screenHeight;
    private Messages messages;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    ObjectMapper objectMapper;
    NIOClient client;
    String role;
    public InvaderPlayerScreen(String role) throws IOException {
        this.role = role;
        registerRole();
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("Deserializer");
        module.addDeserializer(Player.class,new PlayerDeserializer(Player.class));
        module.addDeserializer(Creature.class,new CreatureDeserializer(Creature.class));
        objectMapper.registerModule(module);
        socket = new Socket("127.0.0.1",18848);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.screenWidth = DIM;
        this.screenHeight = DIM;
        loadWorld();
        messages = new Messages(DIM,0);
        client = NIOClient.start();
    }

    private void registerRole(){
        out.println("role");
        out.println(role);
    }

    private void loadCreatures() throws IOException {
        out.println("creatures");
        out.flush();
        String creaturesInfo = null;
        creaturesInfo = in.readLine();
        ArrayList<Creature> temp = objectMapper.readValue(creaturesInfo,new TypeReference<List<Creature>>(){});
        world.setCreatures(temp);
    }

    private void loadPlayer() throws IOException {
        out.println("player");
        out.flush();
        String playerInfo = null;
        playerInfo = in.readLine();
        player = objectMapper.readValue(playerInfo,Player.class);
        world.register(player);
        player.setWorld(world);
    }

    private void loadWorld() throws IOException {
        out.println("world");
        out.flush();
        String worldInfo = null;
        worldInfo = in.readLine();
        //System.out.println(worldInfo);
        world = objectMapper.readValue(worldInfo, World.class);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        try {
            loadCreatures();
            loadPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // Terrain and creatures
        displayTiles(terminal);
//        // Player
        terminal.write(player.getGlyph(), player.getX() , player.getY());
//        // Messages
        messages.display(terminal);
    }

    private void displayTiles(AsciiPanel terminal) {
        // Show terrain
        for (int x = 0; x < DIM; x++) {
            for (int y = 0; y < DIM; y++) {
                int wx = x;
                int wy = y;
                //Take care! The x, y here is not that matrix's width or height;
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
        List<Creature> creatures = world.getCreatures();
        for (Creature creature : creatures){
            terminal.write(creature.getGlyph(), creature.getX(), creature.getY());
        }
        world.unlockWorld();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                client.sendMessage("left");
                break;
            case KeyEvent.VK_RIGHT:
                client.sendMessage("right");
                break;
            case KeyEvent.VK_UP:
                client.sendMessage("up");
                break;
            case KeyEvent.VK_DOWN:
                client.sendMessage("down");
                break;
        }
        return this;
    }

    @Override
    public Screen nextFrame() {
        return this;
    }
}
