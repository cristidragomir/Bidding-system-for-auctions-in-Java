import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import static java.util.Collections.sort;

/**
 * Clasa CasaLicitatii retine toate atributele necesare
 * pentru organizarea licitatiilor
 */
public class CasaLicitatii {
    private static CasaLicitatii casaLicitatii;
    private CopyOnWriteArrayList<Produs> produsePtVanzare;
    private ArrayList<Client> clientiSistem;
    private ArrayList<Licitatie> licitatiiActive;
    private ArrayList<Solicitare> solicitariClienti;

    private CasaLicitatii() {
    }

    /**
     * Implementarea design-pattern-ului Singleton
     * luand in considerare ca instanta clasei
     * trebuie sa fie unica in tot programul.
     *
     * Pentru mai multe clase de licitatii se pot
     * utiliza fisiere binare in care sa existe
     * mai multe salvari ale caselor de licitatii
     * @return instanta clasei
     */
    public static CasaLicitatii getInstance() {
        if (casaLicitatii == null) {
            casaLicitatii = new CasaLicitatii();
        }
        return casaLicitatii;
    }

    public CopyOnWriteArrayList<Produs> getProdusePtVanzare() {
        return produsePtVanzare;
    }

    public void setProdusePtVanzare(CopyOnWriteArrayList<Produs> produsePtVanzare) {
        this.produsePtVanzare = produsePtVanzare;
    }

    public ArrayList<Client> getClientiSistem() {
        return clientiSistem;
    }

    public void setClientiSistem(ArrayList<Client> clientiSistem) {
        this.clientiSistem = clientiSistem;
    }

    public ArrayList<Licitatie> getLicitatiiActive() {
        return licitatiiActive;
    }

    public void setLicitatiiActive(ArrayList<Licitatie> licitatiiActive) {
        this.licitatiiActive = licitatiiActive;
    }

    public ArrayList<Solicitare> getSolicitariClienti() {
        return solicitariClienti;
    }

    public void setSolicitariClienti(ArrayList<Solicitare> solicitariClienti) {
        this.solicitariClienti = solicitariClienti;
    }

    /**
     * Metoda care asociaza fiecarui client aflat
     * in lista de solicitari un broker
     *
     * @param brokeri
     */
    public void asociereBrokerClient (Broker[] brokeri) {
        sort(solicitariClienti);
        // se vrea ca fiecare client ce participa
        // la una din licitatii sa fie asociat o singura
        // data, unui singur broker
        // nu se poate ca un client sa fie asociat la 2 brokeri
        int idClientCurrent = -1;
        int nrBrokeri = brokeri.length;
        for (Broker broker : brokeri) {
            broker.setClienti(null);
        }
        for (Solicitare i : solicitariClienti) {
            if (idClientCurrent != i.getIdClient()) {
                idClientCurrent = i.getIdClient();
                Client solicitant = Client.findClientById(clientiSistem, idClientCurrent);
                int indexBroker = new Random().nextInt(nrBrokeri);
                if (brokeri[indexBroker].getClienti() == null) {
                    brokeri[indexBroker].setClienti(new ArrayList<>());
                }
                brokeri[indexBroker].getClienti().add(solicitant);
            }
        }
        for (Broker i : brokeri) {
            if (i.getClienti() != null) {
                i.setSumeClienti(new double[i.getClienti().size()]);
                for (int j = 0; j < i.getClienti().size(); j++) {
                    i.getSumeClienti()[j] = 0;
                }
            }
        }
    }

