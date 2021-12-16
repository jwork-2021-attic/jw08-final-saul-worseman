package screen.invader;

import asciiPanel.AsciiPanel;
import screen.Screen;
import screen.player.PlayScreen;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class InvaderStartScreen implements Screen {
    private String role;

    InvaderStartScreen(String role){
        this.role = role;
    }


    public InvaderStartScreen() throws IOException {

    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Hello,invader", 0, 0);
        terminal.write("You choose to play the role of" + role,0,1);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new InvaderPlayerScreen(role);
            default:
                return this;
        }
    }

    @Override
    public Screen nextFrame() {
        return this;
    }


}
