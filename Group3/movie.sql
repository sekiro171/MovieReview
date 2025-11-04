USE master;
GO

-- Xóa database cũ
DROP DATABASE IF EXISTS MovieDB;
GO

-- Tạo lại database với collation hỗ trợ tiếng Việt
CREATE DATABASE MovieDB
COLLATE Vietnamese_CI_AS;
GO

-- Kiểm tra collation
SELECT name, collation_name 
FROM sys.databases 
WHERE name = 'MovieDB';
GO