    /**
     * Metoda principala a acestei clase
     * Prin intermediul ei se realizeaza mecanismul de
     * licitare descris in tema
     *
     * Se citesc toate bid-urile din fisierul de intrare
     * Se trece prin lista de licitatii active
     * Se verifica daca sunt suficienti participanti
     * Dupa caz se face notificarea entitatilor
     * Daca licitatia are suficienti participanti
     * atunci se vor organiza mai multe runde de licitatii,
     * existand un numar maxim. La fiecare pas brokerii, la randul
     * lor clientii, vor fi notificati de suma maxima licitata
     * la pasul respectiv. Dupa terminarea acestei etape
     * daca pretul oferit pe obiect este mai mare decat cel minim
     * se va desemna castigatorul, criteriul fiind numarul de licitatii
     * castigate. Ulterior, clientii vor fi notificati de brokeri
     * si se vor reface asocierile broker-client pentru urmatoarea licitatie
     *
     * @param brokers
     * @param admin             are capacitatea de a adauga
     *                          la un moment dat produse in
     *                          lista de produse comercializate
     * @param numeFisierBids    fisier in care sunt stocate
     *                          sumele licitate de clienti
     *                          pentru fiecare pas
     */
    public void desfLicitatii(Broker[] brokers, Administrator admin, String numeFisierBids) {
        ArrayList<String> bids = new ArrayList<>();
        int bidIndex = 0;
        citireBids(numeFisierBids, bids);
        // citirea tuturor bid-urilor(in mod normal ar
        // trebui ca la fiecare pas sa fie interogati clientii
        // si ar fi necesare multe fisiere)
        while (!licitatiiActive.isEmpty()) {
            new CommandAsociereBrokeri().execute(brokers);
            // la fiecare licitatie, fiecare broker isi selecteaza clientii
            Licitatie licitatieCurenta = licitatiiActive.get(0);
            Produs produsInLicitatie = Produs.findProductById(produsePtVanzare, licitatieCurenta.getIdProdus());
            ObserverClient detaliiLicitatie = new ObserverClient();
            detaliiLicitatie.setProdusInLicitatie(produsInLicitatie);
            // detaliile despre licitatie sunt memorate intr-un Observer ce va notifica
            // clientii cu privire la desfasurarea licitatiei
            if (licitatieCurenta.verificareNrParticipanti(solicitariClienti, brokers)) {
                new ObserverBrokers(brokers, 1).toNotify();
                for (Broker i : brokers) {
                    i.notifyClients(produsInLicitatie,1);
                }
                // notificare brokerilor si a clientilor (de catre brokeri)
                // de inceputul licitatiei
                bidIndex = execPasiLicitatie(brokers, bids, bidIndex, licitatieCurenta, detaliiLicitatie);
                anuntareRezultat(brokers, produsInLicitatie, detaliiLicitatie);
            } else {
                new ObserverBrokers(brokers, 2).toNotify();
                for (Broker i : brokers) {
                    i.notifyClients(produsInLicitatie,2);
                 }
                // se afiseaza anuntul cu privire la "suspendarea" licitatiei
                // din cauza neindeplinirii numarului minim de participanti
            }
            licitatiiActive.remove(0);
        }
    }

    /**
     * Metoda care incarca in memorie bid-urile clientilor
     * // Doar pentru testarea automata. In realitate, bid-ul unui
     * client este preluat de broker care va transmite casei de licitatii
     * suma licitata
     * @param numeFisierBids
     * @param bids
     */
    private void citireBids(String numeFisierBids, ArrayList<String> bids) {
        // se citesc toate bid-urile din fisierul de intrare
        Scanner bidsScanner;
        try {
            bidsScanner = new Scanner(new File(numeFisierBids));
            while(bidsScanner.hasNextLine()) {
                bids.add(bidsScanner.nextLine());
            }
        } catch (IOException ignored) {}
    }

    /**
     * Se executa ficare runda de licitatii asupra unui produs
     * Dupa fiecare runda se calculeaza suma maxima licitata
     * Brokeri, clientii sunt notificati(de catre brokeri) de noua suma
     * Se reia procedeul pana se atinge numarul maxim de pasi permis
     * @param brokers
     * @param bids
     * @param bidIndex              parametru necesar pentru a delimita intre bid-urile
     *                              clientilor la o licitatie, respectiv bid-urile realizate
     *                              intr-o runda de licitatie
     * @param licitatieCurenta
     * @param detaliiLicitatie      parametru prin care se retin detaliile despre licitatia
     *                              curenta - suma maxima, clienti potentiali castigatori
     * @return
     */
    private int execPasiLicitatie(Broker[] brokers, ArrayList<String> bids, int bidIndex,
                                  Licitatie licitatieCurenta, ObserverClient detaliiLicitatie) {
        Produs produsInLicitatie = detaliiLicitatie.getProdusInLicitatie();
        int nrPasiMaxim = licitatieCurenta.getNrPasiMaxim();
        for (int i = 0; i < nrPasiMaxim; i++) {
            for (int j = bidIndex; j < bids.size(); j++) {
                String[] bidDetails = bids.get(j).split(" ");
                if (bidDetails[0].equals("--")) {
                    bidIndex = j + 1;
                    break;
                }
                Client bidder = Client.findClientById(clientiSistem,
                        Integer.parseInt(bidDetails[1]));
                if (bidder != null) {
                    bidder.bid(brokers, Double.parseDouble(bidDetails[2]),
                            solicitariClienti, licitatieCurenta.getIdProdus());
                }
            }
            double sMax = calculSumaMaxima(brokers, detaliiLicitatie);
            // se calculeaza suma maxima si se actualizeaza lista clientilor
            // care au oferit acelasi pret maxim
            detaliiLicitatie.setSumaMaxLicitata(sMax);
            new ObserverBrokers(brokers, sMax, 3).toNotify();
            for (Broker j : brokers) {
                j.notifyClients(produsInLicitatie, sMax, 3);
            }
            // notificarea clientilor de catre brokeri cu privire la
            // status-ul licitatiei
        }
        return bidIndex;
    }

