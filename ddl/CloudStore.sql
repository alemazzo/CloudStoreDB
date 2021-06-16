# Create schemas

# Create tables
CREATE TABLE IF NOT EXISTS Directory
(
    id INT NOT NULL AUTOINCREMENT,
    nome VARCHAR(20) NOT NULL,
    data_creazione DATETIME NOT NULL,
    padre INT,
    propietario VARCHAR(50) NOT NULL,
    PRIMARY KEY(ID),
    UNIQUE(padre, nome, proprietario)
);

CREATE TABLE IF NOT EXISTS File
(
    id INT NOT NULL,
    directory INT NOT NULL,
    nome VARCHAR(0) NOT NULL,
    estensione VARCHAR(0) NOT NULL,
    proprietario VARCHAR(50) NOT NULL,
    numero_ultima_versione INT NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(directory, nome, estensione)
    
);

CREATE TABLE IF NOT EXISTS Utenti
(
    email VARCHAR(50) NOT NULL,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL,
    data_registrazione DATE NOT NULL,
    password VARCHAR(20) NOT NULL,
    data_di_nascita DATE NOT NULL,
    numero_directory INT NOT NULL,
    PRIMARY KEY(email)
);

CREATE TABLE IF NOT EXISTS Preferenze
(
    id_file INT NOT NULL,
    utente VARCHAR(50) NOT NULL,
    data DATETIME NOT NULL,
    PRIMARY KEY(id_file, utente)
);

CREATE TABLE IF NOT EXISTS Versioni
(
    id INT NOT NULL,
    file INT NOT NULL,
    numero INT NOT NULL,
    data DATETIME NOT NULL,
    dimensione INT NOT NULL,
    link VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Downloads
(
    versione_numero INT NOT NULL,
    versione_file INT NOT NULL,
    utente VARCHAR(50) NOT NULL,
    data DATETIME NOT NULL,
    PRIMARY KEY(versione_numero, versione_file, utente)
);

CREATE TABLE IF NOT EXISTS Visualizzazioni
(
    id_versione INT NOT NULL,
    utente VARCHAR(50) NOT NULL,
    data DATETIME NOT NULL,
    PRIMARY KEY(id_versione, utente)
);

CREATE TABLE IF NOT EXISTS Segnalazioni
(
    ID INT NOT NULL,
    utente VARCHAR(50) NOT NULL,
    descrizione VARCHAR(0) NOT NULL,
    operatore INT,
    data_accettazione DATETIME,
    data_chiusura DATETIME,
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS Operatori
(
    codice INT NOT NULL,
    nome VARCHAR(0) NOT NULL,
    password VARCHAR(0) NOT NULL,
    data_di_nascita DATE NOT NULL,
    PRIMARY KEY(codice)
);

CREATE TABLE IF NOT EXISTS Interventi
(
    segnalazione INT NOT NULL,
    numero INT NOT NULL,
    utente VARCHAR(50),
    operatore INT,
    messaggio VARCHAR(0) NOT NULL,
    data DATETIME NOT NULL,
    PRIMARY KEY(segnalazione, numero)
);

CREATE TABLE IF NOT EXISTS Condivisioni
(
    id_file INT NOT NULL,
    utente VARCHAR(50) NOT NULL,
    lettura TINYINT(1),
    scrittura TINYINT(1),
    data DATE,
    PRIMARY KEY(id_file, utente)
);


# Create FKs
ALTER TABLE File
    ADD    FOREIGN KEY (directory)
    REFERENCES Directory(ID)
;
    
ALTER TABLE Directory
    ADD    FOREIGN KEY (padre)
    REFERENCES Directory(ID)
;
    
ALTER TABLE Directory
    ADD    FOREIGN KEY (propietario)
    REFERENCES Utenti(email)
;
    
ALTER TABLE Preferenze
    ADD    FOREIGN KEY (utente)
    REFERENCES Utenti(email)
;
    
ALTER TABLE Downloads
    ADD    FOREIGN KEY (versione_file)
    REFERENCES Versioni(file)
;
    
ALTER TABLE Downloads
    ADD    FOREIGN KEY (versione_numero)
    REFERENCES Versioni(numero)
;
    
ALTER TABLE Downloads
    ADD    FOREIGN KEY (utente)
    REFERENCES Utenti(email)
;
    
ALTER TABLE Visualizzazioni
    ADD    FOREIGN KEY (utente)
    REFERENCES Utenti(email)
;
    
ALTER TABLE Segnalazioni
    ADD    FOREIGN KEY (utente)
    REFERENCES Utenti(email)
;
    
ALTER TABLE Segnalazioni
    ADD    FOREIGN KEY (operatore)
    REFERENCES Operatori(codice)
;
    
ALTER TABLE Interventi
    ADD    FOREIGN KEY (segnalazione)
    REFERENCES Segnalazioni(ID)
;
    
ALTER TABLE Interventi
    ADD    FOREIGN KEY (utente)
    REFERENCES Utenti(email)
;
    
ALTER TABLE Interventi
    ADD    FOREIGN KEY (operatore)
    REFERENCES Operatori(codice)
;
    
ALTER TABLE Condivisioni
    ADD    FOREIGN KEY (utente)
    REFERENCES Utenti(email)
;
    
ALTER TABLE File
    ADD    FOREIGN KEY (proprietario)
    REFERENCES Utenti(email)
;
    
ALTER TABLE File
    ADD    FOREIGN KEY (numero_ultima_versione)
    REFERENCES Versioni(numero)
;
    
ALTER TABLE Preferenze
    ADD    FOREIGN KEY (id_file)
    REFERENCES File(id)
;
    
ALTER TABLE Condivisioni
    ADD    FOREIGN KEY (id_file)
    REFERENCES File(id)
;
    
ALTER TABLE Visualizzazioni
    ADD    FOREIGN KEY (id_versione)
    REFERENCES Versioni(id)
;
    

# Create Indexes