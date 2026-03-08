-- ============================================================
-- Mockup / Init data cho Student Management System
-- Chạy sau khi schema đã có (vd: sau lần chạy app với ddl-auto: update)
-- MySQL: mysql -u root -p student_management < init.sql
-- Hoặc dùng spring.datasource.url với ?allowPublicKeyRetrieval=true nếu cần
-- ============================================================

USE student_management;

-- Xóa dữ liệu cũ (tùy chọn, cẩn thận khi có dữ liệu thật)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE scores;
-- TRUNCATE TABLE schedules;
-- TRUNCATE TABLE course_classes;
-- TRUNCATE TABLE students;
-- TRUNCATE TABLE classes;
-- TRUNCATE TABLE subjects;
-- TRUNCATE TABLE lecturers;
-- TRUNCATE TABLE semesters;
-- TRUNCATE TABLE rooms;
-- TRUNCATE TABLE departments;
-- SET FOREIGN_KEY_CHECKS = 1;

-- ------------------------------------------------------------
-- 1. Departments
-- ------------------------------------------------------------
INSERT INTO departments (department_name, established_date, description) VALUES
('Công nghệ thông tin', '2010-09-01', 'Khoa Công nghệ thông tin'),
('Điện - Điện tử', '2008-09-01', 'Khoa Điện - Điện tử'),
('Kinh tế', '2005-09-01', 'Khoa Kinh tế');

-- ------------------------------------------------------------
-- 2. Classes (department_id: 1, 2, 3)
-- ------------------------------------------------------------
INSERT INTO classes (class_name, course_year, department_id) VALUES
('CNTT-K1', '2022', 1),
('CNTT-K2', '2022', 1),
('DĐT-K1', '2022', 2),
('KT-K1', '2022', 3);

-- ------------------------------------------------------------
-- 3. Students (class_id 1..4)
-- ------------------------------------------------------------
INSERT INTO students (full_name, date_of_birth, gender, address, phone_number, email, status, class_id) VALUES
('Nguyễn Văn An', '2004-01-15', 'Nam', 'Hà Nội', '0901234567', 'an.nv@edu.vn', 'ACTIVE', 1),
('Trần Thị Bình', '2004-03-20', 'Nữ', 'TP.HCM', '0912345678', 'binh.tt@edu.vn', 'ACTIVE', 1),
('Lê Văn Cường', '2004-05-10', 'Nam', 'Đà Nẵng', '0923456789', 'cuong.lv@edu.vn', 'ACTIVE', 2),
('Phạm Thị Dung', '2004-07-08', 'Nữ', 'Cần Thơ', '0934567890', 'dung.pt@edu.vn', 'ACTIVE', 2),
('Hoàng Văn Em', '2004-09-12', 'Nam', 'Hải Phòng', '0945678901', 'em.hv@edu.vn', 'ACTIVE', 3);

-- ------------------------------------------------------------
-- 4. Subjects (department_id 1, 2, 3)
-- ------------------------------------------------------------
INSERT INTO subjects (subject_name, credits, department_id, description) VALUES
('Lập trình hướng đối tượng', 4, 1, 'Môn cơ sở lập trình OOP'),
('Cơ sở dữ liệu', 3, 1, 'SQL và thiết kế CSDL'),
('Mạch điện tử', 3, 2, 'Mạch tương tự và số'),
('Kinh tế vi mô', 3, 3, 'Kinh tế học vi mô');

-- ------------------------------------------------------------
-- 5. Semesters
-- ------------------------------------------------------------
INSERT INTO semesters (semester_name, academic_year) VALUES
('Học kỳ 1', '2022-2023'),
('Học kỳ 2', '2022-2023'),
('Học kỳ 1', '2023-2024');

-- ------------------------------------------------------------
-- 6. Lecturers (department_id 1, 2, 3)
-- ------------------------------------------------------------
INSERT INTO lecturers (lecturer_name, academic_degree, email, phone_number, department_id) VALUES
('PGS.TS Nguyễn Văn Giảng', 'Tiến sĩ', 'giang.nv@edu.vn', '0987654321', 1),
('TS Trần Thị Hương', 'Tiến sĩ', 'huong.tt@edu.vn', '0976543210', 1),
('ThS Lê Văn Khoa', 'Thạc sĩ', 'khoa.lv@edu.vn', '0965432109', 2),
('TS Phạm Thị Lan', 'Tiến sĩ', 'lan.pt@edu.vn', '0954321098', 3);

-- ------------------------------------------------------------
-- 7. Rooms
-- ------------------------------------------------------------
INSERT INTO rooms (room_code, building, capacity, room_type, description) VALUES
('A101', 'Nhà A', 50, 'Lý thuyết', 'Phòng học lý thuyết'),
('A102', 'Nhà A', 50, 'Lý thuyết', 'Phòng học lý thuyết'),
('B201', 'Nhà B', 30, 'Thực hành', 'Phòng máy tính'),
('C301', 'Nhà C', 80, 'Hội trường', 'Hội trường lớn');

-- ------------------------------------------------------------
-- 8. Course classes (subject_id, semester_id, class_id, lecturer_id)
-- ------------------------------------------------------------
INSERT INTO course_classes (subject_id, semester_id, class_id, lecturer_id) VALUES
(1, 1, 1, 1),  -- OOP - HK1 - CNTT-K1 - Giảng
(1, 1, 2, 1),  -- OOP - HK1 - CNTT-K2 - Giảng
(2, 1, 1, 2),  -- CSDL - HK1 - CNTT-K1 - Hương
(3, 1, 3, 3),  -- Mạch điện tử - HK1 - DĐT-K1 - Khoa
(4, 1, 4, 4);  -- KT vi mô - HK1 - KT-K1 - Lan

-- ------------------------------------------------------------
-- 9. Schedules (course_class_id, room_id, day_of_week, period_start, period_end, note)
-- day_of_week: 1=Thứ 2 .. 7=Chủ nhật
-- ------------------------------------------------------------
INSERT INTO schedules (course_class_id, room_id, day_of_week, period_start, period_end, note) VALUES
(1, 1, 2, 1, 2, 'Tiết 1-2 Thứ 2'),
(1, 1, 4, 3, 4, 'Tiết 3-4 Thứ 4'),
(2, 2, 3, 1, 2, 'Tiết 1-2 Thứ 3'),
(3, 3, 2, 5, 6, 'Tiết 5-6 Thứ 2'),
(4, 1, 5, 1, 3, 'Tiết 1-3 Thứ 6'),
(5, 4, 3, 4, 5, 'Tiết 4-5 Thứ 3');

-- ------------------------------------------------------------
-- 10. Scores (student_id, course_class_id, continuous, final_exam, final)
-- course_class 1 = OOP CNTT-K1, 2 = OOP CNTT-K2, 3 = CSDL CNTT-K1
-- ------------------------------------------------------------
INSERT INTO scores (student_id, course_class_id, continuous_assessment_score, final_exam_score, final_score) VALUES
(1, 1, 8.0, 7.5, 7.7),
(2, 1, 8.5, 8.0, 8.2),
(3, 2, 7.0, 7.5, 7.3),
(4, 2, 9.0, 8.5, 8.7),
(1, 3, 7.5, 8.0, 7.8),
(2, 3, 8.0, 8.0, 8.0);

-- ============================================================
-- Kết thúc init data
-- ============================================================
