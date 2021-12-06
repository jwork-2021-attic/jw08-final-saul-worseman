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
public class CreatureFactory {


    public CreatureFactory() {

    }

    public Creature newCoin(){
        Creature coin = new Creature(1, (char) 144,1,0);
        new CoinAI(coin);
        return coin;
    }


    public Creature newBlinky(){
        Creature blinky= new Creature(200, (char)186, 200,2);
        new BlinkyAI(blinky);
        return blinky;
    }

    public Creature newPinky(){
        Creature pinky= new Creature(200, (char)166, 200,2);
        new PinkyAI(pinky);
        return pinky;
    }

    public Creature newClyde(){
        Creature clyde= new Creature(200, (char)182, 200,2);
        new ClydeAI(clyde);
        return clyde;
    }
}
