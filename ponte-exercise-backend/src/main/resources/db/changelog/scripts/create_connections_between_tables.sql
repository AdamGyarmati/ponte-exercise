ALTER TABLE address
ADD COLUMN app_user_id INT;

ALTER TABLE address
ADD CONSTRAINT app_user_id FOREIGN KEY (app_user_id) REFERENCES app_user(id);

ALTER TABLE phone_number
ADD COLUMN app_user_id INT;

ALTER TABLE phone_number
ADD CONSTRAINT app_user_id FOREIGN KEY (app_user_id) REFERENCES app_user(id);