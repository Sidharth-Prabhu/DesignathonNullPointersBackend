-- ============================================
-- Centralized Login System - Database Setup
-- ============================================
-- This script creates ONLY the admin user
-- Admin will create faculty/student accounts through dashboard
-- ============================================

-- Insert Admin User (password: admin123)
INSERT INTO user (username, password, role) 
VALUES ('admin', '$2b$10$W85YxmEqSG5S4l.4lkWDC.iaqBcQBoRr.XLLDoHJqmdR/HIrruJf6', 'ADMIN')
ON DUPLICATE KEY UPDATE password = VALUES(password), role = VALUES(role);

-- Verify users
SELECT id, username, role FROM user;

-- ============================================
-- Test Credentials:
-- Admin: username: admin, password: admin123
-- ============================================
-- Faculty and Student accounts should be created
-- through the Admin Dashboard after logging in
-- ============================================
