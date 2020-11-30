-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema lramos6db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema lramos6db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lramos6db` DEFAULT CHARACTER SET utf8 ;
USE `lramos6db` ;

-- -----------------------------------------------------
-- Table `lramos6db`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Employee` (
  `SSN` INT(9) NOT NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `mInit` VARCHAR(45) NULL,
  `sex` CHAR(1) NULL,
  `DOB` DATE NULL,
  `phoneNo` VARCHAR(10) NULL,
  `employeeType` VARCHAR(45) NULL,
  `workLocation` VARCHAR(45) NULL,
  `salary` VARCHAR(45) NULL,
  `yearsWorked` VARCHAR(45) NULL,
  `address` VARCHAR(70) NULL,
  `hoursWorked` DECIMAL NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `managerSSN` INT(9) NULL,
  PRIMARY KEY (`SSN`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `username_UNIQUE` ON `lramos6db`.`Employee` (`username` ASC) VISIBLE;

CREATE UNIQUE INDEX `SSN_UNIQUE` ON `lramos6db`.`Employee` (`SSN` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Department` (
  `departmentNo` INT NOT NULL,
  `departmentName` VARCHAR(45) NULL,
  `managerSSN_FK2` INT(9) NULL,
  PRIMARY KEY (`departmentNo`),
  CONSTRAINT `managerSSN_FK2`
    FOREIGN KEY (`managerSSN_FK2`)
    REFERENCES `lramos6db`.`Employee` (`SSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `managerSSN_idx` ON `lramos6db`.`Department` (`managerSSN_FK2` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Mechanic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Mechanic` (
  `mechanicSSN` INT(9) NOT NULL,
  `departmentNo_FK2` INT NULL,
  PRIMARY KEY (`mechanicSSN`),
  CONSTRAINT `mechanicSSN`
    FOREIGN KEY (`mechanicSSN`)
    REFERENCES `lramos6db`.`Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `departmentNo_FK2`
    FOREIGN KEY (`departmentNo_FK2`)
    REFERENCES `lramos6db`.`Department` (`departmentNo`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `departmentNo_idx` ON `lramos6db`.`Mechanic` (`departmentNo_FK2` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`SalesAssociate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`SalesAssociate` (
  `associateSSN` INT(9) NOT NULL,
  `departmentNo_FK` INT NULL,
  PRIMARY KEY (`associateSSN`),
  CONSTRAINT `departmentNo_FK`
    FOREIGN KEY (`departmentNo_FK`)
    REFERENCES `lramos6db`.`Department` (`departmentNo`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `associateSSN`
    FOREIGN KEY (`associateSSN`)
    REFERENCES `lramos6db`.`Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `departmentNo_idx` ON `lramos6db`.`SalesAssociate` (`departmentNo_FK` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`SiteManager`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`SiteManager` (
  `siteManagerSSN` INT(9) NOT NULL,
  PRIMARY KEY (`siteManagerSSN`),
  CONSTRAINT `siteManagerSSN`
    FOREIGN KEY (`siteManagerSSN`)
    REFERENCES `lramos6db`.`Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lramos6db`.`Manager`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Manager` (
  `managerSSN` INT(9) NOT NULL,
  `superSSN` INT(9) NULL,
  PRIMARY KEY (`managerSSN`),
  CONSTRAINT `managerSSN`
    FOREIGN KEY (`managerSSN`)
    REFERENCES `lramos6db`.`Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `superSSN`
    FOREIGN KEY (`superSSN`)
    REFERENCES `lramos6db`.`SiteManager` (`siteManagerSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `superSSN_idx` ON `lramos6db`.`Manager` (`superSSN` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`CarBay`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`CarBay` (
  `carBayID` INT NOT NULL,
  `managerSSN_FK` INT(9) NULL,
  PRIMARY KEY (`carBayID`),
  CONSTRAINT `managerSSN_FK`
    FOREIGN KEY (`managerSSN_FK`)
    REFERENCES `lramos6db`.`Manager` (`managerSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `managerSSN_idx` ON `lramos6db`.`CarBay` (`managerSSN_FK` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Vehicle` (
  `VIN` VARCHAR(45) NOT NULL,
  `make` VARCHAR(45) NULL,
  `model` VARCHAR(45) NULL,
  `year` VARCHAR(45) NULL,
  `color` VARCHAR(45) NULL,
  `vehicleType` VARCHAR(45) NULL,
  `transmission` VARCHAR(45) NULL,
  `features` VARCHAR(45) NULL,
  `location` VARCHAR(45) NULL,
  `MPG` VARCHAR(45) NULL,
  `mileage` INT NULL,
  `price` INT NULL,
  `carBayID_FK` INT NULL,
  PRIMARY KEY (`VIN`),
  CONSTRAINT `carBayID_FK`
    FOREIGN KEY (`carBayID_FK`)
    REFERENCES `lramos6db`.`CarBay` (`carBayID`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `carBayID_idx` ON `lramos6db`.`Vehicle` (`carBayID_FK` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`ServiceTicket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`ServiceTicket` (
  `serviceTicketNo` INT NOT NULL,
  `VIN_FK` VARCHAR(45) NOT NULL,
  `mechanicSSN_FK` INT(9) NULL,
  `comment` VARCHAR(45) NULL,
  `serviceDate` DATE NULL,
  PRIMARY KEY (`serviceTicketNo`, `VIN_FK`),
  CONSTRAINT `VIN_FK`
    FOREIGN KEY (`VIN_FK`)
    REFERENCES `lramos6db`.`Vehicle` (`VIN`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `mechanicSSN_FK`
    FOREIGN KEY (`mechanicSSN_FK`)
    REFERENCES `lramos6db`.`Mechanic` (`mechanicSSN`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `VIN_idx` ON `lramos6db`.`ServiceTicket` (`VIN_FK` ASC) VISIBLE;

CREATE INDEX `mechanicSSN_idx` ON `lramos6db`.`ServiceTicket` (`mechanicSSN_FK` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Dealer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Dealer` (
  `dealerSSN` INT(9) NOT NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phoneNo` VARCHAR(10) NULL,
  `address` VARCHAR(45) NULL,
  `associateSSN_FK` INT(9) NULL,
  PRIMARY KEY (`dealerSSN`),
  CONSTRAINT `associateSSN_FK`
    FOREIGN KEY (`associateSSN_FK`)
    REFERENCES `lramos6db`.`SalesAssociate` (`associateSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `associateSSN_idx` ON `lramos6db`.`Dealer` (`associateSSN_FK` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Client` (
  `clientSSN` INT(9) NOT NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `sex` CHAR(1) NULL,
  `email` VARCHAR(45) NULL,
  `phoneNo` VARCHAR(10) NULL,
  `address` VARCHAR(45) NULL,
  `associateSSN_FK2` INT(9) NULL,
  `minimumPrice` VARCHAR(45) NULL,
  `maximumPrice` VARCHAR(45) NULL,
  PRIMARY KEY (`clientSSN`),
  CONSTRAINT `associateSSN_FK2`
    FOREIGN KEY (`associateSSN_FK2`)
    REFERENCES `lramos6db`.`SalesAssociate` (`associateSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `associateSSN_idx` ON `lramos6db`.`Client` (`associateSSN_FK2` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Location` (
  `locationID` INT NOT NULL,
  `locationName` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `siteManagerSSN_FK` INT(9) NULL,
  PRIMARY KEY (`locationID`),
  CONSTRAINT `siteManagerSSN_FK`
    FOREIGN KEY (`siteManagerSSN_FK`)
    REFERENCES `lramos6db`.`SiteManager` (`siteManagerSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `managerSSN_idx` ON `lramos6db`.`Location` (`siteManagerSSN_FK` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `lramos6db`.`Lot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lramos6db`.`Lot` (
  `lotNo` INT NOT NULL,
  `lotSize` VARCHAR(45) NULL,
  `locationID_FK` INT NULL,
  PRIMARY KEY (`lotNo`),
  CONSTRAINT `locationID`
    FOREIGN KEY (`locationID_FK`)
    REFERENCES `lramos6db`.`Location` (`locationID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `locationID_idx` ON `lramos6db`.`Lot` (`locationID_FK` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
