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

/**
 *
 * @author Aeranythe Echosong
 */
public class CreatureFactory {


    public CreatureFactory() {

    }

    public Creature newCoin(){
        Creature coin = new Creature(1, (char) 144,1,"Coin");
        new CoinAI(coin);
        return coin;
    }


    public Creature newBlinky(){
        Creature blinky = new Creature(1, (char)186, 200,"Blinky");
        new BlinkyAI(blinky);
        return blinky;
    }

    public Creature newPinky(){
        Creature pinky = new Creature(1, (char)166, 200,"Pinky");
        new PinkyAI(pinky);
        return pinky;
    }

    public Creature newClyde(){
        Creature clyde = new Creature(1, (char)182, 200,"Clyde");
        new ClydeAI(clyde);
        return clyde;
    }

    public Creature newInky(){
        Creature clyde = new Creature(1, (char)178, 200,"Inky");
        new InkyAI(clyde);
        return clyde;
    }

    public Creature newPower(){
        Creature power = new Creature(1, (char)152, 0,"Power");
        new CreatureAI(power);
        return power;
    }
}
