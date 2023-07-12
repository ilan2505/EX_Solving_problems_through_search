public class Operator {

    public static State generator(State current, char[][] board, int n, int[] move) {
        return checkValidation(current, board, move[0], move[1], n);
    }

    private static State checkValidation(State current, char[][] board, int i, int j, int n) {
        int newI = i+current.getCarPosition().getI();
        int newJ = j+current.getCarPosition().getJ();

        // checking that we are not going out of bounds and there is no cliff there
        if (newI < 0 || newI >= n || newJ < 0 || newJ >= n || board[newI][newJ] == 'X') {
            return null;
        }

        return moveTheCar(current, board, newI, newJ, i, j);
    }

    private static State moveTheCar(State current, char[][] board, int i, int j, int oldI, int oldJ) {
        String direction = Operator.getDirection(oldI + "," + oldJ);

        int cost;
        if (direction.length() > 1 && board[i][j] == 'H') {
            cost = 10;
        }
        else {
            cost = Operator.getCost(board[i][j]);
        }

        String path = current.getPath() + direction + "-";

        Point position = new Point(i ,j);
        State newNode = new State(current, path, cost, position);

        // check opposite step
        if (newNode.equals(newNode.getParent().getParent())) {
            return null;
        }

        return newNode;
    }

    private static int getCost(char c) {
        switch (c) {
            case 'D':
                return 1;
            case 'R':
                return 3;
            case 'H':
            case 'G':
                return 5;
            case 'S':
                return 0;
            default:
                return -1;
        }
    }

    private static String getDirection(String pos) {
        switch (pos) {
            case "0,1":
                return "R";
            case "1,1":
                return "RD";
            case "1,0":
                return "D";
            case "1,-1":
                return "LD";
            case "0,-1":
                return "L";
            case "-1,-1":
                return "LU";
            case "-1,0":
                return "U";
            case "-1,1":
                return "RU";
            default:
                return "";
        }
    }
}
