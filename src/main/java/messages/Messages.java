package messages;
import creature.Player;
import asciiPanel.AsciiPanel;
import screen.PlayScreen;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    int srcX;
    int srcY;
    int index;
    int count = 0;
    private int reserved = 5;
    private AsciiPanel terminal;
    private List<String> otherInfo;
    public Messages(int srcX, int srcY){
        this.index = reserved;
        this.srcX = srcX;
        this.srcY = srcY;
        this.otherInfo = new ArrayList<>();
    }

    public void writeAdapter(){
        for(int i = 0; i < otherInfo.size();i++) {
            terminal.write(otherInfo.get(i), srcX, srcY + index);
            index++;
        }
        index = reserved;
    }

    public void addAdapter(String s){
        if(otherInfo.size() > 40)
            otherInfo.clear();
        otherInfo.add(s);
    }



    public synchronized void display(AsciiPanel terminal){
        // lazy
        this.terminal = terminal;
        //System.out.println(Player.getPlayer().hp());
        terminal.write(String.format(" hp  :%3d", Player.getPlayer().getHp()),srcX,srcY);
        terminal.write(String.format("score:%3d", Player.getPlayer().getCredits()),srcX,srcY + 1);
        terminal.write(String.format("goal :%3d", PlayScreen.target()),srcX,srcY + 2);
        terminal.write("Press C to cheat ",srcX,srcY + 3);
        terminal.write("Press F5 to save ",srcX,srcY + 4);
        writeAdapter();
    }

    public void receiveCheatMessage(){
        if(count % 2 == 0)
            addAdapter("cheat mode");
        else
            addAdapter("normal mode");
        count ++;
    }



}
