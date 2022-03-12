/**
 * Clasa Bijuterie reprezinta un tip de Produs
 * cu detalii suplimentare
 */
public class Bijuterie extends Produs{
    private String material;
    private boolean piatraPretioasa;

    public Bijuterie() {
    }

    public Bijuterie(String material, boolean piatraPretioasa) {
        this.material = material;
        this.piatraPretioasa = piatraPretioasa;
    }

    public Bijuterie(int id, String nume, double pretVanzare,
                     double pretMinim, int an, String material, boolean piatraPretioasa) {
        super(id, nume, pretVanzare, pretMinim, an);
        this.material = material;
        this.piatraPretioasa = piatraPretioasa;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isPiatraPretioasa() {
        return piatraPretioasa;
    }

    public void setPiatraPretioasa(boolean piatraPretioasa) {
        this.piatraPretioasa = piatraPretioasa;
    }

    @Override
    public String toString() {
        return super.generalToString() +
                "\n" +
                "   Bijuterie: " + '\n' +
                "       material = " + material + '\n' +
                "       piatraPretioasa = " + piatraPretioasa + "\n\n";
    }
}
