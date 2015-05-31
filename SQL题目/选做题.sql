CREATE TABLE Student(
	SNO VARCHAR(20),
	Name VARCHAR(10), 
	Age INTEGER,
	College varchar(30)
);

CREATE TABLE Course(
	CourseID VARCHAR(15),
	CourseName VARCHAR(30),
	CourseBeforeID VARCHAR(15)
);

CREATE TABLE Choose(
	SNO VARCHAR(20),
	CourseID VARCHAR(30),
	Score DECIMAL(5,2)
);

INSERT INTO Student(SNO, Name, Age, College) VALUES('S00001', '张三', 20, '计算机学院');
INSERT INTO Student(SNO, Name, Age, College) VALUES('S00002', '李四', 19, '通信学院');
INSERT INTO Student(SNO, Name, Age, College) VALUES('S00003', '王五', 21, '计算机学院');

INSERT INTO Course(CourseID, CourseName) VALUES('C1', '计算机引论');
INSERT INTO Course(CourseID, CourseName, CourseBeforeID) VALUES('C2', 'C语言', 'C1');
INSERT INTO Course(CourseID, CourseName, CourseBeforeID) VALUES('C3', '数据结构', 'C2');

INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00001', 'C1', 95);
INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00001', 'C2', 80);
INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00001', 'C3', 84);
INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00002', 'C1', 80);
INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00002', 'C2', 85);
INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00003', 'C1', 78);
INSERT INTO Choose(SNO, CourseID, Score) VALUES('S00003', 'C3', 70);

SELECT CourseID AS '课程编号', CourseName AS '课程名称', CourseBeforeID AS '课程原编号' FROM Course 
	WHERE CourseID IN (SELECT CourseID FROM Choose WHERE SNO = (SELECT SNO FROM Student WHERE Name = '王五') );

UPDATE Student SET Age=Age+2 WHERE College = '计算机学院';

DELETE FROM Choose WHERE SNO = (SELECT SNO FROM Student WHERE Name = '李四');
DELETE FROM Student WHERE Name = '李四';