
-- Create CLIENT table
CREATE TABLE IF NOT EXISTS CLIENT (
    id_client INT AUTO_INCREMENT PRIMARY KEY,  -- Unique ID for the client
    name VARCHAR(45) NOT NULL,  -- First name of the client
    surname VARCHAR(45) NOT NULL,  -- Last name of the client
    company VARCHAR(45) NOT NULL UNIQUE,  -- Company of the client (Unique)
    position VARCHAR(45) NOT NULL,  -- Position of the client
    address VARCHAR(45) NOT NULL,  -- Address of the client
    zip_code VARCHAR(10) NOT NULL,  -- Postal code of the client
    province VARCHAR(45) NOT NULL,  -- Province of the client
    phone_number VARCHAR(45) NOT NULL UNIQUE,  -- Phone number of the client (Unique)
    birth_date DATE NOT NULL  -- Birth date of the client
);

-- Create ITEM table
CREATE TABLE IF NOT EXISTS ITEM (
    id_item INT AUTO_INCREMENT PRIMARY KEY,  -- Unique ID for the item
    name VARCHAR(45) NOT NULL UNIQUE,  -- Name of the item (Unique)
    description VARCHAR(500) NOT NULL UNIQUE,  -- Description of the item (Unique)
    unit_price DECIMAL(38,2) NOT NULL,  -- Unit price of the item
    unit_stock INT NOT NULL,  -- Stock quantity of the item
    type VARCHAR(30) NOT NULL,  -- Type of the item (e.g., electronic, clothing)
    supplier VARCHAR(45) NOT NULL,  -- Supplier of the item
    date DATE NOT NULL  -- Creation date of the item
);

-- Create PURCHASE table
CREATE TABLE IF NOT EXISTS PURCHASE (
    id_purchase INT AUTO_INCREMENT PRIMARY KEY,  -- Unique ID for the purchase
    id_client INT NOT NULL,  -- Reference to CLIENT table
    id_item INT NOT NULL,  -- Reference to ITEM table
    purchase_date DATE NOT NULL,  -- Purchase date
    quantity INT NOT NULL,  -- Quantity purchased
    total DECIMAL(38,2) NOT NULL,  -- Total amount (without VAT)
    iva DECIMAL(38,2) NOT NULL,  -- VAT percentage
    total_iva DECIMAL(38,2) NOT NULL,  -- Total VAT amount
    total_price DECIMAL(38,2) NOT NULL,  -- Total price (with VAT)

    -- Foreign keys
    FOREIGN KEY (client_id) REFERENCES CLIENT(id) ON DELETE CASCADE,  -- Relationship with CLIENT table
    FOREIGN KEY (item_id) REFERENCES ITEM(id) ON DELETE CASCADE  -- Relationship with ITEM table
);
