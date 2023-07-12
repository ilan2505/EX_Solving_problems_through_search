import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;


public class AStar {

    private static HashMap<String, Integer> openList;
    private static HashSet<String> closedList;
    private State startState;
    private Point goal;
    private char[][] board;
    private int[][] nodesCreatingOrder;
    private boolean withTime;
    private boolean withOpen;
    private boolean removeNewFirst;
    private int num;

    public AStar(State startState, Point goal, char[][] board, int[][] nodesCreatingOrder, boolean withTime, boolean withOpen, boolean removeNewFirst) {

        this.startState = startState;
        this.goal = goal;
        this.board = board;
        this.nodesCreatingOrder = nodesCreatingOrder;
        this.withTime = withTime;
        this.withOpen = withOpen;
        this.removeNewFirst = removeNewFirst;
        openList = new HashMap<>();
        closedList = new HashSet<>();
        search();
    }

    private void search() {

        long startTimer = System.currentTimeMillis();
        PriorityQueue<State> queue = new PriorityQueue<>(new NodeComparator(removeNewFirst));
        queue.add(startState);
        openList.put(startState.getCarPosition().toString(), startState.getCost());

        num = 1;

        while (!queue.isEmpty()) {

            State n = queue.poll();

            if (n.getCarPosition().equals(goal)) {
                Output.createOutputFile(n.getPath(), num, n.getCost() + "", System.currentTimeMillis() - startTimer, withTime);
                return;
            }

            closedList.add(n.getCarPosition().toString());

            if (withOpen) {
                System.out.println(n.toString());
            }

            for (int[] move : nodesCreatingOrder) {
                State g = Operator.generator(n, board, board.length, move);

                if (g != null) {
                    num++;

                    g.setHeuristic(HeuristicFunc.CD(g, goal));
                    if (!(closedList.contains(g.getCarPosition().toString())) && !(openList.containsKey(g.getCarPosition().toString()))) {
                        queue.add(g);
                        openList.put(g.getCarPosition().toString(), g.getCost());
                    }
                    else if (openList.containsKey(g.getCarPosition().toString()) && openList.get(g.getCarPosition().toString()) > g.getCost()) {
                        queue.remove(g);
                        queue.add(g);
                        openList.put(g.getCarPosition().toString(), g.getCost());
                    }
                }
            }
        }

        Output.createOutputFile("no path ", num, "inf", System.currentTimeMillis() - startTimer, withTime);
    }
}