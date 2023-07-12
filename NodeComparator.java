import java.util.Comparator;


public class NodeComparator implements Comparator<State> {

    private final boolean removeNewFirst;

    public NodeComparator(boolean removeNewFirst) {
        this.removeNewFirst = removeNewFirst;
    }

    @Override
    public int compare(State o1, State o2) {

        if (o1.getHeuristic() + o1.getCost() > o2.getHeuristic() + o2.getCost()) {
            return 1;
        }
        else if (o1.getHeuristic() + o1.getCost() < o2.getHeuristic() + o2.getCost()) {
            return -1;
        }
        else {
            // if f(n) is equal, then compare it by counter value according to new-first/old-first
            if (removeNewFirst) {
                return Long.compare(o2.getTimeOfBirth(), o1.getTimeOfBirth());
            }
            return Long.compare(o1.getTimeOfBirth(), o2.getTimeOfBirth());
        }
    }
}
