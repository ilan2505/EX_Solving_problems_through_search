public class HeuristicFunc {

    public static int CD(State currentState, Point goal) {
        int i = Math.abs(currentState.getCarPosition().getI() - goal.getI());
        int j = Math.abs(currentState.getCarPosition().getJ() - goal.getJ());

        // Chebyshev Distance
        return Math.max(i, j);
    }
}
