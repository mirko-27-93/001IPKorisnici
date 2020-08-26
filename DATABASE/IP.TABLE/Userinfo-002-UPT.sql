USE ip; 

ALTER TABLE userinfo ADD COLUMN profile_image_path VARCHAR(200);
ALTER TABLE userinfo ADD COLUMN user_image_path VARCHAR(200);
ALTER TABLE userinfo ADD COLUMN country_flag_image_path VARCHAR(200);
ALTER TABLE userinfo ADD COLUMN notification_webapp_supported BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE userinfo ADD COLUMN notification_email_supported BOOLEAN NOT NULL DEFAULT FALSE;



