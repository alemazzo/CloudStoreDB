
from mysql.connector import connect, Error, MySQLConnection
from random import randint

hostname = '127.0.0.1'
username = 'root'
password = ''
database = 'CloudStore'

"""
COSTANTS
"""
NUMERO_UTENTI = 10  # 10_000
NUMERO_OPERATORI = 20
NUMERO_DIRECTORY_RANDOM = 20  # 200_000
NUMERO_FILE_RANDOM = 30
NUMERO_PREFERENZE = 50

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
    year = randint(1900, 2021)
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
Step 1 : Connect to database
"""

database = connect(host=hostname, user=username, passwd=password, db=database)

#############################################################
#############################################################
#############################################################

"""
Step 2 : Generate Users
"""

USERS_EMAILS = []


def generate_users():
    print()
    print("-----------------------")
    print("Inizio creazione UTENTI")

    query = """
    INSERT INTO Utenti(Email, Nome, Cognome, DataRegistrazione, Password, DataNascita, NumeroDirectory) VALUES (%s, %s, %s, %s, %s, %s, %s)
    """
    query_cartella = """
    INSERT INTO Directories(Nome, DataCreazione, Proprietario) VALUES (%s, %s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_UTENTI):

        nome = random_name()
        cognome = random_surname()
        email = f'{nome}@gmail.com'
        password = random_words()
        data_registrazione = random_date()
        data_nascita = random_date()
        numero_directory = 1

        try:
            with database.cursor() as cursor:
                cursor.execute(query, (email, nome, cognome, data_registrazione,
                                       password, data_nascita, numero_directory))
                cursor.execute(
                    query_cartella, (nome, data_registrazione, email))
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
    print()
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

DIRECTORY_IDS = []


def generate_directory():
    print()
    print("-----------------------")
    print("Inizio creazione DIRECTORY")
    query = """
        INSERT INTO Directories(Nome, DataCreazione, Padre, Proprietario) VALUES (%s, %s, %s, %s)
    """
    SUCCESS = 0

    with database.cursor() as cursor:
        cursor.execute(
            "SELECT Id, Proprietario FROM Directories WHERE Padre is NULL")
        result = cursor.fetchall()
        for _id, proprietario in result:
            DIRECTORY_IDS.append((_id, proprietario))

    prev = 0
    for i in range(NUMERO_DIRECTORY_RANDOM):
        nome = random_words()
        data_creazione = random_date()
        directory = random_element(DIRECTORY_IDS)
        padre = directory[0]
        proprietario = directory[1]
        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (nome, data_creazione, padre, proprietario))
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
    print()
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
    INSERT INTO Files(Directory, Nome, Estensione, Proprietario) VALUES (%s, %s, %s, %s)
    """
    SUCCESS = 0

    prev = 0
    for i in range(NUMERO_FILE_RANDOM):

        nome = random_words()
        estensione = ['exe', 'png', 'csv', 'jpg', 'txt'][randint(0, 4)]
        directory, proprietario = random_element(DIRECTORY_IDS)

        try:
            with database.cursor() as cursor:
                cursor.execute(
                    query, (directory, nome, estensione, proprietario))
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
    print()
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

"""
Final Step: Close the database
"""
database.close()
