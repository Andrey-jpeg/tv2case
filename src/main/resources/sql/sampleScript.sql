
-- database S2DemoTV2Case
-- user S2DemoUser
-- password S2DemoPassword

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
    'BNAD',
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
    'TEkSTERE',
    'TEKST_OG_MUSIK',
    'UNPAID_AND_EXTRAORDINARY_EFFORT'
);

CREATE TYPE userrole AS ENUM ('admin', 'producer');


-- ************************************** Credit
CREATE TABLE ProductionCompany
(
    "id"   bigint NOT NULL,
    name varchar(250) NOT NULL,
    CONSTRAINT PK_productioncompany PRIMARY KEY ( "id" )
);

CREATE TABLE "User"
(
    "id"                   bigint NOT NULL,
    username             varchar(50) NOT NULL,
    email                varchar(50) NOT NULL,
    password             varchar(50) NOT NULL,
    user_type             userrole NOT NULL,
    id_productioncompany bigint NULL,
    CONSTRAINT PK_user PRIMARY KEY ( "id" ),
    CONSTRAINT FK_ProductionCompany_User FOREIGN KEY ( id_productioncompany ) REFERENCES ProductionCompany ( "id" )
);

CREATE TABLE Production
(
    "id"                   bigint NOT NULL,
    name                 varchar(250) NOT NULL,
    comments             varchar(500) NOT NULL,
    id_productioncompany bigint NOT NULL,
    CONSTRAINT PK_production PRIMARY KEY ( "id" ),
    CONSTRAINT FK_ProducionCompany_Production FOREIGN KEY ( id_productioncompany ) REFERENCES ProductionCompany ( "id" )
);

CREATE TABLE Participant
(
    "id"   bigint NOT NULL,
    name varchar(250) NOT NULL,
    CONSTRAINT PK_participant PRIMARY KEY ( "id" )
);

CREATE TABLE Credit
(
    "id"                   bigint NOT NULL,
    role                 creditrole NOT NULL,
    id_participant       bigint NOT NULL,
    id_production bigint NOT NULL,
    CONSTRAINT PK_credit PRIMARY KEY ( "id" ),
    CONSTRAINT FK_Participant_Credit FOREIGN KEY ( id_participant ) REFERENCES Participant ( "id" ),
    CONSTRAINT FK_Production_Credit FOREIGN KEY ( id_production ) REFERENCES Production ( "id" )
);



