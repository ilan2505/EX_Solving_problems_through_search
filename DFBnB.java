import java.util.*;


public class DFBnB {

    private static ArrayList<State> result;
    private static HashMap<String, State> H;
    private Stack<State> L;
    private State startState;
    private Point goal;
    private char[][] board;
    private int[][] nodesCreatingOrder;
    private boolean withTime;
    private boolean withOpen;
    private boolean removeNewFirst;
    private int num;
    private boolean foundSolution;

    public DFBnB(State startState, Point goal, char[][] board, int[][] nodesCreatingOrder, boolean withTime, boolean withOpen, boolean removeNewFirst) {

        this.startState = startState;
        this.goal = goal;
        this.board = board;
        this.nodesCreatingOrder = nodesCreatingOrder;
        this.withTime = withTime;
        this.withOpen = withOpen;
        this.removeNewFirst = removeNewFirst;
        this.foundSolution = false;
        result = new ArrayList<>();
        H = new HashMap<>();
        L = new Stack<>();
        search();
    }

    private void search() {

        long startTimer = System.currentTimeMillis();
        num = 1;

        L.push(startState);
        H.put(startState.getCarPosition().toString(), startState);

        int t = Integer.MAX_VALUE;

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

                // apply all of the allowed operators on n
                // sort the nodes in N according to their f values (increasing order)
                PriorityQueue<State> N = new PriorityQueue<>(new NodeComparator(removeNewFirst));
                for (int[] move : nodesCreatingOrder) {
                    State g = Operator.generator(n, board, board.length, move);

                    if (g != null) {
                        num++;
                        g.setHeuristic(HeuristicFunc.CD(g, goal));
                        N.add(g);
                    }
                }
                ArrayList<State> copyList = new ArrayList<>();

                while (!N.isEmpty()) {
                    State g = N.poll();
                    copyList.add(g);

                    int f = g.getCost() + g.getHeuristic();
                    if (f >= t) {
                        copyList.remove(g);
                        break;
                    }
                    else if (H.containsKey(g.getCarPosition().toString()) && g.getMark().equals("out")) {
                        copyList.remove(g);
                    }
                    else if (H.containsKey(g.getCarPosition().toString()) && !g.getMark().equals("out")) {
                        int gTagValue = H.get(g.getCarPosition().toString()).getCost() + H.get(g.getCarPosition().toString()).getHeuristic();
                        int gValue = g.getCost() + g.getHeuristic();

                        if (gTagValue <= gValue) {
                            copyList.remove(g);
                        }
                        else {
                            L.remove(H.get(g.getCarPosition().toString()));
                            H.remove(g.getCarPosition().toString());
                        }
                    }
                    else if (g.getCarPosition().equals(goal)) {
                        foundSolution = true;
                        t = g.getCost() + g.getHeuristic();
                        result.add(g);
                        copyList.remove(g);
                        break;
                    }
                }

                Collections.reverse(copyList);
                L.addAll(copyList);
                for (State state : copyList) {
                    H.put(state.getCarPosition().toString(), state);
                }
            }
        }

        if (!foundSolution) {
            Output.createOutputFile("no path ", num, "inf", System.currentTimeMillis() - startTimer, withTime);
        }
        else {
            State last = result.get(result.size()-1);
            Output.createOutputFile(last.getPath(), num, last.getCost() + "", System.currentTimeMillis() - startTimer, withTime);
        }
    }
}
