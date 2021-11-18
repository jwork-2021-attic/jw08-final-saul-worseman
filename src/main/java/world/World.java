package world;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

/**
 *
 * @author Aeranythe Echosong
 */
public class World {

    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Creature> creatures;
    private Lock lock = new ReentrantLock();

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        creatures = new ArrayList<>();
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char glyph(int x, int y) {
        return tiles[x][y].glyph();
    }

    public Color color(int x, int y) {
        return tiles[x][y].color();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void setTile(int x, int y, Tile tile){
        tiles[x][y] = tile;
    }

    public synchronized void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (!tile(x, y).isGround() || creature(x, y) != null);

        creature.setX(x);
        creature.setY(y);
        this.creatures.add(creature);
        lock.lock();
        lock.unlock();
    }

    public Creature creature(int x, int y){
        synchronized (this) {
           for(int i = 0; i < creatures.size(); i++){
               Creature c = creatures.get(i);
               if(c.x() == x && c.y() == y)
                   return  creatures.get(i);
           }

            return null;
        }
    }

    public synchronized void updateAll(){
        List<Creature> temp = new ArrayList<>();
        for(Creature c:creatures){
            if(!c.isDead())
                temp.add(c);
        }
        creatures = temp;
    }

    public void registerPlayer(Creature p){
        creatures.add(p);
    }

    public List<Creature> getCreatures(){
        lock.lock();
        return creatures;
    }

    public void unlockWorld(){
        lock.unlock();
    }


    public void randomBecomeLava(){
        int i = (int)(Math.random() * (width - 2)) + 2;
        int j = (int)(Math.random() * (width - 2)) + 2;
        if(!tile(i,j).isDoor() && tile(i,j).isGround())
            setTile(i,j,Tile.LAVA);
    }



}
