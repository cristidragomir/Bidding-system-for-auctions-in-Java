import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa Broker face parte din Angajatii casei
 * de licitatii. El are capacitatea de a-si
 * asigna clieni, de a-i notifica si de a sterge produsele
 * ce au fost vandute in urma licitatiei
 */
public class Broker extends Angajat implements Runnable {
    private ArrayList<Client> clienti;
    private double[] sumeClienti;
    private String removeProductCommand;
    private String log;
    private ArrayList<CalculatorComision> tipuriComisioane;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public ArrayList<Client> getClienti() {
        return clienti;
    }

    public void setClienti(ArrayList<Client> clienti) {
        this.clienti = clienti;
    }

    public String getRemoveProductCommand() {
        return removeProductCommand;
    }

    public void setRemoveProductCommand(String removeProductCommand) {
        this.removeProductCommand = removeProductCommand;
    }

    public double[] getSumeClienti() {
        return sumeClienti;
    }

    public void setSumeClienti(double[] sumeClienti) {
        this.sumeClienti = sumeClienti;
    }

    public ArrayList<CalculatorComision> getTipuriComisioane() {
        return tipuriComisioane;
    }

    public void setTipuriComisioane(ArrayList<CalculatorComision> tipuriComisioane) {
        this.tipuriComisioane = tipuriComisioane;
    }

    /**
     * Pentru a tine informati clientii, brokerii
     * trebuie sa faca diverse anunturi clientilor
     * @param produsScosLaLicitatie
     * @param tipAnunt
     */
    public void notifyClients(Produs produsScosLaLicitatie, int tipAnunt) {
        if (tipAnunt == 1)
            new ObserverClient(clienti, produsScosLaLicitatie, tipAnunt).toNotify();
        else if (tipAnunt == 2)
            new ObserverClient(clienti, 2).toNotify();
        else if (tipAnunt == 5)
            new ObserverClient(clienti, 5).toNotify();
    }

    /**
     * Pentru a tine informati clientii, brokerii
     * trebuie sa faca diverse anunturi clientilor
     * @param produsScosLaLicitatie
     * @param sumaLicitata
     * @param tipAnunt
     */
    public void notifyClients(Produs produsScosLaLicitatie, double sumaLicitata, int tipAnunt) {
        if (tipAnunt == 3)
            new ObserverClient(clienti, produsScosLaLicitatie,
                    sumaLicitata, tipAnunt).toNotify();
    }

    /**
     * Pentru a tine informati clientii, brokerii
     * trebuie sa faca diverse anunturi clientilor
     * @param clientCastigator
     * @param produsScosLaLicitatie
     * @param sumaLicitata
     * @param tipAnunt
     */
    public void notifyClients(ArrayList<Client> clientCastigator,
                              Produs produsScosLaLicitatie,
                              double sumaLicitata, int tipAnunt) {
        if (tipAnunt == 4)
            new ObserverClient(clienti, clientCastigator, produsScosLaLicitatie,
                    sumaLicitata, tipAnunt).toNotify();
    }

    /**
     * Actualizarea numarului de participari ale clientului
     * @param client
     */
    public void updateNrParticipari(Client client) {
        client.setNrParticipari(client.getNrParticipari() + 1);
    }

    /**
     * Actualizarea numarului de licitatii ale clientului
     * @param client
     */
    public void updateNrLicitatiiCastigate(Client client) {
        client.setNrLicitatiiCastigate(client.getNrLicitatiiCastigate() + 1);
    }

    /**
     * Pentru fiecare licitatie incheiata cu succes
     * broker-ul isi asigura o parte din castig
     * in functie de statutul clientului si
     * valoarea tranzactionata
     * @param client
     * @param valTranzationata
     */
    public void retinereComision(Client client, double valTranzationata) {
        double comision = -1;
        for (CalculatorComision i : tipuriComisioane) {
            comision = i.calculate(client, valTranzationata);
            if (comision > -1) {
                break;
            }
        }
        if (log == null) {
            log = "";
        }
        log += "Ati incasat suma de " + comision + " in urma tranzactiei\n";
    }

    /**
     * Revine in sarcina brokerului sa stearga
     * din lista de produse acela care a fost
     * vandut. Pentru a nu perturba desfasurarea celorlalte
     * licitatii, acest lucru se realizeaza intr-un thread
     * separat.
     */
    @Override
    public void run() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            String[] commandArgs = removeProductCommand.split(" ");
            new CommandStergeProdus().execute(commandArgs);
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        } finally {
            lock.unlock();
        }
    }
}
