
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class GuiGame extends JFrame implements MouseListener, ActionListener {

    private int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;

    private JLabel[][] land, water, landAI, waterAI;
    private JLabel[] types;
    private String[][] fields, fieldsAI;
    private JButton bRand, bClear, bStart, bRotate, bBack, bSave;
    private Player player;
    private Player playerAI;
    private AI aiShots;
    private int objectNum = -1;
    private int rotate = 1;
    private int hitPlayer = 0, missPlayer = 0, hitAI = 0, missAI = 0;
    private int lastI, lastJ;
    private String waterOrLand = "water";
    private JLabel lText, lPlayerHit, lPlayerMiss, lAiHit, lAiMiss;
    private boolean mauseMoved = false;

    public GuiGame() {

        setBounds(0, 0, width, height);
        setTitle("Menu");
        setLayout(null);
        chooseType();
        bStart = new JButton("Rozpocznij grę");
        bStart.setBounds(width / 2 - width / 26, height / 2 - 2 * height / 20, width / 13, height / 22);
        add(bStart);
        bStart.addActionListener(this);
        bStart.setFocusable(false);

        bRand = new JButton("Losowe ułożenie");
        bRand.setBounds(width / 2 - width / 26, height / 2 - height / 20, width / 13, height / 22);
        add(bRand);
        bRand.addActionListener(this);
        bRand.setFocusable(false);

        bClear = new JButton("Zresetuj");
        bClear.setBounds(width / 2 - width / 26, height / 2, width / 13, height / 22);
        add(bClear);
        bClear.addActionListener(this);
        bClear.setFocusable(false);

        bSave = new JButton("Zapisz");
        bSave.setBounds(width / 2 - width / 26, height / 2, width / 13, height / 22);
        add(bSave);
        bSave.addActionListener(this);
        bSave.setFocusable(false);
        bSave.setVisible(false);

        bBack = new JButton("Wróć do menu");
        bBack.setBounds(width / 2 - width / 26, height / 2 + height / 20, width / 13, height / 22);
        add(bBack);
        bBack.addActionListener(this);
        bBack.setFocusable(false);

        bRotate = new JButton("Obróć");
        bRotate.addActionListener(this);
        add(bRotate);

        lPlayerHit = new JLabel("Strzały celne: 0");
        lPlayerHit.setBounds(width - (width / 22 + 22 * (width / 55) + 2), 120 + 3 * (width / 55) + 3 * (width / 128), 200, 50);
        add(lPlayerHit);
        lPlayerHit.setFont(new Font("SansSerif", Font.BOLD, 16));
        lPlayerHit.setVisible(false);

        lPlayerMiss = new JLabel("Strzały niecelne: 0");
        lPlayerMiss.setBounds(width - (width / 22 + 22 * (width / 55) + 2), 120 + 4 * (width / 55) + 3 * (width / 128), 200, 50);
        add(lPlayerMiss);
        lPlayerMiss.setFont(new Font("SansSerif", Font.BOLD, 16));
        lPlayerMiss.setVisible(false);

        lAiHit = new JLabel("Strzały celne: 0");
        lAiHit.setBounds(width / 22, 120 + 3 * (width / 55) + 3 * (width / 128), 200, 50);
        add(lAiHit);
        lAiHit.setFont(new Font("SansSerif", Font.BOLD, 16));
        lAiHit.setVisible(false);

        lAiMiss = new JLabel("Strzały niecelne: 0");
        lAiMiss.setBounds(width / 22, 120 + 4 * (width / 55) + 3 * (width / 128), 200, 50);
        add(lAiMiss);
        lAiMiss.setFont(new Font("SansSerif", Font.BOLD, 16));
        lAiMiss.setVisible(false);

        lText = new JLabel("Rozmieść jednostki bojowe", SwingConstants.CENTER);
        lText.setBounds(width / 2 - 200, 30, 400, 90);
        lText.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lText.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lText);

        water = createLavelArray(width / 22, 50, 100, 240);
        land = createLavelArray(width / 22 + 11 * (width / 55) + 1, 50, 150, 50);

        fields = new String[22][14];
        player = new Player(fields);
        fieldsAI = new String[22][14];
        playerAI = new Player(fieldsAI);
        aiShots = new AI(fields);

    }

    public void setFields(String[][] fields, String[][] fieldsAI) {
        this.fields = fields;
        player.setFields(fields);
        updateLabels();

        this.fieldsAI = fieldsAI;

        for (int i = 0; i < 16; i++) {
            types[i].setVisible(false);
        }

        aiShots.setFields(fields);
        startGame();

        upgradeLabelsAI();
        lText.setText("Wczytano grę");

    }

   private JLabel[][] createLavelArray(int x, int r, int g, int b) {
        JLabel[][] array = new JLabel[11][14];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {
                array[i][j] = new JLabel("", SwingConstants.CENTER);
                array[i][j].setBounds(i * (width / 55) + x - 1, j * (width / 55) + 120 + 5 * (width / 55) + 4 * (width / 128), width / 55 + 1, width / 55 + 1);
                array[i][j].setBorder(BorderFactory.createLineBorder(new Color(r, g, b), 1));
                add(array[i][j]);
                array[i][j].addMouseListener(this);
                array[i][j].setForeground(Color.BLUE);
                array[i][j].setOpaque(true);
                array[i][j].setBackground(new Color(235, 235, 235));
                array[i][j].setFont(new Font("SansSerif", Font.BOLD, 20));
            }
        }

        return array;
    }

    private void randomShips() {
        objectNum = -1;
        for (int i = 0; i < 16; i++) {
            types[i].setVisible(false);
        }
        player.fillFields();
        player.randomBattleField();
        updateLabels();
    }

    private void chooseType() {
        types = new JLabel[16];
        for (int i = 0; i < 16; i++) {
            types[i] = new JLabel("", SwingConstants.CENTER);
            types[i].setOpaque(true);
            add(types[i]);
            types[i].setFont(new Font("SansSerif", Font.BOLD, 20));
            if (i < 7) {
                types[i].setBorder(BorderFactory.createLineBorder(new Color(50, 100, 240), 1));
                types[i].setBackground(new Color(190, 190, 245));

            } else if (i > 6 && i < 13) {
                types[i].setBorder(BorderFactory.createLineBorder(new Color(50, 150, 50), 1));
                types[i].setBackground(new Color(190, 220, 190));

            } else {
                types[i].setBorder(BorderFactory.createLineBorder(new Color(0, 200, 200), 1));
                types[i].setBackground(new Color(240, 180, 150));

            }

            types[i].addMouseListener(this);
            types[i].setForeground(Color.BLUE);
        }

        types[0].setBounds(width / 22, 120 + width / 55, width / 55, width / 55);
        types[1].setBounds(width / 22 + width / 55 + width / 128, 120 + width / 55, width / 55, width / 55);
        types[2].setBounds(width / 22, 120 + 2 * (width / 55) + width / 128, 2 * (width / 55), width / 55);
        types[3].setBounds(width / 22 + 2 * (width / 55) + width / 128, 120 + 2 * (width / 55) + width / 128, 2 * (width / 55), width / 55);
        types[4].setBounds(width / 22, 120 + 3 * (width / 55) + 2 * (width / 128), 3 * (width / 55), width / 55);
        types[5].setBounds(width / 22 + 3 * (width / 55) + width / 128, 120 + 3 * (width / 55) + 2 * (width / 128), 3 * (width / 55), width / 55);
        types[6].setBounds(width / 22, 120 + 4 * (width / 55) + 3 * (width / 128), 4 * (width / 55), width / 55);
        types[7].setBounds(width / 22 + 11 * (width / 55) + 1, 120 + 2 * (width / 55) + width / 128, 2 * (width / 55), width / 55);
        types[8].setBounds(width / 22 + 11 * (width / 55) + 1 + 2 * (width / 55) + width / 128, 120 + 2 * (width / 55) + width / 128, 2 * (width / 55), width / 55);
        types[9].setBounds(width / 22 + 11 * (width / 55) + 1 + 4 * (width / 55) + 2 * (width / 128), 120 + 2 * (width / 55) + width / 128, 2 * (width / 55), width / 55);
        types[10].setBounds(width / 22 + 11 * (width / 55) + 1, 120 + 3 * (width / 55) + 2 * (width / 128), 3 * (width / 55), width / 55);
        types[11].setBounds(width / 22 + 11 * (width / 55) + 1 + 3 * (width / 55) + width / 128, 120 + 3 * (width / 55) + 2 * (width / 128), 3 * (width / 55), width / 55);
        types[12].setBounds(width / 22 + 11 * (width / 55) + 1, 120 + 4 * (width / 55) + 3 * (width / 128), 4 * (width / 55), width / 55);
        types[13].setBounds(width / 22 + 6 * (width / 55) + 3 * (width / 128), 120 + 3 * (width / 55) + 2 * (width / 128), 3 * (width / 55), width / 55);
        types[14].setBounds(width / 22 + 6 * (width / 55) + 3 * (width / 128), 120 + 2 * (width / 55) + 2 * (width / 128) + 1, width / 55, width / 55);
        types[15].setBounds(width / 22 + 6 * (width / 55) + 3 * (width / 128), 120 + 4 * (width / 55) + 2 * (width / 128) - 1, width / 55, width / 55);

    }

    private void typeSelected(int i) {
        if (i == 0 || i == 1) {
            objectNum = 1;
        } else if (i == 2 || i == 3) {
            objectNum = 2;
            waterOrLand = "water";
        } else if (i == 4 || i == 5) {
            objectNum = 3;
            waterOrLand = "water";
        } else if (i == 6) {
            objectNum = 4;
            waterOrLand = "water";
        } else if (i == 7 || i == 8 || i == 9) {
            objectNum = 2;
            waterOrLand = "land";
        } else if (i == 10 || i == 11) {
            objectNum = 3;
            waterOrLand = "land";
        } else if (i == 12) {
            objectNum = 4;
            waterOrLand = "land";
        } else if (i == 13 || i == 14 || i == 15) {
            objectNum = 5;
            waterOrLand = "air";
            types[13].setVisible(false);
            types[14].setVisible(false);
            types[15].setVisible(false);

        }
        types[i].setVisible(false);

    }

    private void updateLabels() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {
                if (fields[i][j].equals("O")) {
                    water[i][j].setForeground(Color.BLUE);
                    water[i][j].setBackground(new Color(190, 190, 245));
                } else if (fields[i][j].equals("*")) {
                    missAI++;
                    water[i][j].setText("X");
                    water[i][j].setBackground(new Color(230, 220, 220));
                    water[i][j].setForeground(Color.lightGray);

                } else if (fields[i][j].equals("X")) {
                    hitAI++;
                    water[i][j].setForeground(Color.red);
                    water[i][j].setText("X");
                    water[i][j].setBackground(new Color(190, 190, 245));

                } else if (fields[i][j].equals("")) {
                    water[i][j].setBackground(new Color(235, 235, 235));

                } else if (fields[i][j].equals("P")) {
                    water[i][j].setBackground(new Color(240, 180, 150));
                }
            }
        }
        for (int i = 11; i < 22; i++) {
            for (int j = 0; j < 14; j++) {
                if (fields[i][j].equals("O")) {
                    land[i - 11][j].setText("");
                    land[i - 11][j].setForeground(Color.BLUE);
                    land[i - 11][j].setBackground(new Color(190, 220, 190));
                } else if (fields[i][j].equals("*")) {
                    missAI++;
                    land[i - 11][j].setText("X");
                    land[i - 11][j].setBackground(new Color(230, 220, 220));
                    land[i - 11][j].setForeground(Color.LIGHT_GRAY);
                } else if (fields[i][j].equals("X")) {
                    hitAI++;
                    land[i - 11][j].setForeground(Color.red);
                    land[i - 11][j].setBackground(new Color(190, 220, 190));
                    land[i - 11][j].setText("X");
                } else if (fields[i][j].equals("")) {
                    land[i - 11][j].setBackground(new Color(235, 235, 235));
                } else if (fields[i][j].equals("P")) {
                    land[i - 11][j].setBackground(new Color(240, 180, 150));
                }
            }
        }
        lAiHit.setText("Strzały celne: " + (hitAI));
        lAiMiss.setText("Strzały niecelne: " + (missAI));
        if (hitAI >= 37) {
            playerWinOrLose("lose");
        }
        hitAI = 0;
        missAI = 0;

    }

    private void upgradeLabelsAI() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {
                if (fieldsAI[i][j].equals("*")) {
                    missPlayer++;
                    waterAI[i][j].setText("X");
                    waterAI[i][j].setBackground(new Color(230, 220, 220));
                    waterAI[i][j].setForeground(Color.lightGray);

                } else if (fieldsAI[i][j].equals("X")) {
                    hitPlayer++;
                    waterAI[i][j].setText("X");
                    waterAI[i][j].setBackground(new Color(190, 190, 245));
                    waterAI[i][j].setForeground(Color.RED);
                }
            }
        }
        for (int i = 11; i < 22; i++) {
            for (int j = 0; j < 14; j++) {
                if (fieldsAI[i][j].equals("*")) {
                    missPlayer++;
                    landAI[i - 11][j].setText("X");
                    landAI[i - 11][j].setBackground(new Color(230, 220, 220));
                    landAI[i - 11][j].setForeground(Color.lightGray);

                } else if (fieldsAI[i][j].equals("X")) {
                    hitPlayer++;
                    landAI[i - 11][j].setText("X");
                    landAI[i - 11][j].setBackground(new Color(190, 220, 190));
                    landAI[i - 11][j].setForeground(Color.RED);
                }
            }
        }
        lPlayerHit.setText("Strzały celne: " + (hitPlayer));
        lPlayerMiss.setText("Strzały niecelne: " + (missPlayer));

    }

    private void createObject(int i, int j) {
        mauseMoved = true;

        player.makeSelectType(i, j, objectNum, rotate);
        updateLabels();
    }

    private void checkObject(int i, int j) {
        if (player.checkSelectType(i, j, waterOrLand, objectNum, rotate)) {
            lText.setText("Postaw (spacja)obrót");
            lText.setForeground(Color.black);
            createObject(i, j);
            lastI = i;
            lastJ = j;
        } else {
            mauseMoved = false;
            if (objectNum == -1) {
                if (canIStart()) {

                } else {
                    lText.setText("Wybierz jednostkę bojową!");
                }

            } else {
                lText.setText("Nie mozna tutaj postawić!");
                lText.setForeground(Color.red);
            }

        }
    }

    private void increaseRotation() {
        if (mauseMoved) {
            player.setSymbol("");
            createObject(lastI, lastJ);
            player.setSymbol("O");
            rotate = rotate + 1;
            if (rotate > 4) {
                rotate = 1;
            }
            checkObject(lastI, lastJ);
        }
    }

    private void clearFields() {
        objectNum = -1;
        for (int i = 0; i < 16; i++) {
            types[i].setVisible(true);
        }
        player.fillFields();
        updateLabels();
    }

    private boolean canIStart() {
        if (objectNum != -1) {
            return false;
        }
        for (int i = 0; i < 16; i++) {
            if (types[i].isVisible()) {
                return false;
            }

        }
        return true;
    }

    private void startGame() {
        if (canIStart()) {
            waterAI = createLavelArray(width - (width / 22 + 22 * (width / 55) + 2), 50, 100, 240);
            landAI = createLavelArray(width - (width / 22 + 11 * (width / 55) + 1), 50, 150, 50);
            bClear.setVisible(false);
            bStart.setVisible(false);
            bRand.setVisible(false);
            bRotate.setVisible(false);
            bSave.setVisible(true);

            lPlayerHit.setVisible(true);
            lPlayerMiss.setVisible(true);
            lAiHit.setVisible(true);
            lAiMiss.setVisible(true);

            lText.setText("Oddaj strzał");

            playerAI.fillFields();
            playerAI.randomBattleField();

        } else {
            lText.setText("Umieść najpierw wszystkie jednostki");
        }
    }

    private void aiMove() {

        aiShots.shotEasy();

        updateLabels();
    }

    private void playerWinOrLose(String s) {
        int a;
        if (s.equals("win")) {
            lText.setText("Wygrałeś!");
            a = JOptionPane.showConfirmDialog(null, "Wygrałeś! Czy chcesz zagrać jeszcze raz?", "Gratulacje", JOptionPane.YES_NO_OPTION);
        } else {
            lText.setText("Przegrałeś!");
            a = JOptionPane.showConfirmDialog(null, "Przegrałeś! Czy chcesz zagrać jeszcze raz?", "Gratulacje", JOptionPane.YES_NO_OPTION);
        }

        if (a == JOptionPane.YES_OPTION) {
            GuiGame okno = new GuiGame();
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setVisible(true);
            dispose();

        } else if (a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION) {
            dispose();
            GuiMenu okno = new GuiMenu();
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setVisible(true);
        }
    }

    private void checkShot(int i, int j) {
        if (fieldsAI[i][j].equals("O") || fieldsAI[i][j].equals("P")) {
            lText.setText("Trafiony!");
            lPlayerHit.setText("Strzały celne: " + (++hitPlayer));
            aiMove();
            fieldsAI[i][j] = "X";

            if (i < 11) {
                waterAI[i][j].setText("X");
                waterAI[i][j].setBackground(new Color(190, 190, 245));
                waterAI[i][j].setForeground(Color.RED);

            } else {
                landAI[i - 11][j].setText("X");
                landAI[i - 11][j].setBackground(new Color(190, 220, 190));
                landAI[i - 11][j].setForeground(Color.RED);
            }
        } else if (fieldsAI[i][j].equals("")) {
            lText.setText("Pudło!");
            lPlayerMiss.setText("Strzały niecelne: " + (++missPlayer));
            aiMove();
            fieldsAI[i][j] = "*";
            if (i < 11) {
                waterAI[i][j].setText("X");
                waterAI[i][j].setBackground(new Color(230, 220, 220));
                waterAI[i][j].setForeground(Color.lightGray);
            } else {
                landAI[i - 11][j].setText("X");
                landAI[i - 11][j].setBackground(new Color(230, 220, 220));
                landAI[i - 11][j].setForeground(Color.lightGray);
            }
        } else if (fieldsAI[i][j].equals("*")) {

        } else if (fieldsAI[i][j].equals("X")) {

        }
        if (hitPlayer >= 37) {
            playerWinOrLose("win");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == bRand) {
            randomShips();
        } else if (source == bClear) {
            clearFields();
        } else if (source == bRotate) {
            increaseRotation();
        } else if (source == bStart) {
            startGame();
        } else if (source == bBack) {
            dispose();
            GuiMenu okno = new GuiMenu();
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setVisible(true);
        } else if (source == bSave) {            
            Save.inst().saveGame(fields, fieldsAI);
            lText.setText("Zapisano stan gry");
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        Object source = me.getSource();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {

                if (source == water[i][j]) {
                    if (mauseMoved == true) {
                        objectNum = -1;
                        rotate = 1;
                        mauseMoved = false;
                    }

                } else if (source == land[i][j]) {
                    if (mauseMoved == true) {
                        objectNum = -1;
                        rotate = 1;
                        mauseMoved = false;
                    }

                } else if (waterAI != null && source == waterAI[i][j]) {
                    checkShot(i, j);
                } else if (landAI != null && source == landAI[i][j]) {
                    checkShot(i + 11, j);
                }

            }
        }

        for (int i = 0; i < 16; i++) {
            if (source == types[i]) {
                if (objectNum == -1) {
                    typeSelected(i);
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Object source = me.getSource();
        for (int i = 0; i < 16; i++) {
            if (source == types[i]) {
                lText.setText("Ustaw jednostkę na planszy");

            }
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        player.setSymbol("");
        Object source = me.getSource();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {
                if (source == water[i][j]) {
                    if (mauseMoved) {
                        createObject(i, j);
                        mauseMoved = false;
                    }
                } else if (source == land[i][j]) {
                    if (mauseMoved) {
                        createObject(i + 11, j);
                        mauseMoved = false;
                    }
                }
            }
        }
        player.setSymbol("O");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        Object source = me.getSource();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {
                if (source == water[i][j]) {
                    checkObject(i, j);
                } else if (source == land[i][j]) {
                    checkObject(i + 11, j);
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            if (source == types[i]) {
                lText.setText("Kliknij aby wybrac jednostkę");
                lText.setForeground(Color.black);

            }
        }

    }

}
