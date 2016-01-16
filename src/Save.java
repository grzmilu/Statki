
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Save {

    private String[][] f = new String[22][14];
    private String[][] f2 = new String[22][14];

    private static Save inst;

    public Save() {
    }

    public static Save inst() {
        if (inst == null) {
            inst = new Save();
        }
        return inst;
    }

    public void saveGame(String[][] fields, String[][] fieldsAI) {
        try {
            FileWriter file = new FileWriter("gameSave.txt");

            for (int i = 0; i < 22; i++) {
                for (int j = 0; j < 14; j++) {
                    if (fields[i][j].equals("")) {
                        file.write("-");
                    } else {
                        file.write(fields[i][j]);
                    }
                    if (fieldsAI[i][j].equals("")) {
                        file.write("-");
                    } else {
                        file.write(fieldsAI[i][j]);
                    }
                }
            }

            file.close();

        } catch (Exception e) {
        }

    }

    public boolean loadGame() {
        String s = "";
        int poz = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader("gameSave.txt"));
            s = file.readLine();
            for (int i = 0; i < 22; i++) {
                for (int j = 0; j < 14; j++) {
                    if (s.substring(poz, poz + 1).equals("-")) {
                        f[i][j] = "";
                    } else {
                        f[i][j] = s.substring(poz, poz + 1);
                    }

                    poz++;
                    if (s.substring(poz, poz + 1).equals("-")) {
                        f2[i][j] = "";
                    } else {
                        f2[i][j] = s.substring(poz, poz + 1);
                    }

                    poz++;
                }
            }
            file.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public String[][] getfields() {
        return f;
    }

    public String[][] getfieldsAI() {
        return f2;
    }

}
