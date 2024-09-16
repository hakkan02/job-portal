-- Drop the user if it exists
DROP USER IF EXISTS 'job_portal'@'localhost';

-- Create the user
CREATE USER 'job_portal'@'localhost' IDENTIFIED BY 'job_portal';

-- Grant all privileges to the user
GRANT ALL PRIVILEGES ON *.* TO 'job_portal'@'localhost' WITH GRANT OPTION;

-- Apply the changes
FLUSH PRIVILEGES;

