import java.util.ArrayList;

/**
 * Clasa in care sunt incapsulate informatiile despre o licitatie
 */
public class Licitatie {
    private int id;
    private int nrParticipanti;
    private int idProdus;
    private int nrPasiMaxim;

    public Licitatie() {
    }

    public Licitatie(int id, int nrParticipanti, int idProdus, int nrPasiMaxim) {
        this.id = id;
        this.nrParticipanti = nrParticipanti;
        this.idProdus = idProdus;
        this.nrPasiMaxim = nrPasiMaxim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getNrPasiMaxim() {
        return nrPasiMaxim;
    }

    public void setNrPasiMaxim(int nrPasiMaxim) {
        this.nrPasiMaxim = nrPasiMaxim;
    }

    /**
     * Se numara participantii interesati de produsul acestei licitatii
     * Totodata, se actualizeaza numarul de participari ale solicitantilor
     * (doar celor care vor sa participe la aceasta licitatie, intrucat
     * in lista de solicitari sunt clienti care sunt interesati de alte produse)
     * Daca numarul de participanti este cel minim sau mai mult atunci
     * se porneste licitatia
     * @param solicitari
     * @param brokers
     * @return
     */
    public boolean verificareNrParticipanti(ArrayList<Solicitare> solicitari, Broker[] brokers){
        int cntNrParticipanti = 0;
        for (Solicitare i : solicitari) {
            if (i.getIdProdus() == idProdus) {
                cntNrParticipanti++;
                Client solicitant;
                for (Broker j : brokers) {
                    solicitant = Client.findClientById(j.getClienti(), i.getIdClient());
                    if (solicitant != null) {
                        j.updateNrParticipari(solicitant);
                    }
                }
            }
        }
        return cntNrParticipanti >= nrParticipanti;
    }
}
