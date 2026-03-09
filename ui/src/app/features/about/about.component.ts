import { Component } from '@angular/core';

export interface DesignPatternItem {
  name: string;
  page: string;
  route: string;
  description: string;
}

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [],
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent {
  patterns: DesignPatternItem[] = [
    {
      name: 'Command',
      page: 'Form thêm/sửa sinh viên',
      route: '/students (Thêm mới, Chỉnh sửa)',
      description: 'Component không gọi service.create() hay service.update() trực tiếp. Thay vào đó tạo đối tượng lệnh (CreateStudentCommand, UpdateStudentCommand) và gửi qua StudentCommandExecutor. Executor ánh xạ lệnh sang API POST/PUT tương ứng. Tách biệt thao tác người dùng với logic gọi API.'
    },
    {
      name: 'Strategy',
      page: 'Danh sách sinh viên',
      route: '/students',
      description: 'Bộ lọc và sắp xếp danh sách dùng Strategy. FilterStrategy (StudentFilterStrategy) lọc theo lớp, theo từ khóa tìm kiếm. SortStrategy (StudentSortStrategy) sắp xếp theo tên, email hoặc lớp. Component inject các strategy và áp dụng lên danh sách trước khi hiển thị. Có thể đổi cách lọc/sắp xếp mà không sửa component.'
    },
    {
      name: 'Facade',
      page: 'Tổng quan sinh viên',
      route: '/students/:id/overview',
      description: 'Màn hình tổng quan cần dữ liệu từ nhiều nguồn: thông tin sinh viên, lớp, điểm. StudentOverviewFacadeService gom tất cả vào một lần gọi (hoặc gọi nhiều API nội bộ) và trả về một view model thống nhất. Component chỉ gọi facade, không gọi nhiều service riêng lẻ.'
    },
    {
      name: 'Observer',
      page: 'Cập nhật danh sách sau thao tác',
      route: 'Students (danh sách + form)',
      description: 'Khi thêm, sửa hoặc xóa sinh viên, các màn hình khác cần cập nhật. StudentEventService đóng vai trò subject: phát sự kiện (created, updated, deleted). StudentListComponent subscribe và khi nhận sự kiện thì gọi lại load danh sách. Tách biệt nơi phát sự kiện và nơi phản ứng.'
    },
    {
      name: 'Adapter',
      page: 'Sinh viên nguồn ngoài',
      route: '/students/external',
      description: 'Dữ liệu từ API bên ngoài có format khác với backend nội bộ. ExternalApiStudentAdapter implement interface nguồn dữ liệu (getStudents()), gọi API ngoài và chuyển đổi response sang model dùng trong app. Component chỉ gọi adapter, không biết chi tiết format bên ngoài.'
    },
    {
      name: 'State',
      page: 'Tổng quan sinh viên',
      route: '/students/:id/overview',
      description: 'Sinh viên có các trạng thái: Active, Suspended, Graduated. Mỗi trạng thái có hành vi khác nhau (có được sửa không, có được đăng ký môn không). StudentState (ActiveStudentState, SuspendedStudentState, GraduatedStudentState) và StudentStateContext quyết định hiển thị nút Edit, Restore hay "Đăng ký môn" theo state hiện tại.'
    },
    {
      name: 'Memento',
      page: 'Form chỉnh sửa sinh viên',
      route: '/students/:id/edit',
      description: 'Nút "Restore previous version" cho phép khôi phục bản ghi về phiên bản đã lưu trước đó. Backend lưu snapshot (Memento) trước khi sửa; khi gọi API restore thì áp dụng lại snapshot. Trên UI, form gọi studentService.restore(id) để đồng bộ với bản đã lưu.'
    },
    {
      name: 'Chain of Responsibility',
      page: 'Form thêm/sửa sinh viên',
      route: '/students/new, /students/:id/edit',
      description: 'Validation trước khi submit chạy qua pipeline: InputValidationHandler (bắt buộc, định dạng) → BusinessValidationHandler (lớp tồn tại, quy tắc nghiệp vụ). StudentValidationPipeline nối các handler; mỗi handler xử lý phần việc của mình và chuyển tiếp. Form gọi pipeline.validate(formValue) một lần để nhận danh sách lỗi.'
    },
    {
      name: 'Template Method',
      page: 'Import sinh viên',
      route: '/students/import',
      description: 'Luồng import cố định: đọc file → kiểm tra → ánh xạ sang model → gửi lên server. BaseImportFlow định nghĩa template method run(file) gọi readFile(), validate(), mapToModel(), submit(). StudentImportFlow kế thừa và override từng bước (ví dụ đọc CSV, map cột). Component Import chỉ gọi flow.run(file).'
    },
    {
      name: 'Composite',
      page: 'Cây học vụ',
      route: '/academic-tree',
      description: 'Cấu trúc cây Khoa → Lớp → Sinh viên. Mỗi nút (Department, Class, Student) là một component có thể có con; TreeNode và app-tree-node render đệ quy. Dữ liệu từ API academic-tree trả về cấu trúc lồng nhau; một cách duyệt thống nhất áp dụng cho toàn bộ cây.'
    },
    {
      name: 'Visitor',
      page: 'Xuất báo cáo',
      route: '/reports/export',
      description: 'Xuất báo cáo cây Khoa–Lớp–Sinh viên ra CSV. ExportVisitor (CsvExportVisitor) duyệt từng loại nút (department, class, student) và thu thập dòng CSV. Dữ liệu từ API department-tree; visitor visit từng dòng và build kết quả. Tách logic xuất file khỏi cấu trúc dữ liệu.'
    },
    {
      name: 'Decorator',
      page: 'Gọi API sinh viên (ẩn)',
      route: 'Dùng trong toàn bộ feature Students',
      description: 'LoggingStudentServiceDecorator bọc StudentService thật. Mỗi lần gọi create, update, getById... decorator ghi log (console hoặc logger) rồi ủy quyền cho service gốc. Ứng dụng inject decorator thay vì service gốc để có logging mà không sửa code StudentServiceImpl.'
    },
    {
      name: 'Proxy',
      page: 'Gọi API sinh viên (ẩn)',
      route: 'Form và danh sách sinh viên',
      description: 'StudentServiceProxy implement cùng interface với StudentService, giữ tham chiếu tới service thật. getById(id) kiểm tra cache (Map); nếu đã có thì trả về, không thì gọi service thật và lưu vào cache. Các lệnh create/update/delete xóa cache để đảm bảo dữ liệu mới nhất. Giảm số lần gọi API trùng lặp.'
    },
    {
      name: 'Factory',
      page: 'Form thêm/sửa sinh viên',
      route: '/students/new, /students/:id/edit',
      description: 'StudentCommandFactory tạo đối tượng lệnh từ giá trị form: createFromFormValue(formValue) → CreateStudentCommand, createUpdateCommand(id, formValue) → UpdateStudentCommand. Form lấy form.value → factory tạo command → executor thực thi. Tập trung logic tạo command tại một nơi, dễ bảo trì và mở rộng.'
    }
  ];
}
