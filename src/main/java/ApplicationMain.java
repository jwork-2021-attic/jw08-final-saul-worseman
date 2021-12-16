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

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screen.Screen;
import screen.invader.InvaderPlayerScreen;
import screen.invader.InvaderStartScreen;
import screen.player.StartScreen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Aeranythe Echosong
 */
public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain() {
        super();
        terminal = new AsciiPanel(66, 49, AsciiFont.img2);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
        Thread refreshThread = new Thread(()->{
            while(true){
                try {
                    screen = screen.nextFrame();
                    TimeUnit.MILLISECONDS.sleep(50);
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        refreshThread.start();
    }

    public ApplicationMain(String role){
        super();
        terminal = new AsciiPanel(66, 49, AsciiFont.img2);
        add(terminal);
        pack();
        screen = new InvaderStartScreen(role);
        addKeyListener(this);
        repaint();
        Thread refreshThread = new Thread(()->{
            while(true){
                try {
                    screen = screen.nextFrame();
                    TimeUnit.MILLISECONDS.sleep(50);
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        refreshThread.start();
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    /**
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
            try {
                screen = screen.respondToUserInput(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            repaint();
    }

    /**
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 0){
            ApplicationMain app = new ApplicationMain(args[0]);
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        }
        else {
            ApplicationMain app = new ApplicationMain();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        }
    }

}
