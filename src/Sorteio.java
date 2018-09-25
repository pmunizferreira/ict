import java.util.ArrayList;
import java.util.Collections;


public class Sorteio {
    static final int NUMBER_PER_COL = 15;
    static final int NUMBER_COL = 5;
    ArrayList<Integer> listaSorteada = new ArrayList<>();
    boolean[][] mapa = new boolean[NUMBER_COL][NUMBER_PER_COL];
    static String[] letras = new String[]{" B ", " I ", " N ", " G ", " O "};

    public boolean[][] getMapa() {
        return mapa;
    }

    public void reset() {
        listaSorteada.clear();
        mapa = new boolean[NUMBER_COL][NUMBER_PER_COL];
    }

    public void addNumero(Integer numero) {
        if (numero != null && !listaSorteada.contains(numero)) {
            int col = (numero - 1) / NUMBER_PER_COL;
            int row = (numero - 1) % NUMBER_PER_COL;
            mapa[col][row] = true;

            listaSorteada.add(numero);
            Collections.sort(listaSorteada);
        }
    }
}
