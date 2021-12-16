package screen.invader;

import asciiPanel.AsciiPanel;
import screen.Screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class InvaderWinScreen implements Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You win!",0,0);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        return this;
    }

    @Override
    public Screen nextFrame() {
        return this;
    }
}
