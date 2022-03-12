import java.util.ArrayList;

/**
 * comanda pentru adaugarea unei licitatii in lista de licitatii active
 */
public class CommandAdaugareLicitatie implements Command {
    // Sintaxa comenzii
    // licitatie idLicitatie nrParticipanti idProdus nrPasiMaxim
    @Override
    public void execute(Object[] args) {
        Licitatie newLicitatie = new Licitatie();
        newLicitatie.setId(Integer.parseInt((String)args[1]));
        newLicitatie.setNrParticipanti(Integer.parseInt((String)args[2]));
        newLicitatie.setIdProdus(Integer.parseInt((String)args[3]));
        newLicitatie.setNrPasiMaxim(Integer.parseInt((String)args[4]));
        if (CasaLicitatii.getInstance().getLicitatiiActive() == null) {
            CasaLicitatii.getInstance().setLicitatiiActive(new ArrayList<>());
        }
        CasaLicitatii.getInstance().getLicitatiiActive().add(newLicitatie);
    }
}
