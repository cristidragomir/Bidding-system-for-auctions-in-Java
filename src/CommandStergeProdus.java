/**
 * Comanda pentru eliminarea produsului dintr-o lista
 */
public class CommandStergeProdus implements Command{
    @Override
    // Sintaxa comenzii:
    // delprodus id
    public void execute(Object[] args) {
        Produs produsDeSters = Produs.findProductById(CasaLicitatii.getInstance().getProdusePtVanzare(),
                Integer.parseInt((String)args[1]));
        CasaLicitatii.getInstance().getProdusePtVanzare().remove(produsDeSters);
    }
}
