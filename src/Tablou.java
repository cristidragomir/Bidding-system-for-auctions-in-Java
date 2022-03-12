/**
 * Clasa Tablou este un tip de Produs, cu detalii suplimentare
 */
public class Tablou extends Produs{
    private String numePictor;
    private StilPictura culori;

    public Tablou() {
    }

    public Tablou(String numePictor, StilPictura culori) {
        this.numePictor = numePictor;
        this.culori = culori;
    }

    public Tablou(int id, String nume, double pretVanzare, double pretMinim, int an, String numePictor, StilPictura culori) {
        super(id, nume, pretVanzare, pretMinim, an);
        this.numePictor = numePictor;
        this.culori = culori;
    }

    public String getNumePictor() {
        return numePictor;
    }

    public void setNumePictor(String numePictor) {
        this.numePictor = numePictor;
    }

    public StilPictura getCulori() {
        return culori;
    }

    public void setCulori(StilPictura culori) {
        this.culori = culori;
    }

    @Override
    public String toString() {
        return super.generalToString() +
                "\n" +
                "   Tablou: " + '\n' +
                "       numePictor = " + numePictor + '\n' +
                "       culori = " + culori + "\n\n";
    }
}
