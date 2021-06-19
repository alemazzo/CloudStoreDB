
from mysql.connector import connect, Error, MySQLConnection
from random import randint
from time import sleep

hostname = '127.0.0.1'
username = 'root'
password = ''
database = 'CloudStore'

"""
COSTANTS
"""
ELIMINA_DATABASE = False

NUMERO_UTENTI = 10  # 10_000
NUMERO_OPERATORI = 50
NUMERO_DIRECTORY_RANDOM = 50  # 200_000
NUMERO_FILE_RANDOM = 100
NUMERO_PREFERENZE = 50
NUMERO_CONDIVISIONI = 50
NUMERO_VISUALIZZAZIONI = 100
NUMERO_SEGNALAZIONI = 10

SUCCESS = 0

"""
Step 0 : Load Datasets
"""
names = []
surnames = []
words = []


def load_dataset():
    print("Caricamento dei dataset...")
    # Load Names
    with open('source/names.txt') as file:
        for line in file.readlines():
            names.append(line.replace('\r', '').replace('\n', ''))

    # Load Surnames
    with open('source/surnames.txt') as file:
        for line in file.readlines():
            surnames.append(line.replace('\r', '').replace('\n', ''))

    # Load Words
    with open('source/words.txt') as file:
        for line in file.readlines():
            words.append(line.replace('\r', '').replace('\n', ''))

    print("Dataset caricati")


def random_element(lista):
    return lista[randint(0, len(lista) - 1)]


def random_number():
    return randint(0, 1_000_000)


def random_name():
    return names[randint(0, len(names) - 1)]


def random_surname():
    return surnames[randint(0, len(surnames) - 1)]


def random_words():
    return words[randint(0, len(words) - 1)]


def random_date():
    year = randint(1900, 2000)
    month = randint(1, 12)
    day = randint(1, 28)
    return f'{year}/{month}/{day}'


"""
START
"""

load_dataset()

#############################################################
#############################################################
#############################################################

"""
Step 1 : Connect to database and delete all records
"""

database = connect(host=hostname, user=username, passwd=password, db=database)


def eliminaDatabase():

    print()
    print("-----------------------")
    print("cancellazione DB")

    query = """
    SET FOREIGN_KEY_CHECKS = 0; 
    TRUNCATE TABLE Utenti;
    TRUNCATE TABLE Directories;
    TRUNCATE TABLE Files;
    TRUNCATE TABLE Preferenze;
    TRUNCATE TABLE Versioni;
    TRUNCATE TABLE Downloads;
    TRUNCATE TABLE Visualizzazioni;
    TRUNCATE TABLE Segnalazioni;
    TRUNCATE TABLE Operatori;
    TRUNCATE TABLE Interventi;
    TRUNCATE TABLE Condivisioni;
    SET FOREIGN_KEY_CHECKS = 1;
    """
    try:
        database._execute_query(query)
    except Exception as e:
        print(e)

# NON SO PERCHé NON FUNZIONA
# if ELIMINA_DATABASE:
#    eliminaDatabase()

#############################################################
#############################################################
#############################################################


"""
Step 2 : Generate Users
"""

USERS_EMAILS = []


