/**
 * comanda pentru afisarea detaliilor unor produse aflate intr-o lista
 */
public class CommandAfisareProduse implements Command {
    @Override
    public void execute(Object[] args) {
        Produs.afisareProduse(CasaLicitatii.getInstance().getProdusePtVanzare());
    }
}
