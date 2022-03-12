/**
 * Aplicarea comisionului din suma tranzactionata de o
 * persoana juridica si care are mai putin de 25 participari la licitatii
 */
public class ComisionTip3 implements CalculatorComision{
    @Override
    public double calculate(Client client, double valTranzactionata) {
        if (client instanceof PersoanaJuridica && client.getNrParticipari() <= 25) {
            return valTranzactionata / 4;
        }
        return -1;
    }
}
