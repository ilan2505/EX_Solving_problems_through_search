import java.util.HashSet;


public class DFID {

    private State startState;
    private Point goal;
    private char[][] board;
    private int[][] nodesCreatingOrder;
    private boolean withTime;
    private boolean withOpen;
    private int num;
    private String result;
    long startTimer;

    public DFID(State startState, Point goal, char[][] board, int[][] nodesCreatingOrder, boolean withTime, boolean withOpen) {

        this.startState = startState;
        this.goal = goal;
        this.board = board;
        this.nodesCreatingOrder = nodesCreatingOrder;
        this.withTime = withTime;
        this.withOpen = withOpen;
        search();
    }

    public void search() {

        startTimer = System.currentTimeMillis();
        num = 1;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            HashSet<String> H = new HashSet<>();
            result = limitedDFS(startState, goal, i, H);
            if (!result.equals("cutoff")) {
                return;
            }
        }
    }

    private String limitedDFS(State current, Point goal, int limit, HashSet<String> H) {

        if (withOpen) {
            System.out.println(current.toString());
        }

        if (current.getCarPosition().equals(goal)) {
            Output.createOutputFile(current.getPath(), num, current.getCost() + "", System.currentTimeMillis() - startTimer, withTime);
            result = "goal";
            return result;
        }
        else if (limit == 0) {
            return "cutoff";
        }
        else {
            H.add(current.getCarPosition().toString());
            boolean isCutoff = false;

            for (int[] move : nodesCreatingOrder) {
                State g = Operator.generator(current, board, board.length, move);

                if (g != null) {
                    num++;
                    if (!H.contains(g.getCarPosition().toString())) {
                        result = limitedDFS(g, goal, limit - 1, H);
                        if (result.equals("cutoff")) {
                            isCutoff = true;
                        }
                        else if (!result.equals("fail")) {
                            return result;
                        }
                    }
                }
            }

            H.remove(current.getCarPosition().toString());

            if (isCutoff) {
                return "cutoff";
            }
            else {
                return "fail";
            }
        }
    }
}