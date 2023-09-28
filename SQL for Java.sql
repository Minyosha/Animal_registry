CREATE DATABASE IF NOT EXISTS Human_friends;

USE Human_friends;

DROP TABLE IF EXISTS Animals;
DROP TABLE IF EXISTS Skills;

# Создание таблиц с иерархией классов
CREATE TABLE Animals (
	Id INT AUTO_INCREMENT PRIMARY KEY, 	
    Biological_species VARCHAR(20),
    Name VARCHAR(20), 
    Birthday DATE
);

INSERT INTO Animals (Biological_species, Name, Birthday) VALUES 
('Cat', 'Лузер', '2020-11-22'),
('Dog', 'Трюкач', '2021-05-22'),  
('Hamster', 'Бревно', '2022-02-22');

CREATE TABLE Skills (
	Animal_id int,
    Foreign KEY (Animal_id) REFERENCES Animals (Id) ON DELETE CASCADE ON UPDATE CASCADE,
    Skill VARCHAR(20)
);

INSERT INTO Skills (Animal_id, Skill) VALUES 
('1', 'Мяу'),
('2', 'Прыг'),  
('3', 'Лапу'),
('1', 'Голос'),  
('2', 'Откинуться');

SELECT Animals.Id, Animals.Biological_species, Animals.Name, Animals.Birthday, Skills.Skill FROM Animals
LEFT JOIN Skills ON Animals.Id = Skills.Animal_id;

SELECT Animals.Id, Animals.Biological_species, Animals.Name, Animals.Birthday, GROUP_CONCAT(Skills.Skill) FROM Animals
LEFT JOIN Skills ON Animals.Id = Skills.Animal_id
GROUP BY Animals.Id;

SELECT Biological_species, Id, Name, Birthday FROM animals ORDER BY Id;

SELECT skill FROM skills WHERE Skills.Animal_id = 5;

INSERT INTO Skills (Animal_id, Skill) VALUES ('5','Ауф');

DELETE FROM skills WHERE (animal_id = 5) AND (skill = 'Ауф');

SELECT id, Counter from counter WHERE (id = 1);

SELECT * FROM counter;
DROP TABLE counter;
CREATE TABLE Counter (id INT AUTO_INCREMENT PRIMARY KEY, Counter int);
INSERT INTO Counter (Counter) VALUES ('0');
UPDATE counter SET counter = counter + 1 WHERE id = 1;