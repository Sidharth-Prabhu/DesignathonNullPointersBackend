-- Fix the user table to have AUTO_INCREMENT on id
ALTER TABLE user MODIFY id BIGINT AUTO_INCREMENT;

-- Insert admin user (password: admin123, BCrypt hashed)
INSERT INTO user (username, password, role)
VALUES ('admin', '$2b$10$W85YxmEqSG5S4l.4lkWDC.iaqBcQBoRr.XLLDoHJqmdR/HIrruJf6', 'ADMIN');

-- Verify
SELECT * FROM user;
data