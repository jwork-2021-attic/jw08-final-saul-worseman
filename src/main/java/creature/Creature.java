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
package creature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import world.World;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Aeranythe Echosong
 */
public class Creature extends Thread{

    protected String title;
    protected int Hp;
    protected int maxHp;
    @JsonIgnore
    protected World world;
    private int credits;
    private int x;
    private int y;
    private char glyph;
    @JsonIgnore
    private CreatureAI ai;
    @JsonIgnore
    protected static Lock lock = new ReentrantLock();

    public Creature(){}


    public int getMaxHp(){return maxHp;}

    public void setMaxHp(int maxHp){this.maxHp = maxHp;}

    public int getHp(){
        return Hp;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }


    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }



    public char getGlyph() {
        return this.glyph;
    }

    public void setGlyph(char glyph){
        this.glyph = glyph;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public World getWorld(){
        return world;
    }

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    public CreatureAI getAI(){
        return this.ai;
    }

    public void moveBy(int mx, int my) {
        try{
            lock.lock();
            Creature other = world.creature(x + mx, y + my);
            if (other == null||((other.getTitle().equals("Coin") || other.getTitle().equals("Power"))&& !(this instanceof Player))) {
                ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
                }
            else if(other instanceof Player || this instanceof Player){
                attack(other);
                other.attack(this);
                }
            }finally{
            lock.unlock();
        }
    }

    public int getCredits(){
        return credits;
    }

    public void setCredits(int credits){this.credits = credits;}

    public void setHp(int newHp){
        Hp = newHp;
    }

    public void earnCredits(Creature c){
        this.credits += c.getCredits();
    }

    public void attack(Creature other) {
        ai.attack(other);
    }

    public boolean isDead(){
        return Hp <= 0;
    }

    public Creature(int maxHp, char glyph, int credits,String title) {
        this.title = title;
        this.Hp = maxHp;
        this.maxHp = maxHp;
        this.glyph = glyph;
        this.credits = credits;
    }


    public void setWorld(World world) {
        this.world = world;
    }

    public void route(){
        ai.route();
    }



    public void setDirection(int dir){
        ai.setDirection(dir);
    }

    public int getDirection(){
        return ai.getDirection();
    }

    public void run(){
        while(!isDead()){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                try{
                    //lock.lock();
                    if(!isDead())
                        route();
                    else
                        break;
                }finally {
                    //lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}