    /**
     * Se realizeaza notificarea tuturor brokerilor
     * De asemenea, clientii sunt notificati de catre brokeri
     * cu privire la rezultatul licitatiei
     * @param brokers
     * @param produsScosLaLicitatie
     * @param detaliiLicitatie
     */
    private void anuntareRezultat(Broker[] brokers, Produs produsScosLaLicitatie,
                                  ObserverClient detaliiLicitatie) {
        if (determinaCastigator(brokers, detaliiLicitatie)) {
            new ObserverBrokers(brokers, detaliiLicitatie.getSumaMaxLicitata(),
                    detaliiLicitatie.getClientiCastigatori().get(0), 4);
            for (Broker broker : brokers) {
                broker.notifyClients(detaliiLicitatie.getClientiCastigatori(),
                        detaliiLicitatie.getProdusInLicitatie(),
                        detaliiLicitatie.getSumaMaxLicitata(), 4);
            }
            eliminareProdDinStoc(brokers);
            // se anunta brokerii, care la randul lor anunta clientii
            // cu privire la castigatorul licitatiei
        } else {
            new ObserverBrokers(brokers, 5).toNotify();
            for (Broker i : brokers) {
                i.notifyClients(produsScosLaLicitatie,5);
            }
            // daca licitatia nu a atins pretul minim se notifica brokerii,
            // care la randul lor anunta clientii, cu privire la esec
        }
    }

    /**
     * Algoritmul de determinare a castigatorului enuntat in metoda
     * desfLicitatie()
     *
     * @param brokers
     * @param detaliiLicitatie
     * @return
     */
    private boolean determinaCastigator(Broker[] brokers, ObserverClient detaliiLicitatie) {
        if (detaliiLicitatie.getProdusInLicitatie().getPretMinim() > detaliiLicitatie.getSumaMaxLicitata()) {
            return false;
            // daca pretul minim al produsului nu a fost atins, produsul nu se vinde
        }
        Client castigator = detaliiLicitatie.getClientiCastigatori().get(0);
        for (int i = 1; i < detaliiLicitatie.getClientiCastigatori().size(); i++) {
            Client oponent = detaliiLicitatie.getClientiCastigatori().get(i);
            if (castigator.getNrLicitatiiCastigate() < oponent.getNrLicitatiiCastigate()) {
                castigator = oponent;
            }
            // daca 2 sau mai multi clienti au oferit acelasi pret maxim, mai mare decat
            // pretul minim de vanzare se alege cel cu numarul mai mare
            // de licitatii castigate
        }
        detaliiLicitatie.setClientiCastigatori(new ArrayList<>());
        detaliiLicitatie.getClientiCastigatori().add(castigator);
        for (Broker i : brokers) {
            Client updateClient = Client.findClientById(i.getClienti(), castigator.getId());
            if (updateClient != null) {
                i.updateNrLicitatiiCastigate(updateClient);
                i.retinereComision(castigator, detaliiLicitatie.getSumaMaxLicitata());
            }
            // se actualizeaza numarul de licitatii castigate al celui
            // intrat in posesia produsului
        }
        return true;
    }

    /**
     * Se trece prin toate sumele retinute de brokeri ca urmare a licitatiei
     * si se calculeaza suma maxima pentru pasul/runda k din licitatia curenta
     * @param brokers
     * @param detaliiLicitatie
     * @return
     */
    private double calculSumaMaxima(Broker[] brokers, ObserverClient detaliiLicitatie) {
        double sMax = -1;
        for (Broker broker : brokers) {
            if (broker.getClienti() != null) {
                double[] sumeClienti = broker.getSumeClienti();
                for (int j = 0; j < sumeClienti.length; j++) {
                    if (sumeClienti[j] > sMax) {
                        sMax = sumeClienti[j];
                        detaliiLicitatie.setClientiCastigatori(new ArrayList<>());
                        detaliiLicitatie.getClientiCastigatori().add(broker.getClienti().get(j));
                    } else if (sumeClienti[j] == sMax) {
                        detaliiLicitatie.getClientiCastigatori().add(broker.getClienti().get(j));
                    }
                }
            }
        }
        return sMax;
    }

    /**
     * Daca produsul este vandut, un broker
     * va sterge din baza de date informatiile
     * despre produsul respectiv
     * @param brokers
     */
    private void eliminareProdDinStoc(Broker[] brokers) {
        // unul din brokeri va avea sarcina de a sterge
        // produsul din lista de produse
        Object[] entities = new Object[2];
        String[] delArgs = new String[3];
        delArgs[0] = "broker";
        delArgs[1] = "delprodus";
        delArgs[2] = String.valueOf(licitatiiActive.get(0).getIdProdus());
        entities[0] = brokers[new Random().nextInt(brokers.length)];
        entities[1] = delArgs;
        new CommandBrokerRequest().execute(entities);
    }

    /**
     * Stergerea referintei catre obiectul de tip
     * CasaLicitatii (numai pentru testare automata)
     */
    public void delCasaLicitatii() {
        casaLicitatii = null;
    }
}
