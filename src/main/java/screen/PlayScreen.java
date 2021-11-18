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

import asciiPanel.AsciiPanel;
import messages.Messages;
import world.*;

import java.awt.event.KeyEvent;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen {
    public final static int DIM = 49;
    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private GodThread god;
    private Messages messages;

    public PlayScreen() {

        this.screenWidth = DIM;
        this.screenHeight = DIM;
        createWorld();
        createPlayer();
        world.registerPlayer(player);
        messages = new Messages(DIM,0,30);
        god = new GodThread(this.world);
        god.start();
        player.start();
    }

    private void createPlayer() {
        this.player = Player.getPlayer();
        player.setWorld(world);
        new PlayerAI(player);

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
            terminal.write(creature.glyph(), creature.x(), creature.y(), creature.color());
        }
        world.unlockWorld();
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal);
        // Player
        terminal.write(player.glyph(), player.x() , player.y() , player.color());
        // Messages
        messages.display(terminal);
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player.moveBy(0, 1);
                break;
        }
        return this;
    }

    public Screen nextFrame(){
        if(player.hp() <= 0) {
            player.revive();
            return new LoseScreen();
        }
        else if(player.getCredits()>= 10) {
            player.revive();
            return new WinScreen();
        }
        else
            return this;
    }

}
