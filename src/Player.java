
import java.util.Random;

public class Player {

    String[][] fields;
    Random r = new Random();
    String symbol = "O";

    public Player(String[][] fields) {
        this.fields = fields;
        fillFields();

    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void fillFields() {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 14; j++) {
                fields[i][j] = "";
            }
        }
    }

    public String[][] getFields() {
        return fields;
    }
    public void setFields(String[][] fields){
        this.fields=fields;
    }
    

    public boolean checkField(int x, int y, String s) {
        int xMin, xMax, yMin, yMax, xMax2, xMin2;
        if (s.equals("water")) {
            xMin = 0;
            xMax = 11;
            xMin2 = 0;
            xMax2 = 10;
        } else if (s.equals("land")) {
            xMin = 10;
            xMax = 21;
            xMin2 = 11;
            xMax2 = 21;
        } else {
            xMin = 0;
            xMax = 21;
            xMin2 = 0;
            xMax2 = 21;
        }

        yMin = 0;
        yMax = 13;
        if (x < xMin2 || x > xMax2 || y < yMin || y > yMax) {
            return false;
        }
        if (x != xMin && y != yMin && !fields[x - 1][y - 1].equals("")) {
            return false;
        }
        if (y != yMin && !fields[x][y - 1].equals("")) {
            return false;
        }
        if (x != xMax && y != yMin && !fields[x + 1][y - 1].equals("")) {
            return false;
        }

        if (x != xMin && !fields[x - 1][y].equals("")) {
            return false;
        }
        if (x != xMin && y != yMax && !fields[x - 1][y + 1].equals("")) {
            return false;
        }

        if (y != yMax && !fields[x][y + 1].equals("")) {
            return false;
        }
        if (x != xMax && y != yMax && !fields[x + 1][y + 1].equals("")) {
            return false;
        }

        if (x != xMax && !fields[x + 1][y].equals("")) {
            return false;
        }

        if (!fields[x][y].equals("")) {
            return false;
        }

        return true;
    }

    public void useField(int x, int y) {
        fields[x][y] = symbol;
    }

    public void randomBattleField() {
        randomShipOne();
        randomShipOne();
        randomTwo("water");
        randomTwo("water");
        randomThree("water");
        randomThree("water");
        randomFour("water");
        randomTwo("land");
        randomTwo("land");
        randomTwo("land");
        randomThree("land");
        randomThree("land");
        randomFour("land");
        randomAirplane("air");
    }

    public String[][] randomShipOne() {
        int x, y;
        do {
            x = r.nextInt(11);
            y = r.nextInt(14);

        } while (!checkField(x, y, "water"));
        useField(x, y);
        return fields;
    }

    public String[][] randomTwo(String s) {
        int x, y;
        boolean b;
        int z = 0;
        if (s.equals("land")) {
            z = 11;
        }
        do {
            x = r.nextInt(11) + z;
            y = r.nextInt(14);
            if (r.nextInt(2) == 0) {
                b = !checkSizeTwoHorizontal(x, y, s);
                if (b == false) {
                    makeSizeTwoHorizontal(x, y);
                }
            } else {
                b = !checkSizeTwoVertical(x, y, s);
                if (b == false) {
                    makeSizeTwoVertical(x, y);
                }
            }
        } while (b);
        return fields;
    }

    public String[][] randomThree(String s) {
        int x, y;
        boolean b;
        int z = 0;
        if (s.equals("land")) {
            z = 11;
        }
        do {
            x = r.nextInt(11 + z);
            y = r.nextInt(14);
            if (r.nextInt(2) == 0) {
                b = !checkSizeThreeHorizontal(x, y, s);
                if (b == false) {
                    makeSizeThreeHorizontal(x, y);
                }
            } else {
                b = !checkSizeThreeVertical(x, y, s);
                if (b == false) {
                    makeSizeThreeVertical(x, y);
                }
            }
        } while (b);
        return fields;
    }

    public String[][] randomFour(String s) {
        int x, y;
        boolean b;
        int z = 0;
        if (s.equals("land")) {
            z = 11;
        }
        do {
            x = r.nextInt(11 + z);
            y = r.nextInt(14);
            if (r.nextInt(2) == 0) {
                b = !checkSizeFourHorizontal(x, y, s);
                if (b == false) {
                    makeSizeFourHorizontal(x, y);
                }
            } else {
                b = !checkSizeFourVertical(x, y, s);
                if (b == false) {
                    makeSizeFourVertical(x, y);
                }
            }
        } while (b);
        return fields;
    }

    public String[][] randomAirplane(String s) {
        int x, y;
        boolean b;
        if (!symbol.equals("")) {
            symbol = "P";
        }
        do {
            x = r.nextInt(22);
            y = r.nextInt(14);
            int rand = r.nextInt(4);
            if (rand == 0) {
                b = !checkAirplaneUp(x, y, s);
                if (b == false) {
                    makeAirplaneUp(x, y);
                }
            } else if (rand == 1) {
                b = !checkAirplaneRight(x, y, s);
                if (b == false) {
                    makeAirplaneRight(x, y);
                }
            } else if (rand == 2) {
                b = !checkAirplaneDown(x, y, s);
                if (b == false) {
                    makeAirplaneDown(x, y);
                }
            } else {
                b = !checkAirplaneLeft(x, y, s);
                if (b == false) {
                    makeAirplaneLeft(x, y);
                }
            }
        } while (b);
        if (!symbol.equals("")) {
            symbol = "O";
        }
        return fields;
    }

    public boolean checkSizeTwoHorizontal(int x, int y, String s) {
        return checkField(x, y, s) && checkField(x, y + 1, s);
    }

    public boolean checkSizeTwoVertical(int x, int y, String s) {
        return checkField(x, y, s) && checkField(x + 1, y, s);
    }

    public void makeSizeTwoHorizontal(int x, int y) {
        useField(x, y);
        useField(x, y + 1);
    }

    public void makeSizeTwoVertical(int x, int y) {
        useField(x, y);
        useField(x + 1, y);
    }

    public boolean checkSizeThreeHorizontal(int x, int y, String s) {
        return checkField(x, y - 1, s) && checkField(x, y, s) && checkField(x, y + 1, s);
    }

    public boolean checkSizeThreeVertical(int x, int y, String s) {
        return checkField(x - 1, y, s) && checkField(x, y, s) && checkField(x + 1, y, s);
    }

    public void makeSizeThreeHorizontal(int x, int y) {
        useField(x, y - 1);
        useField(x, y);
        useField(x, y + 1);
    }

    public void makeSizeThreeVertical(int x, int y) {
        useField(x - 1, y);
        useField(x, y);
        useField(x + 1, y);
    }

    public boolean checkSizeFourHorizontal(int x, int y, String s) {
        return checkField(x, y - 1, s) && checkField(x, y, s) && checkField(x, y + 1, s) && checkField(x, y + 2, s);
    }

    public boolean checkSizeFourVertical(int x, int y, String s) {
        return checkField(x - 1, y, s) && checkField(x, y, s) && checkField(x + 1, y, s) && checkField(x + 2, y, s);
    }

    public void makeSizeFourHorizontal(int x, int y) {
        useField(x, y - 1);
        useField(x, y);
        useField(x, y + 1);
        useField(x, y + 2);
    }

    public void makeSizeFourVertical(int x, int y) {
        useField(x - 1, y);
        useField(x, y);
        useField(x + 1, y);
        useField(x + 2, y);
    }

    public boolean checkAirplaneUp(int x, int y, String s) {
        return checkField(x - 1, y - 1, s) && checkField(x, y, s) && checkField(x, y - 1, s) && checkField(x + 1, y - 1, s) && checkField(x, y + 1, s);
    }

    public boolean checkAirplaneRight(int x, int y, String s) {
        return checkField(x, y, s) && checkField(x - 1, y, s) && checkField(x + 1, y, s) && checkField(x + 1, y - 1, s) && checkField(x + 1, y + 1, s);
    }

    public boolean checkAirplaneLeft(int x, int y, String s) {
        return checkField(x, y, s) && checkField(x - 1, y, s) && checkField(x - 1, y - 1, s) && checkField(x - 1, y + 1, s) && checkField(x + 1, y, s);
    }

    public boolean checkAirplaneDown(int x, int y, String s) {
        return checkField(x, y, s) && checkField(x, y - 1, s) && checkField(x, y + 1, s) && checkField(x - 1, y + 1, s) && checkField(x + 1, y + 1, s);

    }

    public void makeAirplaneUp(int x, int y) {
        useField(x, y);
        useField(x, y + 1);
        useField(x, y - 1);
        useField(x - 1, y - 1);
        useField(x + 1, y - 1);

    }

    public void makeAirplaneRight(int x, int y) {
        useField(x, y);
        useField(x - 1, y);
        useField(x + 1, y);
        useField(x + 1, y - 1);
        useField(x + 1, y + 1);

    }

    public void makeAirplaneLeft(int x, int y) {
        useField(x, y);
        useField(x + 1, y);
        useField(x - 1, y);
        useField(x - 1, y + 1);
        useField(x - 1, y - 1);

    }

    public void makeAirplaneDown(int x, int y) {
        useField(x, y);
        useField(x, y - 1);
        useField(x, y + 1);
        useField(x - 1, y + 1);
        useField(x + 1, y + 1);

    }

    public boolean checkSelectType(int x, int y, String waterOrLand, int objectNum, int rotate) {
        if (objectNum == 1) {
            return checkField(x, y, "water");
        } else if (objectNum == 2) {
            if (rotate == 1 || rotate == 3) {
                return checkSizeTwoVertical(x, y, waterOrLand);
            } else {
                return checkSizeTwoHorizontal(x, y, waterOrLand);
            }
        } else if (objectNum == 3) {
            if (rotate == 1 || rotate == 3) {
                return checkSizeThreeVertical(x, y, waterOrLand);
            } else {
                return checkSizeThreeHorizontal(x, y, waterOrLand);
            }
        } else if (objectNum == 4) {
            if (rotate == 1 || rotate == 3) {
                return checkSizeFourVertical(x, y, waterOrLand);
            } else {
                return checkSizeFourHorizontal(x, y, waterOrLand);
            }
        } else if (objectNum == 5) {
            if (rotate == 2) {
                return checkAirplaneUp(x, y, "air");
            } else if (rotate == 3) {
                return checkAirplaneRight(x, y, "air");

            } else if (rotate == 4) {
                return checkAirplaneDown(x, y, "air");

            } else {
                return checkAirplaneLeft(x, y, "air");

            }
        }
        return false;
    }

    public void makeSelectType(int x, int y, int objectNum, int rotate) {
        if (objectNum == 1) {
            useField(x, y);
        } else if (objectNum == 2) {
            if (rotate == 1 || rotate == 3) {
                makeSizeTwoVertical(x, y);
            } else {
                makeSizeTwoHorizontal(x, y);
            }
        } else if (objectNum == 3) {
            if (rotate == 1 || rotate == 3) {
                makeSizeThreeVertical(x, y);
            } else {
                makeSizeThreeHorizontal(x, y);
            }
        } else if (objectNum == 4) {
            if (rotate == 1 || rotate == 3) {
                makeSizeFourVertical(x, y);
            } else {
                makeSizeFourHorizontal(x, y);
            }
        } else if (objectNum == 5) {
            if (!symbol.equals("")) {
                symbol = "P";
            }
            if (rotate == 2) {
                makeAirplaneUp(x, y);
            } else if (rotate == 3) {
                makeAirplaneRight(x, y);

            } else if (rotate == 4) {
                makeAirplaneDown(x, y);

            } else {
                makeAirplaneLeft(x, y);

            }
            if (!symbol.equals("")) {
                symbol = "O";
            }
        }
    }
}
