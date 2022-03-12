/**
 * Afisarea unei exceptii cand se incearca
 * introducerea unui tip invalid de Client
 */
public class TipClientException extends Exception {
    public TipClientException() {
        System.out.println("Exceptie: Tipul clientului este invalid!");
    }
}
