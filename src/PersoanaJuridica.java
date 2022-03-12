/**
 * Informatiile despre o persoana juridica, pe langa
 * cele de Client
 */
public class PersoanaJuridica extends Client {
    private Companie companie;
    private double capitalSocial;

    public PersoanaJuridica() {
    }

    public PersoanaJuridica(Companie companie, double capitalSocial) {
        this.companie = companie;
        this.capitalSocial = capitalSocial;
    }

    public PersoanaJuridica(int id, String nume, String adresa, int nrParticipari, int nrLicitatiiCastigate, Companie companie, double capitalSocial) {
        super(id, nume, adresa, nrParticipari, nrLicitatiiCastigate);
        this.companie = companie;
        this.capitalSocial = capitalSocial;
    }

    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    @Override
    public String toString() {
        return super.afisareDetaliiGeneraleClienti() + '\n' +
                "   PersoanaJuridica: " + '\n' +
                "       companie = " + companie + '\n' +
                "       capitalSocial = " + capitalSocial + "\n\n";
    }
}
