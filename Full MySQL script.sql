CREATE DATABASE IF NOT EXISTS Human_friends;

USE Human_friends;

DROP TABLE IF EXISTS Animals;

# Создание таблиц с иерархией классов
CREATE TABLE Animals (
	Id INT AUTO_INCREMENT PRIMARY KEY, 
	Animal_type VARCHAR(20)
);

INSERT INTO Animals (Animal_type) VALUES ('Домашние'), ('Вьючные');

CREATE TABLE Pack_animals (
	Id INT AUTO_INCREMENT PRIMARY KEY,
    Biological_species VARCHAR (20),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES Animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Pack_animals (Biological_species, Class_id) VALUES ('Лошади', 1), ('Верблюды', 1), ('Ослы', 1);

CREATE TABLE Home_animals (
	Id INT AUTO_INCREMENT PRIMARY KEY,
    Biological_species VARCHAR (20),
    Class_id INT,
    FOREIGN KEY (Class_id) REFERENCES Animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Home_animals (Biological_species, Class_id) VALUES ('Собаки', 2), ('Кошки', 2), ('Хомяки', 2);

# Заполнение таблиц данными
CREATE TABLE Horses (
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Biological_species_id int,
    Foreign KEY (Biological_species_id) REFERENCES Pack_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Horses (Name, Birthday, Commands, Biological_species_id) VALUES 
('Пегас', '2021-11-01', 'галоп, прыжок', 1),
('Фантом', '2020-03-10', 'галоп, копыто', 1),  
('Буря', '2019-02-09', 'прыжок, копыто', 1);

CREATE TABLE Camels (
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Biological_species_id int,
    Foreign KEY (Biological_species_id) REFERENCES Pack_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Camels (Name, Birthday, Commands, Biological_species_id) VALUES 
('Плевун', '2020-10-22', 'плевок', 2),
('Кусака', '2021-11-23', "пить", 2),  
('Шмурдяк', '2022-12-01', 'плевок, пить', 2);

CREATE TABLE Donkeys (
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Biological_species_id int,
    Foreign KEY (Biological_species_id) REFERENCES Pack_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Donkeys (Name, Birthday, Commands, Biological_species_id) VALUES 
('Иа', '2010-10-20', 'стоять', 3),
('Лягало', '2010-10-20', 'тупить', 3),  
('Упрямый', '2011-10-20', 'стоять, тупить', 3);

CREATE TABLE Dogs (
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Biological_species_id int,
    Foreign KEY (Biological_species_id) REFERENCES Home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Dogs (Name, Birthday, Commands, Biological_species_id) VALUES 
('Рекс', '2015-10-03', 'сидеть, лежать', 1),
('Гранд', '2016-09-13', 'сидеть', 1),  
('Цыган', '2017-08-23', 'лежать', 1);

CREATE TABLE Cats (
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Biological_species_id int,
    Foreign KEY (Biological_species_id) REFERENCES Home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO cats (Name, Birthday, Commands, Biological_species_id) VALUES 
('Мурзик', '2019-05-10', 'есть', 2),
('Мурчало', '2017-07-20', 'спать', 2),  
('Карась', '2015-09-10', 'мурчать', 2); 

CREATE TABLE Hamsters (
    Id INT AUTO_INCREMENT PRIMARY KEY, 
    Name VARCHAR(20), 
    Birthday DATE,
    Commands VARCHAR(50),
    Biological_species_id int,
    Foreign KEY (Biological_species_id) REFERENCES Home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO hamsters (Name, Birthday, Commands, Biological_species_id) VALUES 
('Лузер', '2020-11-22', 'протерпеться', 3),
('Трюкач', '2021-05-22', 'сальтуха', 3),  
('Бревно', '2022-02-22', 'утонуть', 3);

# Удаление верблюдов
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Camels;
SELECT * FROM Camels;

# Показ лошадей и ослов в одной таблице
SELECT Name, Birthday, Commands FROM Horses
UNION SELECT  Name, Birthday, Commands FROM Donkeys;

# Таблица со всеми животными
CREATE TEMPORARY TABLE All_animals AS 
SELECT *, 'Лошади' as Biological_species FROM Horses
UNION SELECT *, 'Ослы' AS Biological_species FROM Donkeys
UNION SELECT *, 'Верблюды' AS Biological_species FROM Camels
UNION SELECT *, 'Собаки' AS Biological_species FROM Dogs
UNION SELECT *, 'Кошки' AS Biological_species FROM Cats
UNION SELECT *, 'Хомяки' AS Biological_species FROM Hamsters;
SELECT * FROM All_animals;

# Таблица с молодыми животными (до 3х лет)
CREATE TABLE Young_animals AS
SELECT Name, Birthday, Commands, Biological_species, TIMESTAMPDIFF(MONTH, Birthday, CURDATE()) AS Age_in_month
FROM All_animals WHERE Birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR); 
SELECT * FROM Young_animals;

# Объединение таблиц в одну с сохранением полей
SELECT Horses.Name, Horses.Birthday, Horses.Commands, Pack_animals.Biological_species, Young_animals.Age_in_month 
FROM Horses
LEFT JOIN Young_animals ON Young_animals.Name = Horses.Name
LEFT JOIN Pack_animals ON Pack_animals.Id = Horses.Biological_species_id
UNION 
SELECT Donkeys.Name, Donkeys.Birthday, Donkeys.Commands, Pack_animals.Biological_species, Young_animals.Age_in_month 
FROM Donkeys 
LEFT JOIN Young_animals ON Young_animals.Name = Donkeys.Name
LEFT JOIN Pack_animals ON Pack_animals.Id = Donkeys.Biological_species_id
UNION
SELECT Cats.Name, Cats.Birthday, Cats.Commands, Home_animals.Biological_species, Young_animals.Age_in_month 
FROM Cats
LEFT JOIN Young_animals ON Young_animals.Name = Cats.Name
LEFT JOIN Home_animals ON Home_animals.Id = Cats.Biological_species_id
UNION
SELECT Dogs.Name, Dogs.Birthday, Dogs.Commands, Home_animals.Biological_species, Young_animals.Age_in_month 
FROM Dogs
LEFT JOIN Young_animals ON Young_animals.Name = Dogs.Name
LEFT JOIN Home_animals ON Home_animals.Id = Dogs.Biological_species_id
UNION
SELECT Hamsters.Name, Hamsters.Birthday, Hamsters.Commands, Home_animals.Biological_species, Young_animals.Age_in_month 
FROM Hamsters
LEFT JOIN Young_animals ON Young_animals.Name = Hamsters.Name
LEFT JOIN Home_animals ON Home_animals.Id = Hamsters.Biological_species_id
UNION 
SELECT Camels.Name, Camels.Birthday, Camels.Commands, Pack_animals.Biological_species, Young_animals.Age_in_month 
FROM Camels 
LEFT JOIN Young_animals ON Young_animals.Name = Camels.Name
LEFT JOIN Pack_animals ON Pack_animals.Id = Camels.Biological_species_id;