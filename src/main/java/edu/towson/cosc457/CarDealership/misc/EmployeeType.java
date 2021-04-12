package edu.towson.cosc457.CarDealership.misc;

public enum EmployeeType {
    SALES_ASSOCIATE {
        @Override
        public String toString() {
            return "Sales Associate";
        }
    },
    MECHANIC {
        @Override
        public String toString() {
            return "Mechanic";
        }
    },
    MANAGER {
        @Override
        public String toString() {
            return "Manager";
        }
    },
    SITE_MANAGER {
        @Override
        public String toString() {
            return "Site Manager";
        }
    }
}
