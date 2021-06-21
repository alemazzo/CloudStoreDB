# Create schemas

# Create tables
CREATE TABLE IF NOT EXISTS Directories
(
    Id INT NOT NULL AUTO_INCREMENT,
    Nome VARCHAR(80) NOT NULL,
    DataCreazione DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Padre INT,
    Proprietario VARCHAR(50) NOT NULL,
    PRIMARY KEY(Id),
    UNIQUE(Padre, Nome)
);

CREATE TABLE IF NOT EXISTS Files
(
    Id INT NOT NULL AUTO_INCREMENT,
    Directory INT NOT NULL,
    Nome VARCHAR(80) NOT NULL,
    Estensione VARCHAR(10) NOT NULL,
    Proprietario VARCHAR(50),
    UltimaVersione INT,
    PRIMARY KEY(id),
    UNIQUE(Directory, Nome, Estensione)
);

CREATE TABLE IF NOT EXISTS Utenti
(
    Email VARCHAR(50) NOT NULL,
    Nome VARCHAR(20) NOT NULL,
    Cognome VARCHAR(20) NOT NULL,
    DataRegistrazione DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Password VARCHAR(40) NOT NULL,
    DataNascita DATE NOT NULL,
    NumeroDirectory INT NOT NULL DEFAULT 0,
    PRIMARY KEY(Email)
);


CREATE TABLE IF NOT EXISTS Preferenze
(
    File INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    DataPreferenza DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(File, Utente)
);

CREATE TABLE IF NOT EXISTS Versioni
(
    Id INT NOT NULL AUTO_INCREMENT,
    File INT NOT NULL,
    Numero INT,
    DataCreazione DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Dimensione INT NOT NULL,
    Link VARCHAR(100) NOT NULL,
    PRIMARY KEY(Id),
    UNIQUE(File, Numero),
    
);

CREATE TABLE IF NOT EXISTS Downloads
(
	Id INT NOT NULL AUTO_INCREMENT,
    Versione INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    DataDownload DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(Id)
);

CREATE TABLE IF NOT EXISTS Visualizzazioni
(
	Id INT NOT NULL AUTO_INCREMENT,
    Versione INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    DataVisualizzazione DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(Id)
);

CREATE TABLE IF NOT EXISTS Segnalazioni
(
    Id INT NOT NULL AUTO_INCREMENT,
    Utente VARCHAR(50) NOT NULL,
    Descrizione VARCHAR(1000) NOT NULL,
    Operatore INT,
    DataAccettazione DATETIME,
    DataChiusura DATETIME,
    PRIMARY KEY(Id)
);

CREATE TABLE IF NOT EXISTS Operatori
(
    Codice INT NOT NULL,
    Nome VARCHAR(20) NOT NULL,
    Password VARCHAR(20) NOT NULL,
    DataNascita DATE NOT NULL,
    PRIMARY KEY(Codice)
);

CREATE TABLE IF NOT EXISTS Interventi
(
    Segnalazione INT NOT NULL,
    Numero INT NOT NULL,
    Utente VARCHAR(50),
    Operatore INT,
    Messaggio VARCHAR(1000) NOT NULL,
    DataIntervento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(Segnalazione, Numero),
    CONSTRAINT GERARCHIA_INTERVENTI CHECK (
    	(Operatore is null or Utente is null) 
    	and not 
    	(Operatore is null and Utente is null)
   	)

);

CREATE TABLE IF NOT EXISTS Condivisioni
(
    File INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    Lettura BOOL NOT NULL,
    Scrittura BOOL NOT NULL,
    DataCondivisione DATE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(File, Utente),
    CONSTRAINT PERMESSI_VALIDI CHECK (
    	(Lettura = TRUE) 
    )
);


# Create FKs
ALTER TABLE Files
    ADD    FOREIGN KEY (Directory)
    REFERENCES Directories(Id)
;
    
ALTER TABLE Directories
    ADD    FOREIGN KEY (Padre)
    REFERENCES Directories(Id)
;
    
ALTER TABLE Directories
    ADD    FOREIGN KEY (Proprietario)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Preferenze
    ADD    FOREIGN KEY (Utente)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Downloads
    ADD    FOREIGN KEY (Versione)
    REFERENCES Versioni(Id)
;

ALTER TABLE Downloads
    ADD    FOREIGN KEY (Utente)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Visualizzazioni
    ADD    FOREIGN KEY (Utente)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Segnalazioni
    ADD    FOREIGN KEY (Utente)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Segnalazioni
    ADD    FOREIGN KEY (Operatore)
    REFERENCES Operatori(Codice)
;
    
ALTER TABLE Interventi
    ADD    FOREIGN KEY (Segnalazione)
    REFERENCES Segnalazioni(Id)
;
    
