/**
 * Clasa care retine detaliile despre solicitarea unui Client
 */
public class Solicitare implements Comparable{
    private int idClient;
    private int idProdus;
    private double pretMaximOferit;

    public Solicitare() {
    }

    public Solicitare(int idClient, int idProdus, double pretMaximOferit) {
        this.idClient = idClient;
        this.idProdus = idProdus;
        this.pretMaximOferit = pretMaximOferit;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public double getPretMaximOferit() {
        return pretMaximOferit;
    }

    public void setPretMaximOferit(double pretMaximOferit) {
        this.pretMaximOferit = pretMaximOferit;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.idClient, ((Solicitare) o).idClient);
    }

    @Override
    public String toString() {
        return "Solicitare{" +
                "idClient=" + idClient +
                ", idProdus=" + idProdus +
                ", pretMaximOferit=" + pretMaximOferit +
                '}';
    }
}
