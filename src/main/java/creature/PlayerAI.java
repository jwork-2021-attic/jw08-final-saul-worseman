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

import world.Tile;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayerAI extends CreatureAI {


    private final char[] up = new char[]{240,241,242,243};
    private final char[] left = new char[]{192,193,194,195};
    private final char[] right = new char[]{208,209,210,211};
    private final char[] down = new char[]{224,225,226,227};
    private int index;
    private int dir;
    public PlayerAI(Creature creature) {
        super(creature);
        dir = 0;
        index = 0;
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
        }
    }

    public void route(){
        if(Player.getPlayer().getImmortalSteps() > 0) {
            Player.getPlayer().setImmortalSteps(Player.getPlayer().getImmortalSteps() - 1);
        }
        index = (index + 1) % up.length;
        if(dir == 0){
            creature.setGlyph(left[index]);
       }
        else if(dir == 1){
            creature.setGlyph(right[index]);
        }
        else if(dir == 2){
            creature.setGlyph(up[index]);
        }
        else{
            creature.setGlyph(down[index]);
        }
    }

    @Override
    public void setDirection(int dir){
        if(this.dir == dir)
            return;
        else{
            this.dir = dir;
            index = 0;
        }
    }

    public int getDirection(){
        return dir;
    }


    public void attack(Creature c){
        if(Player.getPlayer().getImmortalSteps() > 0){
            c.setHp(c.getHp() - 1);
        }
        else if(c.getTitle().equals("Coin"))
            c.setHp(c.getHp() - 1);
        else if(c.getTitle().equals("Power")){
            c.setHp(c.getHp() - 1);
            Player.getPlayer().setImmortalSteps(100);
        }
        if(c.isDead()) {
            creature.earnCredits(c);
            creature.world.updateAll();
        }
    }

}
