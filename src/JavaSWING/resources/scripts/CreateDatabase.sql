-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Table `Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Employee` (
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

CREATE UNIQUE INDEX `username_UNIQUE` ON `Employee` (`username` ASC);

CREATE UNIQUE INDEX `SSN_UNIQUE` ON `Employee` (`SSN` ASC);


-- -----------------------------------------------------
-- Table `Department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Department` (
  `departmentNo` INT NOT NULL,
  `departmentName` VARCHAR(45) NULL,
  `managerSSN_FK2` INT(9) NULL,
  PRIMARY KEY (`departmentNo`),
  CONSTRAINT `managerSSN_FK2`
    FOREIGN KEY (`managerSSN_FK2`)
    REFERENCES `Employee` (`SSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `managerSSN_idx` ON `Department` (`managerSSN_FK2` ASC);


-- -----------------------------------------------------
-- Table `Mechanic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Mechanic` (
  `mechanicSSN` INT(9) NOT NULL,
  `departmentNo_FK2` INT NULL,
  PRIMARY KEY (`mechanicSSN`),
  CONSTRAINT `mechanicSSN`
    FOREIGN KEY (`mechanicSSN`)
    REFERENCES `Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `departmentNo_FK2`
    FOREIGN KEY (`departmentNo_FK2`)
    REFERENCES `Department` (`departmentNo`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `departmentNo_idx` ON `Mechanic` (`departmentNo_FK2` ASC);


-- -----------------------------------------------------
-- Table `SalesAssociate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SalesAssociate` (
  `associateSSN` INT(9) NOT NULL,
  `departmentNo_FK` INT NULL,
  PRIMARY KEY (`associateSSN`),
  CONSTRAINT `departmentNo_FK`
    FOREIGN KEY (`departmentNo_FK`)
    REFERENCES `Department` (`departmentNo`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `associateSSN`
    FOREIGN KEY (`associateSSN`)
    REFERENCES `Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `departmentNo_idx` ON `SalesAssociate` (`departmentNo_FK` ASC);


-- -----------------------------------------------------
-- Table `SiteManager`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SiteManager` (
  `siteManagerSSN` INT(9) NOT NULL,
  PRIMARY KEY (`siteManagerSSN`),
  CONSTRAINT `siteManagerSSN`
    FOREIGN KEY (`siteManagerSSN`)
    REFERENCES `Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Manager`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Manager` (
  `managerSSN` INT(9) NOT NULL,
  `superSSN` INT(9) NULL,
  PRIMARY KEY (`managerSSN`),
  CONSTRAINT `managerSSN`
    FOREIGN KEY (`managerSSN`)
    REFERENCES `Employee` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `superSSN`
    FOREIGN KEY (`superSSN`)
    REFERENCES `SiteManager` (`siteManagerSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `superSSN_idx` ON `Manager` (`superSSN` ASC);


-- -----------------------------------------------------
-- Table `CarBay`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CarBay` (
  `carBayID` INT NOT NULL,
  `managerSSN_FK` INT(9) NULL,
  PRIMARY KEY (`carBayID`),
  CONSTRAINT `managerSSN_FK`
    FOREIGN KEY (`managerSSN_FK`)
    REFERENCES `Manager` (`managerSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `managerSSN_idx` ON `CarBay` (`managerSSN_FK` ASC);


-- -----------------------------------------------------
-- Table `Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Vehicle` (
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
    REFERENCES `CarBay` (`carBayID`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `carBayID_idx` ON `Vehicle` (`carBayID_FK` ASC);


-- -----------------------------------------------------
-- Table `ServiceTicket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ServiceTicket` (
  `serviceTicketNo` INT NOT NULL,
  `VIN_FK` VARCHAR(45) NOT NULL,
  `mechanicSSN_FK` INT(9) NULL,
  `comment` VARCHAR(45) NULL,
  `serviceDate` DATE NULL,
  PRIMARY KEY (`serviceTicketNo`, `VIN_FK`),
  CONSTRAINT `VIN_FK`
    FOREIGN KEY (`VIN_FK`)
    REFERENCES `Vehicle` (`VIN`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `mechanicSSN_FK`
    FOREIGN KEY (`mechanicSSN_FK`)
    REFERENCES `Mechanic` (`mechanicSSN`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `VIN_idx` ON `ServiceTicket` (`VIN_FK` ASC);

CREATE INDEX `mechanicSSN_idx` ON `ServiceTicket` (`mechanicSSN_FK` ASC);


-- -----------------------------------------------------
-- Table `Dealer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Dealer` (
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
    REFERENCES `SalesAssociate` (`associateSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `associateSSN_idx` ON `Dealer` (`associateSSN_FK` ASC);


-- -----------------------------------------------------
-- Table `Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Client` (
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
    REFERENCES `SalesAssociate` (`associateSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `associateSSN_idx` ON `Client` (`associateSSN_FK2` ASC);


-- -----------------------------------------------------
-- Table `Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Location` (
  `locationID` INT NOT NULL,
  `locationName` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `siteManagerSSN_FK` INT(9) NULL,
  PRIMARY KEY (`locationID`),
  CONSTRAINT `siteManagerSSN_FK`
    FOREIGN KEY (`siteManagerSSN_FK`)
    REFERENCES `SiteManager` (`siteManagerSSN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `managerSSN_idx` ON `Location` (`siteManagerSSN_FK` ASC);


-- -----------------------------------------------------
-- Table `Lot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Lot` (
  `lotNo` INT NOT NULL,
  `lotSize` VARCHAR(45) NULL,
  `locationID_FK` INT NULL,
  PRIMARY KEY (`lotNo`),
  CONSTRAINT `locationID`
    FOREIGN KEY (`locationID_FK`)
    REFERENCES `Location` (`locationID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `locationID_idx` ON `Lot` (`locationID_FK` ASC);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;