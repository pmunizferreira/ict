import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Bingo implements ActionListener {

    static final int NUMBER_PER_COL = 15;
    static final int NUMBER_COL = 5;
    ArrayList<Integer> sortedNumbers = new ArrayList<>();
    boolean[][] mapOfNumbers = new boolean[NUMBER_COL][NUMBER_PER_COL];

    public boolean[][] getMapOfNumbers() {
        return mapOfNumbers;
    }
    private Tela tela;

	public Bingo() {
        tela = new Tela(Tela.Mode.BINGO);
        tela.setActionListener(this);

        tela.setVisible(true);
    }

    public static void main(String[] args) {
	    new Bingo();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            switch (event.getActionCommand()) {
                case "inputField":
                    String text = tela.getInput();
                    if (text != null && text.trim().length() > 0) {
                        try {
                            addSortedNumber(Integer.valueOf(text));
                        } catch (Exception e) {
                            tela.setInput("");
                        }

                        tela.repaintMapOfNumbersToBingo(getMapOfNumbers());
                    }
                    break;
                case "resetButton":
                    int result = tela.confirmYesNo(
                            "Deseja limpar todos os números do Bingo?",
                            "RECOMEÇAR O BINGO");

                    if (result == 0) {
                        reset();
                        tela.setInput("");
                        tela.repaintMapOfNumbersToBingo(getMapOfNumbers());
                    }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void reset() {
        sortedNumbers.clear();
        mapOfNumbers = new boolean[NUMBER_COL][NUMBER_PER_COL];
    }

    public void addSortedNumber(Integer number) {
        if (number != null && !sortedNumbers.contains(number)) {
            int col = (number - 1) / NUMBER_PER_COL;
            int row = (number - 1) % NUMBER_PER_COL;
            mapOfNumbers[col][row] = true;

            sortedNumbers.add(number);
            Collections.sort(sortedNumbers);
        }
    }
}