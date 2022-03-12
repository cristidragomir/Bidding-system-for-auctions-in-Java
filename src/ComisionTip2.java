/**
 * Aplicarea comisionului din suma tranzactionata de o
 * persoana fizica si care are mai mult de 5 participari la licitatii
 */
public class ComisionTip2 implements CalculatorComision{
    @Override
    public double calculate(Client client, double valTranzactionata) {
        if (client instanceof PersoanaFizica && client.getNrParticipari() > 5) {
            return 3 * valTranzactionata / 20;
        }
        return -1;
    }
}
