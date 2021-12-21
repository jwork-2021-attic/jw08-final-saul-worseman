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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import serializer.PlayerDeserializer;
import serializer.PlayerSerializer;
import world.Tile;
import world.World;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Player extends Creature {

    private static Player player = new Player();
    private boolean cheat;
    private int immortalSteps;
    private Player(){
        super(3, (char)224, 0,"Player");
        setX(1);
        setY(1);
        cheat = false;
        immortalSteps = 0;
    }
    private boolean ready = false;


    public static Player getPlayer(){
        return player;
    }

    public void reverseCheat(){
        cheat = !cheat;
    }

    public boolean getCheat(){
        return cheat;
    }

    public void setCheat(boolean cheat){
        this.cheat = cheat;
    }

    public void setImmortalSteps(int steps){immortalSteps = steps;}

    public int getImmortalSteps(){return immortalSteps;}

    public int getHp(){
        if(cheat == true)
            return 3;
        return super.getHp();
    }

    public int getCredits(){
        return super.getCredits();
    }

    public boolean readyForNextLevel(){
        return ready;
    }

    public void shuttle(){
        world.addAtEmptyLocation(this);
    }

    @Override
    public void run(){
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                route();
                if(world.tile(this.getX(), this.getY()) == Tile.DOOR)
                    ready = true;
                else
                    ready = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
