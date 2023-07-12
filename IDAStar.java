import java.util.HashMap;
import java.util.Stack;


public class IDAStar {

    private static HashMap<String, State> H;
    private static Stack<State> L;
    private State startState;
    private Point goal;
    private char[][] board;
    private int[][] nodesCreatingOrder;
    private boolean withTime;
    private boolean withOpen;
    private boolean removeNewFirst;
    private int num;

    public IDAStar(State startState, Point goal, char[][] board, int[][] nodesCreatingOrder, boolean withTime, boolean withOpen, boolean removeNewFirst) {

        this.startState = startState;
        this.goal = goal;
        this.board = board;
        this.nodesCreatingOrder = nodesCreatingOrder;
        this.withTime = withTime;
        this.withOpen = withOpen;
        this.removeNewFirst = removeNewFirst;
        H = new HashMap<>();
        L = new Stack<>();
        search();
    }

    private void search() {

        long startTimer = System.currentTimeMillis();
        num = 1;

        startState.setHeuristic(HeuristicFunc.CD(startState, goal));
        int t = startState.getHeuristic();

        while (t < Integer.MAX_VALUE) {

            int minF = Integer.MAX_VALUE;

            L.push(startState);
            H.put(startState.getCarPosition().toString(), startState);

            while (!L.empty()) {

                State n = L.pop();

                if (withOpen) {
                    System.out.println(n.toString());
                }

                if (n.getMark().equals("out")) {
                    H.remove(n.getCarPosition().toString());
                }
                else {
                    n.setMark("out");
                    L.push(n);

                    for (int[] move : nodesCreatingOrder) {
                        State g = Operator.generator(n, board, board.length, move);

                        if (g != null) {
                            num++;

                            g.setHeuristic(HeuristicFunc.CD(g, goal));
                            int f = g.getCost() + g.getHeuristic();

                            if (f > t) {
                                minF = Math.min(minF, f);
                                continue;
                            }
                            if (H.containsKey(g.getCarPosition().toString()) && g.getMark().equals("out")) {
                                continue;
                            }
                            if (H.containsKey(g.getCarPosition().toString()) && !g.getMark().equals("out")) {
                                int gTagValue = H.get(g.getCarPosition().toString()).getCost() + H.get(g.getCarPosition().toString()).getHeuristic();
                                int gValue = g.getCost() + g.getHeuristic();

                                if (gTagValue > gValue) {
                                    L.remove(g);
                                    H.remove(g.getCarPosition().toString());
                                }
                                else {
                                    continue;
                                }
                            }
                            if (g.getCarPosition().equals(goal)) {
                                Output.createOutputFile(g.getPath(), num, g.getCost() + "", System.currentTimeMillis() - startTimer, withTime);
                                return;
                            }

                            L.push(g);
                            H.put(g.getCarPosition().toString(), g);
                        }
                    }
                }
            }

            t = minF;
            startState.setMark("");
        }

        Output.createOutputFile("no path ", num, "inf", System.currentTimeMillis() - startTimer, withTime);
    }
}
