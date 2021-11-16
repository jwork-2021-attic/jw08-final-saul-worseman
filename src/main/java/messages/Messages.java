package messages;
import asciiPanel.AsciiPanel;
import world.*;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Messages {
    int srcX;
    int srcY;
    List<String> messages;
    int index;
    public Messages(int srcX, int srcY, int index){
        this.index = 0;
        this.srcX = srcX;
        this.srcY = srcY;
        messages = new ArrayList<>(index);
    }

    public synchronized void display(AsciiPanel terminal){
        //System.out.println(Player.getPlayer().hp());
        terminal.write(String.format(" hp  :%3d", Player.getPlayer().hp()),srcX,srcY);
        terminal.write(String.format("score:%3d", Player.getPlayer().getCredits()),srcX,srcY + 1);
    }



}
