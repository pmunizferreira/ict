import java.awt.*;
import javax.swing.*;

public class Bingo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Bingo() {
        super("Bingo do ICT");
        Container c = getContentPane();
        c.setLayout(new BorderLayout(30, 30));

        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();

        Sorteio sorteio = new Sorteio();
        Tela tela = new Tela(sorteio, maxDimension);
        c.add(tela, BorderLayout.CENTER);

        pack();
        setMaximumSize(maxDimension);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Bingo();
            }
        });
    }

}