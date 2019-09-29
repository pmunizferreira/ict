import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Tela extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int DESCRIPTION_HEIGHT = 0;
    public static final String FONT_NAME = "Calibri";
    private static String[] headers = new String[]{" B ", " I ", " N ", " G ", " O "};

    private JTextField inputField;
    private JLabel[][] mapOfNumbers;
    private JTextArea display;
    private JButton addButton;
    private JButton removeButton;
    private JButton resetButton;
    private JButton applyButton;

    private Mode mode;

    public enum Mode {
        BINGO,
        SORTEIO
    }

    public Tela(Mode mode) {
        this.mode = mode;
        setLayout(new BorderLayout(0, 0));

        switch (this.mode) {
            case BINGO:
                buildBingo();
                break;
            case SORTEIO:
                buildSorteio();
                break;
        }

        pack();
        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(maxDimension);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** ****************************************
     * SORTEIO
     */
    private void buildSorteio() {
        Box left = Box.createVerticalBox();
        left.add(getValueToSorteio(), BorderLayout.NORTH);

        Box right = Box.createVerticalBox();
        right.add(getListOfValuesToSorteio(), BorderLayout.NORTH);

        add(right, BorderLayout.EAST);
        add(left, BorderLayout.WEST);
    }

    private JPanel getListOfValuesToSorteio() {
        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int w = maxDimension.width / 2;
        int h = maxDimension.height - DESCRIPTION_HEIGHT;

        display = new JTextArea();
        display.setEditable(false);
        display.setFont(new Font(FONT_NAME, Font.BOLD, 50));
        JScrollPane scroll = new JScrollPane(display);
        scroll.setPreferredSize(new Dimension(w - 10, 2 * h / 3));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setPreferredSize(new Dimension(w, 3 * h / 4));
        panel.setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.NORTH);

        resetButton = new JButton("Remova todos os Números");
        panel.add(resetButton, BorderLayout.SOUTH);
        applyButton = new JButton("Sorteie os Números");
        panel.add(applyButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getValueToSorteio() {
        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int w = maxDimension.width / 2;
        int h = maxDimension.height - DESCRIPTION_HEIGHT;

        Border redline = BorderFactory.createLineBorder(Color.RED);
        Font titleFont = new Font("Calibri", Font.BOLD, 50);

        inputField = new JTextField();
        inputField.setFont(new Font("Calibri", Font.BOLD, 100));
        inputField.setBorder(BorderFactory.createTitledBorder(redline, "Número", TitledBorder.CENTER, TitledBorder.TOP, titleFont));
        inputField.setPreferredSize(new Dimension(w - 10, 1 * h / 3));
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setDocument(new JTextFieldLimit(10));
        inputField.setForeground(Color.RED);

        JPanel panel = getPanel(w, h);
        panel.add(inputField, BorderLayout.CENTER);

        addButton = new JButton("Adicione Número");
        panel.add(addButton, BorderLayout.CENTER);
        removeButton = new JButton("Remova Número");
        panel.add(removeButton, BorderLayout.CENTER);

        return panel;
    }

    public void loadValuesToSorteio(List<String> values) {
        display.setText("");
        for (String line: values) {
            display.append(line);
            display.append("\n");
        }
    }

    /** ****************************************
     * BINGO
     */
    private void buildBingo() {
        Box left = Box.createVerticalBox();
        left.add(getNumberToBingo(), BorderLayout.NORTH);

        Box right = Box.createVerticalBox();
        right.add(getMapOfNumbersToBingo(), BorderLayout.NORTH);

        add(right, BorderLayout.EAST);
        add(left, BorderLayout.WEST);
    }

    private JPanel getNumberToBingo() {
        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (maxDimension.width / 3) * 1;
        int h = maxDimension.height - DESCRIPTION_HEIGHT;

        Border redline = BorderFactory.createLineBorder(Color.RED);
        Font titleFont = new Font("Calibri", Font.BOLD, 50);

        inputField = new JTextField();
        inputField.setFont(new Font("Calibri", Font.BOLD, 400));
        inputField.setBorder(BorderFactory.createTitledBorder(redline, "Número Sorteado", TitledBorder.CENTER, TitledBorder.TOP, titleFont));
        inputField.setPreferredSize(new Dimension(w - 10, 3 * h / 4));
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setDocument(new JTextFieldLimit(2));
        inputField.setForeground(Color.RED);

        JPanel panel = getPanel(w, h);
        panel.add(inputField, BorderLayout.CENTER);

        resetButton = new JButton("Recomeçe o Bingo");
        panel.add(resetButton, BorderLayout.SOUTH);

        return panel;
    }

    private Box getMapOfNumbersToBingo() {
        mapOfNumbers = new JLabel[Bingo.NUMBER_COL][Bingo.NUMBER_PER_COL];

        Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (maxDimension.width / 3) * 2;
        int h = maxDimension.height - DESCRIPTION_HEIGHT;

        Box grade = Box.createHorizontalBox();

        grade.add(createWhiteSpace(w / 11, h));
        for (int i = 0; i < mapOfNumbers.length; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder());
            panel.setPreferredSize(new Dimension(w / 11, h));

            JLabel letter = new JLabel(headers[i]);
            letter.setFont(new Font("Verdana", Font.BOLD, 50));
            letter.setVerticalTextPosition(JLabel.TOP);
            letter.setHorizontalTextPosition(JLabel.CENTER);
            letter.setHorizontalAlignment(JLabel.CENTER);
            letter.setVerticalAlignment(JLabel.TOP);
            letter.setForeground(Color.BLACK);
            panel.add(letter, BorderLayout.CENTER);

            JLabel[] col = mapOfNumbers[i];
            for (int j = 0; j < col.length; j++) {
                int number = (i * Bingo.NUMBER_PER_COL) + (j + 1);
                JLabel label = new JLabel(number < 10 ? "0" + number : "" + number);

                label.setFont(new Font("Verdana", Font.BOLD, 35));
                label.setVerticalTextPosition(JLabel.TOP);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.TOP);
                label.setForeground(Color.LIGHT_GRAY);

                mapOfNumbers[i][j] = label;
                panel.add(label, BorderLayout.CENTER);
            }

            grade.add(panel);
            grade.add(createWhiteSpace(w / 11, h));
        }

        return grade;
    }

	public void repaintMapOfNumbersToBingo(boolean[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
		    boolean[] col = mapa[i];
		    for (int j = 0; j < col.length; j++) {
		        if (mapa[i][j]) {
		            mapOfNumbers[i][j].setForeground(Color.red);
		        } else {
		            mapOfNumbers[i][j].setForeground(Color.lightGray);
		        }
		    }
		}
	}

    private JPanel createWhiteSpace(int w, int h) {
        JPanel space1 = new JPanel();
        space1.setBackground(Color.WHITE);
        space1.setBorder(BorderFactory.createEmptyBorder());
        space1.setPreferredSize(new Dimension(w, h));
        return space1;
    }

    /** ****************************************
     * GENERAL
     */
    public static int confirmYesNo(String message, String title) {
        String[] options = {"Sim!", "Não!"};
        int result = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //no custom icon
                options,  //button titles
                options[1] //default button
        );
        return result;
    }

    public void setActionListener(ActionListener listener) {
        if (inputField != null) {
            inputField.setActionCommand("inputField");
            inputField.addActionListener(listener);
        }
        if (addButton != null) {
            addButton.setActionCommand("addButton");
            addButton.addActionListener(listener);
        }
        if (removeButton != null) {
            removeButton.setActionCommand("removeButton");
            removeButton.addActionListener(listener);
        }
        if (resetButton != null) {
            resetButton.setActionCommand("resetButton");
            resetButton.addActionListener(listener);
        }
        if (applyButton != null) {
            applyButton.setActionCommand("applyButton");
            applyButton.addActionListener(listener);
        }
    }

    private JPanel getPanel(int w, int h) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setPreferredSize(new Dimension(w, 3 * h / 4));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public String getInput() {
        return inputField.getText();
    }

    public void setInput(String s) {
        inputField.setText(s);
    }

    public static void showSorteioDialog(String value)
    {
        try {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setMaximumSize(maxDimension);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            JPanel contentPane = (JPanel) frame.getContentPane();
            contentPane.setLayout(new BorderLayout());

            contentPane.setBorder(BorderFactory.createEmptyBorder());
            contentPane.setBackground(Color.WHITE);

            final JLabel imageLabel = new JLabel();
            final JLabel headerLabel = new JLabel();

            // add the header label
            headerLabel.setFont(new java.awt.Font(FONT_NAME, Font.BOLD, 40));
            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerLabel.setText("Sorteio em andamento...");
            contentPane.add(headerLabel, BorderLayout.NORTH);

            // add the image label
            int w = maxDimension.width;
            ImageIcon ii = new ImageIcon(frame.getClass().getResource("/sorteio.gif"));
            ii.setImage(ii.getImage().getScaledInstance(w, -1,Image.SCALE_DEFAULT));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setSize(maxDimension);
            imageLabel.setIcon(ii);
            contentPane.add(imageLabel, java.awt.BorderLayout.CENTER);

            // show it
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            ActionListener listener = new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    headerLabel.setText("Número Sorteado!");
                    imageLabel.setVisible(false);

                    final JLabel label = new JLabel();

                    // add the header label
                    label.setFont(new java.awt.Font(FONT_NAME, Font.BOLD, 200));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setForeground(Color.RED);
                    label.setText(value);
                    contentPane.add(label, BorderLayout.CENTER);
                }
            };
            Timer timer = new Timer(7000, listener);
            timer.setRepeats(false);
            timer.start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    class JTextFieldLimit extends PlainDocument {
        private static final long serialVersionUID = 1L;
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null || !Character.isDigit(str.charAt(0)))
                return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}