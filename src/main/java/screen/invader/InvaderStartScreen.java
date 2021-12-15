package screen.invader;

import asciiPanel.AsciiPanel;
import screen.Screen;
import screen.player.PlayScreen;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class InvaderStartScreen implements Screen {
    Socket socket;

    {
        try {
            socket = new Socket("127.0.0.1",18848);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Hello ,invader", 0, 0);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) throws IOException {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_C:
                loadCreatures();
                break;
            case KeyEvent.VK_ENTER:
                return new InvaderPlayerScreen();
            default:
                return this;
        }
        return this;
    }

    @Override
    public Screen nextFrame() {
        return this;
    }

    private void loadCreatures() throws IOException {
        System.out.println(1);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("creatures");
        out.flush();
        String creatures = null;

        creatures = in.readLine();
        System.out.println(creatures);
    }
}
