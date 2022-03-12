/**
 * comanda care va apela metoda specifica din casa de licitatii
 * pentru a asocia un broker clientilor
 */
public class CommandAsociereBrokeri implements Command{
    @Override
    public void execute(Object[] args) {
        CasaLicitatii.getInstance().asociereBrokerClient((Broker[])args);
    }
}
