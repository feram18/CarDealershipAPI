BEGIN;

TRUNCATE "address"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "employee"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "user"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "site_manager"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "manager"
      IGNORE DELETE TRIGGERS
      IMMEDIATE;

TRUNCATE "mechanic"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "sales_associate"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "client"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "location"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "lot"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "vehicle"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "department"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "service_ticket"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

TRUNCATE "comment"
   IGNORE DELETE TRIGGERS
   IMMEDIATE;

END;