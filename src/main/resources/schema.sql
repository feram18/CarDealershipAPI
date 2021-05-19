BEGIN;

DROP SCHEMA public CASCADE;

CREATE SCHEMA public
    AUTHORIZATION pzsezzvkqmgmyj;

SET client_encoding = 'UTF8';

CREATE TABLE "address" (
	address_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	street VARCHAR NOT NULL,
	city VARCHAR NOT NULL,
	state VARCHAR NOT NULL,
	zip_code INT NOT NULL
);

CREATE TABLE "employee" (
	id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	ssn VARCHAR(11) UNIQUE NOT NULL,
	first_name VARCHAR(45),
	middle_init CHAR(1),
	last_name VARCHAR(45),
	gender VARCHAR(6),
	dob DATE,
	phone_no VARCHAR(12),
	email VARCHAR(255) UNIQUE,
	work_location_id BIGINT,
	salary DECIMAL,
	date_started DATE DEFAULT now(),
	address_id BIGINT UNIQUE,
	hours_worked DECIMAL,
	employee_type VARCHAR(15)
);

CREATE TABLE "user" (
    user_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    username VARCHAR(45) UNIQUE NOT NULL,
    password VARCHAR(255),
    user_role VARCHAR(5),
    is_active BOOLEAN DEFAULT false
);

CREATE TABLE "site_manager" (
	id BIGINT UNIQUE NOT NULL PRIMARY KEY
);

CREATE TABLE "manager" (
	id BIGINT UNIQUE NOT NULL PRIMARY KEY,
	site_manager_id BIGINT
);

CREATE TABLE "mechanic" (
	id BIGINT UNIQUE NOT NULL PRIMARY KEY,
	manager_id BIGINT,
	dept_id BIGINT
);

CREATE TABLE "sales_associate" (
	id BIGINT UNIQUE NOT NULL PRIMARY KEY,
	manager_id BIGINT,
	dept_id BIGINT
);

CREATE TABLE "client" (
	client_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	client_ssn VARCHAR(11) UNIQUE NOT NULL,
	first_name VARCHAR(45),
	last_name VARCHAR(45),
	gender VARCHAR(6),
	email VARCHAR(255) UNIQUE,
	phone_no VARCHAR(12),
	address_id BIGINT UNIQUE,
	sales_associate_id BIGINT,
	min_price DECIMAL,
	max_price DECIMAL
);

CREATE TABLE "location" (
	location_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	location_name VARCHAR(45) UNIQUE NOT NULL,
	address_id BIGINT UNIQUE NOT NULL,
	site_manager_id BIGINT
);

CREATE TABLE "lot" (
	lot_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	lot_size DECIMAL NOT NULL,
	location_id BIGINT NOT NULL
);

CREATE TABLE "vehicle" (
	vehicle_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	vin VARCHAR(17) UNIQUE NOT NULL,
	make VARCHAR(45) NOT NULL,
	model VARCHAR(45) NOT NULL,
	year INT NOT NULL,
	color VARCHAR(45) NOT NULL,
	vehicle_type VARCHAR(11) NOT NULL,
	transmission VARCHAR(9) NOT NULL,
	features VARCHAR(255),
	mpg INT NOT NULL,
	mileage INT NOT NULL,
	price DECIMAL,
	lot_id BIGINT
);

CREATE TABLE "department" (
	dept_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	dept_name VARCHAR(45) NOT NULL,
	manager_id BIGINT UNIQUE,
	location_id BIGINT NOT NULL
);

CREATE TABLE "service_ticket" (
	ticket_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	vehicle_id BIGINT NOT NULL DEFAULT 000,
	mechanic_id BIGINT NOT NULL DEFAULT 000,
	date_created TIMESTAMP NOT NULL DEFAULT now(),
	date_updated TIMESTAMP NOT NULL DEFAULT now(),
	ticket_status VARCHAR(9) NOT NULL
);

