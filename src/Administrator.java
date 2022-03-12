import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Administratorul este un Angajat cu abilitatea de a introduce
 * produse in lista de produse
 *
 * Insertia se face intr-un thread separat
 */
public class Administrator extends Angajat implements Runnable {
    private String addProductCommand;

    public Administrator() {
    }

    public String getAddProductCommand() {
        return addProductCommand;
    }

    public void setAddProductCommand(String addProductCommand) {
        this.addProductCommand = addProductCommand;
    }

    @Override
    /**
     * Pentru a rula pe un thread separat adaugarea
     * unui produs
     * se implementeaza metoda run()
     */
    public void run() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            String[] commandArgs = addProductCommand.split(" ");
            new CommandAdaugaProdus().execute(commandArgs);
            Thread.sleep(10);
        } catch (InterruptedException ignored) {

        } finally {
            lock.unlock();
        }
    }
}
