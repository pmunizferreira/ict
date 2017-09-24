import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static final int TITLE_HEIGHT = 80;
    private static final int DESCRIPTION_HEIGHT = 300;
    JTextField pSorteioNumberTA = new JTextField();
    JLabel[][] pSorteioMapa = new JLabel[Sorteio.NUMBER_COL][Sorteio.NUMBER_PER_COL];
    Sorteio sorteio;

    public Tela(Sorteio sorteio, Dimension parentDimension) {
        this.sorteio = sorteio;
        setLayout(new BorderLayout(0, 0));
        setSize(parentDimension);

        Box left = Box.createVerticalBox();
        left.add(getNumeroSorteado(parentDimension.width / 2, parentDimension.height - DESCRIPTION_HEIGHT), BorderLayout.NORTH);
        left.add(getDescricaoPremio(parentDimension.width / 2, DESCRIPTION_HEIGHT), BorderLayout.SOUTH);

        Box right = Box.createVerticalBox();
        right.add(getNuemrosSorteados(parentDimension.width / 2, parentDimension.height - TITLE_HEIGHT), BorderLayout.NORTH);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
    }

    private JPanel getDescricaoPremio(int w, int h) {
        Border redline = BorderFactory.createLineBorder(Color.RED);
        Font titleFont = new Font("Calibri", Font.BOLD, 50);

        JTextArea pDesc = new JTextArea();
        pDesc.setFont(new Font("Serif", Font.ITALIC, 50));
        pDesc.setBorder(BorderFactory.createTitledBorder(redline, "Prêmio", TitledBorder.CENTER, TitledBorder.TOP, titleFont));
        pDesc.setLineWrap(true);
        pDesc.setWrapStyleWord(true);
        pDesc.setPreferredSize(new Dimension(w, h));
        pDesc.setForeground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setPreferredSize(new Dimension(w, h));
        panel.add(pDesc);

        return panel;
    }

    private JPanel getNumeroSorteado(int w, int h) {
        Border redline = BorderFactory.createLineBorder(Color.RED);
        Font titleFont = new Font("Calibri", Font.BOLD, 50);

        pSorteioNumberTA.setFont(new Font("Calibri", Font.BOLD, 170));
        pSorteioNumberTA.setBorder(BorderFactory.createTitledBorder(redline, "Número Sorteado", TitledBorder.CENTER, TitledBorder.TOP, titleFont));
        pSorteioNumberTA.setPreferredSize(new Dimension(w, h / 2));
        pSorteioNumberTA.setHorizontalAlignment(SwingConstants.CENTER);
        pSorteioNumberTA.setDocument(new JTextFieldLimit(2));
        pSorteioNumberTA.setForeground(Color.RED);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setBackground(Color.WHITE);
        panel.add(pSorteioNumberTA, BorderLayout.CENTER);
        pSorteioNumberTA.addActionListener(this);

        return panel;
    }
//
//    private JPanel getTituloCartela(int w, int h) {
//        JTextField pSorteioTitle = new JTextField(SORTEIO_LABEL) ;
//        pSorteioTitle.setFont(new Font("Verdana", Font.ITALIC, 30));
//        pSorteioTitle.setHorizontalAlignment(JLabel.CENTER);
//        pSorteioTitle.setBorder(BorderFactory.createEmptyBorder());
//        pSorteioTitle.setPreferredSize(new Dimension(w, h));
//        pSorteioTitle.setBackground(Color.WHITE);
//        pSorteioTitle.setForeground(Color.BLACK);
//        pSorteioTitle.setEditable(false);
//
//        JPanel panel = new JPanel();
//        panel.setBackground(Color.WHITE);
//        panel.setBorder(BorderFactory.createEmptyBorder());
//        panel.setPreferredSize(new Dimension(w, h));
//        panel.add(pSorteioTitle, BorderLayout.NORTH);
//        return panel;
//    }

    private Box getNuemrosSorteados(int w, int h) {
        Box grade = Box.createHorizontalBox();

        grade.add(createWhiteSpace(w / 11, h));
        for (int i = 0; i < pSorteioMapa.length; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder());
            panel.setPreferredSize(new Dimension(w / 11, h));

            JLabel letter = new JLabel(Sorteio.letras[i]);
            letter.setFont(new Font("Verdana", Font.BOLD, 36));
            letter.setVerticalTextPosition(JLabel.TOP);
            letter.setHorizontalTextPosition(JLabel.CENTER);
            letter.setHorizontalAlignment(JLabel.CENTER);
            letter.setVerticalAlignment(JLabel.TOP);
            letter.setForeground(Color.BLACK);
            panel.add(letter, BorderLayout.CENTER);

            JLabel[] col = pSorteioMapa[i];
            for (int j = 0; j < col.length; j++) {
                int number = (i * Sorteio.NUMBER_PER_COL) + (j + 1);
                JLabel label = new JLabel(number < 10 ? "0" + number : "" + number);

                label.setFont(new Font("Verdana", Font.BOLD, 26));
                label.setVerticalTextPosition(JLabel.TOP);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.TOP);
                label.setForeground(Color.LIGHT_GRAY);

                pSorteioMapa[i][j] = label;
                panel.add(label, BorderLayout.CENTER);
            }

            grade.add(panel);
            grade.add(createWhiteSpace(w / 11, h));
        }

        return grade;
    }

    private JPanel createWhiteSpace(int w, int h) {
        JPanel space1 = new JPanel();
        space1.setBackground(Color.WHITE);
        space1.setBorder(BorderFactory.createEmptyBorder());
        space1.setPreferredSize(new Dimension(w, h));
        return space1;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String text = pSorteioNumberTA.getText();
        if (text != null && text.trim().length() > 0) {
            if (text.equals("00")) {
                sorteio.reset();
                pSorteioNumberTA.setText("");
            } else {
                try {
                    sorteio.addNumero(Integer.valueOf(text));
                } catch (Exception e) {
                    e.printStackTrace();
                    pSorteioNumberTA.setText("");
                }
            }

            boolean[][] mapa = sorteio.getMapa();
            for (int i = 0; i < mapa.length; i++) {
                boolean[] col = mapa[i];
                for (int j = 0; j < col.length; j++) {
                    if (mapa[i][j]) {
                        pSorteioMapa[i][j].setForeground(Color.red);
                    } else {
                        pSorteioMapa[i][j].setForeground(Color.lightGray);
                    }
                }
            }
        }
    }

    class JTextFieldLimit extends PlainDocument {
        private static final long serialVersionUID = 1L;
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldLimit(int limit, boolean upper) {
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

    public class JCustomPanel extends JPanel {
        private float widhtWeight;
        private float heightWeight;

        public JCustomPanel(float widhtWeight, float heightWeight) {
            this.widhtWeight = widhtWeight;
            this.heightWeight = heightWeight;
        }

        private Dimension getCustomDimensions(){
            Rectangle bounds = super.getParent().getBounds();
            return new Dimension((int)(bounds.width * widhtWeight), (int)(bounds.height * heightWeight));
        }
        @Override
        public Dimension getMaximumSize(){
            return getCustomDimensions();
        }
        @Override
        public Dimension getMinimumSize(){
            return getCustomDimensions();
        }
        @Override
        public Dimension getPreferredSize(){
            return getCustomDimensions();
        }
    }
}