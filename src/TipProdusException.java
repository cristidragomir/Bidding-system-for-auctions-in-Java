/**
 * Afisarea unei exceptii cand se incearca
 * introducerea unui tip invalid de Produs
 */
public class TipProdusException extends Exception {
    public TipProdusException() {
        System.out.println("Exceptie: Nu exista tipul de produs indicat!");
    }
}
