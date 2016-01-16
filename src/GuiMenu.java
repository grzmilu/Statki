
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class GuiMenu extends JFrame implements ActionListener {

    private JLabel lTitle;
    private JButton bNewGame, bLoadGame, bAbout, bExit;

    public GuiMenu() {

        int heightScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        int widthScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = 550;
        int width = 550;
        setBounds(widthScreen / 2 - width / 2, heightScreen / 2 - height / 2, width, height);
        setTitle("Menu");
        setLayout(null);

        lTitle = new JLabel("Gra w statki", SwingConstants.CENTER);
        lTitle.setBounds((width - 150) / 2, 50, 150, 50);
        lTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        add(lTitle);

        bNewGame = new JButton("New game");
        bNewGame.setBounds((width - 150) / 2, 150, 150, 50);
        add(bNewGame);
        bNewGame.addActionListener(this);

        bLoadGame = new JButton("Load game");
        bLoadGame.setBounds((width - 150) / 2, 225, 150, 50);
        add(bLoadGame);
        bLoadGame.addActionListener(this);

        bAbout = new JButton("About");
        bAbout.setBounds((width - 150) / 2, 300, 150, 50);
        add(bAbout);
        bAbout.addActionListener(this);

        bExit = new JButton("Exit");
        bExit.setBounds((width - 150) / 2, 375, 150, 50);
        add(bExit);
        bExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == bExit) {
            dispose();
        } else if (source == bAbout) {
            JOptionPane.showMessageDialog(this, "Gra w statki \nPaweł Grzmil");
        } else if (source == bNewGame) {
            dispose();
            GuiGame okno = new GuiGame();
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setVisible(true);
        } else if (source == bLoadGame) {
            dispose();
            GuiGame okno = new GuiGame();
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setVisible(true);

         

            if (Save.inst().loadGame()) {
                okno.setFields(Save.inst().getfields(), Save.inst().getfieldsAI());
            }

        }

    }

}
