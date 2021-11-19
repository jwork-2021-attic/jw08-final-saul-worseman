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
package world;

import screen.Screen;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Aeranythe Echosong
 */
public class Creature extends Thread{

    protected int Hp;
    protected int maxHp;
    protected World world;
    private int attackValue;
    protected static Lock lock = new ReentrantLock();

    private int credits;
    private int x;

    public int hp(){
        return Hp;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }

    private int y;



    public void setY(int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }

    private Color color;

    public Color color() {
        return this.color;
    }

    public void setColor(Color color){this.color = color;}

    private CreatureAI ai;

    public void setAttackValue(int val){this.attackValue = val;}

    public int getAttackValue(){return attackValue;}

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    public void moveBy(int mx, int my) {
        try{
            lock.lock();
            Creature other = world.creature(x + mx, y + my);
            if (other == null) {
                ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
                }
            else {
                attack(other);
                }
            }finally{
            lock.unlock();
        }
    }

    public int getCredits(){
        return credits;
    }

    public void setHp(int newHp){
        Hp = newHp;
    }

    public void earnCredits(Creature c){
        this.credits += c.getCredits();
    }

    public void attack(Creature other) {
        this.Hp -= other.attackValue;
        other.Hp -= this.attackValue;
        System.out.println(this.hp() + " " + other.hp());
        if(this.Hp <= 0){
            other.earnCredits(this);
        }
        else if(other.Hp <= 0){
            this.earnCredits(other);
        }
        world.updateAll();
    }


    public void update() {
        this.ai.onUpdate();
    }

    public boolean isDead(){
        return Hp <= 0;
    }

    public Creature(int maxHp,int attackValue,char glyph, Color color,int credits) {
        this.Hp = maxHp;
        this.maxHp = maxHp;
        this.glyph = glyph;
        this.color = color;
        this.credits = credits;
        this.attackValue = attackValue;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void route(){
        ai.route();
    }

    public void reset(){
        credits = 0;
        Hp = maxHp;
    }
    public void revive(){
        ai.revive();
    }

    public void run(){
        while(!isDead()){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                try{
                    lock.lock();
                    if(!isDead())
                        route();
                    else
                        break;
                }finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
