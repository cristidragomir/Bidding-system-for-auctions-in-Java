/**
 * Aplicarea comisionului din suma tranzactionata de o
 * persoana fizica si care are mai putin de 5 participari la licitatii
 */
public class ComisionTip1 implements CalculatorComision {
    @Override
    public double calculate(Client client, double valTranzactionata){
        if (client instanceof PersoanaFizica && client.getNrParticipari() <= 5) {
            return valTranzactionata / 5;
        }
        return -1;
    }
}
