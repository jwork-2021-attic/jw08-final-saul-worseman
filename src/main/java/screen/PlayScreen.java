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
import world.*;

import java.awt.event.KeyEvent;

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
    public PlayScreen() {
        this.screenWidth = DIM;
        this.screenHeight = DIM;
        createWorld();
        createPlayer();
        god = new GodThread(this.world);
        god.start();
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
        for (Creature creature : world.getCreatures()){
            terminal.write(creature.glyph(), creature.x(), creature.y(), creature.color());
        }
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal);
        // Player
        terminal.write(player.glyph(), player.x() , player.y() , player.color());
        // Messages
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

}
