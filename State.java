public class State {

    private static long COUNTER = 0;
    private final long timeOfBirth;
    private State parent;
    private final String path;
    private int cost;
    private final Point carPosition;
    private int heuristic;
    private String mark;

    public State(State parent, String path, int cost, Point carPosition) {

        this.timeOfBirth = COUNTER++;
        this.parent = parent;
        this.path = path;
        setCost(parent, cost);
        this.carPosition = carPosition;
        this.mark = "";
    }

    public long getTimeOfBirth() {
        return timeOfBirth;
    }

    public State getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    public Point getCarPosition() {
        return carPosition;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public String getMark() {
        return mark;
    }

    public void setCost(State parent, int cost) {
        if (parent == null) {
            this.cost = cost;
        }
        else {
            this.cost += parent.cost + cost;
        }
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "State: \n" +
                "path - " + path + "\n" +
                "cost - " + cost + "\n" +
                "carPosition - " + carPosition + "\n" +
                "timeOfBirth - " + timeOfBirth + "\n\n" +
                "parent - " + parent + "\n\n" +
                "---------------------------------------------";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        return this.carPosition.equals(state.carPosition);
    }
}