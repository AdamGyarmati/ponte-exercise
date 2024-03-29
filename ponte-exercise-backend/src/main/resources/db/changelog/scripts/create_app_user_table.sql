CREATE TABLE app_user (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         email VARCHAR(255),
                         password VARCHAR(255),
                         birth_date DATE,
                         mother_name VARCHAR(255),
                         social_security_number VARCHAR(255),
                         tax_identification_number VARCHAR(255)
)