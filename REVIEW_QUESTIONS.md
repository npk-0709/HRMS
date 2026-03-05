# 📋 HRMS - CÂU HỎI REVIEW CODE & GỢI Ý MỞ RỘNG

> Tài liệu dành cho sinh viên chuẩn bị bảo vệ dự án PRO192 - Human Resource Management System.
> Bao gồm: Câu hỏi giảng viên có thể hỏi, đáp án gợi ý, và các tính năng mở rộng.

---

## 📌 MỤC LỤC

- [PHẦN 1: Câu hỏi về kiến thức OOP cơ bản](#phần-1-câu-hỏi-về-kiến-thức-oop-cơ-bản)
- [PHẦN 2: Câu hỏi về cấu trúc dự án & thiết kế](#phần-2-câu-hỏi-về-cấu-trúc-dự-án--thiết-kế)
- [PHẦN 3: Câu hỏi về xử lý dữ liệu & File I/O](#phần-3-câu-hỏi-về-xử-lý-dữ-liệu--file-io)
- [PHẦN 4: Câu hỏi về Logic nghiệp vụ](#phần-4-câu-hỏi-về-logic-nghiệp-vụ)
- [PHẦN 5: Câu hỏi về Validation & Exception Handling](#phần-5-câu-hỏi-về-validation--exception-handling)
- [PHẦN 6: Câu hỏi về Collection Framework](#phần-6-câu-hỏi-về-collection-framework)
- [PHẦN 7: Câu hỏi tình huống / Edge cases](#phần-7-câu-hỏi-tình-huống--edge-cases)
- [PHẦN 8: Câu hỏi nâng cao & mở rộng kiến thức](#phần-8-câu-hỏi-nâng-cao--mở-rộng-kiến-thức)
- [PHẦN 9: Gợi ý tính năng mở rộng](#phần-9-gợi-ý-tính-năng-mở-rộng)

---

## PHẦN 1: Câu hỏi về kiến thức OOP cơ bản

### Câu 1: Class `Employee` được khai báo là `abstract`. Tại sao em lại chọn `abstract` thay vì class thường?

**Trả lời:**
- `Employee` là lớp cha đại diện cho khái niệm chung "nhân viên", không nên tạo object trực tiếp từ nó vì nhân viên luôn thuộc một loại cụ thể (Full-time hoặc Part-time).
- Phương thức `calculateSalary()` được khai báo `abstract` vì mỗi loại nhân viên có cách tính lương khác nhau (OT_RATE khác nhau: Full-time = 80,000, Part-time = 50,000).
- Điều này buộc các lớp con (`FullTimeEmployee`, `PartTimeEmployee`) **phải override** phương thức này → đảm bảo tính đúng đắn.

---

### Câu 2: Giải thích mối quan hệ kế thừa (inheritance) trong dự án. Lớp con kế thừa những gì từ lớp cha?

**Trả lời:**
- `FullTimeEmployee extends Employee` và `PartTimeEmployee extends Employee`.
- Lớp con kế thừa tất cả **thuộc tính** (id, name, department, jobTitle, type, dateOfJoining, basicSalary, active) và **phương thức** (getter, setter, toString(), toFileString()) từ lớp cha.
- Lớp con **override** phương thức `calculateSalary()` để triển khai logic tính lương riêng.
- Constructor của lớp con gọi `super(...)` để truyền tham số cho constructor lớp cha.

---

### Câu 3: Tại sao các thuộc tính trong class `Employee` đều là `private`? Nếu đổi thành `public` thì sao?

**Trả lời:**
- Đây là nguyên tắc **Encapsulation** (đóng gói) trong OOP.
- `private` đảm bảo dữ liệu chỉ được truy cập/sửa đổi thông qua getter/setter → kiểm soát được giá trị (ví dụ: có thể thêm validation trong setter).
- Nếu đổi thành `public`, bất kỳ class nào cũng có thể trực tiếp sửa đổi thuộc tính mà không qua kiểm tra → dễ gây ra lỗi logic (ví dụ: đặt salary = -1000).

---

### Câu 4: Đa hình (Polymorphism) thể hiện ở đâu trong dự án?

**Trả lời:**
- **Đa hình** thể hiện qua phương thức `calculateSalary()`:
  - Biến kiểu `Employee` có thể tham chiếu đến `FullTimeEmployee` hoặc `PartTimeEmployee`.
  - Khi gọi `emp.calculateSalary(...)`, Java sẽ tự động gọi đúng phiên bản override tương ứng → **Runtime Polymorphism** (Dynamic Binding).
- Ví dụ trong `SalaryManager.calculateSalary()`:
  ```java
  Employee emp = employeeManager.findById(empId); // có thể là FullTime hoặc PartTime
  double totalSalary = emp.calculateSalary(workingDays, overtimeHours, absenceDays);
  // → gọi đúng phiên bản của lớp con
  ```

---

### Câu 5: Từ khóa `super` được sử dụng ở đâu và có tác dụng gì?

**Trả lời:**
- Sử dụng trong constructor của `FullTimeEmployee` và `PartTimeEmployee`:
  ```java
  super(id, name, department, jobTitle, "Full-time", dateOfJoining, basicSalary);
  ```
- `super(...)` gọi constructor của lớp cha `Employee` để khởi tạo các thuộc tính chung.
- Nếu không gọi `super(...)`, Java sẽ tự động gọi `super()` (constructor không tham số), nhưng vì `Employee` có constructor có tham số nên phải gọi rõ ràng.

---

### Câu 6: `@Override` có tác dụng gì? Nếu bỏ annotation này thì chương trình có chạy được không?

**Trả lời:**
- `@Override` là annotation báo cho compiler biết phương thức này đang ghi đè phương thức của lớp cha.
- Nếu bỏ `@Override`, chương trình vẫn chạy bình thường.
- Tuy nhiên, nếu viết sai tên phương thức (ví dụ: `calculateSalery` thay vì `calculateSalary`), khi có `@Override` thì compiler sẽ báo lỗi ngay → giúp phát hiện lỗi sớm. Nếu không có `@Override`, compiler sẽ coi đó là phương thức mới → bug khó phát hiện.

---

### Câu 7: Em có thể giải thích sự khác biệt giữa `abstract class` và `interface` không? Tại sao ở đây em chọn `abstract class`?

**Trả lời:**
- **Abstract class**: có thể có thuộc tính, constructor, phương thức đã implement và phương thức abstract. Dùng khi các lớp con chia sẻ trạng thái (state) và hành vi (behavior) chung.
- **Interface**: chỉ có hằng số và phương thức abstract (trước Java 8). Dùng để định nghĩa "contract" (khả năng) mà class phải tuân theo.
- Ở đây chọn `abstract class` vì `FullTimeEmployee` và `PartTimeEmployee` chia sẻ nhiều thuộc tính chung (id, name, department...) và hành vi chung (toString, toFileString) → abstract class phù hợp hơn.

---

### Câu 8: Trong lớp `Employee`, phương thức `toString()` và `toFileString()` khác nhau thế nào? Tại sao cần cả hai?

**Trả lời:**
- `toString()`: format hiển thị đẹp trên console cho người dùng đọc, sử dụng `String.format()` với căn lề cố định.
- `toFileString()`: format lưu file với ký tự phân cách `|`, dễ đọc lại (parse) khi load.
- Cần cả hai vì:
  - Hiển thị console cần dễ đọc, đẹp mắt.
  - Lưu file cần format đơn giản, dễ split/parse khi load lại.

---

## PHẦN 2: Câu hỏi về cấu trúc dự án & thiết kế

### Câu 9: Em tổ chức package như thế nào? Tại sao lại chia thành `model`, `manager`, `util`?

**Trả lời:**
- **`model`**: chứa các class đại diện cho dữ liệu (Employee, FullTimeEmployee, PartTimeEmployee, Attendance) → tầng Data/Entity.
- **`manager`**: chứa các class xử lý logic nghiệp vụ (EmployeeManager, AttendanceManager, SalaryManager, ReportManager) → tầng Business Logic.
- **`util`**: chứa class tiện ích dùng chung (Validator) → tầng Utility.
- Việc phân chia này giúp code có tổ chức, dễ bảo trì, dễ mở rộng, và tuân theo nguyên tắc **Separation of Concerns**.

---

### Câu 10: Tại sao `SalaryManager` và `ReportManager` không có `saveToFile()`/`loadFromFile()` riêng?

**Trả lời:**
- Salary không phải là dữ liệu cần lưu trữ lâu dài — nó được **tính toán** dựa trên `Employee` (basicSalary) và `Attendance` (workingDays, overtimeHours, absenceDays).
- Mỗi lần cần xem lương, hệ thống tính lại từ dữ liệu gốc → đảm bảo **tính nhất quán** (consistency).
- Report cũng tương tự — là kết quả tổng hợp, không cần lưu riêng.

---

### Câu 11: Tại sao `AttendanceManager` cần tham chiếu đến `EmployeeManager`?

**Trả lời:**
- Khi ghi chấm công, cần kiểm tra xem Employee ID có tồn tại hay không (`employeeManager.findById(empId)`).
- Nếu không kiểm tra, người dùng có thể ghi chấm công cho nhân viên không tồn tại → dữ liệu rác.
- Đây là quan hệ **dependency** (phụ thuộc), được inject qua constructor:
  ```java
  public AttendanceManager(EmployeeManager employeeManager) {
      this.employeeManager = employeeManager;
  }
  ```

---

### Câu 12: Giải thích flow khi người dùng chọn "Calculate Salary" từ Main Menu?

**Trả lời:**
1. `Main.main()` → user chọn 3 → `salaryManager.showMenu(sc)`.
2. User chọn 1 → `salaryManager.calculateSalary(sc)`.
3. Nhập Employee ID → gọi `employeeManager.findById(empId)` để tìm nhân viên.
4. Kiểm tra `emp.isActive()` → chỉ tính lương cho nhân viên đang hoạt động.
5. Nhập month, year → gọi `attendanceManager.getByEmployeeAndMonth(empId, month, year)` để lấy danh sách chấm công.
6. Duyệt danh sách, đếm workingDays, overtimeHours, absenceDays.
7. Gọi `emp.calculateSalary(workingDays, overtimeHours, absenceDays)` → polymorphism gọi đúng phiên bản.
8. Hiển thị kết quả.

---

## PHẦN 3: Câu hỏi về xử lý dữ liệu & File I/O

### Câu 13: Em dùng cách nào để đọc/ghi file? Giải thích `BufferedWriter` và `BufferedReader`.

**Trả lời:**
- Sử dụng `BufferedWriter` (bọc `FileWriter`) để ghi file và `BufferedReader` (bọc `FileReader`) để đọc file.
- **Buffer** là vùng đệm — thay vì ghi/đọc từng ký tự, nó gom lại thành block lớn rồi ghi/đọc một lần → **hiệu suất cao hơn** so với dùng trực tiếp `FileWriter`/`FileReader`.
- Sử dụng **try-with-resources** (`try (BufferedWriter bw = ...)`) để tự động đóng stream khi hoàn thành hoặc khi xảy ra exception → tránh resource leak.

---

### Câu 14: Tại sao em chọn ký tự `|` làm delimiter thay vì `,` hoặc tab?

**Trả lời:**
- Ký tự `,` có thể xuất hiện trong tên hoặc số tiền (ví dụ: "5,000,000") → dễ gây lỗi khi parse.
- Tab (`\t`) không nhìn thấy bằng mắt thường khi mở file text → khó debug.
- `|` ít xuất hiện trong dữ liệu thông thường → an toàn hơn làm delimiter.
- Tuy nhiên, nếu muốn chặt chẽ hơn, có thể dùng CSV library hoặc JSON format.

---

### Câu 15: Nếu file `employees.txt` bị hỏng (thiếu cột, sai format), chương trình xử lý thế nào?

**Trả lời:**
- Trong `loadFromFile()`, có kiểm tra `if (parts.length < 8) continue;` → bỏ qua dòng thiếu cột.
- Có `try-catch NumberFormatException` → bắt lỗi khi parse salary hoặc boolean.
- Tuy nhiên, **hạn chế**: nếu một dòng bị lỗi, chương trình in thông báo lỗi và dừng đọc các dòng còn lại (vì catch nằm ngoài vòng while) → nên cải thiện bằng cách đặt try-catch bên trong vòng while để tiếp tục đọc các dòng khác.

---

### Câu 16: Khi nào thì file được lưu? Nếu chương trình bị tắt đột ngột thì sao?

**Trả lời:**
- File được lưu tại 3 thời điểm:
  1. Sau khi **Add** employee → `saveToFile()`.
  2. Sau khi **Update** employee → `saveToFile()`.
  3. Sau khi **Remove** employee → `saveToFile()`.
  4. Khi chọn **Exit** từ Main Menu → `saveToFile()`.
  5. Tương tự cho Attendance (record, update).
- Nếu bị tắt đột ngột: dữ liệu trong RAM (ArrayList) sẽ mất. Tuy nhiên, vì mỗi thao tác CRUD đều save ngay → chỉ mất thao tác cuối cùng nếu nó đang thực hiện giữa chừng.

---

### Câu 17: `try-with-resources` là gì? Tại sao em dùng nó?

**Trả lời:**
- Là cú pháp Java 7+ cho phép khai báo resource (ví dụ: stream, connection) trong ngoặc `try(...)`.
- Resource sẽ được **tự động đóng** (gọi `.close()`) khi kết thúc block try, kể cả khi có exception.
- Nếu không dùng, phải viết `finally { bw.close(); }` thủ công → dài dòng và dễ quên.
- Tất cả resource implement interface `AutoCloseable` đều dùng được.

---

### Câu 18: Em dùng đường dẫn tương đối (`"employees.txt"`) hay tuyệt đối? Ưu nhược điểm?

**Trả lời:**
- Dùng **đường dẫn tương đối** (`"employees.txt"`) → file nằm ở thư mục hiện tại khi chạy chương trình.
- **Ưu điểm**: đơn giản, portable (chạy được trên máy khác mà không cần sửa path).
- **Nhược điểm**: phụ thuộc vào working directory khi chạy → nếu chạy từ thư mục khác, file sẽ ở vị trí khác. Trong IDE thường là root project, nhưng khi chạy jar có thể khác.

---

## PHẦN 4: Câu hỏi về Logic nghiệp vụ

### Câu 19: Công thức tính lương Full-time và Part-time khác nhau thế nào?

**Trả lời:**
- **Full-time**: `totalSalary = basicSalary + (overtimeHours × 80,000) - (absenceDays × 100,000)`
- **Part-time**: `totalSalary = basicSalary + (overtimeHours × 50,000) - (absenceDays × 100,000)`
- Khác nhau ở **OT_RATE**: Full-time = 80,000 VND/giờ, Part-time = 50,000 VND/giờ.
- ABSENCE_DEDUCTION giống nhau: 100,000 VND/ngày.
- Cả hai đều dùng `Math.max(totalSalary, 0)` để đảm bảo lương không âm.

---

### Câu 20: Tại sao cần `Math.max(totalSalary, 0)` trong `calculateSalary()`?

**Trả lời:**
- Nếu nhân viên vắng nhiều ngày, `deduction` có thể lớn hơn `basicSalary + overtimePay` → tổng lương ra số âm.
- Lương âm không hợp lý trong thực tế → dùng `Math.max(totalSalary, 0)` để đảm bảo lương tối thiểu là 0.
- Ví dụ: basicSalary = 5,000,000, absenceDays = 60 → deduction = 6,000,000 → totalSalary = -1,000,000 → trả về 0.

---

### Câu 21: Tại sao khi status là "Absent" hoặc "Leave" thì overtimeHours = 0?

**Trả lời:**
- Trong `recordAttendance()`:
  ```java
  int overtimeHours = 0;
  if (status.equals("Present")) {
      overtimeHours = Validator.readNonNegativeInt(sc, "Overtime Hours: ");
  }
  ```
- Chỉ khi nhân viên **có mặt** (Present) mới có thể có overtime.
- Nếu **vắng** (Absent) hoặc **nghỉ phép** (Leave), nhân viên không đi làm → không thể có overtime → mặc định 0.

---

### Câu 22: "Low Attendance Report" hoạt động thế nào? Ngưỡng 3 ngày có ý nghĩa gì?

**Trả lời:**
- `LOW_ATTENDANCE_THRESHOLD = 3`: nhân viên nào vắng **hơn 3 ngày** trong tháng sẽ bị liệt kê.
- Quy trình: duyệt tất cả nhân viên → lấy attendance theo tháng/năm → đếm số ngày Absent → nếu > 3 thì hiển thị.
- Ngưỡng 3 là giá trị mặc định, trong thực tế có thể cấu hình được.
- **Lưu ý**: chỉ đếm "Absent", không đếm "Leave" (nghỉ phép có phép).

---

### Câu 23: "Highest Paid Employees" xử lý trường hợp có nhiều người cùng lương cao nhất thế nào?

**Trả lời:**
- Dùng biến `maxSalary` và `List<String> results`:
  - Nếu `totalSalary > maxSalary`: cập nhật maxSalary, xóa list cũ, thêm nhân viên mới.
  - Nếu `totalSalary == maxSalary`: thêm nhân viên vào list (trường hợp nhiều người cùng lương cao nhất).
- → Có thể hiển thị **nhiều** nhân viên nếu họ có cùng tổng lương cao nhất.

---

### Câu 24: Nếu nhân viên bị đánh dấu "Inactive", có ảnh hưởng gì đến hệ thống?

**Trả lời:**
- **Tính lương** (`calculateSalary`): kiểm tra `emp.isActive()`, nếu inactive → từ chối tính, hiển thị lỗi.
- **Salary Report**: chỉ liệt kê nhân viên `isActive() == true`.
- **Highest Paid**: chỉ xét nhân viên active.
- **Chấm công**: hiện tại KHÔNG kiểm tra active → vẫn có thể ghi chấm công cho nhân viên inactive → đây là **bug tiềm ẩn** cần sửa.
- **View, Search**: vẫn hiển thị nhân viên inactive (để tra cứu lịch sử).

---

## PHẦN 5: Câu hỏi về Validation & Exception Handling

### Câu 25: Class `Validator` có vai trò gì? Tại sao không validate trực tiếp trong Manager?

**Trả lời:**
- `Validator` tập trung tất cả logic validate input vào một nơi → **tái sử dụng** (reusability).
- Nhiều Manager đều cần đọc số, đọc chuỗi, đọc ngày → nếu viết lại mỗi lần sẽ **trùng lặp code** (DRY violation).
- Khi cần sửa logic validate (ví dụ: thay đổi format ngày), chỉ cần sửa ở 1 chỗ.
- Các phương thức đều là `static` vì chỉ là utility, không cần tạo object.

---

### Câu 26: Giải thích cách validate ngày `dd/MM/yyyy` trong `isValidDate()`.

**Trả lời:**
1. Kiểm tra format bằng regex: `\\d{2}/\\d{2}/\\d{4}` (2 chữ số / 2 chữ số / 4 chữ số).
2. Split ra lấy day, month, year.
3. Kiểm tra year: 1900 ≤ year ≤ 2100.
4. Kiểm tra month: 1 ≤ month ≤ 12.
5. Dùng mảng `daysInMonth` để kiểm tra ngày hợp lệ cho từng tháng.
6. Xử lý **năm nhuận**: nếu `(year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)` thì tháng 2 có 29 ngày.
7. Kiểm tra: 1 ≤ day ≤ daysInMonth[month].

---

### Câu 27: Chương trình xử lý gì khi người dùng nhập sai kiểu dữ liệu (ví dụ nhập chữ khi yêu cầu số)?

**Trả lời:**
- Tất cả input đều đọc bằng `sc.nextLine()` → luôn nhận được String → **không bị InputMismatchException**.
- Sau đó parse thủ công (`Integer.parseInt()`, `Double.parseDouble()`) trong try-catch.
- Nếu parse lỗi → hiển thị thông báo lỗi và yêu cầu nhập lại (vòng while).
- Ví dụ trong `readPositiveDouble()`:
  ```java
  try {
      double value = Double.parseDouble(input.replace(",", ""));
  } catch (NumberFormatException e) {
      System.out.println("Error: Invalid number format. Please try again.");
  }
  ```

---

### Câu 28: Tại sao dùng `sc.nextLine()` thay vì `sc.nextInt()` hoặc `sc.nextDouble()`?

**Trả lời:**
- `sc.nextInt()` / `sc.nextDouble()` **không đọc ký tự xuống dòng** → khi gọi `sc.nextLine()` sau đó, nó sẽ đọc chuỗi rỗng → gây bug.
- Dùng `sc.nextLine()` đọc toàn bộ dòng rồi tự parse → tránh vấn đề trên.
- Thống nhất dùng `nextLine()` cho mọi input → code nhất quán, dễ bảo trì.

---

### Câu 29: Nếu file `employees.txt` không tồn tại khi chương trình khởi động, điều gì xảy ra?

**Trả lời:**
- Trong `loadFromFile()`:
  ```java
  File file = new File(FILE_NAME);
  if (!file.exists()) {
      return; // không làm gì, danh sách rỗng
  }
  ```
- Chương trình vẫn chạy bình thường với danh sách nhân viên rỗng.
- Khi thêm nhân viên đầu tiên và save, file sẽ được tạo tự động bởi `FileWriter`.

---

### Câu 30: Có trường hợp nào `NumberFormatException` xảy ra trong `loadFromFile()` không?

**Trả lời:**
- Có, nếu file bị sửa tay và cột salary hoặc active không đúng format:
  - `Double.parseDouble(parts[6])` → lỗi nếu parts[6] không phải số (ví dụ: "abc").
  - `Boolean.parseBoolean(parts[7])` → **không** throw exception (trả về false nếu không phải "true") → đây là điểm cần lưu ý.
- Trong attendance: `Integer.parseInt(parts[3])` → lỗi nếu overtime hours không phải số.

---

## PHẦN 6: Câu hỏi về Collection Framework

### Câu 31: Tại sao em dùng `ArrayList` thay vì `LinkedList` hoặc array thường?

**Trả lời:**
- **ArrayList**: truy cập theo index O(1), thêm/xóa cuối O(1) amortized. Phù hợp khi cần duyệt danh sách nhiều (hiển thị, tìm kiếm).
- **LinkedList**: thêm/xóa đầu/giữa O(1), nhưng truy cập index O(n). Không cần thiết ở đây.
- **Array thường**: kích thước cố định, phải quản lý resize thủ công → bất tiện.
- Dự án chủ yếu **duyệt** và **tìm kiếm** → ArrayList là lựa chọn phù hợp nhất.

---

### Câu 32: `findById()` có độ phức tạp thời gian bao nhiêu? Có cách nào tối ưu hơn?

**Trả lời:**
- Hiện tại: duyệt tuần tự (linear search) → **O(n)** với n là số nhân viên.
- Tối ưu:
  - Dùng **HashMap<String, Employee>** với key là employeeId → truy cập **O(1)**.
  - Dùng **TreeMap** nếu cần sắp xếp theo ID → O(log n).
- Với quy mô dự án nhỏ (vài trăm nhân viên), O(n) là chấp nhận được.

---

### Câu 33: Tại sao `searchEmployees()` dùng `List<Employee> results` thay vì in trực tiếp?

**Trả lời:**
- Thu thập kết quả vào list trước, sau đó kiểm tra list rỗng hay không → hiển thị message phù hợp ("No employees found" hoặc bảng kết quả).
- Nếu in trực tiếp trong vòng lặp, khó biết trước có kết quả hay không → phải dùng biến flag → code kém sạch hơn.
- List kết quả cũng có thể tái sử dụng cho mục đích khác (ví dụ: export, đếm số lượng).

---

### Câu 34: Nếu danh sách nhân viên có 10,000 người, hiệu năng tìm kiếm có bị ảnh hưởng không?

**Trả lời:**
- Có, vì tất cả tìm kiếm đều là **linear search O(n)**:
  - `findById()`: duyệt toàn bộ list.
  - `searchEmployees()`: duyệt toàn bộ + so sánh chuỗi.
  - `getByEmployeeAndMonth()`: duyệt toàn bộ attendance list.
- Cải thiện:
  - Dùng `HashMap` cho tìm kiếm theo ID.
  - Dùng index phụ (Map<String, List<Attendance>>) cho attendance theo employee.
  - Nhưng với 10,000 records, Java vẫn xử lý nhanh (< 1 giây) → premature optimization.

---

## PHẦN 7: Câu hỏi tình huống / Edge cases

### Câu 35: Nếu nhập Employee ID trùng khi thêm nhân viên, chương trình xử lý thế nào?

**Trả lời:**
- Trong `addEmployee()`:
  ```java
  while (true) {
      id = Validator.readNonEmptyString(sc, "Employee ID: ");
      if (!isIdExist(id)) break;
      System.out.println("Error: Employee ID '" + id + "' already exists.");
  }
  ```
- Yêu cầu nhập lại cho đến khi ID không trùng → **vòng lặp vô hạn** đến khi hợp lệ.

---

### Câu 36: Nếu ghi chấm công cho cùng nhân viên, cùng ngày hai lần thì sao?

**Trả lời:**
- Kiểm tra trùng lặp bằng `isDuplicateAttendance(empId, date)`:
  ```java
  if (isDuplicateAttendance(empId, date)) {
      System.out.println("Error: Attendance already exists.");
      return;
  }
  ```
- Từ chối ghi và hiển thị thông báo lỗi. Người dùng cần dùng chức năng **Update Attendance** thay thế.

---

### Câu 37: Nếu xóa nhân viên nhưng dữ liệu chấm công của nhân viên đó vẫn còn trong file?

**Trả lời:**
- Đây là **vấn đề hiện tại** (data inconsistency): khi `removeEmployee()`, chỉ xóa khỏi `employees` list, **không xóa** attendance records liên quan.
- Hậu quả: file `attendance.txt` vẫn chứa records của nhân viên đã xóa → "orphaned data".
- **Cách sửa**: khi xóa nhân viên, cũng xóa tất cả attendance records của nhân viên đó, hoặc dùng soft delete (đánh dấu inactive thay vì xóa hẳn).

---

### Câu 38: Nếu chương trình chạy mà `employees.txt` có dòng trống hoặc dòng sai format?

**Trả lời:**
- Dòng trống: `if (line.isEmpty()) continue;` → bỏ qua.
- Dòng thiếu cột: `if (parts.length < 8) continue;` → bỏ qua.
- Cột salary sai format: catch `NumberFormatException` → in lỗi.
- **Hạn chế**: nếu 1 dòng gây NumberFormatException, các dòng sau sẽ không được đọc (catch nằm ngoài while loop). Nên đặt try-catch bên trong vòng while.

---

### Câu 39: Nhân viên mới vào chưa có dữ liệu chấm công, tính lương sẽ ra sao?

**Trả lời:**
- `getByEmployeeAndMonth()` trả về list rỗng → workingDays = 0, overtimeHours = 0, absenceDays = 0.
- `calculateSalary(0, 0, 0)` = basicSalary + 0 - 0 = **basicSalary**.
- Nhân viên chưa chấm công vẫn nhận đủ lương cơ bản → **logic chưa chính xác** trong thực tế (lương thường tính theo ngày công thực tế).

---

### Câu 40: Nếu người dùng nhập year = 99999 hoặc year = -1 thì sao?

**Trả lời:**
- Year được đọc bằng `readPositiveInt()` → phải > 0 → year = -1 bị từ chối.
- Year = 99999 → hợp lệ với `readPositiveInt()`, nhưng khi tìm attendance sẽ không có data → hiển thị kết quả rỗng.
- Trong `readDate()` → `isValidDate()` giới hạn year 1900-2100, nhưng khi nhập year cho salary/report thì **không có giới hạn**.

---

### Câu 41: So sánh Employee ID dùng `equalsIgnoreCase()`. Tại sao?

**Trả lời:**
- Để tìm kiếm **không phân biệt hoa thường**: nhập "emp001" hoặc "EMP001" đều tìm được.
- Tăng trải nghiệm người dùng, tránh trường hợp không tìm thấy vì nhập sai chữ hoa/thường.
- Tuy nhiên, điều này cũng có thể gây nhầm lẫn nếu "emp001" và "EMP001" được coi là cùng ID khi thêm mới.

---

### Câu 42: Khi update employee, nếu nhập salary = "abc" thì sao?

**Trả lời:**
- Trong `updateEmployee()`:
  ```java
  try {
      double salary = Double.parseDouble(newSalary.replace(",", ""));
      if (salary > 0) emp.setBasicSalary(salary);
      else System.out.println("Warning: Invalid salary value, salary not updated.");
  } catch (NumberFormatException e) {
      System.out.println("Warning: Invalid salary format, salary not updated.");
  }
  ```
- Bắt exception, hiển thị warning, **không cập nhật** salary → các trường khác vẫn được cập nhật bình thường.
- Khác với `addEmployee()` dùng `readPositiveDouble()` (bắt nhập lại) → ở đây cho phép skip.

---

### Câu 43: Chuyện gì xảy ra nếu hai instance của chương trình chạy cùng lúc và cùng ghi file?

**Trả lời:**
- **Race condition**: hai process cùng đọc file, sửa dữ liệu, rồi ghi đè → dữ liệu của process ghi sau sẽ mất dữ liệu của process ghi trước.
- Chương trình hiện tại **không xử lý** trường hợp này (không có file locking).
- Giải pháp: dùng `FileLock` từ `java.nio.channels`, hoặc dùng database thay vì file.

---

## PHẦN 8: Câu hỏi nâng cao & mở rộng kiến thức

### Câu 44: Em biết gì về design pattern? Dự án có áp dụng pattern nào không?

**Trả lời:**
- Dự án **chưa áp dụng** design pattern rõ ràng, nhưng có một số nguyên tắc:
  - **Separation of Concerns**: tách model, manager, util.
  - **Single Responsibility**: mỗi Manager chịu trách nhiệm một domain.
- Có thể áp dụng thêm:
  - **Singleton** cho `EmployeeManager` (đảm bảo chỉ có 1 instance).
  - **Factory Pattern** cho việc tạo Employee (thay vì if-else trong `addEmployee`).
  - **Observer Pattern** để notify khi dữ liệu thay đổi.
  - **Strategy Pattern** cho các cách tính lương khác nhau.

---

### Câu 45: Nếu cần thêm loại nhân viên mới (ví dụ: Intern, Contractor), em phải sửa những gì?

**Trả lời:**
1. Tạo class mới `InternEmployee extends Employee`, override `calculateSalary()`.
2. Sửa `Validator.readEmployeeType()` thêm option mới.
3. Sửa `EmployeeManager.addEmployee()` thêm case tạo InternEmployee.
4. Sửa `EmployeeManager.loadFromFile()` thêm case parse InternEmployee.
5. Sửa `SalaryManager.viewSalaryDetails()` nếu cần hiển thị OT rate khác.
- **Nhận xét**: phải sửa nhiều chỗ → vi phạm **Open/Closed Principle**. Có thể cải thiện bằng Factory Pattern.

---

### Câu 46: Sự khác biệt giữa `==` và `.equals()` khi so sánh String? Em dùng cái nào?

**Trả lời:**
- `==`: so sánh **tham chiếu** (reference) → hai biến có trỏ đến cùng object trong bộ nhớ không.
- `.equals()`: so sánh **nội dung** (value) → hai chuỗi có cùng ký tự không.
- Trong dự án, dùng `.equals()` và `.equalsIgnoreCase()` để so sánh nội dung String → **đúng**.
- Ví dụ: `att.getStatus().equals("Present")` → so sánh giá trị, không phải tham chiếu.

---

### Câu 47: `static` trong `Validator` có ý nghĩa gì? Khi nào nên dùng `static`?

**Trả lời:**
- `static` method thuộc về **class**, không thuộc về instance → gọi trực tiếp `Validator.readDate(...)` mà không cần tạo object.
- Phù hợp cho **utility methods** không cần trạng thái (state) của object.
- Tương tự: `Math.max()`, `Integer.parseInt()` cũng là static.
- **Khi nên dùng**: method không sử dụng thuộc tính instance, chỉ dùng tham số đầu vào.
- `OT_RATE`, `ABSENCE_DEDUCTION` cũng là `static final` → hằng số thuộc class, giống nhau cho mọi instance.

---

### Câu 48: Nếu dự án lớn hơn, em sẽ thay thế file text bằng gì?

**Trả lời:**
- **Database**: SQLite (nhẹ, không cần server), MySQL/PostgreSQL (quy mô lớn).
- **Ưu điểm của DB**:
  - Query nhanh hơn (index, SQL).
  - Hỗ trợ concurrent access (file locking tự động).
  - ACID transactions (đảm bảo tính toàn vẹn dữ liệu).
  - Quan hệ giữa các bảng (Foreign Key).
- **Nếu vẫn dùng file**: chuyển sang JSON hoặc XML → dễ đọc/ghi hơn, có thể dùng thư viện Gson/Jackson.

---

### Câu 49: Em hiểu gì về Java Generics? Có dùng trong dự án không?

**Trả lời:**
- **Generics** cho phép viết code tổng quát, hoạt động với nhiều kiểu dữ liệu.
- Trong dự án: `ArrayList<Employee>`, `ArrayList<Attendance>`, `List<String>` → sử dụng Generics.
- Nếu không dùng Generics: `ArrayList` chứa `Object` → phải cast thủ công → dễ gặp `ClassCastException` tại runtime.
- Generics giúp **type-safe** tại compile time.

---

### Câu 50: Giải thích sự khác biệt giữa `ArrayList` và `List` trong khai báo?

**Trả lời:**
- `List<Employee>` là **interface**, `ArrayList<Employee>` là **implementation**.
- Best practice: khai báo biến kiểu `List` (interface), khởi tạo bằng `ArrayList`:
  ```java
  List<Employee> employees = new ArrayList<>();
  ```
- Trong dự án: dùng cả hai cách → `ArrayList<Employee> employees` (cụ thể) và `List<Attendance> result` (tổng quát).
- Dùng interface giúp dễ thay đổi implementation sau này (ví dụ: đổi sang `LinkedList` chỉ cần sửa 1 chỗ).

---

### Câu 51: Giải thích `String.format()` và các format specifier em dùng trong `toString()`.

**Trả lời:**
- `String.format("%-6s %-20s ...", id, name, ...)`:
  - `%s`: String.
  - `%-6s`: String căn trái, tối thiểu 6 ký tự.
  - `%-20s`: String căn trái, tối thiểu 20 ký tự.
  - `%,15.0f`: số thực, dùng dấu phẩy phân cách hàng nghìn, 15 ký tự, 0 chữ số thập phân.
  - `%d`: số nguyên.
  - `%n`: xuống dòng (platform-independent).
- Format specifier giúp tạo bảng hiển thị đẹp, cột thẳng hàng.

---

### Câu 52: Scanner `sc` được truyền qua nhiều method. Tại sao không tạo Scanner mới trong mỗi method?

**Trả lời:**
- `Scanner` wrapping `System.in` → nếu tạo nhiều Scanner cho `System.in`, chúng sẽ **tranh chấp** đọc input → lỗi.
- Nếu close một Scanner wrapping `System.in`, nó sẽ **đóng luôn `System.in`** → các Scanner khác không đọc được nữa.
- Vì vậy chỉ nên tạo **1 Scanner** duy nhất và truyền qua tham số → đảm bảo nhất quán.
- Scanner được tạo ở `Main.main()` và close ở đó khi thoát chương trình.

---

## PHẦN 9: Gợi ý tính năng mở rộng

### 🔹 Mức 1: Mở rộng cơ bản (Dễ)

| # | Tính năng | Mô tả |
|---|-----------|-------|
| 1 | **Sắp xếp danh sách nhân viên** | Sắp xếp theo tên, lương, ngày vào làm. Dùng `Collections.sort()` với `Comparator`. |
| 2 | **Tìm kiếm theo ID** | Thêm option tìm kiếm theo Employee ID trong `searchEmployees()`. |
| 3 | **Đếm số nhân viên theo phòng ban** | Thống kê: "IT: 5, HR: 3, Sales: 8". |
| 4 | **Validate Employee ID format** | Bắt buộc ID theo pattern (ví dụ: EMP001, EMP002). |
| 5 | **Kiểm tra active khi chấm công** | Từ chối ghi chấm công cho nhân viên inactive. |
| 6 | **Xóa attendance khi xóa nhân viên** | Dọn dẹp dữ liệu liên quan khi remove employee. |
| 7 | **Hiển thị tổng số nhân viên** | Thêm dòng "Total: X employees (Y active, Z inactive)" ở cuối danh sách. |

### 🔹 Mức 2: Mở rộng trung bình (Trung bình)

| # | Tính năng | Mô tả |
|---|-----------|-------|
| 8 | **Thêm loại nhân viên Intern** | Tạo class `InternEmployee` với công thức lương riêng (ví dụ: không có OT, lương cố định). |
| 9 | **Quản lý nghỉ phép (Leave Management)** | Mỗi nhân viên có số ngày phép tối đa/năm. Kiểm tra khi ghi "Leave". Cảnh báo khi hết phép. |
| 10 | **Xuất báo cáo ra file** | Export salary report, attendance report ra file `.txt` hoặc `.csv`. |
| 11 | **Tìm kiếm attendance theo khoảng thời gian** | Xem chấm công từ ngày A đến ngày B (không chỉ theo tháng). |
| 12 | **Phân trang danh sách** | Khi có nhiều nhân viên, hiển thị 10 người/trang, cho phép next/prev. |
| 13 | **Thêm trường email, phone** | Mở rộng model Employee thêm thông tin liên lạc, validate email/phone format. |
| 14 | **Tính lương theo ngày công thực tế** | Thay vì `basicSalary` cố định, tính: `(basicSalary / 26) × workingDays + OT - deduction`. |
| 15 | **Undo/Redo thao tác** | Lưu lịch sử thay đổi, cho phép hoàn tác thao tác vừa thực hiện. Dùng Stack. |
| 16 | **Ghi log hoạt động** | Tạo file `log.txt` ghi lại mọi thao tác: ai thêm, xóa, sửa lúc mấy giờ. |

### 🔹 Mức 3: Mở rộng nâng cao (Khó)

| # | Tính năng | Mô tả |
|---|-----------|-------|
| 17 | **Hệ thống đăng nhập (Login)** | Thêm tài khoản Admin/HR. Chỉ Admin mới được xóa nhân viên, sửa lương. |
| 18 | **Mã hóa dữ liệu file** | Mã hóa file employees.txt để bảo vệ thông tin nhạy cảm (salary). |
| 19 | **Chuyển sang dùng Database** | Dùng SQLite/JDBC thay file text. Tạo bảng employees, attendance. |
| 20 | **GUI (Graphical User Interface)** | Dùng JavaFX hoặc Swing tạo giao diện đồ họa thay vì console. |
| 21 | **Export PDF/Excel** | Dùng thư viện Apache POI (Excel) hoặc iText (PDF) để xuất báo cáo. |
| 22 | **Unit Testing** | Viết JUnit test cho `calculateSalary()`, `isValidDate()`, `findById()` để đảm bảo chất lượng. |
| 23 | **Multi-language support** | Hỗ trợ hiển thị tiếng Việt/tiếng Anh, dùng ResourceBundle. |
| 24 | **Backup & Restore** | Tự động backup file trước khi ghi đè. Cho phép restore từ backup. |

---

## 📝 TỔNG HỢP CÁC LỖI/ĐIỂM YẾU CẦN BIẾT

| # | Vấn đề | File | Mức độ |
|---|--------|------|--------|
| 1 | Xóa nhân viên không xóa attendance liên quan | `EmployeeManager.removeEmployee()` | 🔴 Quan trọng |
| 2 | Có thể ghi chấm công cho nhân viên inactive | `AttendanceManager.recordAttendance()` | 🔴 Quan trọng |
| 3 | `loadFromFile()` - NumberFormatException dừng đọc toàn bộ | `EmployeeManager.loadFromFile()` | 🟡 Trung bình |
| 4 | Nhân viên chưa chấm công vẫn nhận đủ basicSalary | `calculateSalary()` logic | 🟡 Trung bình |
| 5 | Year khi tính lương/report không có giới hạn | `SalaryManager`, `ReportManager` | 🟢 Nhẹ |
| 6 | So sánh `totalSalary == maxSalary` dùng `==` cho double | `ReportManager.highestPaidReport()` | 🟡 Trung bình |
| 7 | `SalaryManager.viewSalaryDetails()` hardcode OT rate | `SalaryManager` dòng 113 | 🟡 Trung bình |
| 8 | Không có confirm trước khi Exit (lỡ bấm nhầm) | `Main.java` | 🟢 Nhẹ |
| 9 | Khai báo `ArrayList<Employee>` thay vì `List<Employee>` | `EmployeeManager` | 🟢 Nhẹ (style) |
| 10 | Không xử lý file concurrent access | Toàn dự án | 🟢 Nhẹ (quy mô nhỏ) |

---

## 💡 MẸO KHI BẢO VỆ DỰ ÁN

1. **Hiểu rõ flow chương trình**: Vẽ được sơ đồ từ Main → Menu → Chức năng → Kết quả.
2. **Giải thích được TẠI SAO**, không chỉ NHƯ THẾ NÀO: Tại sao dùng abstract? Tại sao dùng ArrayList?
3. **Thừa nhận hạn chế**: Biết điểm yếu và có giải pháp cải thiện thể hiện hiểu biết sâu.
4. **Demo trường hợp lỗi**: Chủ động demo nhập sai → chương trình xử lý đúng → ấn tượng tốt.
5. **Biết mở rộng**: Nếu giảng viên hỏi "thêm tính năng X thì làm sao?", trả lời được ngay.

---

> 📅 Cập nhật lần cuối: Tháng 3/2026
