/**
 * Informatiile despre o persoana fizica, pe langa
 * cele de Client
 */
public class PersoanaFizica extends Client{
    private String dataNastere;

    public PersoanaFizica() {
    }

    public PersoanaFizica(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    public PersoanaFizica(int id, String nume, String adresa, int nrParticipari, int nrLicitatiiCastigate, String dataNastere) {
        super(id, nume, adresa, nrParticipari, nrLicitatiiCastigate);
        this.dataNastere = dataNastere;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    @Override
    public String toString() {
        return super.afisareDetaliiGeneraleClienti() + '\n' +
                "   Persoana fizica: " + '\n' +
                "       dataNastere = " + dataNastere + "\n\n";
    }
}
