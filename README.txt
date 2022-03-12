324CB - Dragomir Constantin-Cristian

Explicarea detaliilor de implementare ale 
proiectului la POO

Pentru testarea programului se folosesc fisierele:
input.txt - pentru a initializa baza de date a 
produselor, clientii din sistem, administratorul, 
brokerii, licitatiile ce se vor realiza la un 
moment de timp (astazi)
cereri.txt - in functie de produsele scoase la 
licitatie astazi, clientii se pot inscrie la 
licitatie sau nu
bids.txt - pentru a simula cat liciteaza fiecare 
client participant la fiecare licitatie, la 
fiecare pas al licitatiei.
outputLogs.txt - in acest fisier s-au scris 
log-urile clientilor si ale brokerilor - pentru a 
simula ce ar vedea fiecare pe propriul ecran

Pentru diversele comenzi ale programului am 
considerat utila implementarea design pattern-ului 
Command. Pentru notificarea brokerilor si a 
clientilor a fost util design pattern-ul Observer.
Comisionul si modul diferit de calcul a fost 
rezolvat cu ajutorul design pattern-ului Strategy (
Se selecteaza automat calculatorul de comision 
potrivit). Avand in vedere ca se lucreaza cu o 
singura casa de licitatii s-a folosit, astfel, si 
design pattern-ul Singleton.

S-au respectat diversele ierarhii ale obiectelor:
Tablou, Mobila, Bijuterie au ca radacina pe Produs
Administrator, Broker au drept radacina Angajat
PersoanaFizica, PersoanaJuridica au drept radacina 
Client.

MECANISMUL DE LICITATIE:

Am considerat ca odata depuse Solicitarile 
licitatiile si numarul de participanti sunt fixate 
inainte de inceperea efectiva a licitatiilor.

La fiecare licitatie se refac relatiile 
Broker-Client pentru a da sansa si altor brokeri 
sa aiba parte de un castig.

Pentru a tine cont de produsul curent scos la 
licitatie si de potentialii castigatori se 
utilizeaza o instanta a observatorului dedicat 
clientului (fara a se trimite anunturi!).

Se verifica numarul de participanti la Licitatie. 
Pentru fiecare participant (client) se 
actualizeaza numarul de participari. In Liciatie, 
metoda verificareNrParticipanti identifica 
clientul din solicitarile care includ id-ul 
produsului scos in acest moment la licitatie. 
Pentru acesta i se identifica Brokerul asociat. 
Cel din urma va avea dreptul de a incrementa 
numarul de participari.

Daca licitatia nu are loc sunt anuntati Brokerii, 
care la randul lor anunta Clientii, despre 
anularea licitatiei.

Daca licitatia incepe, exista acelasi mecansim de 
anuntare mentionat anterior.

La fiecare pas se realizeaza mai multe runde de 
licitare(existand, evident, un numar maxim).
Pentru o runda de licitatii clientii trimit 
brokerilor ofertele lor. Brokerii transmit mai 
departe casei de licitatii ofertele. Este de 
retinut ca participantii nu pot oferi mai mult
decat au declarat ca pot oferi maxim pe produs
In cazul unei greseli a clientului, se va trimite 
Broker-ului oferta maxima declarata in cerere. Casa de 
licitatii calculeaza oferta maxima de la acel pas 
si se notifica entitatile. Procedeul se reia pana 
se termina toate rundele.

Cand rundele pentru o licitatie se termina se 
verifica daca oferta maxima depaseste pretul 
minim. Daca nu, se face o notificare a entitatilor 
in acest sens.

In caz pozitiv, daca sunt mai multe persoane cu 
aceeasi oferta maxima, castigatorul va fi cel cu 
mai multe participari. Pentru castigator, brokerul 
ce ii este asignat ii va incrementa numarul de 
licitatii castigate. De asemenea, se face o 
notificare a entitatilor in modul enuntat mai sus.
Mai mult, brokerul isi va opri un comision 
specific. Tot in sarcina brokerilor revine 
stergerea produsului vandut, lucru care se 
realizeaza intr-un thread separat. Se reia algoritmul
descris incepand cu linia 44.

Este de mentionat ca administratorul poate aduga noi
produse oricand in desfasurarea licitatie, prin
utilizarea unui nou thread.

In timpul licitatiei, clientii au posibilitatea de 
a vedea oricand oferta maxima. Mai mult, inainte 
de depunerea solicitarilor, Clientii pot vedea ce 
produse sunt scoase la licitatie azi.

Pentru afisarea detaliilor despre un Produs de un anume 
tip se apeleaza metoda mama care generaza un String cu 
detaliile generale, iar in metoda toString din subtipul 
de Produs se completeaza cu particularitatile. Totodata 
si Clientii, subtipurile acestei clase au implementate 
aceasta afisare.

De asemenea, exista metode de cautare a Clientilor, a 
Produselor dupa id intr-o lista de obiecte specifice.