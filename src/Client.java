import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Clasa Client retine detaliile despre persoanele
 * care vor sa participe in cadrul licitatiilor
 */
public class Client {
    private int id;
    private String nume;
    private String adresa;
    private int nrParticipari;
    private int nrLicitatiiCastigate;
    private String log;

    public Client() {
    }

    public Client(int id, String nume, String adresa, int nrParticipari, int nrLicitatiiCastigate) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.nrParticipari = nrParticipari;
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNrParticipari() {
        return nrParticipari;
    }

    public void setNrParticipari(int nrParticipari) {
        this.nrParticipari = nrParticipari;
    }

    public int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    public void setNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    /**
     * Metoda prin care un client poate vedea ce produse
     * vor intra in licitatie la un moment de timp
     */
    public void vizualizareLicitatiiActive() {
        ArrayList<Licitatie> licitatii = CasaLicitatii.getInstance().getLicitatiiActive();
        CopyOnWriteArrayList<Produs> produse = CasaLicitatii.getInstance().getProdusePtVanzare();
        if (log == null) {
            log = "";
        }
        log += "Urmatoarele produse vor fi scoase la licitatie:\n";
        for (Licitatie i : licitatii) {
            Produs foundProd = Produs.findProductById(produse, i.getIdProdus());
            if (foundProd != null) {
                log += foundProd.toString() + '\n';
            }
        }
    }

    /**
     * Metoda care permite personalului sa vada datele
     * despre o anumita persoana retinuta de casa de licitatii
     * @return
     */
    public String afisareDetaliiGeneraleClienti() {
        return "Detalii generale client:" + '\n' +
                "   id = " + id + '\n' +
                "   nume = " + nume + '\n' +
                "   adresa = " + adresa + '\n' +
                "   nrParticipari = " + nrParticipari + '\n' +
                "   nrLicitatiiCastigate = " + nrLicitatiiCastigate;
    }

    public static void afisareClienti(ArrayList<Client> listaClienti) {
        for (Client i : listaClienti) {
            System.out.println(i.toString());
        }
    }

    /**
     * Cautarea unui client intr-o lista de clienti pe baza
     * unui id
     * @param listaClienti
     * @param id
     * @return
     */
    public static Client findClientById(ArrayList<Client> listaClienti, int id) {
        if (listaClienti == null) {
            return null;
        }
        for (Client i : listaClienti) {
            if (id == i.id) {
                return i;
            }
        }
        return null;
    }

    /**
     * Un client poate verifica suma maxima licitata pentru
     * produsul curent
     * @param detaliiLicitCurenta
     */
    public void verifSumaCurenta(ObserverClient detaliiLicitCurenta) {
        if (log == null) {
            log = "";
        }
        log += "Oferta pentru produsul curent s-a ridicat la: " +
                detaliiLicitCurenta.getSumaMaxLicitata();
    }

    /**
     * Un client va trimite brokerului asignat suma cu care
     * ar vrea sa obtina obiectul aflat in licitatie
     * Totodata, daca suma ce se vrea a fi depusa depaseste suma
     * maxima pe care clientul este dispus sa o dea, suma transmisa
     * brokerului va fi cea maxima inscrisa in solicitarea clientului
     * @param brokers
     * @param sumaLicitata
     * @param solicitariClienti
     * @param idProdus
     */
    public void bid(Broker[] brokers, double sumaLicitata,
                    ArrayList<Solicitare> solicitariClienti, int idProdus) {
        for (Broker broker : brokers) {
            int indexInBrokerList = -1;
            if (broker.getClienti() != null) {
                indexInBrokerList = broker.getClienti().indexOf(this);
            }
            if (indexInBrokerList != -1) {
                if (sumaLicitata > broker.getSumeClienti()[indexInBrokerList]) {
                    (broker.getSumeClienti())[indexInBrokerList] = sumaLicitata;
                    double plafonClient = -1;
                    for (Solicitare i : solicitariClienti) {
                        if (this.id == i.getIdClient() && idProdus == i.getIdProdus()) {
                            plafonClient = i.getPretMaximOferit();
                        }
                    }
                    if (sumaLicitata > plafonClient) {
                        broker.getSumeClienti()[indexInBrokerList] = plafonClient;
                    }
                }
            }
        }
    }
}
