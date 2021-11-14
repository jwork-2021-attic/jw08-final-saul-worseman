package world;

import java.util.concurrent.TimeUnit;

public class GodThread extends Thread {
    private int i = 10;
    private World world;
    private CreatureFactory factory;
    public GodThread(World world){
        this.world = world;
        factory = new CreatureFactory();
    }

    public void run(){
        while(i > 0){
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                Creature c = factory.newFungus();
                world.addAtEmptyLocation(c);
                System.out.println(i);
                i--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
