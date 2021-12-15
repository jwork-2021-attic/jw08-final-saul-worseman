package screen.invader;

import asciiPanel.AsciiPanel;
import creature.Creature;
import creature.Player;
import messages.Messages;
import screen.Screen;
import world.World;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class InvaderPlayerScreen implements Screen {
    public final static int DIM = 49;
    private World world;
    private Player player;
    private int screenWidth;
    private int screenHeight;
    private Messages messages;
    private Socket socket;
    public InvaderPlayerScreen(){
        this.screenWidth = DIM;
        this.screenHeight = DIM;
        try {
            loadCreatures();
        } catch (IOException e) {
            e.printStackTrace();
        }
        messages = new Messages(DIM,0);
    }

    private void loadCreatures() throws IOException {
//        socket = new Socket("127.0.0.1",18848);
//        OutputStream out = socket.getOutputStream();
//        out.write("creatures".getBytes());
//        out.flush();
//        out.close();
//        socket = new Socket("127.0.0.1",18848);
//        InputStream in = socket.getInputStream();
//        String creatures;
//        StringBuilder stringBuilder = new StringBuilder();
//        int read = in.read();
//        while(read != -1){
//            stringBuilder.append(read);
//            read = in.read();
//        }
//        creatures = stringBuilder.toString();
//        System.out.println(creatures);
    }

    private void loadPlayer() {
    }

    private void loadWorld() {
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal);
        // Player
        terminal.write(player.getGlyph(), player.getX() , player.getY());
        // Messages
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
        if(key.getKeyCode() == KeyEvent.VK_E){
            loadCreatures();
        }
        return this;
    }

    @Override
    public Screen nextFrame() {
        return null;
    }
}
