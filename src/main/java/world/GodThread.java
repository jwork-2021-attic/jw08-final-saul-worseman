package world;

import java.util.concurrent.TimeUnit;

public class GodThread extends Thread {
    private World world;
    private CreatureFactory factory;
    public GodThread(World world){
        this.world = world;
        factory = new CreatureFactory();
    }

    public void run(){
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                Creature c = factory.newFungus();
                c.setWorld(world);
                world.addAtEmptyLocation(c);
                world.routeAll();
                world.updateAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
