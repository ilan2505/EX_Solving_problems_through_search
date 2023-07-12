import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Ex1 {
    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);

            int[][] clockwise = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
            int[][] counterClockwise = {{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1}};
            String algoName = scanner.nextLine();
            int[][] nodesCreatingOrder;
            boolean removeNewFirst = false;
            if (algoName.equals("A*") || algoName.equals("DFBnB")) {
                String str = scanner.nextLine();
                String[] tempArr = str.trim().split(" ");
                nodesCreatingOrder = tempArr[0].equals("clockwise") ? clockwise : counterClockwise;
                if (tempArr[1].equals("new-first")) {
                    removeNewFirst = true;
                }
            }
            else {
                nodesCreatingOrder = scanner.nextLine().equals("clockwise") ? clockwise : counterClockwise;
            }
            boolean withTime = scanner.nextLine().equals("with time");
            boolean withOpen = scanner.nextLine().equals("with open");

            int n = Integer.parseInt(scanner.nextLine());

            List<String> coordinates = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
            Matcher matcher = pattern.matcher(scanner.nextLine());
            while (matcher.find()) {
                String i = matcher.group(1);
                String j = matcher.group(2);
                coordinates.add(i);
                coordinates.add(j);
            }
            Point start = new Point(Integer.parseInt(coordinates.get(0))-1, Integer.parseInt(coordinates.get(1))-1);
            Point goal = new Point(Integer.parseInt(coordinates.get(2))-1, Integer.parseInt(coordinates.get(3))-1);

            char[][] board = new char[n][n];
            for (int i = 0; i < n; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < n; j++) {
                    board[i][j] = line.charAt(j);
                }
            }

            State state = new State(null, "", 0, start);

            scanner.close();

            switch (algoName) {
                case "BFS":
                    new BFS(state, goal, board, nodesCreatingOrder, withTime, withOpen);
                    break;
                case "DFID":
                    new DFID(state, goal, board, nodesCreatingOrder, withTime, withOpen);
                    break;
                case "A*":
                    new AStar(state, goal, board, nodesCreatingOrder, withTime, withOpen, removeNewFirst);
                    break;
                case "IDA*":
                    new IDAStar(state, goal, board, nodesCreatingOrder, withTime, withOpen, removeNewFirst);
                    break;
                case "DFBnB":
                    new DFBnB(state, goal, board, nodesCreatingOrder, withTime, withOpen, removeNewFirst);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}