ALTER TABLE Interventi
    ADD    FOREIGN KEY (Utente)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Interventi
    ADD    FOREIGN KEY (Operatore)
    REFERENCES Operatori(Codice)
;
    
ALTER TABLE Condivisioni
    ADD    FOREIGN KEY (Utente)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Files
    ADD    FOREIGN KEY (Proprietario)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE Files
    ADD    FOREIGN KEY (UltimaVersione)
    REFERENCES Versioni(Id)
;
    
ALTER TABLE Preferenze
    ADD    FOREIGN KEY (File)
    REFERENCES Files(Id)
;
    
ALTER TABLE Condivisioni
    ADD    FOREIGN KEY (File)
    REFERENCES Files(Id)
;
    
ALTER TABLE Visualizzazioni
    ADD    FOREIGN KEY (Versione)
    REFERENCES Versioni(Id)
;
   
# Create Triggers

CREATE DEFINER=`root`@`localhost` TRIGGER UTENTE_MAGGIORENNE
BEFORE INSERT
ON Utenti FOR EACH ROW
BEGIN
IF YEAR(curdate()) - YEAR(NEW.DataNascita) - (DATE_FORMAT(curdate(), "%m%d") < DATE_FORMAT(NEW.DataNascita, "%m%d")) < 18
THEN SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT = "Gli utenti devono essere maggiorenni";
END IF;
END;

CREATE DEFINER=`root`@`localhost` TRIGGER PROPRIETARIO_FILE_AUTOMATICO
BEFORE INSERT
ON Files FOR EACH ROW
BEGIN
	SET NEW.Proprietario = (SELECT Proprietario FROM Directories WHERE Id = NEW.Directory);
END

CREATE DEFINER=`root`@`localhost` TRIGGER PROPRIETARIO_DIRECTORY_AUTOMATICO
BEFORE INSERT
ON Directories FOR EACH ROW
BEGIN
	DECLARE Prop VARCHAR(50);
	SET Prop = (SELECT Proprietario FROM Directories WHERE Id = NEW.Padre);
	IF not (Prop is NULL)
	THEN 
		SET NEW.Proprietario = Prop;
	END IF;
END

CREATE DEFINER=`root`@`localhost` TRIGGER INCREMENTO_VERSIONE
BEFORE INSERT
ON Versioni FOR EACH ROW
BEGIN
	DECLARE Prev INT;
IF NEW.Numero is NULL
THEN
	SET Prev = (SELECT MAX(Numero) FROM Versioni WHERE File = NEW.File);
	IF Prev is NULL 
	THEN 
		SET NEW.Numero = 1;
	ELSE
		SET NEW.Numero = Prev + 1;
	END IF;
END IF;
END;

CREATE DEFINER=`root`@`localhost` TRIGGER AGGIORNAMENTO_ULTIMA_VERSIONE
AFTER INSERT
ON Versioni FOR EACH ROW
BEGIN
UPDATE Files SET UltimaVersione = NEW.Id WHERE Id = NEW.File; 
END;

CREATE DEFINER=`root`@`localhost` TRIGGER UPDATE_UTENTE
AFTER INSERT
ON Directories FOR EACH ROW
BEGIN
	UPDATE Utenti SET NumeroDirectory = NumeroDirectory + 1 WHERE Email = NEW.Proprietario;
END

CREATE DEFINER=`root`@`localhost` TRIGGER INCREMENTO_INTERVENTO
BEFORE INSERT
ON Interventi FOR EACH ROW
BEGIN
	DECLARE Prev INT;
IF NEW.Numero is NULL
THEN
	SET Prev = (SELECT MAX(Numero) FROM Interventi WHERE Segnalazione = NEW.Segnalazione);
	IF Prev is NULL 
	THEN 
		SET NEW.Numero = 1;
	ELSE
		SET NEW.Numero = Prev + 1;
	END IF;
END IF;
END

## Possibili constraint che sono stati evitati per non complicare la gestione dell applicazione
/*

TABLE: Preferenze
CONSTRAINT PREFERENZA_VALIDA CHECK (
	((SELECT Proprietario FROM Files WHERE Id = File) = Utente)
	or
	EXISTS (SELECT * FROM Condivisioni c WHERE c.File = File AND c.Utente = Utente)
)
    

TABLE: Downloads
CONSTRAINT DOWNLOAD_VALIDO CHECK (
	((SELECT Proprietario FROM Files WHERE Id = File) = Utente)
	or
	EXISTS (SELECT * FROM Condivisioni c WHERE c.File = File AND c.Utente = Utente)
)


TABLE: Visualizzazioni
CONSTRAINT VISUALIZZAZIONE_VALIDA CHECK (
	((SELECT Proprietario FROM Files WHERE Id = File) = Utente)
	or
	EXISTS (SELECT * FROM Condivisioni c WHERE c.File = File AND c.Utente = Utente)
)
*/
