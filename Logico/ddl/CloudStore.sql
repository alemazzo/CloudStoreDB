# Create schemas

# Create tables
CREATE TABLE IF NOT EXISTS Directory
(
    Id INT NOT NULL,
    Nome VARCHAR(20) NOT NULL,
    DataCreazione DATETIME NOT NULL,
    Padre INT,
    Proprietario VARCHAR(50) NOT NULL,
    PRIMARY KEY(Id),
    UNIQUE(Padre, Nome)
);

CREATE TABLE IF NOT EXISTS File
(
    Id INT NOT NULL,
    Directory INT NOT NULL,
    Nome VARCHAR(30) NOT NULL,
    Estensione VARCHAR(3) NOT NULL,
    Proprietario VARCHAR(50) NOT NULL,
    UltimaVersione INT NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(Directory, Nome, Estensione)
);

CREATE TABLE IF NOT EXISTS Utenti
(
    Email VARCHAR(50) NOT NULL,
    Nome VARCHAR(20) NOT NULL,
    Cognome VARCHAR(20) NOT NULL,
    DataRegistrazione DATE NOT NULL,
    Password VARCHAR(20) NOT NULL,
    DataNascita DATE NOT NULL,
    NumeroDirectory INT NOT NULL,
    PRIMARY KEY(Email)
);

CREATE TABLE IF NOT EXISTS Preferenze
(
    File INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    Data DATETIME NOT NULL,
    PRIMARY KEY(File, Utente)
);

CREATE TABLE IF NOT EXISTS Versioni
(
    Id INT NOT NULL,
    File INT NOT NULL,
    Numero INT NOT NULL,
    Data DATETIME NOT NULL,
    Dimensione INT NOT NULL,
    Link VARCHAR(30) NOT NULL,
    PRIMARY KEY(Id),
    UNIQUE(File, Numero)
);

CREATE TABLE IF NOT EXISTS Downloads
(
    Versione INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    Data DATETIME NOT NULL,
    PRIMARY KEY(Versione, Utente)
);

CREATE TABLE IF NOT EXISTS Visualizzazioni
(
    Versione INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    Data DATETIME NOT NULL,
    PRIMARY KEY(Versione, Utente)
);

CREATE TABLE IF NOT EXISTS Segnalazioni
(
    Id INT NOT NULL,
    Utente VARCHAR(50) NOT NULL,
    Descrizione VARCHAR(0) NOT NULL,
    Operatore INT,
    DataAccettazione DATETIME,
    DataChiusura DATETIME,
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS Operatori
(
    Codice INT NOT NULL,
    Nome VARCHAR(0) NOT NULL,
    Password VARCHAR(0) NOT NULL,
    DataNascita DATE NOT NULL,
    PRIMARY KEY(Codice)
);

CREATE TABLE IF NOT EXISTS Interventi
(
    Segnalazione INT NOT NULL,
    Numero INT NOT NULL,
    Utente VARCHAR(50),
    Operatore INT,
    Messaggio VARCHAR(0) NOT NULL,
    Data DATETIME NOT NULL,
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
    Lettura TINYINT(1),
    Scrittura TINYINT(1),
    Data DATE,
    PRIMARY KEY(File, Utente)
);


# Create FKs
ALTER TABLE File
    ADD    FOREIGN KEY (Directory)
    REFERENCES Directory(Id)
;
    
ALTER TABLE Directory
    ADD    FOREIGN KEY (Padre)
    REFERENCES Directory(Id)
;
    
ALTER TABLE Directory
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
    
ALTER TABLE File
    ADD    FOREIGN KEY (Proprietario)
    REFERENCES Utenti(Email)
;
    
ALTER TABLE File
    ADD    FOREIGN KEY (UltimaVersione)
    REFERENCES Versioni(Id)
;
    
ALTER TABLE Preferenze
    ADD    FOREIGN KEY (File)
    REFERENCES File(Id)
;
    
ALTER TABLE Condivisioni
    ADD    FOREIGN KEY (File)
    REFERENCES File(Id)
;
    
ALTER TABLE Visualizzazioni
    ADD    FOREIGN KEY (Versione)
    REFERENCES Versioni(Id)
;
