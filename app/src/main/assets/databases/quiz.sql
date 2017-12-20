BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `quiz` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`cat_id`	INTEGER NOT NULL,
	`question`	TEXT NOT NULL,
	`options`	TEXT NOT NULL,
	`answer`	TEXT NOT NULL,
	`image`	TEXT
);
INSERT INTO `quiz` (id,cat_id,question,options,answer,image) VALUES (1,1,'My job frequently involves having to work _____intense pressure. ','below,under,beneath,underneath
','under',NULL),
 (2,1,'This type of decision has to be made at ______ board
','layer,rank,grade,level','level',NULL),
 (3,1,'Name the Toyota ___ car in the picture below?','seeking,pursuing,searching,hunting
','seeking','car1'),
 (4,1,'This Mazda car was manufactured which year?','introduce,innovate,confer,embark
','introduce','car2'),
 (5,1,'The company has decided to a share option scheme, starting next year
','down,on,up,off
','off',NULL),
 (6,1,'"Lack of orders has meant that a number of employees have been laid"','delivery,
arrival,
transport,
postage
','delivery',NULL),
 (7,1,'The company has good industrial ____  and disputes are rare. ','institution,
initiation,
introduction,
installation
','installation',NULL);
CREATE TABLE IF NOT EXISTS `category` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`image`	TEXT NOT NULL,
	`quiz_type`	INTEGER NOT NULL
);
INSERT INTO `category` (id,name,image,quiz_type) VALUES (1,'General','general',0),
 (2,'Politics','politics',0),
 (3,'Geography','geography',0),
 (4,'Business','business',0),
 (5,'History','history',0),
 (6,'Education','education',0),
 (7,'Celebrity','celebrity',1),
 (8,'Personality','personality',1),
 (9,'Animals','animals',1),
 (10,'Cars','cars',1),
 (11,'Logo','logo',1),
 (12,'Fruits','fruits',1);
COMMIT;
