import java.io.BufferedWriter;
import java.io.FileWriter;


public class Output {

    public static void createOutputFile(String path, int num, String cost, long time, boolean withTime) {
        try {
            StringBuilder output = new StringBuilder(path);
            output.deleteCharAt(output.length()-1).append("\n");
            output.append("Num: ").append(num).append("\n");
            output.append("Cost: ").append(cost);

            if (withTime) {
                output.append("\n").append(time / 1000.0);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write(String.valueOf(output));

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
