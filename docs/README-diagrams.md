# Sơ đồ kiến trúc và Design Patterns

## File

- **architecture-system.drawio** — Gồm 2 trang (tab):
  1. **Kiến trúc hệ thống** — Luồng Client → Frontend (Angular) → Backend (Spring Boot) → MySQL.
  2. **Design Patterns đã làm** — Bảng ánh xạ 14 pattern (Command, Strategy, Facade, Observer, Adapter, State, Memento, Chain of Responsibility, Template Method, Composite, Visitor, Decorator, Proxy, Factory) với màn hình (route) và component/vai trò tương ứng trên UI.

## Cách mở / chỉnh sửa

1. **Draw.io Desktop:** [https://github.com/jgraph/drawio-desktop/releases](https://github.com/jgraph/drawio-desktop/releases) — mở file `architecture-system.drawio`.
2. **draw.io (diagrams.net) online:** [https://app.diagrams.net/](https://app.diagrams.net/) — File → Open from → Device, chọn file.
3. **VS Code:** Cài extension "Draw.io Integration", mở file `.drawio` trong editor.

## Nội dung sơ đồ

- **Client:** Trình duyệt, người dùng tương tác với Angular.
- **Frontend (Angular 21):** Components (List, Form, Overview, Import, Tree, Report), Services/Core (API, Command, Strategy, Facade, State, Visitor...), HttpClient → Base URL `/api`.
- **Backend (Spring Boot 4):** Controllers REST (`/api/...`), Services/Handlers, JPA/Repositories.
- **MySQL:** Database `student_management`, port 3306.

**Luồng:** Client → UI (:4200) → REST /api (JSON) → Backend (:1122/api) → JDBC/JPA → MySQL (:3306). Response đi ngược lại (mũi tên nét đứt).
