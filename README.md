# Animal_registry

Организуйте систему учёта для питомника, в котором живут домашние и вьючные животные.

# Задание 1:
Используя команду cat в терминале операционной системы Linux, создать
два файла: Домашние животные (заполнив файл собаками, кошками,
хомяками) и Вьючные животными (заполнив файл лошадьми, верблюдами и
ослами), а затем объединить их. Просмотреть содержимое созданного файла.
Переименовать файл, дав ему новое имя (Друзья человека).

![cat](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_1.JPG)

# Задание 2:
Создать директорию, переместить файл туда.

![mv](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_2.JPG)

# Задание 3:
Подключить дополнительный репозиторий MySQL. Установить любой пакет
из этого репозитория.

![rep1](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_3_1.JPG)
![rep2](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_3_2.JPG)
![rep3](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_3_3.JPG)

# Задание 4:
Установить и удалить deb-пакет с помощью dpkg.

![deb](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_4.JPG)

# Задание 5:
[Выложить историю команд в терминале ubuntu.](https://github.com/Minyosha/Animal_registry/blob/c72495c3efe00eb828ff1950d6d458e5c4a046b8/Ubuntu%20commands.txt)

# Задание 6:
Нарисовать диаграмму, в которой есть класс родительский класс, домашние
животные и вьючные животные, в составы которых в случае домашних
животных войдут классы: собаки, кошки, хомяки, а в класс вьючные животные
войдут: лошади, верблюды и ослы.

![Диаграмма](https://github.com/Minyosha/Animal_registry/blob/master/Screenshots/Scr_6.JPG)

# Задание 7:
В подключенном MySQL репозитории создать базу данных “Друзья
человека”.
```
CREATE DATABASE IF NOT EXISTS Human_friends;
USE Human_friends;
```

# Задание 8:
Создать таблицы с иерархией из диаграммы в БД.
```
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
```

# Задание 9:
Заполнить низкоуровневые таблицы именами(животных), командами
которые они выполняют и датами рождения.
```
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
```

# Задание 10:
Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой
питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу.
```
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Camels;
SELECT * FROM Camels;

SELECT Name, Birthday, Commands FROM Horses
UNION SELECT  Name, Birthday, Commands FROM Donkeys;
```

# Задание 11:
Создать новую таблицу “молодые животные” в которую попадут все
животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
до месяца подсчитать возраст животных в новой таблице.
```
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
```

# Задание 12:
Объединить все таблицы в одну, при этом сохраняя поля, указывающие на
прошлую принадлежность к старым таблицам.
```
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
```

# Задание 13:
Создать класс с Инкапсуляцией методов и наследованием по диаграмме.

# Задание 14:
Написать программу, имитирующую работу реестра домашних животных. Реализовать следующий функционал:
* Завести новое животное;
* Определять животное в правильный класс;
* Увидеть список команд, которое выполняет животное;
* Обучить животное новым командам;
* Реализовать навигацию по меню;

# Задание 15:
Создайте класс Счетчик, у которого есть метод add(), увеличивающий
значение внутренней int переменной на 1 при нажатие “Завести новое
животное” Сделайте так, чтобы с объектом такого типа можно было работать в
блоке try-with-resources. Нужно бросить исключение, если работа с объектом
типа счетчик была не в ресурсном try и/или ресурс остался открыт. Значение
считать в ресурсе try, если при заведения животного заполнены все поля.
