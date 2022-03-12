import java.util.ArrayList;

/**
 * Comanda care adauga un client in sistemul casei de licitatii
 */
public class CommandAdaugaClient implements Command {
    @Override
    // Sintaxa comenzii
    // addclient PF id nume adresa nrParticipari nrLicitatiiCastigate dataNasterii
    // addclient PJ id nume adresa nrParticipari nrLicitatiiCastigate companie capitalSocial
    public void execute(Object[] args) {
        Client newClient = null;
        if (((String)args[1]).equals("PF")) {
            newClient = new PersoanaFizica();
        } else if (((String)args[1]).equals("PJ")){
            newClient = new PersoanaJuridica();
        }
        try {
            if (newClient == null) {
                throw new TipClientException();
            }
            newClient.setId(Integer.parseInt((String)args[2]));
            newClient.setNume((String)args[3]);
            newClient.setAdresa((String)args[4]);
            newClient.setNrParticipari(Integer.parseInt((String)args[5]));
            newClient.setNrLicitatiiCastigate(Integer.parseInt((String)args[6]));
            if (newClient instanceof PersoanaFizica) {
                ((PersoanaFizica) newClient).setDataNastere((String)args[7]);
            } else {
                ((PersoanaJuridica) newClient).setCompanie(Companie.valueOf((String)args[7]));
                ((PersoanaJuridica) newClient).setCapitalSocial(Double.parseDouble((String)args[8]));
            }
            if (CasaLicitatii.getInstance().getClientiSistem() == null) {
                CasaLicitatii.getInstance().setClientiSistem(new ArrayList<Client>());
            }
            CasaLicitatii.getInstance().getClientiSistem().add(newClient);
        } catch (TipClientException e) {
            System.out.println("Primul argument al comenzii este gresit!");
        }
    }
}
