# Applicazione Java per il calcolo di un itinerario Interrail
## Studente proponente

s270867 Castellarin Filippo

## Descrizione del problema proposto

 L'applicazione si propone di calcolare un itinerario percorribile in treno attraverso le stazioni delle citt&agrave; europee. Per fare ci&ograve; &egrave; necessario l'inserimento da parte dell'utente dei propri interessi turistici, inteso come le proprie preferenze in termini di attivit&agrave; da svolgere durante un viaggio, da quale citt&agrave; desidera partire, e il numero di *'giorni di viaggio'* del biglietto Interrail da lui acquistato. 

 Per la definizione di 'giorni di viaggio' vedasi fondo pagina.

### Descrizione della rilevanza gestionale del problema

L'algoritmo tratta il calcolo di un itinerario turistico ottimale a partire dall'inserimento di semplici preferenze turistiche da parte del fruitore dell'applicazione. Per questo motivo, tale applicazione potrebbe risultare appropriata per tour operator e agenzie viaggi che si occupano nella definizione di itinerari turistici da vendere ai propri clienti.<br>Inoltre, l'applicazione in questione, potrebbe risultare utile anche ai fini del progetto Interrail stesso, in quanto potrebbe suggerire già in partenza un itinerario a coloro i quali sono interessati a viaggi di questo stile (che però sono indecisi sui luoghi da visitare), e quindi permettere una maggior diffusione del progetto Interrail tra i cittadini europei.

## Descrizione dei data-set

Le principali fonti di reperimento delle informazioni sono:
- Kaggle
- Eurostat
  
I database utilizzati sono:
- https://ec.europa.eu/eurostat/databrowser/view/urb_ctour/default/table?lang=en : mostra alcuni indicatori in termini di interessi culturali delle citt&agrave; europee che forniscono i dati all'Unione Europea, e di alcune citt&agrave; non facenti parte di essa.
- https://www.kaggle.com/datasets/headsortails/train-stations-in-europe : contiene le informazioni circa la posizione geografica delle varie stazioni nel continente europeo.
  
**Nota:** <br>I database sono stati uniti tramite script R (file `tidyDatabase.Rmd` nella cartella Database) per permettere un uso pi&ugrave; agevole del contenuto per il fine dell'applicazione in questione. <br>Il database completo &egrave; presente nel progetto nella cartella Database al nome di `urban_cities_def.sql`.

## Descrizione degli algoritmi coinvolti e istruzioni sull'uso
Avviato il programma, l'utente si trova di fronte a un'interfaccia grafica attraverso la quale pu&ograve;:
- selezionare lo stato di partenza, il che far&agrave; in modo che appena selezionato appaiano le citt&agrave; di quello stato;
- selezionare la citt&agrave; di partenza;
- selezionare il numero di giorni di viaggio;
- selezionare i propri interessi turistici (in base a quelli presenti nel database);
- premere il bottone `Plan your trip` per avviare gli algoritmi per il calcolo del percorso;
- premere il bottone `Reset` per azzerare le selezioni.

Ogni parametro &egrave; fondamentale per il calcolo dell'itinerario, pertanto nel caso in cui l'utente non selezioni qualche campo, l'applicazione segnaler&agrave; nell'area di testo sottostante la necessit&agrave; di selezionare i campi mancanti.

L'algoritmo per il calcolo dell'itinerario sfrutta il principio della **ricorsione** a partire dalla citt&agrave; di origine selezionata dall'utente. In particolare, l'algoritmo va a massimizzare un parametro (dipeso dall'interesse turistico) calcolato per ogni citt&agrave; aggiunta alla soluzione parziale. L'algoritmo termina quando si &egrave; trovata la soluzione che va a massimizzare le citt&agrave; che risultano essere pi&ugrave; affini per l'utente.<br> In caso di uguale *peso*, viene privilegiato l'itinerario con distanza minore.
<br>
Terminata l'elaborazione, il risultato viene mostrato in un'apposita area di testo.
<br> <br>

**NOTA**: Per poter rimanere il pi&ugrave; verosimile all'effettiva quantit&agrave; di chilometri percorribili durante un singolo giorno, il programma considera la possibilit&agrave; di percorrere al pi&ugrave; `1100 km` (in linea d'aria) e di poter visitare pi&ugrave; citt&agrave; nel caso esse abbiano una distanza pari o inferiore a `70 km`.

<hr>

Giorno di viaggio Interrail, dal sito ufficiale <a href="https://www.interrail.eu/it/support/interested-in-interrailing/what-is-a-travel-day"> Interrail</a>:
 <br>Un giorno di viaggio equivale a un periodo di 24 ore in cui puoi viaggiare in treno con il tuo Interrail Pass e va dalle 00:00 (mezzanotte) alle 23:59 dello stesso giorno di calendario. In ciascun giorno di viaggio puoi accedere alle reti ferroviarie di validit&agrave; del tuo Interrail Pass.

<hr>
 <footer>
<p style="text-align:center"> Autore: Filippo Castellarin < filippo.castellarin@studenti.polito.it ></p>
 </footer>