INSERT INTO Categories(id ,category_name ,category_description ) VALUES(1,'Technical','This category represent the techincal related skills like Java, BigData, Agile,DevOPs etc');
INSERT INTO Categories(id ,category_name ,category_description ) VALUES(2, 'Non Technical', 'This category represent the non techincal related skill like Admin, HR, IT Team etc');


INSERT INTO IDEA_STATUS(id,status,status_description) values(1,'Draft','Initial status of idea');
INSERT INTO IDEA_STATUS(id,status,status_description) values(2,'Submitted',' Submit the idea');
INSERT INTO IDEA_STATUS(id,status,status_description) values(3,'Reviewed','Review done on idea');
INSERT INTO IDEA_STATUS(id,status,status_description) values(4,'ReSubmitted','Idea get resubmit after suggested changes');
INSERT INTO IDEA_STATUS(id,status,status_description) values(5,'Approved','Idea approved');
INSERT INTO IDEA_STATUS(id,status,status_description) values(6,'Rejected','Idea Rejected');
INSERT INTO IDEA_STATUS(id,status,status_description) values(7,'Development','Idea in development phase');
INSERT INTO IDEA_STATUS(id,status,status_description) values(8,'Compleleted','Idea executed completely');
INSERT INTO IDEA_STATUS(id,status,status_description) values(9,'Closed','Idea close state');

INSERT INTO authority (id,role) VALUES (1,'ROLE_ADMIN');
INSERT INTO authority (id,role) VALUES (2,'ROLE_MANAGER');
INSERT INTO authority (id,role) VALUES (3,'ROLE_USER');

