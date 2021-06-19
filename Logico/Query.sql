/* Operazione 01 - Registrare un nuovo utente */
INSERT INTO Utenti (Email, Nome, Cognome, Password, DataNascita) VALUES (?, ?, ?, ?, ?)
INSERT INTO Directories (Nome, Proprietario) VALUES (?, ?)
/* quando viene creato l utente viene creata anche la sua cartella principale */


/* Operazione 02 - Creare una nuova directory */
INSERT INTO Directories (Nome, Padre) VALUES (?, ?)
/*
il trigger prende il proprietario dalla cartella padre che non dovrà essere nulla
il trigger incrementa il numero delle directory del proprietario da solo
*/


/* Operazione 03 - Aggiungere un nuovo file */
SET IdFile = INSERT INTO Files (Directory, Nome, Estensione) VALUES (?, ?, ?)
INSERT INTO Versioni (File, Dimensione, Link) VALUES (IdFile, ?, ?)
/* 
il trigger setta automaticamente il proprietario del file prendendolo dalla directory
il trigger setta automaticamente la versione giusta incrementando il precedente
il trigger va ad aggiorna il campo UltimaVersione di File
*/

/* Operazione 04 - Rinominare un file */
UPDATE Files SET Nome = ? and Estensione = ? WHERE Id = ?


/* Operazione 05 - Aggiungere una nuova versione di un file */
INSERT INTO Versioni (File, Dimensione, Link) VALUES (?, ?, ?)
/*
il trigger setta automaticamente la versione giusta incrementando il precedente
L altro trigger va ad aggiornare la relazione all ultima versione nel file
*/

/* Operazione 06 - Scaricare una versione di un file */
INSERT INTO Downloads (Versione, Utente) VALUES (?, ?)


/* Operazione 07 - Aggiungere un file ai preferiti */
INSERT INTO Preferenze (File, Utente) VALUES (?, ?)


/* Operazione 08 - Condividere un file con un utente */
INSERT INTO Condivisioni (File, Utente, Lettura, Scrittura) VALUES (?, ?, ?, ?)


/* Operazione 09 - Visualizzare un file */
INSERT INTO Visualizzazioni (Versione, Utente) VALUES (?, ?)


/* Operazione 10 - Aprire una segnalazione */
INSERT INTO Segnalazioni (Utente, Descrizione) VALUES (?, ?)


/* Operazione 11 - Accettare una segnalazione */
UPDATE Segnalazioni SET Operatore = ?, DataAccettazione = NOW() WHERE Id = ?


/* Operazione 12 - Chiudere una segnalazione */
UPDATE Segnalazioni SET DataChiusura = NOW() WHERE Id = ?


/* Operazione 13 - Fare un intervento da parte di un utente */
INSERT INTO Interventi (Segnalazione, Utente, Messaggio) VALUES (?, ?, ?)
/* il trigger gestisce automatica la sequenza numerica */


/* Operazione 14 - Fare un intervento da parte di un operatore */
INSERT INTO Interventi (Segnalazione, Operatore, Messaggio) VALUES (?, ?, ?)
/* il trigger gestisce automatica la sequenza numerica */


/* Operazione 15 - Registrare un nuovo operatore */
INSERT INTO Operatori (Codice, Nome, DataNascita, Password) VALUES (?, ?, ?, ?)


/* Operazione 16 - Visualizzare l’operatore che ha chiuso più interventi */
SELECT Operatore
FROM Segnalazioni
WHERE not (DataChiusura is null)
GROUP BY Operatore
ORDER BY COUNT(*) DESC
LIMIT 1


/* Operazione 17 - Visualizzare il tipo di file più presente */
SELECT Estensione
FROM Files 
GROUP BY Estensione 
ORDER BY COUNT(*) DESC
LIMIT 1

/* Operazione 18 - Visualizzare il numero di file preferiti per utente */
SELECT u.*, COUNT(*) as NumeroPreferiti
FROM Utenti u inner join Preferenze p on u.Email = p.Utente 
GROUP BY u.Email


/* Operazione 19 - Visualizzare il numero di cartelle per utente */
SELECT u.Email, u.NumeroDirectory
FROM Utenti u


/* Operazione 20 - Visualizzare il file più condiviso */
SELECT f.*
FROM Files f inner join Condivisioni c on f.Id = c.File 
GROUP BY f.Id 
ORDER BY COUNT(*) DESC 
LIMIT 1


/* Operazione 21 - Visualizzare il file più visualizzato */
SELECT f.*
FROM Files f, Versioni v, Visualizzazioni vi
WHERE f.Id = v.File and v.Id = vi.Versione 
GROUP BY f.Id 
ORDER BY COUNT(*) DESC 
LIMIT 1


/* Operazione 22 - Visualizzare l’utente che ha aperto più segnalazioni */
SELECT *
FROM Utenti u inner join Segnalazioni s on u.Email = s.Utente 
GROUP BY f.Id
ORDER BY COUNT(*) DESC 
LIMIT 1


/* Operazione 23 - Visualizzare il file più scaricato */
SELECT f.*
FROM Files f, Versioni v, Downloads d 
WHERE f.Id = v.File and v.Id = d.Versione 
GROUP BY f.Id
ORDER BY COUNT(*) DESC 
LIMIT 1


/* Operazione 24 - Visualizzare tutti i file scaricati di un utente */
SELECT DISTINCT f.*
FROM Utenti u, Downloads d, Versioni v, Files f 
WHERE u.Email = d.Utente and d.Versione = v.Id and v.File = f.Id 
and f.Proprietario = ?


/* Operazione 25 - Visualizzare per un file il numero di versioni */
SELECT COUNT(*) as NumeroVersioni 
FROM Files f inner join Versioni v on f.Id = v.File 
WHERE f.Id = ?


/* Operazione 26 - Visualizzare per un utente il numero di file */
SELECT COUNT(*) as NumeroFile
FROM Utenti u inner join File f on u.Email = f.Proprietario
WHERE u.Email = ?


/* Operazione 27 - Visualizzare per un utente il numero di directory */
SELECT NumeroDirectory 
FROM Utenti
WHERE u.Email = ?


/* Operazione 28 - Visualizzare per un utente il numero di file preferiti */
SELECT COUNT(*) as NumeroPreferenze
FROM Utenti u inner join Preferenze p on u.Email = p.Utente
WHERE u.Email = ?


/* Operazione 29 - Visualizzare il numero di file in una cartella */
SELECT COUNT(*) as NumeroFile
FROM Directories d inner join Files f on d.Id = f.Directory
WHERE d.Id = ?


/* Operazione 30 - Visualizzare i file in una cartella */
SELECT f.*
from Directories d inner join Files f on d.Id = f.Directory 
WHERE d.Id = ?


/* Operazione 31 - Visualizzare il numero di cartelle in una cartella */
SELECT COUNT(*) as NumeroSottoCartelle
FROM Directories d inner join Directories d2 on d.Id = d2.Padre 
WHERE d.Id = ?


/* Operazione 32 - Visualizzare le cartelle in una directory */
SELECT d.*
FROM Directories d
WHERE d.Padre = ?


/* Operazione 33 - Visualizzare la dimensione di un file */
SELECT f.*, v.Dimensione 
FROM Files f inner join Versioni v on v.Id = f.UltimaVersione 
WHERE f.Id = ?


/* Operazione 34 - Visualizzare la dimensione di una cartella */
SELECT sum(v.Dimensione) as Dimensione
FROM Directories d, Files f, Versioni v
WHERE d.Id = f.Directory and f.UltimaVersione = v.Id and d.Id = ?



