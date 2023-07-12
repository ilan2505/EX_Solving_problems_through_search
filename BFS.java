import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


public class BFS {

    private static HashSet<String> openList;
    private static HashSet<String> closedList;
    private Queue<State> L;
    private State startState;
    private Point goal;
    private char[][] board;
    private int[][] nodesCreatingOrder;
    private boolean withTime;
    private boolean withOpen;
    private int num;

    public BFS(State startState, Point goal, char[][] board, int[][] nodesCreatingOrder, boolean withTime, boolean withOpen) {

        this.startState = startState;
        this.goal = goal;
        this.board = board;
        this.nodesCreatingOrder = nodesCreatingOrder;
        this.withTime = withTime;
        this.withOpen = withOpen;
        openList = new HashSet<>();
        closedList = new HashSet<>();
        this.L = new LinkedList<>();
        search();
    }

    // BFS continues until a goal node is generated.
    // If a goal exists in the tree BFS will find a shortest path to a goal.
    private void search() {

        long startTimer = System.currentTimeMillis();
        num = 1;

        L.add(startState);
        openList.add(startState.getCarPosition().toString());

        while (!L.isEmpty()) {

            State n = L.poll();
            openList.remove(n.getCarPosition().toString());
            closedList.add(n.getCarPosition().toString());

            if (withOpen) {
                System.out.println(n.toString());
            }

            for (int[] move : nodesCreatingOrder) {
                State g = Operator.generator(n, board, board.length, move);

                if (g != null) {
                    num++;
                    if (!(closedList.contains(g.getCarPosition().toString())) && !(openList.contains(g.getCarPosition().toString()))) {
                        if (g.getCarPosition().equals(goal)) {
                            Output.createOutputFile(g.getPath(), num, g.getCost() + "", System.currentTimeMillis() - startTimer, withTime);
                            return;
                        }

                        L.add(g);
                        openList.add(g.getCarPosition().toString());
                    }
                }
            }
        }

        Output.createOutputFile("no path ", num, "inf", System.currentTimeMillis() - startTimer, withTime);
    }
}