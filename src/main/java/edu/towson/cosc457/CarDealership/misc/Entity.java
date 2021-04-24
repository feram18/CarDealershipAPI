package edu.towson.cosc457.CarDealership.misc;

import java.util.Locale;

public enum Entity {
    USER {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    ADDRESS {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    CLIENT {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    COMMENT {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    DEPARTMENT {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    EMPLOYEE {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    LOCATION {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    LOT {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    MANAGER {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    MECHANIC {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    },
    SALES_ASSOCIATE {
        @Override
        public String toString() {
            return super.toString().replace("_", " ").toLowerCase(Locale.ROOT);
        }
    },
    SERVICE_TICKET {
        @Override
        public String toString() {
            return super.toString().replace("_", " ").toLowerCase(Locale.ROOT);
        }
    },
    SITE_MANAGER {
        @Override
        public String toString() {
            return super.toString().replace("_", " ").toLowerCase(Locale.ROOT);
        }
    },
    VEHICLE {
        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    }
}
