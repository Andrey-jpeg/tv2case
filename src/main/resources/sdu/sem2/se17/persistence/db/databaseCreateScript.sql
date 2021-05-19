CREATE TYPE creditrole AS ENUM (
    'BILLEDKUNSTNERE',
    'BILLEDREDIGERING',
    'LYDREDIGERING',
    'CASTING',
    'COLOURGRADING',
    'DIRIGENTER',
    'DRONE',
    'PUPPETRY',
    'DUKKESKABER',
    'NARRATOR',
    'FOTOGRAFER',
    'PUBLISHER',
    'GRAFISKE_DESIGNERE',
    'INDTALERE',
    'KAPELMESTER',
    'KLIPPER',
    'KONCEPT',
    'KONSULENT',
    'KOR',
    'KOREOGRAFI',
    'LYDMESTER',
    'LYS',
    'MEDVIRKENDE',
    'MUSIKERE',
    'MUSIKALSK_ARRANGEMENT',
    'BAND',
    'TRANSLATORS',
    'PRODUCENT',
    'PRODUCER',
    'PRODUKTIONSKOORDINATOR_EL_PRODUKTIONSLEDER',
    'PROGRAMANSVARLIG',
    'REDAKTION',
    'EDITOR',
    'PROBS',
    'SCENOGRAFER',
    'SCRIPTER',
    'SPECIAL_EFFECTS',
    'SPONSORE',
    'ANIMATION',
    'TEKSTERE',
    'TEKST_OG_MUSIK',
    'UNPAID_AND_EXTRAORDINARY_EFFORT'
);

CREATE TYPE userrole AS ENUM ('admin', 'producer');

CREATE TYPE approval as ENUM ('not_seen', 'not_approved', 'approved');

-- ************************************** Credit
CREATE TABLE ProductionCompany
(
    id BIGSERIAL PRIMARY KEY ,
    name varchar(250) NOT NULL
);

CREATE TABLE "User"
(
    id BIGSERIAL PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    user_type userrole NOT NULL,
    companyId bigint REFERENCES ProductionCompany(id)
);

CREATE TABLE Production
(
    id BIGSERIAL PRIMARY KEY,
    companyId bigint NOT NULL REFERENCES ProductionCompany(id),
    approvalStatus approval NOT NULL,
    name varchar(250) NOT NULL,
    comments varchar(500) DEFAULT ""

);

CREATE TABLE Participant
(
    id BIGSERIAL PRIMARY KEY ,
    name varchar(250) NOT NULL
);

CREATE TABLE Credit
(
    id BIGSERIAL PRIMARY KEY,
    role creditrole NOT NULL,
    participantId bigint NOT NULL REFERENCES Participant(id),
    productionId bigint NOT NULL REFERENCES Production(id) ON DELETE CASCADE
);

INSERT INTO ProductionCompany( name )
values ( 'TV2'),
       ( 'DR1');

INSERT INTO "User"( username, email, password, user_type, companyId)
VALUES ( 'sysadmin', 'sysadmin@sys.dk', '1234', 'admin', NULL),
       ( 'DR1', 'dr1@dr1.dk', '2345', 'producer', 2),
       ( 'TV2', 'tv2@tv2.dk', '3456', 'producer', 1);


INSERT INTO Production ( companyId, approvalStatus, name, comments)
VALUES ( 1 ,'not_approved', 'blinkende lygter', 'Anders Andersen var klipper ikke billedkunstner '),
       ( 1 ,'not_seen', 'Blå mænd' , NULL),
       ( 2 ,'not_seen', 'De grønne slagtere', NULL);

INSERT INTO Participant( name)
VALUES ('Anders Andersen'),
       ('Hans Hansen'),
       ('Peter jensen');


INSERT INTO Credit( role, participantId, productionId)
VALUES ('BILLEDKUNSTNERE', 1, 1),
       ('BILLEDKUNSTNERE', 2 ,3),
       ('BILLEDKUNSTNERE', 3, 2);

