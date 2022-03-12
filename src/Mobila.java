/**
 * Clasa Mobila reprezinta un tip de Produs ce contine
 * detalii suplimentare
 */
public class Mobila extends Produs{
    private String tip;
    private String material;

    public Mobila() {
    }

    public Mobila(String tip, String material) {
        this.tip = tip;
        this.material = material;
    }

    public Mobila(int id, String nume, double pretVanzare, double pretMinim, int an, String tip, String material) {
        super(id, nume, pretVanzare, pretMinim, an);
        this.tip = tip;
        this.material = material;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return super.generalToString() +
                "\n" +
                "   Mobila: " + '\n' +
                "       tip = " + tip + '\n' +
                "       material = " + material + "\n\n";
    }
}
