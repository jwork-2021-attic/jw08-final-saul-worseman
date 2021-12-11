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
package screen;

import creature.Creature;
import asciiPanel.AsciiPanel;
import creature.CreatureFactory;
import creature.Player;
import creature.PlayerAI;
import messages.Messages;
import world.*;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen{
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
        createCreatures();
        world.registerPlayer(player);
        messages = new Messages(DIM,0);
        player.start();
    }

    public PlayScreen(String url) {
        this.screenWidth = DIM;
        this.screenHeight = DIM;
    }



    private void createPlayer() {
        this.player = Player.getPlayer();
        player.setWorld(world);
        new PlayerAI(player);
    }

    private void createCreatures(){
        Creature c;
        for(int i = 0; i < target(); i++){
            c = creatureFactory.newCoin();
            c.setWorld(world);
            world.addAtEmptyLocation(c);
            c.start();
            world.registerPlayer(player);

        }
        c = creatureFactory.newBlinky();
        c.setWorld(world);
        world.addAtEmptyLocation(c);
        c.start();
        world.registerPlayer(player);
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
        world.registerPlayer(player);

    }

    public static int target(){
        return 10;
    }

    private void createWorld() {
        world = new WorldBuilder(DIM).setTiles().makeCaves().build();
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
            terminal.write(creature.glyph(), creature.x(), creature.y());
        }
        world.unlockWorld();
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal);
        // Player
        terminal.write(player.glyph(), player.x() , player.y());
        // Messages
        messages.display(terminal);
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_F5:
                //TODO save some necessary info
                break;
            case KeyEvent.VK_C:
                player.setCheat();
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
        if(player.hp() <= 0) {
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
