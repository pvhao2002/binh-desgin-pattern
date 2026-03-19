# Phân tích chi tiết Design Pattern trên UI

Tài liệu liệt kê **từng mẫu Design Pattern** được sử dụng trong frontend Angular, **màn hình nào** dùng pattern đó, **file nào** tham gia và **luồng xử lý** cụ thể. Không bỏ sót chi tiết.

---

## 1. Command (Lệnh)

### Màn hình sử dụng
- **Form thêm/sửa sinh viên**  
  - Route: `/students/new` (thêm mới), `/students/:id/edit` (chỉnh sửa).  
  - Component: `StudentFormComponent`.

### Mô tả chức năng
Component **không** gọi trực tiếp `studentService.create()` hay `studentService.update()`. Thay vào đó:
1. Lấy giá trị form → tạo đối tượng **Command** (CreateStudentCommand hoặc UpdateStudentCommand).
2. Gửi command cho **StudentCommandExecutor**.
3. Executor ánh xạ command sang API (POST/PUT) và gọi service tương ứng.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/commands/student.command.ts` | Định nghĩa `CreateStudentCommand` (alias `StudentRequest`), `UpdateStudentCommand` (id + request), `DeleteStudentCommand` (id). |
| `core/services/student-command.executor.ts` | `StudentCommandExecutor`: `executeCreate(command)`, `executeUpdate(command)`, `executeDelete(command)` — nhận command, gọi `StudentService.create/update/delete`. |
| `features/students/student-form/student-form.component.ts` | Inject `StudentCommandExecutor`, `StudentCommandFactory`. Trong `onSubmit()`: tạo command bằng factory, gọi `commandExecutor.executeCreate(createCmd)` hoặc `commandExecutor.executeUpdate(command)`. |

### Luồng cụ thể (Form submit)
1. User bấm Save trên form.  
2. `StudentFormComponent.onSubmit()` lấy `this.form.value`.  
3. **Nếu thêm mới:** `createCmd = this.commandFactory.createFromFormValue(v)` → `commandExecutor.executeCreate(createCmd).subscribe(...)`.  
4. **Nếu sửa:** `command = this.commandFactory.createUpdateCommand(this.id()!, v)` → `commandExecutor.executeUpdate(command).subscribe(...)`.  
5. Executor gọi `studentService.create(command)` hoặc `studentService.update(command.id, command.request)`.  
6. Sau khi thành công: `studentEvents.notify({ type: 'created'|'updated', studentId })`, điều hướng về `/students`.

---

## 2. Strategy (Chiến lược)

### Màn hình sử dụng
- **Danh sách sinh viên**  
  - Route: `/students`.  
  - Component: `StudentListComponent`.

### Mô tả chức năng
Bộ **lọc** và **sắp xếp** danh sách được tách thành hai Strategy:
- **FilterStrategy**: lọc theo lớp (`classId`) và theo từ khóa tìm kiếm (fullName, email, className).
- **SortStrategy**: sắp xếp theo trường (fullName, email, classId) và chiều (asc/desc).

Component inject hai strategy, áp dụng **filter trước, sort sau** lên danh sách rồi hiển thị. Đổi cách lọc/sắp xếp chỉ cần đổi implementation, không sửa component.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/strategies/filter.strategy.ts` | Interface `FilterStrategy`: `filter(items: Student[], criteria: StudentFilterCriteria): Student[]`. `StudentFilterCriteria`: classId?, searchText?. |
| `core/strategies/sort.strategy.ts` | Interface `SortStrategy`: `sort(items: Student[], key, direction): Student[]`. `SortDirection`: 'asc' \| 'desc'. |
| `core/strategies/impl/student-filter.strategy.ts` | `StudentFilterStrategy` implement FilterStrategy: lọc theo classId, theo searchText (fullName, email, className). |
| `core/strategies/impl/student-sort.strategy.ts` | `StudentSortStrategy` implement SortStrategy: sort theo key (string/number), direction. |
| `features/students/student-list/student-list.component.ts` | Inject `StudentFilterStrategy`, `StudentSortStrategy` (khai báo type FilterStrategy, SortStrategy). Signal `filterCriteria`, `sortKey`, `sortDirection`. Computed `displayedStudents`: `filterStrategy.filter(raw, criteria)` → `sortStrategy.sort(filtered, sortKey, sortDirection)`. |

