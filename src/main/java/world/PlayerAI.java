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

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayerAI extends CreatureAI {

    private boolean[][] memory;
    public PlayerAI(Creature creature) {
        super(creature);
        memory = new boolean[creature.world.width()][creature.world.height()];
        memory[1][1] = true;
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
            creature.world.setTile(x,y, Tile.VISITED) ;
        }
    }

    public void route(){
        int[][] directions = {{0, 1},{0, -1},{1, 0},{-1, 0}};
        for(int i = 0; i < 4; i++){
            int tempX = creature.x() + directions[i][0];
            int tempY = creature.y() + directions[i][1];
            if(creature.world.tile(tempX, tempY) == Tile.PATH && !memory[tempX][tempY]){
                onEnter(tempX, tempY, creature.world.tile(tempX, tempY));
                memory[tempX][tempY] = true;
                break;
            }
        }
    }
}
