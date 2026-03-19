# Các bước để báo cáo — Hệ thống quản lý học vụ (EduManager Pro)

Tài liệu hướng dẫn **cấu trúc và các bước** khi viết và trình bày báo cáo đồ án / môn học.

---

## 1. Cấu trúc báo cáo đề xuất

| Bước | Nội dung | Ghi chú |
|------|----------|--------|
| **1** | Mở đầu (Giới thiệu, mục tiêu, phạm vi) | 1–2 trang |
| **2** | Công nghệ & công cụ sử dụng | Backend, Frontend, DB |
| **3** | Phân tích & thiết kế hệ thống | Use case, kiến trúc, sơ đồ |
| **4** | Thiết kế cơ sở dữ liệu | ERD, bảng, quan hệ |
| **5** | Design Pattern đã áp dụng | Backend + Frontend, từng pattern |
| **6** | Triển khai & hướng dẫn chạy | Cấu hình, lệnh chạy, Docker (nếu có) |
| **7** | Demo chức năng chính | Danh sách tính năng, minh họa |
| **8** | Kết luận & hướng phát triển | 1 trang |

---

## 2. Chi tiết từng bước

### Bước 1 — Mở đầu

- **Giới thiệu đề tài:** Hệ thống quản lý học vụ (quản lý khoa, lớp, sinh viên, môn học, điểm, lịch, …).
- **Mục tiêu:** Xây dựng ứng dụng web full-stack; áp dụng Design Pattern (GoF); thực hành Spring Boot, Angular, MySQL.
- **Phạm vi:** 10 thực thể (Department, Class, Student, Subject, Semester, Lecturer, Room, CourseClass, Schedule, Score); CRUD; một số tính năng mở rộng (tổng quan sinh viên, import, cây học vụ, báo cáo).

**Tài liệu tham chiếu:** `description.md` (nếu có), `requirement.md` (yêu cầu chức năng & pattern).

---

### Bước 2 — Công nghệ & công cụ

- **Backend:** Java 21, Spring Boot 4, JPA (Hibernate), MySQL, Maven, Lombok.
- **Frontend:** Angular 21, TypeScript, (Tailwind / CSS).
- **Database:** MySQL 8.x.
- **Công cụ:** IDE (IntelliJ / VS Code), Git, (Docker nếu dùng docker-compose).

Liệt kê ngắn gọn; có thể kèm bảng version.

---

### Bước 3 — Phân tích & thiết kế hệ thống

- **Use case (chức năng chính):** Quản lý khoa, lớp, sinh viên, môn học, học kỳ, giảng viên, phòng, lớp học phần, lịch học, điểm; tổng quan sinh viên; import sinh viên; cây học vụ; xuất báo cáo.
- **Kiến trúc tổng quan:** Client (trình duyệt) → Frontend (Angular) → Backend (Spring Boot, REST API) → MySQL. Dùng **sơ đồ Draw.io** đã có: mở `docs/architecture-system.drawio` → trang **Kiến trúc hệ thống**.
- **Luồng dữ liệu:** Request từ client → UI → API (/api) → Service → Repository → DB; Response trả ngược lại. Có thể mô tả bằng đoạn văn hoặc sơ đồ.

**Tài liệu tham chiếu:** `docs/architecture-system.drawio`, `README.md`.

---

### Bước 4 — Thiết kế cơ sở dữ liệu

- **Mô hình thực thể – quan hệ:** 10 bảng (departments, classes, students, subjects, semesters, lecturers, rooms, course_classes, schedules, scores) và quan hệ 1-N, N-1 (ví dụ Department 1-N Class, Class N-1 Department).
- **Liệt kê bảng & cột chính:** Tên bảng, khóa chính, khóa ngoại, một số cột quan trọng.
- **Sơ đồ ERD:** Có thể vẽ thêm trong Draw.io hoặc công cụ khác; hoặc mô tả bằng bảng.

**Tài liệu tham chiếu:** `description.md`, `service/src/main/java/com/db/service/entity/*.java`, `service/src/main/resources/init.sql`.

---

### Bước 5 — Design Pattern đã áp dụng

Trình bày **Backend** và **Frontend** tách bảng hoặc tách mục.

**Backend (Spring Boot):**  
Liệt kê pattern đã làm (ví dụ: Command, Chain of Responsibility, Facade, Strategy, Observer, State, Memento, Factory, Template Method, Visitor, Decorator, Proxy, Adapter, …). Với mỗi pattern: **tên pattern**, **dùng ở đâu** (package/class/API), **mục đích** (1–2 câu).

**Frontend (Angular):**  
Dùng bảng trong tài liệu hoặc trong Draw.io:

- Mở `docs/architecture-system.drawio` → trang **Design Patterns đã làm** (bảng Pattern | Màn hình | Component).
- Hoặc copy bảng từ `ui/DESIGN_PATTERNS_UI.md` (phần **Tổng hợp: Màn hình ↔ Pattern**).

Với từng pattern quan trọng có thể thêm 1–2 câu: **dùng để làm gì** (ví dụ: Command dùng để tách thao tác form với gọi API; Strategy dùng để lọc/sắp xếp danh sách).

**Tài liệu tham chiếu:** `ui/DESIGN_PATTERNS_UI.md`, `docs/architecture-system.drawio` (trang Design Patterns), `requirement.md`, `.cursor/rules/` (nếu có).

