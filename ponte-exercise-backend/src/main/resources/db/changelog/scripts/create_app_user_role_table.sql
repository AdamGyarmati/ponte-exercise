CREATE TABLE app_user_role (
    id SERIAL PRIMARY KEY,
    app_user_id INT,
    role VARCHAR(255),
    FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);