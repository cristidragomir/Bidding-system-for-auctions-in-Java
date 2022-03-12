import java.time.LocalTime;

/**
 * Observator pentru brokeri
 * In functiile de informatiile necesare formularii
 * anuntului se apeleaza constructori specifici
 */
public class ObserverBrokers implements Observer {
    private final Broker[] brokersToNotify;
    private double sumaLicitata;
    private Client clientCastigator;
    private final int tipAnunt;

    public ObserverBrokers(Broker[] brokersToNotify, int tipAnunt) {
        this.brokersToNotify = brokersToNotify;
        this.tipAnunt = tipAnunt;
    }

    public ObserverBrokers(Broker[] brokersToNotify, double sumaLicitata, int tipAnunt) {
        this.brokersToNotify = brokersToNotify;
        this.sumaLicitata = sumaLicitata;
        this.tipAnunt = tipAnunt;
    }

    public ObserverBrokers(Broker[] brokersToNotify, double sumaLicitata, Client clientCastigator, int tipAnunt) {
        this.brokersToNotify = brokersToNotify;
        this.sumaLicitata = sumaLicitata;
        this.tipAnunt = tipAnunt;
        this.clientCastigator = clientCastigator;
    }

    /**
     * Fiecare tip de anunt are nevoie de diverse atribute
     * (declarate la inceputul clasei) pentru
     * a formula anunturi utile. Mai mult, fiecare
     * tip de anunt are asociat un numar
     */
    @Override
    public void toNotify() {
        for (Broker i : brokersToNotify) {
            if (i.getLog() == null) {
                i.setLog("");
            }
            if (tipAnunt == 1) {
                i.setLog(i.getLog() + LocalTime.now()
                        + " Pregatiti-va Brokeri! Licitatia a inceput!" + '\n');
            } else if (tipAnunt == 2) {
                i.setLog(i.getLog() + LocalTime.now()
                        + " Licitatia nu a putut incepe din cauza numarului mic de participanti" + '\n');
            } else if (tipAnunt == 3) {
                i.setLog(i.getLog() + LocalTime.now()
                        + " Produsul curent a ajuns la valoarea " + sumaLicitata + '\n');
            } else if (tipAnunt == 4) {
                i.setLog(i.getLog() + LocalTime.now()
                        + " Produsul curent a fost vandut clientului " + clientCastigator
                        + " pentru suma de " + sumaLicitata + '\n');
            } else if (tipAnunt == 5) {
                i.setLog(i.getLog() + LocalTime.now()
                        + " Licitatia a avut loc, dar produsul nu a " +
                        "fost vandut intrucat nu s-a atins pretul minim" + '\n');
            }
        }
    }
}