---

### Bước 6 — Triển khai & hướng dẫn chạy

- **Yêu cầu môi trường:** JDK 21, Maven, Node.js 18+, npm, MySQL 8.
- **Cấu hình database:** Tạo database `student_management`; có thể dùng `service/src/main/resources/init.sql` để nạp dữ liệu mẫu. Nêu rõ biến môi trường (DB_URL, DB_USERNAME, DB_PASSWORD) nếu dùng.
- **Chạy Backend:**  
  `cd service` → `mvn spring-boot:run`  
  API: `http://localhost:1122/api`
- **Chạy Frontend:**  
  `cd ui` → `npm install` → `npm start`  
  Ứng dụng: `http://localhost:4200`
- **Chạy bằng Docker (nếu có):**  
  `docker compose up -d --build`  
  Nêu rõ port (UI, API, MySQL) và cách truy cập.

**Tài liệu tham chiếu:** `README.md`, `service/src/main/resources/application.yml`, `docker-compose.yml` (nếu có).

---

### Bước 7 — Demo chức năng chính

Liệt kê **các tính năng cần demo** khi báo cáo (trình bày trực tiếp hoặc quay video):

| STT | Chức năng | Cách demo |
|-----|-----------|-----------|
| 1 | CRUD Khoa / Lớp / Sinh viên / Môn / … | Mở menu → Danh sách → Thêm mới / Sửa / (Xóa nếu có) |
| 2 | Tổng quan sinh viên (Facade) | Students → chọn 1 SV → Overview: thông tin + điểm |
| 3 | Lọc/sắp xếp danh sách sinh viên (Strategy) | Students → đổi bộ lọc lớp, ô tìm kiếm, cột sắp xếp |
| 4 | Import sinh viên (Template Method) | Students → Import → chọn file CSV → Import |
| 5 | Sinh viên nguồn ngoài (Adapter) | Students → External source |
| 6 | Cây học vụ (Composite) | Students → Academic tree (hoặc menu tương ứng) |
| 7 | Xuất báo cáo (Visitor) | Reports → Export (hoặc Students → Report export) → Export CSV |
| 8 | Restore sinh viên (Memento) | Sửa sinh viên → nút Restore previous version |
| 9 | Trạng thái sinh viên (State) | Overview: nút Edit / Can register theo status |

Gợi ý: Chuẩn bị sẵn dữ liệu (chạy `init.sql` hoặc thao tác vài bản ghi) để demo không bị trống dữ liệu.

---

### Bước 8 — Kết luận & hướng phát triển

- **Kết luận:** Hệ thống đã triển khai đủ chức năng theo yêu cầu; đã áp dụng các Design Pattern (liệt kê ngắn); công nghệ ổn định, có thể mở rộng.
- **Hạn chế:** (tùy thực tế, ví dụ: chưa có đăng nhập/phân quyền, chưa deploy production.)
- **Hướng phát triển:** Đăng nhập/phan quyền; tối ưu hiệu năng; thêm báo cáo khác; deploy (Docker/cloud).

---

## 3. Thứ tự trình bày khi báo cáo (gợi ý)

1. **Mở đầu** (1–2 phút): Tên đề tài, mục tiêu, phạm vi.
2. **Công nghệ** (1 phút): Stack Backend / Frontend / DB.
3. **Kiến trúc & thiết kế** (2–3 phút): Chiếu sơ đồ Draw.io (Kiến trúc hệ thống); nói qua luồng Client → UI → API → DB.
4. **Cơ sở dữ liệu** (1–2 phút): ERD hoặc bảng chính.
5. **Design Pattern** (3–5 phút): Chiếu trang Draw.io **Design Patterns đã làm**; nêu 3–4 pattern tiêu biểu (Command, Strategy, Facade, Observer hoặc Composite, Visitor) và áp dụng ở màn hình nào.
6. **Hướng dẫn chạy** (1 phút): Nêu lệnh chạy Backend + Frontend (và Docker nếu dùng).
7. **Demo** (5–10 phút): Chạy ứng dụng, lần lượt demo các chức năng trong bảng Bước 7.
8. **Kết luận** (1 phút): Tóm tắt, hạn chế, hướng phát triển.

Tổng khoảng **15–25 phút** tùy yêu cầu.

---

## 4. Danh sách tài liệu trong repo hỗ trợ báo cáo

| Tài liệu | Nội dung |
|----------|----------|
| `README.md` | Tổng quan, cấu hình, lệnh chạy, API chính |
| `requirement.md` | Yêu cầu chức năng, công nghệ, Design Pattern (nếu có) |
| `description.md` | Mô tả thực thể, quan hệ (nếu có) |
| `docs/architecture-system.drawio` | Sơ đồ kiến trúc + bảng Design Patterns |
| `docs/README-diagrams.md` | Hướng dẫn mở/sửa file Draw.io |
| `ui/DESIGN_PATTERNS_UI.md` | Chi tiết từng pattern trên UI, màn hình, file, luồng |
| `service/src/main/resources/init.sql` | Dữ liệu mẫu cho DB |
| `docker-compose.yml` | Chạy UI + Service + MySQL bằng Docker (nếu có) |

---

*Tài liệu này chỉ mang tính gợi ý; có thể chỉnh sửa cho phù hợp với quy định báo cáo của trường / giảng viên.*
