import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Inceputul executiei programului
 *
 * Contine cateva comenzi de baza in vederea initializarii
 * diferitelor entitati utilizand fisiere
 *
 * Log-urile diverselor obiecte se scriu in fisiere de
 * iesire aferente
 */
public class Main {
    public static void main(String[] args) {
        for (int k = 1; k <= 10; k++) {
            String path = "rulare";
            path += k;
            Map<String, Command> console = new HashMap<>();
            console.put("addclient", new CommandAdaugaClient());
            console.put("addprodus", new CommandAdaugaProdus());
            console.put("delprodus", new CommandStergeProdus());
            console.put("addsolicitare", new CommandInregistrareSolicitare());
            console.put("afisprod", new CommandAfisareProduse());
            console.put("admin", new CommandAdminRequest());
            console.put("licitatie", new CommandAdaugareLicitatie());
            // lista comenzi utile
            CasaLicitatii LicitatiiPOO = CasaLicitatii.getInstance();
            ArrayList<CalculatorComision> calculatoareComision= new ArrayList<>();
            calculatoareComision.add(new ComisionTip1());
            calculatoareComision.add(new ComisionTip2());
            calculatoareComision.add(new ComisionTip3());
            calculatoareComision.add(new ComisionTip4());
            // initializarea calculatoarelor de comision
            Administrator admin = new Administrator();
            Broker[] brokers = new Broker[3];
            // initializarea administratorilor si a brokerilor din sistem
            for (int i = 0; i < brokers.length; i++) {
                brokers[i] = new Broker();
                brokers[i].setTipuriComisioane(calculatoareComision);
            }
            // fiecare broker isi va percepe un anumit comision

            String command;
            Scanner input;
            try {
                input = new Scanner(new File(path + "\\input.txt"));
                while (input.hasNextLine()) {
                    command = input.nextLine();
                    String[] commandArgs = command.split(" ");
                    Object[] entities = new Object[2];
                    if (commandArgs[0].equals("admin")) {
                        entities[0] = admin;
                    } else if (commandArgs[0].equals("broker")) {
                        entities[0] = brokers[new Random().nextInt(brokers.length)];
                    }
                    entities[1] = commandArgs;
                    for (Map.Entry mapEl : console.entrySet()) {
                        if (commandArgs[0].equals("admin")) {
                            new CommandAdminRequest().execute(entities);
                            break;
                        } else if (commandArgs[0].equals("broker")) {
                            new CommandBrokerRequest().execute(entities);
                            break;
                        }
                        if (mapEl.getKey().equals(commandArgs[0])) {
                            ((Command) mapEl.getValue()).execute(commandArgs);
                            break;
                        }
                    }
                }
                // initializarea listei de produse, a clientilor, a listei de
                // licitatii ce vor fi facute astazi
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File file = new File(path + "\\cereri.txt");
            try {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    command = fileScanner.nextLine();
                    String[] commandArgs = command.split(" ");
                    new CommandInregistrareSolicitare().execute(commandArgs);
                }
            } catch (IOException e) {
                System.out.println("Fisier negasit!");
            }
            // depunerea de catre clienti a solicitarilor
            LicitatiiPOO.desfLicitatii(brokers, admin, path + "\\bids.txt");
            // desfasurarea efectiva a licitatiei de catre casa de licitatii
            try {
                FileWriter output = new FileWriter(path + "\\outputLogs.txt");
                int cnt = 0;
                for (Broker broker : brokers) {
                    output.write("Log-ul brokerului " + cnt + ":\n");
                    output.write(broker.getLog());
                    if (k == 1) {
                        GUI toDisplay = new GUI();
                        toDisplay.setTextToDisplay(broker.getLog());
                        toDisplay.displayGUI();
                    }
                    output.write("//////////////\n");
                    cnt++;
                }
                output.write("//////////////\n");
                cnt = 0;
                for (Client client : LicitatiiPOO.getClientiSistem()) {
                    output.write("Log-ul clientului " + cnt + ":\n");
                    if (client.getLog() != null) {
                        output.write(client.getLog());
                    }
                    output.write("//////////////\n");
                    cnt++;
                }
                output.write("-------------");
                output.close();
            } catch (IOException ignored) {}
            // scrierea log-urilor (ale brokerilor si ale clientilor)
            LicitatiiPOO.delCasaLicitatii();
        }
    }
}
