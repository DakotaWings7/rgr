DROP EXTENSION IF EXISTS "uuid-ossp";
DROP TABLE IF EXISTS University CASCADE;
DROP TABLE IF EXISTS Speciality CASCADE;
DROP TABLE IF EXISTS Graduate CASCADE;
DROP TABLE IF EXISTS Admission CASCADE;
DROP TABLE IF EXISTS Employment CASCADE;

CREATE EXTENSION "uuid-ossp";

CREATE TABLE University
(
    id      uuid PRIMARY KEY,
    name    varchar(255),
    address varchar(255)
);

CREATE TABLE Speciality
(
    id              uuid PRIMARY KEY,
    name            varchar(255),
    university_id   uuid REFERENCES University (id) ON DELETE SET NULL,
    education_years integer,
    CHECK ( education_years BETWEEN 0 AND 10)
);

CREATE TABLE Admission
(
    id             uuid PRIMARY KEY,
    speciality_id  uuid REFERENCES Speciality (id) ON DELETE CASCADE,
    admission_year integer,
    students_count integer,
    CHECK ( students_count >= 0),
    CHECK ( admission_year BETWEEN 1900 AND 2024)
);

CREATE TABLE Graduate
(
    id            uuid PRIMARY KEY,
    first_name    varchar(255),
    last_name     varchar(255),
    middle_name   varchar(255),
    graduate_year integer,
    speciality_id uuid REFERENCES Speciality (id) ON DELETE CASCADE,
    CHECK ( graduate_year BETWEEN 1900 AND 2024)
);

CREATE TABLE Employment
(
    id           uuid PRIMARY KEY,
    graduate_id  uuid REFERENCES Graduate (id) ON DELETE CASCADE,
    company_name varchar(255),
    position     varchar(255),
    salary       integer,
    CHECK ( salary > 0 )
);