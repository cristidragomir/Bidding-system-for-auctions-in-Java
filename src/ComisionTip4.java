/**
 * Aplicarea comisionului din suma tranzactionata de o
 * persoana juridica si care are mai mult de 25 participari la licitatii
 */
public class ComisionTip4 implements CalculatorComision{
    @Override
    public double calculate(Client client, double valTranzactionata) {
        if (client instanceof PersoanaJuridica && client.getNrParticipari() > 25) {
            return valTranzactionata / 10;
        }
        return -1;
    }
}
