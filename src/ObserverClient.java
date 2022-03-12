import java.util.ArrayList;
/**
 * Observator pentru clienti
 * In functiile de informatiile necesare formularii
 * anuntului se apeleaza constructori specifici
 */
public class ObserverClient implements Observer {
    private ArrayList<Client> clientsToNotify;
    private ArrayList<Client> clientiCastigatori;
    private int tipAnunt;
    private Produs produsInLicitatie;
    private double sumaMaxLicitata = 0;

    public ObserverClient(){}

    public ObserverClient(ArrayList<Client> clientsToNotify, Produs produsInLicitatie, int tipAnunt) {
        this.clientsToNotify = clientsToNotify;
        this.produsInLicitatie = produsInLicitatie;
        this.tipAnunt = tipAnunt;
    }

    public ObserverClient(ArrayList<Client> clientsToNotify, Produs produsInLicitatie, double sumaMaxLicitata, int tipAnunt) {
        this.clientsToNotify = clientsToNotify;
        this.produsInLicitatie = produsInLicitatie;
        this.sumaMaxLicitata = sumaMaxLicitata;
        this.tipAnunt = tipAnunt;
    }

    public ObserverClient(ArrayList<Client> clientsToNotify, int tipAnunt) {
        this.clientsToNotify = clientsToNotify;
        this.tipAnunt = tipAnunt;
    }

    public ObserverClient(ArrayList<Client> clientsToNotify, ArrayList<Client> clientiCastigatori,
                          Produs produsInLicitatie, double sumaMaxLicitata, int tipAnunt) {
        this.clientsToNotify = clientsToNotify;
        this.clientiCastigatori = clientiCastigatori;
        this.tipAnunt = tipAnunt;
        this.produsInLicitatie = produsInLicitatie;
        this.sumaMaxLicitata = sumaMaxLicitata;
    }

    public int getTipAnunt() {
        return tipAnunt;
    }

    public void setTipAnunt(int tipAnunt) {
        this.tipAnunt = tipAnunt;
    }

    public double getSumaMaxLicitata() {
        return sumaMaxLicitata;
    }

    public void setSumaMaxLicitata(double sumaMaxLicitata) {
        this.sumaMaxLicitata = sumaMaxLicitata;
    }

    public Produs getProdusInLicitatie() {
        return produsInLicitatie;
    }

    public void setProdusInLicitatie(Produs produsInLicitatie) {
        this.produsInLicitatie = produsInLicitatie;
    }

    public ArrayList<Client> getClientiCastigatori() {
        return clientiCastigatori;
    }

    public void setClientiCastigatori(ArrayList<Client> clientiCastigatori) {
        this.clientiCastigatori = clientiCastigatori;
    }

    @Override
    public void toNotify() {
        if (clientsToNotify == null) {
            return;
        }
        for (Client i : clientsToNotify) {
            if (i.getLog() == null) {
                i.setLog("");
            }
            if (tipAnunt == 1) {
                i.setLog(i.getLog() + "Atentie clienti! Se liciteaza pentru urmatorul produs: "
                        + '\n' + produsInLicitatie.toString());
            } else if (tipAnunt == 2) {
                i.setLog(i.getLog() + "Licitatia nu a " +
                        "putut incepe din cauza numarului " +
                        "redus de participanti\n");
            } else if (tipAnunt == 3) {
                i.setLog(i.getLog() + "Pentru produsul cu numele " + produsInLicitatie.getNume()
                        + " s-a licitat suma de " + sumaMaxLicitata + '\n');
            } else if (tipAnunt == 4) {
                i.setLog(i.getLog() + "Clientul "
                        + clientiCastigatori.get(0).getNume()
                        + " a achizitionat produsul "
                        + produsInLicitatie.getNume()
                        + " la pretul de " + sumaMaxLicitata + '.' + " Felicitari!\n");
            } else if (tipAnunt == 5) {
                i.setLog(i.getLog() + "Licitatia a avut loc, dar produsul" +
                        " nu a fost vandut din cauza neatingerii pretului minim\n");
            }
        }
    }
}
