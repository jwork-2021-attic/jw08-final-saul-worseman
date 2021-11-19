package world;

import java.util.concurrent.TimeUnit;

public class GodThread extends Thread {
    private World world;
    private CreatureFactory factory;
    public GodThread(World world){
        this.world = world;
        factory = new CreatureFactory();
    }
    int count = 0;

    public void run(){
        while(true){
            try {
                count++;
                TimeUnit.MILLISECONDS.sleep(1000);
                Creature c = factory.newFungus();
                if(count  % 5 == 0){
                    c = factory.newHunter();
                }
                c.setWorld(world);
                world.addAtEmptyLocation(c);
                world.randomBecomeLava();
                //world.routeAll();
                c.start();
                world.updateAll();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
