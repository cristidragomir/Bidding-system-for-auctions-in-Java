import java.util.ArrayList;

/**
 * Comanda care adauga in lista specifica o solicitare emisa
 * de un Client
 */
public class CommandInregistrareSolicitare implements Command{
    @Override
    // Sintaxa comenzii
    // solicitare idClient idProdus pretMaxim oferit
    public void execute(Object[] args) {
        Solicitare solicitare = new Solicitare();
        solicitare.setIdClient(Integer.parseInt((String)args[1]));
        solicitare.setIdProdus(Integer.parseInt((String) args[2]));
        solicitare.setPretMaximOferit(Double.parseDouble((String)args[3]));
        if (CasaLicitatii.getInstance().getSolicitariClienti() == null) {
            CasaLicitatii.getInstance().setSolicitariClienti(new ArrayList<>());
        }
        CasaLicitatii.getInstance().getSolicitariClienti().add(solicitare);
    }
}
