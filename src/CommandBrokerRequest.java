/**
 * comanda care permite unui Broker sa stearga un produs din
 * lista de produse comercializate
 *
 * Aceasta comanda ruleaza intr-un thread nou, astfel
 * incat sa nu perturbeze desfasurarea licitatiei
 */
public class CommandBrokerRequest implements Command{
    @Override
    // Sintaxa comezii
    // broker delprodus id
    public void execute(Object[] args) {
        Broker broker = (Broker)args[0];
        broker.setRemoveProductCommand("");
        for (int i = 1; i < ((String[])args[1]).length; i++) {
            broker.setRemoveProductCommand(broker.getRemoveProductCommand()
                    + ((String[])args[1])[i] + ' ');
        }
        Thread t = new Thread(broker);
        t.start();
        while (t.isAlive()){}
    }
}
