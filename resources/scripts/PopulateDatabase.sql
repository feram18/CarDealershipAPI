

INSERT INTO `lramos6db`.`Department` (`departmentNo`, `departmentName`, `managerSSN_FK2`) VALUES ('1', 'Department A', '782572787');
INSERT INTO `lramos6db`.`Department` (`departmentNo`, `departmentName`, `managerSSN_FK2`) VALUES ('2', 'Department B', '907333862');
INSERT INTO `lramos6db`.`Department` (`departmentNo`, `departmentName`, `managerSSN_FK2`) VALUES ('3', 'Department C', '987623724');

INSERT INTO `lramos6db`.`Mechanic` (`mechanicSSN`, `departmentNo_FK2`) VALUES ('242870455', '1');
INSERT INTO `lramos6db`.`Mechanic` (`mechanicSSN`, `departmentNo_FK2`) VALUES ('798284527', '3');
INSERT INTO `lramos6db`.`Mechanic` (`mechanicSSN`, `departmentNo_FK2`) VALUES ('951753852', '2');

INSERT INTO lramos6db.SalesAssociate VALUES (985982760, 3);
INSERT INTO lramos6db.SalesAssociate VALUES (287293227, 1);
INSERT INTO lramos6db.SalesAssociate VALUES (289346716, 3);
INSERT INTO lramos6db.SalesAssociate VALUES (336503486, 3);
INSERT INTO lramos6db.SalesAssociate VALUES (703925855, 1);
INSERT INTO lramos6db.SalesAssociate VALUES (741951943, 2);
INSERT INTO lramos6db.SalesAssociate VALUES (761328317, 2);
INSERT INTO lramos6db.SalesAssociate VALUES (773674144, 3);
INSERT INTO lramos6db.SalesAssociate VALUES (782279982, 1);
INSERT INTO lramos6db.SalesAssociate VALUES (987654321, 2);

INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (203625558, 'Alair', 'Bothwell', 'abothwell0@usatoday.com', '8611229360', '576 Nancy Pass Perry Hall, MD 21128', 287293227);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (366745481, 'Clifford', 'Lackington', 'clackington1@microsoft.com', '4395657581', '3 Sunnyside Pass Federalsburg, MD 21632', 703925855);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (558948717, 'Reginauld', 'Schoffel', 'rschoffel2@indiatimes.com', '8797816033', '37604 Debs Point Port Republic, MD 20676', 773674144);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (117237579, 'Emelita', 'Adcock', 'eadcock3@de.vu', '8216685211', '716 Hoard Avenue North Beach, MD 20714', 782279982);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (641575096, 'Carey', 'Scottrell', 'cscottrell4@china.com.cn', '9937363223', '91 Green Ridge Park Cambridge, MD 21613', 741951943);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (713238841, 'Mellie', 'Huske', 'mhuske5@jimdo.com', '1568852172', '25902 Forest Run Junction Severn, MD 21144', 287293227);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (179103452, 'Kaia', 'McKirdy', 'kmckirdy6@bloglines.com', '7537200918', '9756 Dapin Crossing Laurel, MD 20708', 761328317);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (279715089, 'Beverlee', 'Carrodus', 'bcarrodus7@theguardian.com', '8064576453', '30265 Loftsgordon Road Parkville, MD 21234', 987654321);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (713542704, 'Cherrita', 'Yurshev', 'cyurshev8@jigsy.com', '5565881868', '51 Arrowood Avenue Huntingtown, MD 20639', 289346716);
INSERT INTO lramos6db.Dealer (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK) values (632870555, 'Reeba', 'Lockey', 'rlockey9@slate.com', '5979130656', '1188 Eagle Crest Pass Clear Spring, MD 21722', 336503486);

