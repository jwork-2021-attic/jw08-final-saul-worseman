package world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import creature.Creature;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
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
    @JsonIgnore
    private List<Creature> creatures;
   //private Lock lock = new ReentrantLock();

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        creatures = new ArrayList<>();
    }


    public World(){
        creatures = new ArrayList<>();
    }

    public Tile[][] getTiles(){return tiles;}

    public void setTiles(Tile[][] tiles){
        this.tiles = tiles;
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char glyph(int x, int y) {
        return tiles[x][y].getGlyph();
    }

    public Color color(int x, int y) {
        return tiles[x][y].color();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width){this.width = width;}

    public void setHeight(int height){this.height = height;}

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
    }

    public Creature creature(int x, int y){
        synchronized (this) {
           for(int i = 0; i < creatures.size(); i++){
               Creature c = creatures.get(i);
               if(c.getX() == x && c.getY() == y)
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

    public void register(Creature p){
        creatures.add(p);
    }

    public List<Creature> getCreatures(){
        //lock.lock();
        return creatures;
    }

    public void unlockWorld(){
        //lock.unlock();
    }
    
    


    public void end(){
        //lock.lock();
        for(Creature c: creatures){
            c.setHp(0);
        }
        //lock.unlock();
    }

    public static void main(String[] args) throws IOException {
//        WorldBuilder worldBuilder = new WorldBuilder(49);
//        World world = worldBuilder.setTiles().build();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValue(
//                new OutputStreamWriter(new FileOutputStream("src/main/resources/world.json"),"UTF-8"),world);
//        objectMapper.writeValueAsString(world);
//        //String s = new String("{\"tiles\":[[\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"WALL\",\"WALL\",\"PATH\",\"WALL\"],[\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"WALL\",\"PATH\",\"PATH\",\"PATH\",\"WALL\",\"DOOR\",\"WALL\"],[\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\",\"WALL\"]],\"width\":49,\"height\":49}");
//        try {
//           // BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\test.txt"),"UTF-8"))
//            InputStreamReader reader = (new InputStreamReader(new FileInputStream("src/main/resources/world.json"),"UTF-8"));
//            //s = objectMapper.writeValueAsString(world);
//            //System.out.println(s);
//            world = objectMapper.readValue(reader, World.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
