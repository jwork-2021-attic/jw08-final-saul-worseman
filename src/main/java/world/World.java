package world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import creature.*;
import serializer.CreatureDeserializer;
import serializer.CreatureSerializer;
import serializer.PlayerDeserializer;
import serializer.PlayerSerializer;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class World {

    private Tile[][] tiles;
    private int width;
    private int height;
    @JsonIgnore
    private List<Creature> creatures;
    private Lock lock = new ReentrantLock();
    private ObjectMapper objectMapper = new ObjectMapper();
    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        creatures = new ArrayList<>();
    }

    public void setCreatures(ArrayList<Creature> temp){this.creatures = temp;}

    public World(){
        creatures = new ArrayList<>();
    }

    public Tile[][] getTiles(){return tiles;}

    public void setTiles(Tile[][] tiles){
        this.tiles = tiles;
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char glyph(int x, int y) {
        return tiles[x][y].getGlyph();
    }

    public Color color(int x, int y) {
        return tiles[x][y].color();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width){this.width = width;}

    public void setHeight(int height){this.height = height;}

    public void setTile(int x, int y, Tile tile){
        tiles[x][y] = tile;
    }

    public synchronized void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (!tile(x, y).isGround() || creature(x, y) != null);

        creature.setX(x);
        creature.setY(y);
        this.creatures.add(creature);
    }

    public Creature creature(int x, int y){
        synchronized (this) {
           for(int i = 0; i < creatures.size(); i++){
               Creature c = creatures.get(i);
               if(c.getX() == x && c.getY() == y)
                   return  creatures.get(i);
           }

            return null;
        }
    }

    public synchronized void updateAll(){
        List<Creature> temp = new ArrayList<>();
        for(Creature c:creatures){
            if(!c.isDead())
                temp.add(c);
        }
        creatures = temp;
    }

    public void register(Creature p){
        creatures.add(p);
    }

    public List<Creature> getCreatures(){
        lock.lock();
        return creatures;
    }

    public void unlockWorld(){
        lock.unlock();
    }

    public void end(){
        //lock.lock();
        for(Creature c: creatures){
            c.setHp(0);
        }
        //lock.unlock();
    }

    public String saveCreaturesAsString() throws JsonProcessingException {
        CreatureSerializer creatureSerializer = new CreatureSerializer(Creature.class);
        List<Creature> temp = this.getCreatures();
        SimpleModule module = new SimpleModule("CreatureSerializer");
        module.addSerializer(creatureSerializer);
        objectMapper.registerModule(module);
        String res = objectMapper.writeValueAsString(temp);
        unlockWorld();
        return res;
    }



    public void resumeCreaturesfromString(String info) throws IOException {
        List<Creature> temp = null;
        SimpleModule module = new SimpleModule("CreatureDeserializer");
        module.addDeserializer(Creature.class,new CreatureDeserializer(Creature.class));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        temp = objectMapper.readValue(info, new TypeReference<List<Creature>>(){});
        for(int i = 0; i < temp.size(); i++) {
            Creature c = temp.get(i);
            if (c.getTitle().equals("Coin")) {
                new CoinAI(c);
            } else if (c.getTitle().equals("Power")) {
                new CreatureAI(c);
            } else if (c.getTitle().equals("Blinky")) {
                new BlinkyAI(c);
            } else if (c.getTitle().equals("Pinky")) {
                new PinkyAI(c);
            } else if (c.getTitle().equals("Clyde")) {
                new ClydeAI(c);
            } else if (c.getTitle().equals("Inky")) {
                new InkyAI(c);
            }
            c.setWorld(this);
            this.register(c);
            c.start();
        }
    }

    public String saveWorldAsString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("1"+ objectMapper.writeValueAsString(this));
        return objectMapper.writeValueAsString(this);
    }


    public String savePlayerAsString() throws JsonProcessingException {
        Player player = Player.getPlayer();
        PlayerSerializer playerSerializer = new PlayerSerializer(Player.class);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("PlayerSerializer");
        module.addSerializer(playerSerializer);
        objectMapper.registerModule(module);
        return objectMapper.writeValueAsString(player);
    }

    public void resumePlayerfromString(String info) throws IOException {
        Player player = Player.getPlayer();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("PlayerDeserializer");
        module.addDeserializer(Player.class,new PlayerDeserializer(Player.class));
        objectMapper.registerModule(module);
        player = objectMapper.readValue(info, Player.class);
        this.register(player);
        player.setWorld(this);
        new PlayerAI(player);
    }


    public static void main(String[] args) throws IOException {
//        WorldBuilder worldBuilder = new WorldBuilder(49);
//        World world = worldBuilder.setTiles().build();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValue(
//                new OutputStreamWriter(new FileOutputStream("src/main/resources/world.json"),"UTF-8"),world);
//        objectMapper.writeValueAsString(world);
//        
//        try {
//           // BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\test.txt"),"UTF-8"))
//            InputStreamReader reader = (new InputStreamReader(new FileInputStream("src/main/resources/world.json"),"UTF-8"));
//            //s = objectMapper.writeValueAsString(world);
//            //System.out.println(s);
//            world = objectMapper.readValue(reader, World.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
