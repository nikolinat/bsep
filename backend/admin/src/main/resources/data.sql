INSERT INTO roles (name) values ('ROLE_ADMIN');
INSERT INTO roles (name) values ('ROLE_HOUSE_OWNER');
INSERT INTO roles (name) values ('ROLE_TENANT');

INSERT INTO users (email_address, name, last_name, password, username, salt, deleted) values
    ('mirkomiric@gmail.com','mirko','miric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','mirko123','0e4ace1c-d09e-11ec-9d64-0242ac120002',false);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO users (email_address, name, last_name, password, username, salt, deleted) values
    ('peraperic@gmail.com','pera','peric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','pera123','0e4ace1c-d09e-11ec-9d64-0242ac120002',false);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO users (email_address, name, last_name, password, username, salt, deleted) values
    ('zikazikic@gmail.com','zika','zikic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','zika123','0e4ace1c-d09e-11ec-9d64-0242ac120002',false);
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);

INSERT INTO users (email_address, name, last_name, password, username, salt, deleted) values
    ('anamirkovic@gmail.com','ana','mirkovic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','ana123','0e4ace1c-d09e-11ec-9d64-0242ac120002',false);
INSERT INTO user_role (user_id, role_id) VALUES (4, 3);