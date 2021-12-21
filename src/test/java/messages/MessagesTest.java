package messages;

import creature.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessagesTest {

    static Messages messages = new Messages(0,0);
    @Test
    void writeAdapter() {
        messages.addAdapter("123");
        assertEquals(1,messages.getOtherInfo().size());
    }



    @Test
    void receiveCheatMessage() {
        if(Player.getPlayer().getCheat()) {
            messages.receiveCheatMessage();
            messages.getOtherInfo().get(1).equals("cheat mode");
        }
    }
}