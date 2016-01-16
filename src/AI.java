
import java.util.Random;

public class AI {

    int i, j;
    Random r = new Random();
    String[][] fieldsAI;

    public AI(String[][] fieldsAI) {
        this.fieldsAI = fieldsAI;
    }

    public void setFields(String[][] fieldsAI) {
        this.fieldsAI = fieldsAI;
    }

    public String[][] getFields() {
        return fieldsAI;
    }

    public void shotEasy() {

        i = r.nextInt(22);
        j = r.nextInt(14);
        if (fieldsAI[i][j].equals("*")||fieldsAI[i][j].equals("X")) {
            shotEasy();
        } else if (fieldsAI[i][j].equals("O")||fieldsAI[i][j].equals("P")) {
            fieldsAI[i][j] = "X";
        } else if (fieldsAI[i][j].equals("")) {
            fieldsAI[i][j] = "*";
        }
    }
}