INSERT INTO lramos6db.Client VALUES (376635672, 'Jamima', 'Byass', 'F', 'jbyass0@msn.com', '3511156655', '44 Fallview Terrace Smithtown, NY 11787', 987654321, 1921, 79217);
INSERT INTO lramos6db.Client VALUES (167811854, 'Mollie', 'Fishby', 'F', 'mfishby1@europa.eu', '2346508372', '615 Pond Avenue New Lenox, IL 60451', 987654321, 10171, 79812);
INSERT INTO lramos6db.Client VALUES (119634343, 'Tarra', 'Krol', 'F', 'tkrol2@lulu.com', '1098303354', '6762 Raven Crossing Marshall, WI 53559', 987654321, 11886, 22199);
INSERT INTO lramos6db.Client VALUES (248215526, 'Elijah', 'Basnall', 'M', 'ebasnall3@fda.gov', '3942755661', '64 Calypso Road Fredonia, NY 14063', 987654321, 4407, 20662);
INSERT INTO lramos6db.Client VALUES (365912346, 'Bobbe', 'Taffley', 'F', 'btaffley4@eepurl.com', '1995256613', '0 Washington Junction Skippers, VA 23879', 782279982, 4431, 21587);
INSERT INTO lramos6db.Client VALUES (194521326, 'Klara', 'Philippou', 'F', 'kphilippou5@omniture.com', '8292654945', '7 Beilfuss Plaza Moreno Valley, CA 92557', 782279982, 23801, 87636);
INSERT INTO lramos6db.Client VALUES (381964497, 'Guss', 'Oade', 'M', 'goade6@mediafire.com', '4035719707', '34462 Brown Park Apple Valley, CA 92307', 782279982, 13909, 38434);
INSERT INTO lramos6db.Client VALUES (148293030, 'Marc', 'Ranaghan', 'M', 'mranaghan7@go.com', '4166834245', '924 Haas Parkway Winchester, VA 22602', 782279982, 4074, 17964);
INSERT INTO lramos6db.Client VALUES (325900094, 'Torrence', 'Rasell', 'M', 'trasell8@hp.com', '5943113711', '2504 Boyd Hill Beaufort, SC 29902', 773674144, 16958, 100064);
INSERT INTO lramos6db.Client VALUES (194516503, 'Alfie', 'Raynard', 'M', 'araynard9@mozilla.com', '5188378777', '5 Dexter Hill Dorset, VT 05251', 773674144, 14036, 43686);
INSERT INTO lramos6db.Client VALUES (759356471, 'Rutledge', 'Peddel', 'M', 'rpeddela@whitehouse.gov', '9407545553', '56 Randy Crossing Jackson, MO 63755', 773674144, 6272, 25865);
INSERT INTO lramos6db.Client VALUES (221614199, 'Vida', 'Beeckx', 'F', 'vbeeckxb@exblog.jp', '3598006802', '21305 Browning Court Baytown, TX 77521', 773674144, 45995, 62355);
INSERT INTO lramos6db.Client VALUES (262967561, 'Abigale', 'Menendes', 'F', 'amenendesc@hhs.gov', '8776396450', '524 Maple Hill Pinellas Park, FL 33781', 761328317, 19186, 39664);
INSERT INTO lramos6db.Client VALUES (521342031, 'Roshelle', 'Balassa', 'F', 'rbalassad@nba.com', '9266567998', '33720 Division Drive Bethesda, MD 20817', 761328317, 20013, 58318);
INSERT INTO lramos6db.Client VALUES (180336987, 'Putnam', 'Plues', 'M', 'ppluese@google.ru', '8439798432', '025 Forster Lane Winchester, VA 22602', 761328317, 10943, 17652);
INSERT INTO lramos6db.Client VALUES (795830905, 'Tadd', 'Gavaran', 'M', 'tgavaranf@soundcloud.com', '1388566130', '77 Jenifer Street Laurel, MD 20708', 741951943, 10622, 39247);
INSERT INTO lramos6db.Client VALUES (254713058, 'Wang', 'McClure', 'M', 'wmcclureg@sun.com', '4169989195', '08 1st Court Bethesda, MD 20817', 741951943, 7123, 39439);
INSERT INTO lramos6db.Client VALUES (716630723, 'Verge', 'Tofanelli', 'M', 'vtofanellih@yahoo.co.jp', '6915028019', '69174 Kings Center Sharptown, MD 21861', 741951943, 16805, 61327);
INSERT INTO lramos6db.Client VALUES (175239477, 'Olly', 'Harradine', 'F', 'oharradinei@netlog.com', '7804826302', '11945 Fallview Way Lanham, MD 20706', 703925855, 1043, 8730);
INSERT INTO lramos6db.Client VALUES (186332618, 'Cly', 'Cosgriff', 'M', 'ccosgriffj@nifty.com', '6059581331', '61543 Fairview Place Essex, MD 21221', 703925855, 2837, 19973);
INSERT INTO lramos6db.Client VALUES (377210817, 'Sheilah', 'Mullard', 'F', 'smullardk@skyrock.com', '3883243473', '3080 Sage Trail Lanham, MD 20706', 336503486, 11483, 107219);
INSERT INTO lramos6db.Client VALUES (365723268, 'Kevyn', 'Studart', 'F', 'kstudartl@macromedia.com', '8991461550', '7 Bobwhite Center Dorset, VT 05251', 289346716, 10136, 73398);
INSERT INTO lramos6db.Client VALUES (512744704, 'Bordy', 'Enderlein', 'M', 'benderleinm@rediff.com', '4388637866', '9 Barnett Plaza Essex, MD 21221', 289346716, 43690, 51917);
INSERT INTO lramos6db.Client VALUES (165252629, 'Luca', 'Domelow', 'M', 'ldomelown@skype.com', '3566014805', '8 Blue Bill Park Place Essex, MD 21221', 289346716, 21192, 63451);
INSERT INTO lramos6db.Client VALUES (463828712, 'Eduard', 'Devereu', 'M', 'edevereuo@ning.com', '7175211819', '2 Waywood Avenue Middletown, MD 21769', 287293227, 37938, 82512);

