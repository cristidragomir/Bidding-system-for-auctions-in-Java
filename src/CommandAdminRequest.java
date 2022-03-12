/**
 * Comanda pentru adaugarea unui produs in lista de
 * produse comercializate de catre un administrator
 *
 * Aceasta comanda ruleaza intr-un thread nou, astfel
 * incat sa nu perturbeze desfasurarea licitatiei
 */
public class CommandAdminRequest implements Command {
    @Override
    // Sintaxa comenzii
    // admin addprodus Bjuterie 100 Pandantiv 3999.99 1800 2003 Aur true
    // admin addprodus Tablou 125 Tablou2 659.99 200 1970 AutorNumePrenume acrilic
    public void execute(Object[] args) {
        Administrator a = (Administrator)args[0];
        a.setAddProductCommand("");
        for (int i = 1; i < ((String[])args[1]).length; i++) {
            a.setAddProductCommand(a.getAddProductCommand() +
                    ((String[]) args[1])[i] + ' ');
        }
        Thread t = new Thread(a);
        t.start();
        while (t.isAlive()){}
    }
}
