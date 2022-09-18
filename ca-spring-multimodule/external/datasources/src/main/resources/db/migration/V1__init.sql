CREATE TABLE customer (
  id UUID NOT NULL,
  name TEXT,
  email TEXT,
  active BOOLEAN,
  address_street TEXT,
  address_number TEXT,
  address_zip TEXT,
  address_city TEXT,
  PRIMARY KEY (id)
);