def generate_users():
    print("-----------------------")
    print("Inizio creazione UTENTI")

    query = """
    INSERT INTO Utenti(Email, Nome, Cognome, Password, DataNascita) VALUES (%s, %s, %s, %s, %s)
    """
    query_cartella = """
    INSERT INTO Directories(Nome, Proprietario) VALUES (%s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_UTENTI):

        nome = random_name()
        cognome = random_surname()
        email = f'{nome}@gmail.com'
        password = random_words()
        data_nascita = random_date()

        try:
            with database.cursor() as cursor:
                cursor.execute(query, (email, nome, cognome,
                                       password, data_nascita))
                cursor.execute(
                    query_cartella, (nome, email))
            SUCCESS += 1
            USERS_EMAILS.append(email)
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_UTENTI)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
        # print(f'[nome = {nome}, cognome = {cognome}, email = {email}, password = {password}, data = {data}, numero_directory = {numero_directory}]')
    database.commit()
    print(f'\n{SUCCESS} utenti creati')
    print("-----------------------")
    print()


generate_users()

#############################################################
#############################################################
#############################################################

"""
Step 3 : Generate Operatori
"""

OPERATORI_CODICI = []


def generate_operatori():
    print("-----------------------")
    print("Inizio creazione OPERATORI")

    query = """
    INSERT INTO Operatori(Codice, Nome, Password, DataNascita) VALUES (%s, %s, %s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_OPERATORI):

        codice = random_number()
        nome = random_name()
        password = random_words()
        data_nascita = random_date()

        try:
            with database.cursor() as cursor:
                cursor.execute(query, (codice, nome, password, data_nascita))
            SUCCESS += 1
            OPERATORI_CODICI.append(codice)
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_OPERATORI)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
        # print(f'[nome = {nome}, cognome = {cognome}, email = {email}, password = {password}, data = {data}, numero_directory = {numero_directory}]')
    database.commit()
    print(f'\n{SUCCESS} operatori creati')
    print("-----------------------")
    print()


generate_operatori()

#############################################################
#############################################################
#############################################################

"""
Step 4 : Generate Directory
"""


def generate_directory():
    print("-----------------------")
    print("Inizio creazione DIRECTORY")
    query = """
        INSERT INTO Directories(Nome, Padre) VALUES (%s, %s)
    """
    SUCCESS = 0
    DIRECTORY_IDS = []

    with database.cursor() as cursor:
        cursor.execute(
            "SELECT Id, Proprietario FROM Directories")
        result = cursor.fetchall()
        for _id, proprietario in result:
            DIRECTORY_IDS.append((_id, proprietario))

    prev = 0
    for i in range(NUMERO_DIRECTORY_RANDOM):
        nome = random_words()
        padre = random_element(DIRECTORY_IDS)[0]
        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (nome, padre))
            SUCCESS += 1
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_DIRECTORY_RANDOM)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
    database.commit()
    print(f'\n{SUCCESS} Directory create')
    print("-----------------------")
    print()


generate_directory()

#############################################################
#############################################################
#############################################################

"""
Step 5 : Generate File
"""


def generate_files():
    print("-----------------------")
    print("Inizio creazione FILES")

    DIRECTORY_IDS = []
    with database.cursor() as cursor:

        cursor.execute(
            "SELECT Id, Proprietario FROM Directories")
        result = cursor.fetchall()
        for _id, proprietario in result:
            DIRECTORY_IDS.append((_id, proprietario))

    query = """
    INSERT INTO Files(Directory, Nome, Estensione) VALUES (%s, %s, %s)
    """
    queryVersione = """
    INSERT INTO Versioni (File, Dimensione, Link) VALUES (%s, %s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_FILE_RANDOM):

        directory = random_element(DIRECTORY_IDS)[0]
        nome = random_words()
        estensione = ['exe', 'png', 'csv', 'jpg', 'txt'][randint(0, 4)]
        link = f'https://{random_words()}.{random_words()}'
        dimensione = random_number()

        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (directory, nome, estensione))
                cursor.execute(
                    queryVersione, (cursor.lastrowid, dimensione, link))

            SUCCESS += 1
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_FILE_RANDOM)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
    database.commit()
    print(f'\n{SUCCESS} file creati')
    print("-----------------------")
    print()


generate_files()


#############################################################
#############################################################
#############################################################

"""
Step 6 : Generate Preferenze
"""


def generate_preferenze():
    print("-----------------------")
    print("Inizio creazione PREFERENZE")

    FILE_IDS = []
    with database.cursor() as cursor:
        cursor.execute(
            "SELECT Id, Proprietario FROM Files")
        result = cursor.fetchall()
        for _id, proprietario in result:
            FILE_IDS.append((_id, proprietario))

    query = """
    INSERT INTO Preferenze(File, Utente, DataPreferenza) VALUES (%s, %s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_PREFERENZE):
        file, utente = random_element(FILE_IDS)
        data = random_date()

        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (file, utente, data))
            SUCCESS += 1
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_PREFERENZE)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
    database.commit()
    print(f'\n{SUCCESS} preferenze create')
    print("-----------------------")
    print()


