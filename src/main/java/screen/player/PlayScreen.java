/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package screen.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import creature.*;
import asciiPanel.AsciiPanel;
import messages.Messages;
import screen.Screen;
import serializer.CreatureDeserializer;
import serializer.CreatureSerializer;
import serializer.PlayerDeserializer;
import serializer.PlayerSerializer;
import world.*;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen {
    public final static int DIM = 49;
    private World world;
    private Player player;
    private int screenWidth;
    private int screenHeight;
    private Messages messages;
    private CreatureFactory creatureFactory = new CreatureFactory();

    public PlayScreen() {
        this.screenWidth = DIM;
        this.screenHeight = DIM;
        createWorld();
        createPlayer();
        world.register(player);
        createCreatures();
        messages = new Messages(DIM,0);
        player.start();
        Thread listenThread = new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(18848);
                Socket socket = serverSocket.accept();
                while(true) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String read = in.readLine();
                    while (read != null) {
                        if(read.equals("creatures")){
                            out.println("creaturesinfo");
                            out.flush();
                            System.out.println("creaturesinfo");
                        }
                        read = in.readLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listenThread.start();
    }

    public PlayScreen(String url) {
        this.screenWidth = DIM;
        this.screenHeight = DIM;
        resumeWorld();
        resumePlayer();
        resumeCreatures();
        messages = new Messages(DIM,0);
        player.start();
    }

    private void createPlayer() {
        this.player = Player.getPlayer();
        player.setWorld(world);
        new PlayerAI(player);
    }

    private void savePlayer() throws IOException {
        PlayerSerializer playerSerializer = new PlayerSerializer(Player.class);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("PlayerSerializer");
        module.addSerializer(playerSerializer);
        objectMapper.registerModule(module);
        objectMapper.writeValue(
                new FileOutputStream("src/main/resources/player.json"),player);

    }

    private void resumePlayer(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("PlayerDeserializer");
        module.addDeserializer(Player.class,new PlayerDeserializer(Player.class));
        objectMapper.registerModule(module);
        try {
            File file = new File("src/main/resources/player.json");
            player = objectMapper.readValue(file, Player.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        world.register(player);
        player.setWorld(world);
        new PlayerAI(player);
    }

    private void saveCreatures() throws IOException {
        CreatureSerializer creatureSerializer = new CreatureSerializer(Creature.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Creature> temp = world.getCreatures();
        SimpleModule module = new SimpleModule("CreatureSerializer");
        module.addSerializer(creatureSerializer);
        objectMapper.registerModule(module);
        temp.remove(0);
        objectMapper.writeValue(
                new FileOutputStream("src/main/resources/creatures.json"),temp);
        temp.add(0,player);
        world.unlockWorld();
    }

    private void resumeCreatures(){
        List<Creature> temp = null;
        SimpleModule module = new SimpleModule("CreatureDeserializer");
        module.addDeserializer(Creature.class,new CreatureDeserializer(Creature.class));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        try {
            File file = new File("src/main/resources/creatures.json");
            temp = objectMapper.readValue(file, new TypeReference<List<Creature>>(){});
            System.out.println(temp.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < temp.size(); i++){
            Creature c = temp.get(i);
            if(c.getTitle().equals("Coin")){
                new CoinAI(c);
                c.setWorld(world);
                world.register(c);
                c.start();
            }
            else if(c.getTitle().equals("Power")){
                new CreatureAI(c);
                c.setWorld(world);
                world.register(c);
                c.start();
            }
            else if(c.getTitle().equals("Blinky")){
                new BlinkyAI(c);
                c.setWorld(world);
                world.register(c);
                c.start();
            }
            else if(c.getTitle().equals("Pinky")){
                new PinkyAI(c);
                c.setWorld(world);
                world.register(c);
                c.start();
            }
            else if(c.getTitle().equals("Clyde")){
                new ClydeAI(c);
                c.setWorld(world);
                world.register(c);
                c.start();
            }
            else if(c.getTitle().equals("Inky")) {
                new InkyAI(c);
                c.setWorld(world);
                world.register(c);
                c.start();
            }
        }
    }


    private void createCreatures(){
        Creature c;
        for(int i = 0; i < target(); i++){
            c = creatureFactory.newCoin();
            c.setWorld(world);
            world.addAtEmptyLocation(c);
            c.start();

        }
        c = creatureFactory.newBlinky();
        c.setWorld(world);
        world.addAtEmptyLocation(c);
        c.start();
        c = creatureFactory.newPinky();
        c.setWorld(world);
        world.addAtEmptyLocation(c);
        c.start();
        c = creatureFactory.newClyde();
        c.setWorld(world);
        world.addAtEmptyLocation(c);
        c.start();
        c = creatureFactory.newInky();
        c.setWorld(world);
        world.addAtEmptyLocation(c);
        c.start();
        c = creatureFactory.newPower();
        c.setWorld(world);
        world.addAtEmptyLocation(c);
        c.start();

    }

    public static int target(){
        return 10;
    }

    private void createWorld() {
        world = new WorldBuilder(DIM).setTiles().makeCaves().build();

    }

    private void saveWorld() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(
                new FileOutputStream("src/main/resources/world.json"),world);
    }

    public void resumeWorld(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/world.json");
            world = objectMapper.readValue(file, World.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal);
        // Player
        terminal.write(player.getGlyph(), player.getX() , player.getY());
        // Messages
        messages.display(terminal);
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_F5:
                //TODO save some necessary info
                saveWorld();
                savePlayer();
                saveCreatures();

                break;
            case KeyEvent.VK_C:
                player.reverseCheat();
                messages.receiveCheatMessage();
                break;
            case KeyEvent.VK_LEFT:
                player.moveBy(-1, 0);
                player.setDirection(0);
                break;
            case KeyEvent.VK_RIGHT:
                player.moveBy(1, 0);
                player.setDirection(1);
                break;
            case KeyEvent.VK_UP:
                player.moveBy(0, -1);
                player.setDirection(2);
                break;
            case KeyEvent.VK_DOWN:
                player.moveBy(0, 1);
                player.setDirection(3);
                break;
        }
        return this;
    }

    public Screen nextFrame(){
        if(player.getHp() <= 0) {
            world.end();
            player.revive();
            return new LoseScreen();
        }
        else if(player.getCredits()>= target() && player.readyForNextLevel()) {
            world.end();
            player.revive();
            return new WinScreen();
        }
        else
            return this;
    }

}
