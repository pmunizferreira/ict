import java.awt.*;

import javax.swing.*;

public class Bingo extends JFrame {

    public Bingo() {
        super("Bingo do ICT");
        final int SIZE = 3;
        Container c = getContentPane();
        c.setLayout(new BorderLayout(30, 30));

        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();

        Sorteio sorteio = new Sorteio();
        c.add(new Tela(sorteio, maxDimension), BorderLayout.CENTER);

        pack();
        setMaximumSize(maxDimension);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Bingo app = new Bingo();
            }
        });
    }
}