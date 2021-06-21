# CloudStoreDB
Gestionale per una società di cloud storage.

## Generazione DB
Per la generazione della struttura del database è necessario utilizzare il file [CloudStore.sql](./Logico/ddl/CloudStore.sql) che contiene le ddl relative alla base dati.

## Riempimento DB
Per la generazione di dati coerenti a validi si può utilizzare lo script [dbgenerator.py](./PythonGenerator/dbgenerator.py).
Le variabili di configurazioni si trovano all'interno del codice

## Esecuzione Applicazione
Per l'esecuzione basterà scaricare il file jar ed eseguirlo

## Importazione progetto
Il progetto è stato sviluppato con IntelliJ ma può essere importato anche su Eclipse poichè è un normale Gradle project