# Student Management System - Entity Description

## Overview

The Student Management System is designed to manage academic information within a school.
The system organizes and maintains data related to departments, classes, students, subjects, semesters, course classes, lecturers, and student scores.

Each entity represents an important component of the academic structure and maintains relationships with other entities in the system.

---

## Đề tài và các thực thể (Topic & Entities)

Đề tài **Student Management System** bao gồm các thực thể chính sau:

| STT | Thực thể (Entity)     | Tên tiếng Việt   | Mô tả ngắn |
|-----|------------------------|------------------|------------|
| 1   | Department             | Khoa             | Đơn vị đào tạo, quản lý lớp, môn học, giảng viên |
| 2   | Class                  | Lớp              | Nhóm sinh viên theo khóa/ngành |
| 3   | Student                | Sinh viên        | Thông tin cá nhân và học tập |
| 4   | Subject                | Môn học          | Môn học trong chương trình đào tạo |
| 5   | Semester               | Học kỳ           | Kỳ học trong năm học |
| 6   | Lecturer               | Giảng viên       | Người dạy lớp học phần |
| 7   | Course Class           | Lớp học phần     | Môn học được dạy cho một lớp trong một học kỳ |
| 8   | Score                  | Điểm             | Điểm số của sinh viên trong lớp học phần |
| 9   | Room                   | Phòng học        | Phòng/cơ sở vật chất để tổ chức lớp học phần |
| 10  | Schedule               | Lịch học         | Thời khóa biểu (thứ, tiết, phòng) của lớp học phần |

---

# 1. Department (Khoa)

The **Department** entity represents an academic unit within the school responsible for managing classes, lecturers, and subjects.

### Attributes

* **departmentId** – Unique identifier of the department
* **departmentName** – Name of the department
* **establishedDate** – Date when the department was established
* **description** – Additional information about the department

### Relationships

* One department manages multiple classes
* One department manages multiple subjects
* One department manages multiple lecturers

---

# 2. Class (Lớp)

The **Class** entity represents a group of students enrolled in the same program or course year.

### Attributes

* **classId** – Unique identifier of the class
* **className** – Name of the class
* **courseYear** – The academic intake year (e.g., K2023)
* **departmentId** – Reference to the department managing the class

### Relationships

* One class belongs to one department
* One class contains multiple students
* One class may participate in multiple course classes

---

# 3. Student (Sinh viên)

The **Student** entity stores personal and academic information about students studying in the school.

### Attributes

* **studentId** – Unique identifier of the student
* **fullName** – Student's full name
* **dateOfBirth** – Student's date of birth
* **gender** – Gender of the student
* **address** – Residential address
* **phoneNumber** – Contact phone number
* **email** – Email address
* **classId** – Reference to the class the student belongs to

### Relationships

* Each student belongs to one class
* Each student can have multiple scores in different course classes

---

# 4. Subject (Môn học)

The **Subject** entity represents courses taught in the school curriculum.

### Attributes

* **subjectId** – Unique identifier of the subject
* **subjectName** – Name of the subject
* **credits** – Number of credits assigned to the subject
* **departmentId** – Reference to the department responsible for the subject
* **description** – Description of the subject content

### Relationships

* Each subject belongs to one department
* One subject can have multiple course classes across semesters

---

# 5. Semester (Học kỳ)

The **Semester** entity represents an academic period during which subjects are taught.

### Attributes

* **semesterId** – Unique identifier of the semester
* **semesterName** – Name of the semester (e.g., Semester 1, Semester 2)
* **academicYear** – Academic year (e.g., 2025–2026)

### Relationships

* One semester can include multiple course classes

---

# 6. Lecturer (Giảng viên)

The **Lecturer** entity represents teachers responsible for teaching course classes.

### Attributes

* **lecturerId** – Unique identifier of the lecturer
* **lecturerName** – Full name of the lecturer
* **academicDegree** – Academic degree or title
* **email** – Lecturer email address
* **phoneNumber** – Contact phone number
* **departmentId** – Department the lecturer belongs to

### Relationships

* One lecturer belongs to one department
* One lecturer may teach multiple course classes

---

# 7. Course Class (Lớp học phần)

The **Course Class** entity represents a subject being taught to a specific class during a specific semester.

For example:

* Data Structures – Semester 1 – Class K2023

### Attributes

* **courseClassId** – Unique identifier of the course class
* **subjectId** – Reference to the subject
* **semesterId** – Reference to the semester
* **classId** – Reference to the class
* **lecturerId** – Reference to the lecturer teaching the course

### Relationships

* Each course class belongs to one subject
* Each course class belongs to one semester
* Each course class is taught by one lecturer
* Each course class may contain scores of multiple students

---

# 8. Score (Điểm)

The **Score** entity records the academic performance of students in a course class.

### Attributes

* **scoreId** – Unique identifier of the score record
* **studentId** – Reference to the student
* **courseClassId** – Reference to the course class
* **continuousAssessmentScore** – Score for assignments or coursework
* **finalExamScore** – Final exam score
* **finalScore** – Overall calculated score

### Relationships

* Each score belongs to one student
* Each score belongs to one course class

---

# 9. Room (Phòng học)

The **Room** entity represents physical spaces (classrooms, labs) where course classes are held.

### Attributes

* **roomId** – Unique identifier of the room
* **roomCode** – Room code or name (e.g., A101, B205)
* **building** – Building or block name
* **capacity** – Maximum number of seats
* **roomType** – Type of room (e.g., theory, lab, seminar)
* **description** – Additional information about the room

### Relationships

* One room can be used in multiple schedule slots
* Each schedule slot references one room

---

# 10. Schedule (Lịch học)

The **Schedule** entity represents the timetable of a course class: which day of the week, which period, and in which room the class meets.

### Attributes

* **scheduleId** – Unique identifier of the schedule record
* **courseClassId** – Reference to the course class
* **roomId** – Reference to the room
* **dayOfWeek** – Day of the week (e.g., 2 = Monday, 7 = Saturday)
* **periodStart** – Starting period/slot (e.g., 1, 2, 3)
* **periodEnd** – Ending period/slot (optional; if one slot, same as periodStart)
* **note** – Optional note (e.g., "Tuần chẵn", "Cả tuần")

### Relationships

* Each schedule belongs to one course class
* Each schedule uses one room
* One course class can have multiple schedule slots per week

---

# Entity Relationship Summary

The system includes the following main entities:

* Department
* Class
* Student
* Subject
* Semester
* Lecturer
* Course Class
* Score
* Room
* Schedule

These entities work together to represent the structure of academic management within the school.

The relationships ensure that:

* Departments organize classes, lecturers, and subjects
* Classes contain students
* Subjects are taught through course classes
* Course classes occur in specific semesters
* Lecturers teach course classes
* Students receive scores in course classes
* Rooms are assigned to course classes via Schedule
* Schedules define when and where each course class meets
