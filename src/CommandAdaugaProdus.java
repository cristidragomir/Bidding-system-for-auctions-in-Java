import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Comanda pentru adaugarea unui produs intr-o lista
 * care permite accesul concurent
 */
public class CommandAdaugaProdus implements Command{
    @Override
    // Sintaxa comenzii
    // addprodus tipProdus idProdus numeProdus pretVanzare pretMinim an material arePiatraPretioasa
    public synchronized void execute(Object[] args) {
        Produs newProdus = switch (((String) args[1])) {
            case "Tablou" -> new Tablou();
            case "Bijuterie" -> new Bijuterie();
            case "Mobila" -> new Mobila();
            default -> null;
        };
        try {
            if (newProdus == null) {
                throw new TipProdusException();
            }
            newProdus.setId(Integer.parseInt((String)args[2]));
            newProdus.setNume((String)args[3]);
            newProdus.setPretVanzare(Double.parseDouble((String)args[4]));
            newProdus.setPretMinim(Double.parseDouble((String)args[5]));
            newProdus.setAn(Integer.parseInt((String)args[6]));
            if (newProdus instanceof Tablou) {
                ((Tablou) newProdus).setNumePictor((String)args[7]);
                ((Tablou) newProdus).setCulori(StilPictura.valueOf((String)args[8]));
            } else if (newProdus instanceof Bijuterie) {
                ((Bijuterie) newProdus).setMaterial((String)args[7]);
                ((Bijuterie) newProdus).setPiatraPretioasa(Boolean.getBoolean((String)args[8]));
            } else {
                ((Mobila) newProdus).setTip((String)args[7]);
                ((Mobila) newProdus).setMaterial((String)args[8]);
            }
            if (CasaLicitatii.getInstance().getProdusePtVanzare() == null) {
                CasaLicitatii.getInstance().setProdusePtVanzare(new CopyOnWriteArrayList<>());
            }
            CasaLicitatii.getInstance().getProdusePtVanzare().add(newProdus);
        } catch (TipProdusException e) {
            System.out.println("Primul argument al comenzii este gresit!");
        }
    }
}