INSERT INTO `lramos6db`.`SiteManager` (`siteManagerSSN`) VALUES ('123456789');
INSERT INTO `lramos6db`.`SiteManager` (`siteManagerSSN`) VALUES ('197269570');
INSERT INTO `lramos6db`.`SiteManager` (`siteManagerSSN`) VALUES ('278996378');

INSERT INTO lramos6db.Location VALUES (1527, "Columbia", "8800 Stanford Blvd, Columbia, MD 21045", 197269570);
INSERT INTO lramos6db.Location VALUES (1049, "Towson", "1040 York Rd, Towson, MD 21204", 123456789);
INSERT INTO lramos6db.Location VALUES (0096, "Essex", "10201 Philadelphia Road Essex, MD 21162", 278996378);

INSERT INTO lramos6db.Lot VALUES (15, "3000 sq ft", 96);
INSERT INTO lramos6db.Lot VALUES (298, "24000 sq ft", 96);
INSERT INTO lramos6db.Lot VALUES (47, "12500 sq ft", 1049);
INSERT INTO lramos6db.Lot VALUES (36, "19250 sq ft", 1049);
INSERT INTO lramos6db.Lot VALUES (91, "7600 sq ft", 1527);
INSERT INTO lramos6db.Lot VALUES (78, "20000 sq ft", 1527);

INSERT INTO lramos6db.Manager VALUES (782572787, 197269570);
INSERT INTO lramos6db.Manager VALUES (907333862, 123456789);
INSERT INTO lramos6db.Manager VALUES (987623724, 278996378);

INSERT INTO lramos6db.CarBay VALUES (44, 907333862);
INSERT INTO lramos6db.CarBay VALUES (60, 907333862);
INSERT INTO lramos6db.CarBay VALUES (71, 782572787);
INSERT INTO lramos6db.CarBay VALUES (03, 782572787);
INSERT INTO lramos6db.CarBay VALUES (33, 987623724);
INSERT INTO lramos6db.CarBay VALUES (35, 987623724);

INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('3B7HF13Z5VG778508', "Toyota", "Camry", "2012", "Black", "Sedan", "Automatic", "Leather seats", "Essex", "25", 29751, 17500, 33);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('2GKFLWEK3F6183316', "Toyota", "RAV4", "2010", "White", "SUV", "Automatic", "Sunroof", "Essex", "17", 69000, 13998, 33);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('1HGCG56461A029824', "Honda", "Pilot", "2011", "Gray", "SUV", "Automatic", "Navigation system", "Essex", "19", 66000, 18998, 33);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('3GYFNEE38ES687802', "Chevrolet", "Malibu", "2012", "White", "Sedan", "Manual", "Sunroof", "Essex", "24", 61000, 11998, 33);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('2FMDK3KC0EBA86061', "Lincoln", "MKZ", "2010", "Tan", "Sedan", "Automatic", "Leather seats", "Essex", "20", 81000, 11598, 35);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('2D8HN44E29R547499', "Hyundai", "Elantra", "2009", "Blue", "Sedan", "Automatic", "Sunroof", "Essex", "25", 101000, 8998, 35);

INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('4T1BK1EB9FU183298', "Jeep", "Grand Chreokee", "2014", "Gray", "SUV", "Manual", "Navigation system", "Columbia", "19", 74000, 20998, 3);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('4T1BB46K27U088809', "Lexus", "RX350", "2011", "White", "SUV", "Automatic", "Leather seats", "Columbia", "23", 90000, 17998 , 3);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('4JGBB8GB2AA514853', "Nissan", "Sentra", "2012", "Red", "Hatchback", "Automatic", "Rear view camera", "Columbia", "29", 113000, 8998, 3);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('1N4AL11D75N924616', "Ford", "Escape", "2013", "Black", "Hatchback", "Automatic", "Sunroof", "Columbia", "25", 83000, 13598, 71);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('JTDZN3EU9C3169989', "Chevrolet", "Volt", "2012", "Black", "Sedan", "Automatic", "Navigation system", "Columbia", "31", 20000, 13998, 71);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('1G1PE5SB6E7124308', "BMW", "328i", "2013", "White", "Sedan", "Manual", "Leather seats", "Columbia", "15", 79000, 16998, 71);

INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('JN1CV6ARXCM675031', "Mercedes-Benz", "E350", "2012", "Black", "Sedan", "Automatic", "Leather seats", "Towson", "20", 62000, 19998, 44);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('1FMCU0D71BKA75138', "Subaru", "Forester", "2013", "Black", "SUV", "Automatic", "Rear view camera", "Towson", "20", 124000, 12998, 44);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('YV4902NC4F1123581', "Acura", "TL", "2012", "White", "Sedan", "Automatic", "Leather seats", "Towson", "25", 60000, 15998, 44);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('2G1WA5EK2A1107838', "GMC", "Sierra 15000", "2020", "Gray", "Truck", "Manual", "Rear view camera", "Towson", "13", 4000, 59998, 44);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('1J4RR4GG4BC738354', "Dodge", "Avenger", "2013", "White", "Sedan", "Automatic", "Navigation system", "Towson", "35", 85000, 10598, 60);
INSERT INTO lramos6db.Vehicle (`VIN`, `make`, `model`, `year`, `color`, `vehicleType`, `transmission`, `features`, `location`, `MPG`, `mileage`, `price`, `carBayID_FK`) VALUES ('WDDGF4HB4EA955899', "Kia", "Optima", "2012", "Green", "Sedan", "Manual", "Rear view camera", "Towson", "31", 93000, 11998, 60);

INSERT INTO lramos6db.ServiceTicket (`serviceTicketNo`, `VIN_FK`, `mechanicSSN_FK`, `comment`, `serviceDate`) VALUES (1, "3B7HF13Z5VG778508", 242870455, "Replaced alternator", '2019-09-15');
INSERT INTO lramos6db.ServiceTicket (`serviceTicketNo`, `VIN_FK`, `mechanicSSN_FK`, `comment`, `serviceDate`) VALUES (2, "1HGCG56461A029824", 242870455, "Replaced defective battery", '2020-01-22');
INSERT INTO lramos6db.ServiceTicket (`serviceTicketNo`, `VIN_FK`, `mechanicSSN_FK`, `comment`, `serviceDate`) VALUES (3, "4T1BK1EB9FU183298", 798284527, "Fixed coolant leak", '2020-02-25');
INSERT INTO lramos6db.ServiceTicket (`serviceTicketNo`, `VIN_FK`, `mechanicSSN_FK`, `comment`, `serviceDate`) VALUES (4, "4T1BB46K27U088809", 798284527, "Fixed transmission issue", '2020-03-01');
INSERT INTO lramos6db.ServiceTicket (`serviceTicketNo`, `VIN_FK`, `mechanicSSN_FK`, `comment`, `serviceDate`) VALUES (5, "1FMCU0D71BKA75138", 951753852, "Replaced rear bumper", '2020-06-06');
INSERT INTO lramos6db.ServiceTicket (`serviceTicketNo`, `VIN_FK`, `mechanicSSN_FK`, `comment`, `serviceDate`) VALUES (6, "YV4902NC4F1123581", 951753852, "Installed new brakes", '2020-11-25');