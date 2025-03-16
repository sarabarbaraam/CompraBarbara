-- Insert example into CLIENT table
INSERT INTO CLIENT (name, surname, company, position, address, zip_code, province, phone_number, birth_date)
VALUES 
    ('John', 'Doe', 'Company A', 'Manager', '123 Main St', '12345', 'Province A', '555-1234', '1985-01-01'),
    ('Jane', 'Smith', 'Company B', 'Developer', '456 Oak St', '67890', 'Province B', '555-5678', '1990-02-02');

-- Insert example into ITEM table
INSERT INTO ITEM (name, description, unit_price, unit_stock, type, supplier, date)
VALUES 
    ('Laptop', 'High-performance laptop for work and gaming', 1200.00, 100, 'Electronics', 'Supplier A', '2025-03-15'),
    ('Chair', 'Ergonomic office chair', 150.00, 50, 'Furniture', 'Supplier B', '2025-03-16');

-- Insert example into PURCHASE table
INSERT INTO PURCHASE (id_client, id_item, purchase_date, quantity, total, iva, total_iva, total_price)
VALUES 
    (1, 1, '2025-03-15', 2, 2400.00, 21.00, 504.00, 2904.00),
    (2, 2, '2025-03-16', 1, 150.00, 21.00, 31.50, 181.50);
