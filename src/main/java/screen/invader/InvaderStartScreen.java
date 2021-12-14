package screen;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class InvaderStartScreen implements Screen{
    @Override
    public void displayOutput(AsciiPanel terminal) {

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        return null;
    }

    @Override
    public Screen nextFrame() {
        return null;
    }
}
