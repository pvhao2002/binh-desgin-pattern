# Student Management System

Hệ thống quản lý học vụ: Backend Spring Boot (Java 21, JPA, MySQL) + Frontend Angular 21 (TypeScript, Tailwind-ready).

## Yêu cầu

- **Backend:** JDK 21, Maven 3.x, MySQL 8.x
- **Frontend:** Node.js 18+, npm

## Cấu hình database

Tạo database (hoặc để ứng dụng tạo tự động):

```sql
CREATE DATABASE IF NOT EXISTS student_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Có thể ghi đè bằng biến môi trường:

- `DB_URL` – JDBC URL (mặc định: `jdbc:mysql://localhost:3306/student_management?createDatabaseIfNotExist=true&...`)
- `DB_USERNAME` – user MySQL (mặc định: `root`)
- `DB_PASSWORD` – mật khẩu MySQL (mặc định: rỗng)

## Chạy Backend

```bash
cd service
mvn spring-boot:run
```

API chạy tại: **http://localhost:1122/api**

Ví dụ: `GET http://localhost:1122/api/departments`

## Chạy Frontend

```bash
cd ui
npm install
npm start
```

Ứng dụng mở tại: **http://localhost:4200**

Đảm bảo backend đang chạy để gọi API (CORS đã bật cho `*`).

## Cấu trúc

- **service/** – Spring Boot: entities, repositories, DTOs, commands, services, controllers. Design patterns: Command (CRUD), Chain of Responsibility (validation), Facade (student overview), Strategy (score calculation).
- **ui/** – Angular 21: core (API, models, services), features (departments, classes, students, subjects, semesters, lecturers, rooms, course-classes, schedules, scores).

## API chính

| Resource       | Path             | Mô tả          |
|----------------|------------------|----------------|
| Departments    | /api/departments | CRUD khoa      |
| Classes        | /api/classes     | CRUD lớp       |
| Students       | /api/students    | CRUD sinh viên |
| Subjects       | /api/subjects    | CRUD môn học   |
| Semesters      | /api/semesters   | CRUD học kỳ    |
| Lecturers      | /api/lecturers   | CRUD giảng viên|
| Rooms          | /api/rooms       | CRUD phòng học |
| Course Classes | /api/course-classes | CRUD lớp học phần |
| Schedules      | /api/schedules   | CRUD lịch học  |
| Scores         | /api/scores      | CRUD điểm     |
| Student Overview | /api/student-overview/{studentId} | Tổng quan sinh viên (Facade) |