CREATE TABLE "comment" (
	comment_id BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	ticket_id BIGINT NOT NULL,
	mechanic_id BIGINT NOT NULL DEFAULT 000,
	date_created TIMESTAMP NOT NULL DEFAULT now(),
	content TEXT NOT NULL
);

ALTER TABLE "employee"
	ADD CONSTRAINT FK_employee_work_location
	FOREIGN KEY (work_location_id)
	REFERENCES "location" (location_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "employee"
	ADD CONSTRAINT FK_employee_address
	FOREIGN KEY (address_id)
	REFERENCES "address" (address_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "user"
    ADD CONSTRAINT FK_user_id
    FOREIGN KEY (user_id)
    REFERENCES "employee" (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "site_manager"
	ADD CONSTRAINT FK_site_manager_id
	FOREIGN KEY (id)
	REFERENCES "employee" (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "manager"
	ADD CONSTRAINT FK_manager_id
	FOREIGN KEY (id)
	REFERENCES "employee" (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "manager"
	ADD CONSTRAINT FK_manager_site_manager
	FOREIGN KEY (site_manager_id)
	REFERENCES "site_manager" (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "mechanic"
	ADD CONSTRAINT FK_mechanic_id
	FOREIGN KEY (id)
	REFERENCES "employee" (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "mechanic"
	ADD CONSTRAINT FK_mechanic_manager
	FOREIGN KEY (manager_id)
	REFERENCES "manager" (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "mechanic"
	ADD CONSTRAINT FK_mechanic_department
	FOREIGN KEY (dept_id)
	REFERENCES "department" (dept_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "sales_associate"
	ADD CONSTRAINT FK_associate_id
	FOREIGN KEY (id)
	REFERENCES "employee" (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "sales_associate"
	ADD CONSTRAINT FK_associate_manager
	FOREIGN KEY (manager_id)
	REFERENCES "manager" (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "sales_associate"
	ADD CONSTRAINT FK_associate_dept
	FOREIGN KEY (dept_id)
	REFERENCES "department" (dept_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "client"
	ADD CONSTRAINT FK_client_address
	FOREIGN KEY (address_id)
	REFERENCES "address" (address_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "client"
	ADD CONSTRAINT FK_client_associate
	FOREIGN KEY (sales_associate_id)
	REFERENCES "sales_associate" (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "location"
	ADD CONSTRAINT FK_location_address
	FOREIGN KEY (address_id)
	REFERENCES "address" (address_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "location"
	ADD CONSTRAINT FK_location_site_manager
	FOREIGN KEY (site_manager_id)
	REFERENCES "site_manager" (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "lot"
	ADD CONSTRAINT FK_lot_location
	FOREIGN KEY (location_id)
	REFERENCES "location" (location_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "vehicle"
	ADD CONSTRAINT FK_vehicle_lot
	FOREIGN KEY (lot_id)
	REFERENCES "lot" (lot_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "department"
	ADD CONSTRAINT FK_department_manager
	FOREIGN KEY (manager_id)
	REFERENCES "manager" (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE "department"
	ADD CONSTRAINT FK_department_location
	FOREIGN KEY (location_id)
	REFERENCES "location" (location_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE "service_ticket"
	ADD CONSTRAINT FK_ticket_vehicle
	FOREIGN KEY (vehicle_id)
	REFERENCES "vehicle" (vehicle_id)
    ON DELETE SET DEFAULT
    ON UPDATE CASCADE;

ALTER TABLE "service_ticket"
	ADD CONSTRAINT FK_ticket_mechanic
	FOREIGN KEY (mechanic_id)
	REFERENCES "mechanic" (id)
    ON DELETE SET DEFAULT
    ON UPDATE CASCADE;

ALTER TABLE "comment"
	ADD CONSTRAINT FK_comment_ticket
	FOREIGN KEY (ticket_id)
	REFERENCES "service_ticket" (ticket_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE "comment"
	ADD CONSTRAINT FK_comment_mechanic
	FOREIGN KEY (mechanic_id)
	REFERENCES "mechanic" (id)
    ON DELETE SET DEFAULT
    ON UPDATE CASCADE;

END;