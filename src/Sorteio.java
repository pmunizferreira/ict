import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Sorteio implements ActionListener {
    private static String FILE_NAME = "sorteio_ICT.txt";

    private Tela tela;
    private List<String> values = new ArrayList<>();
    private List<String> sorted = new ArrayList<>();
    private Random rand = new Random();

    public Sorteio() {
        tela = new Tela(Tela.Mode.SORTEIO);
        tela.setActionListener(this);
        tela.setVisible(true);

        try {
            loadFile();
            tela.loadValuesToSorteio(values);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Sorteio();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            String text = tela.getInput();

            switch (event.getActionCommand()) {
                case "inputField":
                case "addButton":
                    if (text != null && text.trim().length() > 0) {
                        addValue(text);
                        tela.loadValuesToSorteio(values);
                    }
                    break;
                case "removeButton":
                    if (text != null && text.trim().length() > 0) {
                        int result = Tela.confirmYesNo(
                                "Deseja remover o número do sorteio?",
                                "REMOVER NÚMEROS"
                        );

                        if (result == 0) {
                            removeValue(text);
                            tela.loadValuesToSorteio(values);
                        }
                    }
                    break;
                case "resetButton":
                    int result = Tela.confirmYesNo(
                            "Deseja remover todos os números do Sorteio? Você não poderá recuperá-los depois disso.",
                            "REMOVER TODOS OS NÚMEROS"
                    );

                    if (result == 0) {
                        reset();
                        tela.setInput("");
                        tela.loadValuesToSorteio(values);
                    }
                    break;
                case "applyButton":
                    if (values.size() > 1 && !sorted.containsAll(values)) {
                        String random = "";
                        do {
                            int index = rand.nextInt(values.size());
                            random = values.get(index);
                        }
                        while (sorted.contains(random));

                        sorted.add(random);
                        Tela.showSorteioDialog(random);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reset() {
        try {
            values.clear();
            sorted.clear();

            File file = new File(FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException("Arquivo nao pode ser limpo. Feche o arquivo.");
        }
    }

    public void addValue(String value) {
        if (!values.contains(value)) {
            try {
                values.add(value);
                writeFile(value);
            } catch (Exception e) {
                throw new RuntimeException("Arquivo nao pode ser modificado. Feche o arquivo.");
            }
        } else {
            throw new RuntimeException("Número '"+value+"' já foi inserido antes.");
        }
    }

    public void removeValue(String value) {
        if (values.contains(value)) {
            try {
                values.remove(value);
                writeFile();
            } catch (Exception e) {
                throw new RuntimeException("Arquivo nao pode ser modificado. Feche o arquivo.");
            }
        } else {
            throw new RuntimeException("Número '"+value+"' não inserido antes.");
        }
    }

    private void writeFile(String... lines) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(FILE_NAME, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);

            if (lines != null && lines.length > 0) {
                for (String line: lines) {
                    if (line != null) {
                        out.println(line);
                    }
                }
            } else {
                out.write("");
            }

            out.close();
        } finally {
            if(out != null)
                out.close();
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) { }
            try {
                if(fw != null)
                    fw.close();
            } catch (IOException e) { }
        }
    }

    public void loadFile() throws Exception {
        // The name of the file to open.
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(FILE_NAME);
            bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                if (!values.contains(line)) {
                    values.add(line);
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException ex) {
            try {
                values.clear();
                sorted.clear();
                writeFile();
            } catch (Exception e) {
                throw new Exception("Arquivo do sorteio nao pode ser criado.");
            }
        } catch (IOException ex) {
            throw new Exception("Arquivo do sorteio nao pode ser lido.");
        } finally {
            // Always close files.
            if (bufferedReader != null)
                bufferedReader.close();
            try {
                if(fileReader != null)
                    fileReader.close();
            } catch (IOException e) { }

        }
    }

}