### Luồng cụ thể
1. `rawStudents` lấy từ API qua `LoggingStudentServiceDecorator.getAll()`.  
2. `filterCriteria` = computed từ `filterClassId`, `searchText`.  
3. `displayedStudents` = computed: `filterStrategy.filter(rawStudents(), filterCriteria())` → `sortStrategy.sort(..., sortKey(), sortDirection())`.  
4. Template bind `displayedStudents()` vào bảng. Khi user đổi dropdown lớp hoặc ô search, `filterClassId`/`searchText` đổi → computed cập nhật → danh sách hiển thị thay đổi. Khi đổi cột sắp xếp hoặc chiều, `sortKey`/`sortDirection` đổi → danh sách sắp xếp lại.

---

## 3. Facade (Mặt tiền)

### Màn hình sử dụng
- **Tổng quan sinh viên**  
  - Route: `/students/:id/overview`.  
  - Component: `StudentOverviewComponent`.

### Mô tả chức năng
Màn hình cần **một view tổng hợp**: thông tin sinh viên + lớp + điểm. Thay vì component gọi nhiều API (student, class, scores) rời rạc, chỉ gọi **một Facade**: `StudentOverviewFacadeService.getOverview(studentId)`. Backend (hoặc facade) gom dữ liệu và trả về một model `StudentOverview` (student, classInfo, scores). Component chỉ gọi facade, không biết chi tiết nguồn dữ liệu bên trong.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/services/student-overview.facade.ts` | `StudentOverviewFacadeService`: method `getOverview(studentId: number): Observable<StudentOverview>`, gọi `this.api.get<StudentOverview>(\`/student-overview/${studentId}\`)`. |
| `core/models/student-overview.model.ts` | Định nghĩa model `StudentOverview` (student, classInfo, scores). |
| `features/students/student-overview/student-overview.component.ts` | Inject `StudentOverviewFacadeService`. Trong `ngOnInit()`: `this.facade.getOverview(id).subscribe(...)` → set `overview.set(data)`. Template hiển thị overview.student, overview.classInfo, overview.scores. |

### Luồng cụ thể
1. User vào `/students/123/overview`.  
2. Component lấy `id` từ route, gọi `facade.getOverview(id)`.  
3. Facade gọi GET `/api/student-overview/123`.  
4. Nhận `StudentOverview` → set vào signal `overview`.  
5. Template hiển thị tên, lớp, bảng điểm từ `overview()`.

---

## 4. Observer (Quan sát)

### Màn hình / phạm vi sử dụng
- **Danh sách sinh viên** (`StudentListComponent`, route `/students`): subscribe sự kiện để reload danh sách.  
- **Form sinh viên** (`StudentFormComponent`): sau khi create/update thành công thì **phát** sự kiện (subject).

### Mô tả chức năng
**Subject**: `StudentEventService` — phát sự kiện khi có thay đổi sinh viên (created, updated, deleted). **Observers**: component danh sách subscribe `events$`; khi nhận sự kiện thì gọi lại `loadStudents()`. Form sau khi gọi executor thành công thì gọi `studentEvents.notify({ type: 'created'|'updated', studentId })`. Tách biệt nơi phát sự kiện và nơi phản ứng (list refresh).

### File liên quan

| File | Vai trò |
|------|--------|
| `core/services/student-event.service.ts` | `Subject<StudentEvent>`, `events$ = events.asObservable()`, `notify(event: StudentEvent)`. |
| `features/students/student-form/student-form.component.ts` | Inject `StudentEventService`. Trong `executeCreate`/`executeUpdate` success: `this.studentEvents.notify({ type: 'created'|'updated', studentId: s.studentId })`. |
| `features/students/student-list/student-list.component.ts` | Inject `StudentEventService`. Trong `ngOnInit()`: `this.studentEvents.events$.subscribe(() => this.loadStudents())`. |

### Luồng cụ thể
1. User thêm/sửa sinh viên trên form → executor thành công → `studentEvents.notify({ type: 'updated', studentId })`.  
2. List component đang subscribe `events$` → callback chạy → `loadStudents()` → gọi lại API getAll → cập nhật `rawStudents` → `displayedStudents` computed cập nhật → bảng hiển thị lại.

---

## 5. Adapter (Bộ chuyển đổi)

### Màn hình sử dụng
- **Sinh viên nguồn ngoài**  
  - Route: `/students/external`.  
  - Component: `ExternalStudentsComponent`.

### Mô tả chức năng
Nguồn dữ liệu bên ngoài (API `/sync/external-students`) có thể có format khác. **Interface** `ExternalStudentSource` với method `getStudents(): Observable<ExternalStudentDto[]>`. **ExternalApiStudentAdapter** implement interface này: gọi API, map/chuẩn hóa response sang `ExternalStudentDto[]` (studentId, fullName, email). Component chỉ gọi adapter (interface), không phụ thuộc chi tiết API ngoài.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/adapters/external-student.adapter.ts` | Interface `ExternalStudentSource`: `getStudents(): Observable<ExternalStudentDto[]>`. `ExternalStudentDto`: studentId, fullName, email. `ExternalApiStudentAdapter` implement: gọi `this.api.get<ExternalStudentDto[]>('/sync/external-students')`, pipe map `list => list ?? []`. |
| `features/students/external-students/external-students.component.ts` | Inject `ExternalApiStudentAdapter`. `ngOnInit()`: `this.adapter.getStudents().subscribe(...)` → set `students.set(list)`. Template hiển thị bảng students (fullName, email). |

### Luồng cụ thể
1. User vào `/students/external`.  
2. Component gọi `adapter.getStudents()`.  
3. Adapter gọi GET `/api/sync/external-students`, trả về mảng DTO.  
4. Component nhận list → hiển thị bảng.

---

## 6. State (Trạng thái)

### Màn hình sử dụng
- **Tổng quan sinh viên**  
  - Route: `/students/:id/overview`.  
  - Component: `StudentOverviewComponent`.

### Mô tả chức năng
Sinh viên có trạng thái: **ACTIVE**, **SUSPENDED**, **GRADUATED**. Mỗi trạng thái có hành vi khác nhau: có được sửa không (`canEdit()`), có được đăng ký môn không (`canRegisterCourse()`), có được restore không (`canRestore()`). **StudentState** (interface) và các implementation **ActiveStudentState**, **SuspendedStudentState**, **GraduatedStudentState** đóng gói hành vi. **StudentStateContextService** nhận status string từ API, trả về đúng instance State. Component overview gọi `stateContext.getStateForStatus(res.status)` → nhận `StudentState`, dùng `st.canEdit()`, `st.canRegisterCourse()` để ẩn/hiện nút "Edit Student", "Can register course".

### File liên quan

| File | Vai trò |
|------|--------|
| `core/state/student-state.ts` | Interface `StudentState`: `getStatusName()`, `canEdit()`, `canRegisterCourse()`, `canRestore()`. |
| `core/state/impl/active-student.state.ts` | Active: canEdit true, canRegisterCourse true. |
| `core/state/impl/suspended-student.state.ts` | Suspended: canEdit true, canRegisterCourse false. |
| `core/state/impl/graduated-student.state.ts` | Graduated: canEdit false, canRegisterCourse false. |
| `core/state/student-state.context.ts` | `StudentStateContextService`: inject 3 state impl, `getStateForStatus(status: string): StudentState` (map ACTIVE/SUSPENDED/GRADUATED). |
| `features/students/student-overview/student-overview.component.ts` | Inject `StudentStateContextService`, `StudentService`. Load overview qua facade; gọi `studentService.getState(id)` → nhận status → `currentState.set(stateContext.getStateForStatus(res.status))`. |
| `features/students/student-overview/student-overview.component.html` | `@if (currentState(); as st) { @if (st.canEdit()) { ... Edit Student ... } @if (st.canRegisterCourse()) { ... Can register course ... } }`. |

### Luồng cụ thể
1. Overview load xong, gọi thêm GET `/students/:id/state` → nhận `{ status, canRegisterCourse }`.  
2. `currentState.set(stateContext.getStateForStatus(res.status))`.  
3. Template: nếu `st.canEdit()` thì hiển thị link "Edit Student"; nếu `st.canRegisterCourse()` thì hiển thị "Can register course". Trạng thái Graduated sẽ không hiện Edit.

---

## 7. Memento (Bản ghi nhớ)

### Màn hình sử dụng
- **Form chỉnh sửa sinh viên**  
  - Route: `/students/:id/edit`.  
  - Component: `StudentFormComponent`.

### Mô tả chức năng
Nút **"Restore previous version"** cho phép khôi phục bản ghi về phiên bản đã lưu trước đó (snapshot do backend lưu). Trên UI: form inject `StudentServiceProxy`; khi user bấm Restore, gọi `studentService.restore(id)`. API restore (backend) áp dụng lại snapshot (Memento). Sau khi restore thành công, response trả về dữ liệu sinh viên mới → form `patchValue` và notify event. Không có Memento client-side (snapshot form) trong code hiện tại; Memento thể hiện ở phía backend (restore bản đã lưu).

### File liên quan

| File | Vai trò |
|------|--------|
| `features/students/student-form/student-form.component.ts` | Method `onRestore()`: confirm, gọi `this.studentService.restore(id).subscribe(next: s => form.patchValue(...), studentEvents.notify({ type: 'updated', studentId }))`. |
| `features/students/student-form/student-form.component.html` | Nút "Restore previous version" chỉ hiện khi `isEdit() && id()`, `(click)="onRestore()"`. |
| `core/services/proxy/student-service.proxy.ts` | `restore(id)`: xóa cache, gọi `this.real.restore(id)`. |

### Luồng cụ thể
1. User đang ở form sửa sinh viên, bấm "Restore previous version".  
2. Confirm → gọi `studentService.restore(id)` (qua Proxy → real service).  
3. Backend xử lý Memento (khôi phục snapshot).  
4. Response trả về Student → form patchValue, notify event → list có thể reload.

---

## 8. Chain of Responsibility (Chuỗi trách nhiệm)

### Màn hình sử dụng
- **Form thêm/sửa sinh viên**  
  - Route: `/students/new`, `/students/:id/edit`.  
  - Component: `StudentFormComponent`.

### Mô tả chức năng
Validation trước khi submit **không** chỉ dựa vào Validators của form. Chạy thêm một **pipeline** (chuỗi trách nhiệm): mỗi handler kiểm tra một phần (required, format email), nếu lỗi thì trả về ngay, nếu hợp lệ thì chuyển tiếp cho handler kế. **StudentValidationPipeline** nối: RequiredValidationHandler (fullName) → RequiredValidationHandler (classId) → EmailFormatValidationHandler (email). Form gọi `validationPipeline.validate(formValue)` một lần, nhận `ValidationResult` (valid, errors[]); nếu không valid thì alert(errors) và không submit.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/validation/validation-handler.ts` | Interface `ValidationHandler`: `setNext(handler): ValidationHandler`, `validate(value: unknown): ValidationResult`. `ValidationResult`: valid, errors[]. |
| `core/validation/handlers/required.handler.ts` | `RequiredValidationHandler(fieldName)`: validate giá trị field không null/undefined/empty; nếu ok gọi nextHandler.validate(value). |
| `core/validation/handlers/email-format.handler.ts` | `EmailFormatValidationHandler(fieldName)`: nếu email có giá trị thì kiểm tra regex; nếu ok gọi nextHandler. |
| `core/validation/student-validation.pipeline.ts` | `StudentValidationPipeline`: tạo requiredFullName, requiredClassId, emailFormat; `requiredFullName.setNext(requiredClassId).setNext(emailFormat)`; chain = requiredFullName. Method `validate(formValue)` gọi `this.chain.validate(formValue)`. |
| `features/students/student-form/student-form.component.ts` | Inject `StudentValidationPipeline`. Trong `onSubmit()`: `const result = this.validationPipeline.validate(v)`; nếu `!result.valid` thì `alert(result.errors.join('\n'))`, return; ngược lại mới tạo command và gọi executor. |

### Luồng cụ thể
1. User bấm Save.  
2. `onSubmit()` lấy `v = this.form.value`.  
3. `result = validationPipeline.validate(v)`.  
4. Chain chạy: required fullName → required classId → email format. Bất kỳ bước nào fail → return { valid: false, errors: [...] }.  
5. Nếu result.valid === false → alert(errors), return.  
6. Nếu valid → tiếp tục tạo command và executeCreate/executeUpdate.

---

## 9. Template Method (Phương thức mẫu)

### Màn hình sử dụng
- **Import sinh viên**  
  - Route: `/students/import`.  
  - Component: `StudentImportComponent`.

### Mô tả chức năng
Luồng import có **các bước cố định**: đọc file → validate nội dung → map sang model → gửi lên server. **BaseImportFlow&lt;T&gt;** định nghĩa template method `run(file: File): Observable<ImportResult>`: gọi lần lượt `readFile(file)` → `validate(raw)` → nếu lỗi trả ngay; không thì `mapToModel(raw)` → `submit(items)`. Các bước **abstract** (readFile, validate, mapToModel, submit) do subclass override. **StudentImportFlow** kế thừa BaseImportFlow&lt;StudentRequest&gt;, override: readFile (FileReader), validate (có header + ít nhất 1 dòng), mapToModel (parse CSV → StudentRequest[]), submit (gọi studentService.create từng item). Component Import chỉ gọi `importFlow.run(file)`.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/import/base-import.flow.ts` | Abstract class `BaseImportFlow<T>`: abstract readFile, validate, mapToModel, submit. Template method `run(file)`: readFile → validate → mapToModel → submit; xử lý Observable và errors. |
| `core/import/student-import.flow.ts` | `StudentImportFlow extends BaseImportFlow<StudentRequest>`: override readFile (FileReader readAsText), validate (lines length), mapToModel (split CSV, map cột fullName, classId, email), submit (loop create qua studentService). |
| `features/students/student-import/student-import.component.ts` | Inject `StudentImportFlow`. Method `import()`: lấy file đã chọn, gọi `this.importFlow.run(file).subscribe(...)` → set result (success, count, errors). |

### Luồng cụ thể
1. User chọn file CSV, bấm Import.  
2. Component gọi `importFlow.run(file)`.  
3. Base run(): readFile → raw string; validate(raw) → nếu có lỗi trả ngay ImportResult { success: false, errors }; không thì mapToModel(raw) → items; submit(items) → từng item create qua API.  
4. Kết quả (success, count, errors) trả về → component set result signal → template hiển thị.

---

## 10. Composite (Tổ hợp)

### Màn hình sử dụng
- **Cây học vụ (Academic Tree)**  
  - Route: `/academic-tree`.  
  - Component: `AcademicTreeComponent`, `TreeNodeComponent` (con).

### Mô tả chức năng
Cấu trúc cây **Department → Class → Student**: mỗi nút có thể có con (department có classes, class có students), nút lá (student) có children rỗng. **TreeNode** (interface): id, label, type ('department'|'class'|'student'), children: TreeNode[], expandable. **AcademicTreeService** build cây từ 3 API (departments, classes, students): tạo DepartmentNode (children = ClassNode), ClassNode (children = StudentNode), StudentNode (children = []). Component tree nhận roots, render từng node bằng **app-tree-node**; tree-node nhận input `node`, hiển thị label và nếu `node.children.length` thì render đệ quy `<app-tree-node [node]="child" />`. Một cấu trúc thống nhất (composite) để duyệt và hiển thị toàn bộ cây.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/models/tree-node.model.ts` | Interface `TreeNode`: id, label, type, children: TreeNode[], expandable. |
| `core/services/academic-tree.service.ts` | `buildTree()`: forkJoin getAll departments, classes, students; map thành cây: với mỗi department tạo node (type department), với mỗi class thuộc department tạo node (type class), với mỗi student thuộc class push vào classNode.children (type student, children []). Trả về roots (TreeNode[]). |
| `features/academic-tree/academic-tree.component.ts` | Inject `AcademicTreeService`. `ngOnInit`: `treeService.buildTree().subscribe(roots.set)`. Template: `@for (node of roots()) { <li><app-tree-node [node]="node" /></li> }`. |
| `features/academic-tree/tree-node/tree-node.component.ts` | Input `node: TreeNode`. Template: hiển thị node.label; `@if (node.children.length) { @for (child of node.children) { <li><app-tree-node [node]="child" /> } }` — đệ quy. |

### Luồng cụ thể
1. User vào `/academic-tree`.  
2. AcademicTreeComponent gọi `treeService.buildTree()` → nhận roots (mảng TreeNode gốc).  
3. Template render từng root bằng app-tree-node.  
4. TreeNodeComponent với node (department): hiển thị label; có children → render từng child (class) bằng app-tree-node. TreeNodeComponent với node (class): hiển thị label; có children → render từng child (student). TreeNode (student): children = [] → không render con. Kết quả: cây Department → Class → Student hiển thị đệ quy.

---

## 11. Visitor (Khách)

### Màn hình sử dụng
- **Xuất báo cáo (Report Export)**  
  - Route: `/reports/export`.  
  - Component: `ReportExportComponent`.

### Mô tả chức năng
Dữ liệu báo cáo (danh sách dòng từ API department-tree) cần **xuất ra CSV**. Logic xuất (escape, format từng dòng) tách vào **Visitor**: interface `ReportExportVisitor` với `visitLine(line: string)`, `getResult(): string`. **CsvExportVisitor** implement: visitLine đẩy dòng vào mảng (có escape), getResult trả về chuỗi CSV (mỗi dòng trong dấu ngoặc kép). Component lấy lines từ ReportService, tạo visitor, `lines.forEach(line => visitor.visitLine(line))`, `visitor.getResult()` → tạo Blob và download file. Tách logic xuất file (visitor) khỏi cấu trúc dữ liệu (lines).

### File liên quan

| File | Vai trò |
|------|--------|
| `core/visitor/export.visitor.ts` | Interface `ReportExportVisitor`: visitLine(line), getResult(): string. `CsvExportVisitor`: mảng lines; visitLine push (replace " → ""); getResult trả về lines.map(l => `"${l}"`).join('\n'). |
| `features/reports/report-export/report-export.component.ts` | Inject `ReportService`. ngOnInit: load lines từ API. Method `exportCsv()`: `const visitor = new CsvExportVisitor()`; `this.lines().forEach(line => visitor.visitLine(line))`; `const csv = visitor.getResult()`; tạo Blob, download 'department-tree-report.csv'. |

### Luồng cụ thể
1. User vào `/reports/export`, trang load lines từ GET `/reports/department-tree`.  
2. User bấm "Export CSV".  
3. exportCsv(): tạo CsvExportVisitor, visitLine từng dòng, getResult() → chuỗi CSV.  
4. Tạo Blob, thẻ a download → file CSV tải về.

---

## 12. Decorator (Trang trí)

### Màn hình / phạm vi sử dụng
- **Danh sách sinh viên** (route `/students`).  
  - Component: `StudentListComponent` inject **LoggingStudentServiceDecorator** thay vì StudentService trực tiếp.

### Mô tả chức năng
**LoggingStudentServiceDecorator** implement cùng interface **IStudentService**, giữ reference tới **StudentService** thật (real). Mỗi method (getAll, getById, create, update, delete, restore): ghi log (console.debug) trước/sau, rồi ủy quyền cho real. Danh sách sinh viên gọi getAll qua decorator → mỗi lần load danh sách đều có log mà không sửa code StudentService. Decorator bọc (wrap) service thật để thêm hành vi phụ (logging).

### File liên quan

| File | Vai trò |
|------|--------|
| `core/services/student-service.interface.ts` | Interface IStudentService (getAll, getById, create, update, delete, restore, getState). |
| `core/services/student.service.ts` | StudentService implement IStudentService, gọi API. |
| `core/services/decorators/logging-student.service.ts` | `LoggingStudentServiceDecorator` implement IStudentService; constructor(private real: StudentService). Mỗi method: console.debug(...), return this.real.xxx().pipe(tap(() => console.debug(...))). |
| `features/students/student-list/student-list.component.ts` | Inject `LoggingStudentServiceDecorator`. loadStudents() gọi `this.service.getAll()` — qua decorator nên có log. |

### Luồng cụ thể
1. StudentListComponent.loadStudents() gọi service.getAll().  
2. service là LoggingStudentServiceDecorator → console.debug('[LoggingStudentService] getAll') → gọi this.real.getAll() → khi xong tap log 'getAll done'.  
3. Danh sách hiển thị như bình thường, đồng thời có log trong console.

---

## 13. Proxy (Ủy quyền)

### Màn hình sử dụng
- **Form thêm/sửa sinh viên** (route `/students/new`, `/students/:id/edit`).  
  - Component: `StudentFormComponent` inject **StudentServiceProxy** thay vì StudentService trực tiếp.

### Mô tả chức năng
**StudentServiceProxy** implement IStudentService, giữ reference tới StudentService thật. **getById(id)**: kiểm tra cache (Map<id, Student>); nếu đã có thì trả Observable.of(cached), không gọi API; nếu chưa thì gọi real.getById(id) và pipe tap để cache kết quả. **create, update, delete, restore**: không cache, gọi real và khi ghi/xóa thì **invalidate cache** (cache.delete(id)) để lần sau getById lấy dữ liệu mới. Form dùng proxy cho getById (load dữ liệu khi sửa) và restore; giảm gọi API getById trùng lặp (ví dụ mở form sửa nhiều lần cùng id).

### File liên quan

| File | Vai trò |
|------|--------|
| `core/services/proxy/student-service.proxy.ts` | `StudentServiceProxy`: private cache = new Map<number, Student>; constructor(private real: StudentService). getById: if (cached) return of(cached); else return real.getById(id).pipe(tap(s => this.cache.set(id, s))). create/update/delete/restore: xóa cache liên quan (id), gọi real. |
| `features/students/student-form/student-form.component.ts` | Inject `StudentServiceProxy`. ngOnInit (khi edit): this.studentService.getById(id).subscribe(...). onRestore: this.studentService.restore(id).subscribe(...). |

### Luồng cụ thể
1. User mở form sửa sinh viên id=5.  
2. Component gọi studentService.getById(5). Proxy kiểm tra cache: chưa có → gọi real.getById(5) → tap cache.set(5, s) → trả về.  
3. Lần sau (ví dụ quay lại form cùng id 5): getById(5) trả từ cache, không gọi API.  
4. Khi user Restore hoặc khi update thành công (qua executor), proxy không cache create/update; restore/update xóa cache id → lần getById tiếp theo sẽ gọi API.

---

## 14. Factory (Nhà máy)

### Màn hình sử dụng
- **Form thêm/sửa sinh viên** (route `/students/new`, `/students/:id/edit`).  
  - Component: `StudentFormComponent`.

### Mô tả chức năng
Tạo đối tượng **Command** từ giá trị form không để component tự build. **StudentCommandFactory** cung cấp: `createFromFormValue(formValue): CreateStudentCommand` (map form value sang StudentRequest), `createUpdateCommand(id, formValue): UpdateStudentCommand` (id + request). Form lấy form.value → gọi factory tạo command → đưa command cho executor. Tập trung logic tạo command tại một nơi (factory), dễ bảo trì và thay đổi cấu trúc command.

### File liên quan

| File | Vai trò |
|------|--------|
| `core/factories/student-command.factory.ts` | `StudentCommandFactory`: createFromFormValue(formValue) trả object có fullName, dateOfBirth, gender, address, phoneNumber, email, classId (CreateStudentCommand). createUpdateCommand(id, formValue) trả { id, request: same shape } (UpdateStudentCommand). |
| `features/students/student-form/student-form.component.ts` | Inject `StudentCommandFactory`. onSubmit: createCmd = this.commandFactory.createFromFormValue(v) hoặc command = this.commandFactory.createUpdateCommand(this.id()!, v); sau đó executor.executeCreate(createCmd) hoặc executor.executeUpdate(command). |

### Luồng cụ thể
1. User bấm Save, form.value = v.  
2. Thêm mới: createCmd = commandFactory.createFromFormValue(v) → commandExecutor.executeCreate(createCmd).  
3. Sửa: command = commandFactory.createUpdateCommand(id, v) → commandExecutor.executeUpdate(command).  
4. Factory đảm bảo payload đúng kiểu (StudentRequest / UpdateStudentCommand) cho API.

---

## Tổng hợp: Màn hình ↔ Pattern

| Màn hình (route) | Component | Pattern(s) áp dụng |
|------------------|-----------|---------------------|
| `/students` | StudentListComponent | **Strategy** (Filter, Sort), **Observer** (subscribe events → loadStudents), **Decorator** (LoggingStudentServiceDecorator cho getAll) |
| `/students/new`, `/students/:id/edit` | StudentFormComponent | **Command** (Executor + Command), **Factory** (StudentCommandFactory), **Chain of Responsibility** (StudentValidationPipeline), **Observer** (notify sau create/update), **Memento** (restore qua API), **Proxy** (StudentServiceProxy cho getById, restore) |
| `/students/:id/overview` | StudentOverviewComponent | **Facade** (StudentOverviewFacadeService), **State** (StudentState, StudentStateContextService) |
| `/students/import` | StudentImportComponent | **Template Method** (StudentImportFlow extends BaseImportFlow) |
| `/students/external` | ExternalStudentsComponent | **Adapter** (ExternalApiStudentAdapter) |
| `/academic-tree` | AcademicTreeComponent + TreeNodeComponent | **Composite** (TreeNode, cây Department→Class→Student, render đệ quy) |
| `/reports/export` | ReportExportComponent | **Visitor** (CsvExportVisitor) |

---

## Các màn hình không dùng pattern đặc thù

Các màn hình CRUD còn lại (Departments, Classes, Subjects, Semesters, Lecturers, Rooms, Course Classes, Schedules, Scores) **không** inject Command/Strategy/Facade/Observer/Adapter/State/Memento/CoR/Template Method/Composite/Visitor/Decorator/Proxy/Factory. Chúng gọi trực tiếp service tương ứng (DepartmentService, ClassService, ...) và không có pipeline validation, không có command executor, không có filter/sort strategy. Rule "mỗi chức năng một pattern" được áp dụng tập trung ở **feature Students** và **Academic tree / Reports**.

---

*Tài liệu này mô tả đầy đủ từng pattern, màn hình, file và luồng xử lý trên UI. Cập nhật khi thêm pattern hoặc màn hình mới.*