generate_preferenze()


#############################################################
#############################################################
#############################################################

"""
Step 7 : Generate Condivisioni
"""


def generate_condivisioni():
    print("-----------------------")
    print("Inizio creazione CONDIVISIONI")

    FILE_IDS = []
    UTENTI_IDS = []
    with database.cursor() as cursor:

        # Retrieve the files
        cursor.execute(
            "SELECT Id, Proprietario FROM Files")
        result = cursor.fetchall()
        for _id, proprietario in result:
            FILE_IDS.append((_id, proprietario))

        # Retrieve the Utenti
        cursor.execute("SELECT Email FROM Utenti")
        result = cursor.fetchall()
        for _id in result:
            UTENTI_IDS.append(_id)

    query = """
    INSERT INTO Condivisioni(File, Utente, Lettura, Scrittura) VALUES (%s, %s, %s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_CONDIVISIONI):
        file = random_element(FILE_IDS)[0]
        utente = random_element(UTENTI_IDS)[0]
        lettura = True
        scrittura = randint(0, 1) == 1

        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (file, utente, lettura, scrittura))
            SUCCESS += 1
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_CONDIVISIONI)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
    database.commit()
    print(f'\n{SUCCESS} condivisioni create')
    print("-----------------------")
    print()


generate_condivisioni()

#############################################################
#############################################################
#############################################################

"""
Step 8 : Generate Condivisioni
"""


def generate_visualizzazioni():
    print("-----------------------")
    print("Inizio creazione VISUALIZZAZIONI")

    VERSIONI_ID = []
    UTENTI_IDS = []
    with database.cursor() as cursor:

        # Retrieve the files
        cursor.execute(
            "SELECT Id FROM Versioni")
        result = cursor.fetchall()
        for _id in result:
            VERSIONI_ID.append(_id)

        # Retrieve the Utenti
        cursor.execute("SELECT Email FROM Utenti")
        result = cursor.fetchall()
        for _id in result:
            UTENTI_IDS.append(_id)

    query = """
    INSERT INTO Visualizzazioni(Versione, Utente) VALUES (%s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_VISUALIZZAZIONI):
        versione = random_element(VERSIONI_ID)[0]
        utente = random_element(UTENTI_IDS)[0]

        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (versione, utente))
            SUCCESS += 1
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_VISUALIZZAZIONI)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
    database.commit()
    print(f'\n{SUCCESS} visualizzazioni create')
    print("-----------------------")
    print()


generate_visualizzazioni()


#############################################################
#############################################################
#############################################################

"""
Step 9 : Generate Segnalazioni
"""


def generate_segnalazioni():
    print("-----------------------")
    print("Inizio creazione SEGNALAZIONI")

    UTENTI_IDS = []
    with database.cursor() as cursor:

        # Retrieve the Utenti
        cursor.execute("SELECT Email FROM Utenti")
        result = cursor.fetchall()
        for _id in result:
            UTENTI_IDS.append(_id)

    query = """
    INSERT INTO Segnalazioni(Utente, Descrizione) VALUES (%s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_SEGNALAZIONI):
        utente = random_element(UTENTI_IDS)[0]
        descrizione = random_words()

        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (utente, descrizione))
            SUCCESS += 1
        except Exception as e:
            pass

        percentage = int((i + 1) * 100 / NUMERO_SEGNALAZIONI)
        if percentage != prev:
            print(f'\rProgress: {percentage} %', end='')
            prev = percentage
    database.commit()
    print(f'\n{SUCCESS} segnalazioni create')
    print("-----------------------")
    print()


generate_segnalazioni()

"""
Final Step: Close the database
"""
database.close()
