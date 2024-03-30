ALTER TABLE app_user ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE app_user ADD CONSTRAINT email_unique UNIQUE (email);
