import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Clasa Produs retine detaliile generale cu privire
 * la un produs disponibil in casa de licitatii
 */
public class Produs {
    private int id;
    private String nume;
    double pretVanzare;
    double pretMinim;
    int an;

    public Produs() {
    }

    public Produs(int id, String nume, double pretVanzare, double pretMinim, int an) {
        this.id = id;
        this.nume = nume;
        this.pretVanzare = pretVanzare;
        this.pretMinim = pretMinim;
        this.an = an;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPretVanzare() {
        return pretVanzare;
    }

    public void setPretVanzare(double pretVanzare) {
        this.pretVanzare = pretVanzare;
    }

    public double getPretMinim() {
        return pretMinim;
    }

    public void setPretMinim(double pretMinim) {
        this.pretMinim = pretMinim;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public String generalToString() {
        return "Detalii generale produs: " + "\n" +
                "   id = " + id + "\n" +
                "   nume = " + nume + '\n' +
                "   pretVanzare = " + pretVanzare + '\n' +
                "   pretMinim = " + pretMinim + '\n' +
                "   an = " + an;
    }

    /**
     * Metoda pentru afisarea detaliilor generale
     * si particulare(ex: Bijuterie) despre produsele
     * dintr-o lista
     * @param deAfisat
     */
    public static void afisareProduse(CopyOnWriteArrayList<Produs> deAfisat) {
        for (Produs i : deAfisat) {
            System.out.print(i.toString());
        }
    }

    /**
     * Cautarea unui produs dupa id
     * @param produse
     * @param id
     * @return
     */
    public static Produs findProductById(CopyOnWriteArrayList<Produs> produse, int id) {
        for (Produs i : produse) {
            if (i.id == id) {
                return i;
            }
        }
        return null;
    }
}
