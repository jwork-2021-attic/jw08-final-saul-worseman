package router;

public class InvaderControlRouter implements Router{
    String cmd = "stay";
    private int[][] directions = new int[][]{{0,0},{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    @Override
    public synchronized int[] nextSteps() {
        var res = directions[0];
        if(cmd.equals("stay"))
            res = directions[0];
        else if(cmd.equals("left")){
            res = directions[1];
        }
        else if(cmd.equals("right")) {
            res = directions[2];
        }
        else if(cmd.equals("down")){
            res = directions[3];
        }
        else {
            res = directions[4];
        }
        cmd = "stay";
        System.out.println(cmd);
        return res;
    }

    public void receive(String cmd){
        this.cmd = cmd;
    }
}
