package world;

import asciiPanel.AsciiPanel;

import java.awt.*;
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
public enum Tile {

    WALL((char) 251 ),

    BOUNDS('x'),

    PATH((char) 202),

    DOOR((char) 154),

    LAVA((char) 178);

    private char glyph;


    public char getGlyph() {
        return glyph;
    }

    public void setGlyph(char glyph){this.glyph = glyph;}

    private Color color;

    public Color color() {
        return color;
    }

    public boolean isGround() {
        return this != Tile.WALL && this != Tile.BOUNDS;
    }

    public boolean isDoor(){return this == Tile.DOOR;}

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    Tile(char glyph) {
        this.glyph = glyph;
    }


}
