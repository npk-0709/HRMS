# 📘 HƯỚNG DẪN DỰ ÁN HRMS — Human Resource Management System

> **Môn học:** PRO192 — Object-Oriented Programming  
> **Ngôn ngữ:** Java (Console Application)  
> **IDE khuyến nghị:** IntelliJ IDEA

---

## 📑 MỤC LỤC

1. [Tổng quan dự án](#1-tổng-quan-dự-án)
2. [Cấu trúc thư mục](#2-cấu-trúc-thư-mục)
3. [Hướng dẫn chạy dự án](#3-hướng-dẫn-chạy-dự-án)
4. [Hướng dẫn sử dụng chương trình](#4-hướng-dẫn-sử-dụng-chương-trình)
5. [Giải thích chi tiết từng Package / Class / Method](#5-giải-thích-chi-tiết-từng-package--class--method)
   - [5.1 Package `model` — Lớp dữ liệu](#51-package-model--lớp-dữ-liệu)
   - [5.2 Package `util` — Tiện ích xử lý input](#52-package-util--tiện-ích-xử-lý-input)
   - [5.3 Package `manager` — Xử lý logic nghiệp vụ](#53-package-manager--xử-lý-logic-nghiệp-vụ)
   - [5.4 Class `Main` — Điểm vào chương trình](#54-class-main--điểm-vào-chương-trình)
6. [Dữ liệu lưu trữ (File I/O)](#6-dữ-liệu-lưu-trữ-file-io)
7. [Business Rules (BR)](#7-business-rules-br)
8. [Các khái niệm OOP được sử dụng](#8-các-khái-niệm-oop-được-sử-dụng)

---

## 1. Tổng quan dự án

HRMS là hệ thống **Quản lý nhân sự** chạy trên console, cho phép:

- **Quản lý nhân viên:** Thêm, sửa, xóa, xem danh sách, tìm kiếm
- **Quản lý chấm công:** Ghi nhận, cập nhật, xem lịch sử, tổng hợp theo tháng
- **Quản lý lương:** Tính lương, xem chi tiết lương, xuất báo cáo lương
- **Báo cáo:** Nhân viên nghỉ nhiều, nhân viên lương cao nhất

Dữ liệu được lưu vào file `.txt` và tự động load lại khi khởi chạy chương trình.

---

## 2. Cấu trúc thư mục

```
HRMS/
├── Main.iml                    ← File cấu hình IntelliJ IDEA
├── HUONG_DAN_DU_AN.md          ← File hướng dẫn này
├── employees.txt               ← File lưu dữ liệu nhân viên (tự tạo khi chạy)
├── attendance.txt              ← File lưu dữ liệu chấm công (tự tạo khi chạy)
└── src/                        ← Thư mục mã nguồn
    ├── Main.java               ← Điểm vào chương trình (hàm main)
    ├── model/                  ← Package chứa các lớp dữ liệu (Data Model)
    │   ├── Employee.java       ← Lớp trừu tượng (abstract) — lớp cha
    │   ├── FullTimeEmployee.java  ← Nhân viên toàn thời gian — kế thừa Employee
    │   ├── PartTimeEmployee.java  ← Nhân viên bán thời gian — kế thừa Employee
    │   └── Attendance.java     ← Bản ghi chấm công
    ├── manager/                ← Package chứa các lớp xử lý nghiệp vụ
    │   ├── EmployeeManager.java   ← Quản lý CRUD nhân viên + File I/O
    │   ├── AttendanceManager.java ← Quản lý chấm công + File I/O
    │   ├── SalaryManager.java     ← Tính lương + báo cáo lương
    │   └── ReportManager.java     ← Báo cáo nghỉ nhiều + lương cao nhất
    └── util/                   ← Package chứa lớp tiện ích
        └── Validator.java      ← Xử lý & validate tất cả input từ người dùng
```

**Tổng cộng: 9 file Java**

---

## 3. Hướng dẫn chạy dự án

### Cách 1: Chạy bằng IntelliJ IDEA (Khuyến nghị)

1. Mở IntelliJ IDEA
2. Chọn **File → Open** → chọn thư mục `HRMS`
3. Đợi IntelliJ load project xong (nhìn thanh loading phía dưới)
4. Mở file `src/Main.java`
5. Nhấn nút ▶ **Run** (biểu tượng tam giác xanh) bên cạnh `public static void main`
6. Chương trình sẽ chạy trong cửa sổ **Run** phía dưới

> **Lưu ý:** Nếu IntelliJ báo lỗi không tìm thấy JDK, vào **File → Project Structure → SDK** và chọn JDK đã cài (JDK 8 trở lên).

### Cách 2: Chạy bằng Command Line

```bash
# Bước 1: Mở terminal, di chuyển đến thư mục dự án
cd C:\Users\Khuong\Desktop\FPTU\PRO192\Project\HRMS

# Bước 2: Compile tất cả file Java
javac -d out src\Main.java src\model\*.java src\manager\*.java src\util\*.java

# Bước 3: Chạy chương trình
java -cp out Main
```

### Yêu cầu hệ thống
- **JDK:** 8 trở lên (đã cài `java` và `javac` trong PATH)
- **Hệ điều hành:** Windows / macOS / Linux

---

## 4. Hướng dẫn sử dụng chương trình

### 4.1 Menu chính

Khi chạy chương trình, menu chính hiển thị:

```
======================================
    HUMAN RESOURCE MANAGEMENT
======================================
1. Manage Employees
2. Attendance Management
3. Salary Management
4. Reports
5. Exit
--------------------------------------
Choose an option:
```

Nhập số **1–5** để chọn chức năng.

### 4.2 Quản lý nhân viên (Menu 1)

| Chức năng | Mô tả |
|---|---|
| **1. Add Employee** | Thêm nhân viên mới. Nhập: ID (duy nhất), tên, phòng ban, chức vụ, loại (Full-time/Part-time), ngày vào làm, lương cơ bản. Xác nhận [1] Save hoặc [2] Cancel |
| **2. Update Employee** | Sửa thông tin nhân viên theo ID. Để trống trường nào thì trường đó không đổi. ID không thể sửa |
| **3. Remove Employee** | Xóa nhân viên theo ID. Xác nhận y/n |
| **4. View All Employees** | Hiển thị bảng toàn bộ nhân viên |
| **5. Search Employees** | Tìm kiếm theo tên / phòng ban / chức vụ |

**Ví dụ thêm nhân viên:**
```
Employee ID: E01
Full Name: Nguyen Van An
Department: IT
Job Title: Developer
Type (Full-time/Part-time): Full-time
Date of Joining (dd/MM/yyyy): 15/01/2024
Basic Salary: 15000000
[1] Save  [2] Cancel: 1
Employee added successfully.
```

### 4.3 Quản lý chấm công (Menu 2)

| Chức năng | Mô tả |
|---|---|
| **1. Record Attendance** | Ghi chấm công: nhập ID nhân viên, ngày, trạng thái (Present/Absent/Leave), giờ OT (nếu Present) |
| **2. Update Attendance** | Sửa bản ghi chấm công theo ID + ngày |
| **3. View Attendance History** | Xem toàn bộ lịch sử chấm công của 1 nhân viên |
| **4. View Working Summary** | Tổng hợp ngày làm việc / nghỉ / OT theo tháng |

**Ví dụ ghi chấm công:**
```
Employee ID: E01
Date (dd/MM/yyyy): 01/02/2026
Status (Present/Absent/Leave): Present
Overtime Hours: 2
Attendance recorded successfully.
```

### 4.4 Quản lý lương (Menu 3)

| Chức năng | Mô tả |
|---|---|
| **1. Calculate Salary** | Tính lương 1 nhân viên theo tháng/năm |
| **2. View Salary Details** | Xem bảng chi tiết lương (lương cơ bản, OT, khấu trừ, tổng) |
| **3. Generate Salary Report** | Bảng lương toàn bộ nhân viên active trong tháng |

**Ví dụ xem chi tiết lương:**
```
============ SALARY DETAILS ============
Employee: Nguyen Van An (E01)
Type: Full-time
Period: 2/2026
----------------------------------------
Working Days:     20
Absence Days:     2
Leave Days:       1
Overtime Hours:   10
----------------------------------------
Basic Salary:        15,000,000 VND
Overtime Pay:   +       800,000 VND
Deduction:      -       200,000 VND
----------------------------------------
TOTAL SALARY:        15,600,000 VND
========================================
```

### 4.5 Báo cáo (Menu 4)

| Chức năng | Mô tả |
|---|---|
| **1. Low Attendance Report** | Liệt kê nhân viên nghỉ > 3 ngày Absent trong tháng |
| **2. Highest Paid Employees** | Nhân viên có lương cao nhất trong tháng |

### 4.6 Thoát (Menu 5)

Chọn **5** ở menu chính → dữ liệu tự động lưu vào file → thoát chương trình.

---

## 5. Giải thích chi tiết từng Package / Class / Method

---

### 5.1 Package `model` — Lớp dữ liệu

Package `model` chứa các lớp đại diện cho **đối tượng dữ liệu** trong hệ thống. Đây là nơi áp dụng **Encapsulation** (đóng gói), **Inheritance** (kế thừa), **Polymorphism** (đa hình) và **Abstraction** (trừu tượng hóa).

---

#### 📄 `Employee.java` — Lớp trừu tượng (Abstract Class)

**Vai trò:** Lớp **cha** chung cho tất cả nhân viên. Không thể tạo đối tượng trực tiếp (`new Employee()` sẽ lỗi). Các lớp con bắt buộc phải override phương thức `calculateSalary()`.

**Thuộc tính (fields) — tất cả `private`:**

| Thuộc tính | Kiểu | Mô tả |
|---|---|---|
| `id` | `String` | Mã nhân viên (VD: "E01"). Duy nhất, không trùng |
| `name` | `String` | Họ tên đầy đủ |
| `department` | `String` | Phòng ban (VD: "IT", "HR") |
| `jobTitle` | `String` | Chức vụ (VD: "Developer", "Manager") |
| `type` | `String` | Loại nhân viên: "Full-time" hoặc "Part-time" |
| `dateOfJoining` | `String` | Ngày vào làm, định dạng `dd/MM/yyyy` |
| `basicSalary` | `double` | Lương cơ bản (VND) |
| `active` | `boolean` | Trạng thái: `true` = đang làm, `false` = đã nghỉ |

**Constructors:**

| Constructor | Mô tả |
|---|---|
| `Employee()` | Constructor mặc định. Đặt `active = true` |
| `Employee(id, name, department, jobTitle, type, dateOfJoining, basicSalary)` | Constructor đầy đủ. Gán tất cả thuộc tính, `active = true` |

**Methods:**

| Method | Kiểu trả về | Mô tả |
|---|---|---|
| `calculateSalary(int workingDays, int overtimeHours, int absenceDays)` | `double` | **Abstract method** — bắt buộc lớp con phải override. Tính tổng lương dựa trên ngày làm, giờ OT, ngày nghỉ |
| `getId()` / `setId(String)` | `String` / `void` | Getter/Setter cho `id` |
| `getName()` / `setName(String)` | `String` / `void` | Getter/Setter cho `name` |
| `getDepartment()` / `setDepartment(String)` | `String` / `void` | Getter/Setter cho `department` |
| `getJobTitle()` / `setJobTitle(String)` | `String` / `void` | Getter/Setter cho `jobTitle` |
| `getType()` / `setType(String)` | `String` / `void` | Getter/Setter cho `type` |
| `getDateOfJoining()` / `setDateOfJoining(String)` | `String` / `void` | Getter/Setter cho `dateOfJoining` |
| `getBasicSalary()` / `setBasicSalary(double)` | `double` / `void` | Getter/Setter cho `basicSalary` |
| `isActive()` / `setActive(boolean)` | `boolean` / `void` | Getter/Setter cho `active` |
| `toString()` | `String` | Override từ `Object`. Trả về chuỗi định dạng bảng: `ID  Name  Dept  Job  Type  Date  Salary  Status` |
| `toFileString()` | `String` | Chuyển đối tượng thành chuỗi dạng `id|name|dept|job|type|date|salary|active` để lưu file |

**Tại sao dùng `abstract`?**
- Vì mỗi loại nhân viên (Full-time, Part-time) tính lương **khác nhau** → cần override riêng
- Không cho phép tạo nhân viên chung chung, bắt buộc phải là 1 loại cụ thể

---

#### 📄 `FullTimeEmployee.java` — Nhân viên toàn thời gian

**Vai trò:** Kế thừa từ `Employee`. Đại diện cho nhân viên **Full-time**.

**Hằng số (constants):**

| Hằng | Giá trị | Mô tả |
|---|---|---|
| `OT_RATE` | `80,000` | Phụ cấp overtime: 80,000 VND/giờ (BR8) |
| `ABSENCE_DEDUCTION` | `100,000` | Khấu trừ nghỉ: 100,000 VND/ngày (BR9) |

**Constructors:**

| Constructor | Mô tả |
|---|---|
| `FullTimeEmployee()` | Constructor mặc định, gọi `super()` |
| `FullTimeEmployee(id, name, department, jobTitle, dateOfJoining, basicSalary)` | Gọi `super(...)` với `type = "Full-time"` |

**Methods:**

| Method | Mô tả |
|---|---|
| `calculateSalary(workingDays, overtimeHours, absenceDays)` | **Override.** Công thức: `Lương = Lương cơ bản + (OT giờ × 80,000) - (Ngày nghỉ × 100,000)`. Nếu âm → trả 0 |

**Ví dụ tính:**
```
Lương cơ bản: 15,000,000
OT: 10 giờ → 10 × 80,000 = 800,000
Nghỉ: 2 ngày → 2 × 100,000 = 200,000
→ Tổng = 15,000,000 + 800,000 - 200,000 = 15,600,000 VND
```

---

#### 📄 `PartTimeEmployee.java` — Nhân viên bán thời gian

**Vai trò:** Kế thừa từ `Employee`. Đại diện cho nhân viên **Part-time**.

**Hằng số:**

| Hằng | Giá trị | Mô tả |
|---|---|---|
| `OT_RATE` | `50,000` | Phụ cấp overtime: 50,000 VND/giờ (BR8) |
| `ABSENCE_DEDUCTION` | `100,000` | Khấu trừ nghỉ: 100,000 VND/ngày (BR9) |

**Constructors:** Giống `FullTimeEmployee`, nhưng `type = "Part-time"`.

**Methods:**

| Method | Mô tả |
|---|---|
| `calculateSalary(workingDays, overtimeHours, absenceDays)` | **Override.** Công thức: `Lương = Lương cơ bản + (OT giờ × 50,000) - (Ngày nghỉ × 100,000)`. Nếu âm → trả 0 |

> **💡 Đây là Polymorphism:** Cùng gọi `emp.calculateSalary(...)` nhưng kết quả khác nhau tùy vào đối tượng thực tế là `FullTimeEmployee` hay `PartTimeEmployee`.

---

#### 📄 `Attendance.java` — Bản ghi chấm công

**Vai trò:** Lưu thông tin **1 lần chấm công** của 1 nhân viên trong 1 ngày.

**Thuộc tính:**

| Thuộc tính | Kiểu | Mô tả |
|---|---|---|
| `employeeId` | `String` | Mã nhân viên (liên kết với `Employee.id`) |
| `date` | `String` | Ngày chấm công, định dạng `dd/MM/yyyy` |
| `status` | `String` | Trạng thái: `"Present"`, `"Absent"`, hoặc `"Leave"` (BR5) |
| `overtimeHours` | `int` | Số giờ tăng ca (chỉ có khi status = Present) |

**Constructors:**

| Constructor | Mô tả |
|---|---|
| `Attendance()` | Constructor mặc định (rỗng) |
| `Attendance(employeeId, date, status, overtimeHours)` | Constructor đầy đủ |

**Methods:**

| Method | Kiểu trả về | Mô tả |
|---|---|---|
| `getEmployeeId()` / `setEmployeeId(String)` | `String` / `void` | Getter/Setter |
| `getDate()` / `setDate(String)` | `String` / `void` | Getter/Setter |
| `getStatus()` / `setStatus(String)` | `String` / `void` | Getter/Setter |
| `getOvertimeHours()` / `setOvertimeHours(int)` | `int` / `void` | Getter/Setter |
| `toString()` | `String` | Hiển thị: `Date  Status  OvertimeHours` |
| `toFileString()` | `String` | Chuỗi `employeeId|date|status|overtimeHours` để lưu file |

---

### 5.2 Package `util` — Tiện ích xử lý input

#### 📄 `Validator.java` — Lớp tiện ích validate input

**Vai trò:** Chứa toàn bộ **static methods** để đọc và validate input từ người dùng. Tất cả method đều dùng vòng lặp `while(true)` + `try-catch` để **yêu cầu nhập lại** nếu sai (BR11).

**Tại sao tách riêng?** Để tránh lặp code validate ở nhiều nơi. Tất cả Manager class đều gọi `Validator.readXxx()`.

**Danh sách methods:**

| Method | Kiểu trả về | Mô tả |
|---|---|---|
| `readNonEmptyString(Scanner sc, String prompt)` | `String` | Đọc chuỗi **không được rỗng**. Loop cho đến khi user nhập ít nhất 1 ký tự |
| `readOptionalString(Scanner sc, String prompt)` | `String` | Đọc chuỗi **có thể rỗng** (dùng khi update — để trống = bỏ qua) |
| `readPositiveDouble(Scanner sc, String prompt)` | `double` | Đọc số thực **> 0** (dùng cho lương). Hỗ trợ dấu phẩy `15,000,000` |
| `readNonNegativeInt(Scanner sc, String prompt)` | `int` | Đọc số nguyên **≥ 0** (dùng cho giờ OT) |
| `readPositiveInt(Scanner sc, String prompt)` | `int` | Đọc số nguyên **> 0** (dùng cho năm) |
| `readMenuChoice(Scanner sc, String prompt, int min, int max)` | `int` | Đọc lựa chọn menu trong khoảng `[min, max]` |
| `readDate(Scanner sc, String prompt)` | `String` | Đọc ngày định dạng `dd/MM/yyyy`. Validate đúng ngày/tháng/năm, năm nhuận |
| `isValidDate(String date)` | `boolean` | Kiểm tra chuỗi có phải ngày hợp lệ. Check regex `\\d{2}/\\d{2}/\\d{4}`, check năm nhuận |
| `readAttendanceStatus(Scanner sc, String prompt)` | `String` | Đọc trạng thái chấm công. Chỉ chấp nhận `Present`/`Absent`/`Leave` (BR5) |
| `readEmployeeType(Scanner sc, String prompt)` | `String` | Đọc loại nhân viên. Chấp nhận `Full-time`/`Part-time` (cả `Fulltime`/`Parttime`) |
| `readSaveOrCancel(Scanner sc)` | `boolean` | Hiển thị `[1] Save  [2] Cancel`. Trả `true` nếu chọn 1 |
| `readUpdateOrCancel(Scanner sc)` | `boolean` | Hiển thị `[1] Update  [2] Cancel`. Trả `true` nếu chọn 1 |
| `pressEnterToContinue(Scanner sc)` | `void` | Hiển thị `Press ENTER to return...` và đợi user nhấn Enter |
| `getMonthFromDate(String date)` | `int` | Trích xuất tháng từ chuỗi `dd/MM/yyyy`. VD: `"15/02/2026"` → `2` |
| `getYearFromDate(String date)` | `int` | Trích xuất năm từ chuỗi `dd/MM/yyyy`. VD: `"15/02/2026"` → `2026` |

**Chi tiết `isValidDate()`:**
1. Check regex `\\d{2}/\\d{2}/\\d{4}` (2 số / 2 số / 4 số)
2. Tách lấy `day`, `month`, `year`
3. Check `year` trong khoảng 1900–2100
4. Check `month` trong khoảng 1–12
5. Tạo mảng `daysInMonth` = `{0, 31, 28, 31, ...}` (index 0 bỏ trống)
6. Nếu năm nhuận → `daysInMonth[2] = 29`
7. Check `day` trong khoảng `[1, daysInMonth[month]]`

---

### 5.3 Package `manager` — Xử lý logic nghiệp vụ

Package `manager` chứa các lớp xử lý **logic nghiệp vụ chính**: CRUD, tính toán, lưu/đọc file, hiển thị menu.

---

#### 📄 `EmployeeManager.java` — Quản lý nhân viên

**Vai trò:** Quản lý danh sách nhân viên (thêm, sửa, xóa, tìm kiếm) và lưu/đọc file `employees.txt`.

**Thuộc tính:**

| Thuộc tính | Kiểu | Mô tả |
|---|---|---|
| `employees` | `ArrayList<Employee>` | Danh sách nhân viên trong bộ nhớ |
| `FILE_NAME` | `String` (static final) | Tên file lưu trữ: `"employees.txt"` |

**Constructor:**

| Constructor | Mô tả |
|---|---|
| `EmployeeManager()` | Khởi tạo `ArrayList` rỗng → gọi `loadFromFile()` để đọc dữ liệu từ file |

**Methods — Truy vấn:**

| Method | Mô tả |
|---|---|
| `getEmployees()` | Trả về toàn bộ danh sách `ArrayList<Employee>` |
| `findById(String id)` | Duyệt danh sách, so sánh `equalsIgnoreCase`. Trả `Employee` nếu tìm thấy, `null` nếu không |
| `isIdExist(String id)` | Gọi `findById()` → trả `true` nếu khác `null` |

**Methods — CRUD:**

| Method | Mô tả |
|---|---|
| `addEmployee(Scanner sc)` | **Thêm nhân viên.** Flow: Nhập ID (loop cho đến khi unique) → nhập name, dept, job, type, date, salary → xác nhận Save/Cancel → tạo `FullTimeEmployee` hoặc `PartTimeEmployee` tùy type → add vào list → `saveToFile()` |
| `updateEmployee(Scanner sc)` | **Sửa nhân viên.** Flow: Nhập ID → hiển thị thông tin hiện tại → nhập từng trường mới (để trống = skip) → xác nhận Update/Cancel → cập nhật các trường không rỗng → `saveToFile()`. **ID không thể sửa** (BR1) |
| `removeEmployee(Scanner sc)` | **Xóa nhân viên.** Flow: Nhập ID → hiển thị tên → xác nhận y/n → `employees.remove(emp)` → `saveToFile()` |
| `viewAllEmployees(Scanner sc)` | **Xem tất cả.** In header bảng → duyệt list → gọi `emp.toString()` cho mỗi nhân viên |
| `searchEmployees(Scanner sc)` | **Tìm kiếm.** Chọn tiêu chí (Name/Dept/Job) → nhập keyword → duyệt list → `contains()` (không phân biệt hoa thường) → hiển thị kết quả |

**Methods — File I/O:**

| Method | Mô tả |
|---|---|
| `saveToFile()` | Dùng `BufferedWriter` + `FileWriter`. Duyệt list → gọi `emp.toFileString()` → ghi từng dòng. Try-catch `IOException` |
| `loadFromFile()` | Dùng `BufferedReader` + `FileReader`. Đọc từng dòng → `split("\\|")` → tạo `FullTimeEmployee` hoặc `PartTimeEmployee` tùy `type` → add vào list. Try-catch `IOException` và `NumberFormatException` |

**Methods — Menu:**

| Method | Mô tả |
|---|---|
| `showMenu(Scanner sc)` | Vòng lặp `do-while`: hiển thị 6 lựa chọn → switch-case gọi method tương ứng → thoát khi chọn 6 |

---

#### 📄 `AttendanceManager.java` — Quản lý chấm công

**Vai trò:** Quản lý bản ghi chấm công và lưu/đọc file `attendance.txt`.

**Thuộc tính:**

| Thuộc tính | Kiểu | Mô tả |
|---|---|---|
| `attendanceList` | `ArrayList<Attendance>` | Danh sách bản ghi chấm công |
| `employeeManager` | `EmployeeManager` | Tham chiếu đến EmployeeManager để kiểm tra nhân viên tồn tại |
| `FILE_NAME` | `String` (static final) | Tên file: `"attendance.txt"` |

**Constructor:**

| Constructor | Mô tả |
|---|---|
| `AttendanceManager(EmployeeManager employeeManager)` | Nhận `EmployeeManager` → khởi tạo list → `loadFromFile()` |

**Methods — Truy vấn:**

| Method | Mô tả |
|---|---|
| `getAttendanceList()` | Trả về toàn bộ list |
| `isDuplicateAttendance(String empId, String date)` | **Private.** Kiểm tra đã có record cho nhân viên + ngày đó chưa (BR4) |
| `findAttendance(String empId, String date)` | **Private.** Tìm 1 record cụ thể theo ID + ngày |
| `getByEmployeeId(String empId)` | Trả về tất cả record của 1 nhân viên |
| `getByEmployeeAndMonth(String empId, int month, int year)` | Trả về record của nhân viên trong 1 tháng/năm cụ thể. Dùng `Validator.getMonthFromDate()` và `getYearFromDate()` |

**Methods — CRUD:**

| Method | Mô tả |
|---|---|
| `recordAttendance(Scanner sc)` | **Ghi chấm công.** Flow: Nhập empId → kiểm tra nhân viên tồn tại (BR3) → nhập date → kiểm tra trùng (BR4) → nhập status (BR5) → nếu Present thì nhập OT → tạo `Attendance` → add → `saveToFile()` |
| `updateAttendance(Scanner sc)` | **Sửa chấm công.** Flow: Nhập empId + date → tìm record → hiển thị hiện tại → nhập status mới + OT mới → xác nhận Update/Cancel → `saveToFile()` |
| `viewAttendanceHistory(Scanner sc)` | **Xem lịch sử.** Nhập empId → gọi `getByEmployeeId()` → hiển thị bảng |
| `viewWorkingSummary(Scanner sc)` | **Tổng hợp theo tháng.** Nhập empId + tháng/năm → duyệt record → đếm workingDays, absenceDays, leaveDays, totalOT |

**Methods — File I/O:** Giống `EmployeeManager` nhưng lưu/đọc `Attendance`.

**Methods — Menu:**

| Method | Mô tả |
|---|---|
| `showMenu(Scanner sc)` | 5 lựa chọn: Record / Update / View History / Working Summary / Back |

---

#### 📄 `SalaryManager.java` — Quản lý lương

**Vai trò:** Tính lương và tạo báo cáo lương. **Không lưu file riêng** — tính lương dựa trên dữ liệu Employee + Attendance.

**Thuộc tính:**

| Thuộc tính | Kiểu | Mô tả |
|---|---|---|
| `employeeManager` | `EmployeeManager` | Lấy thông tin nhân viên |
| `attendanceManager` | `AttendanceManager` | Lấy dữ liệu chấm công |

**Constructor:**

| Constructor | Mô tả |
|---|---|
| `SalaryManager(EmployeeManager, AttendanceManager)` | Nhận 2 manager |

**Methods:**

| Method | Mô tả |
|---|---|
| `calculateSalary(Scanner sc)` | **Tính lương.** Flow: Nhập empId → kiểm tra active (BR10) → nhập tháng/năm → lấy attendance → đếm workingDays, OT, absence → gọi `emp.calculateSalary(...)` (**đa hình** — tự gọi đúng method của FullTime hoặc PartTime) → hiển thị kết quả |
| `viewSalaryDetails(Scanner sc)` | **Chi tiết lương.** Giống `calculateSalary` nhưng hiển thị dạng bảng chi tiết: Basic + OT Pay - Deduction = Total. Tính `otRate` dựa trên `emp.getType()` |
| `generateSalaryReport(Scanner sc)` | **Báo cáo toàn bộ.** Nhập tháng/năm → duyệt **tất cả nhân viên active** → tính lương từng người → in bảng tổng hợp: ID, Name, Type, WorkDays, OT, Abs, TotalSalary |

**Methods — Menu:**

| Method | Mô tả |
|---|---|
| `showMenu(Scanner sc)` | 4 lựa chọn: Calculate / Details / Report / Back |

---

#### 📄 `ReportManager.java` — Báo cáo

**Vai trò:** Tạo báo cáo thống kê: nhân viên nghỉ nhiều, nhân viên lương cao nhất.

**Thuộc tính:**

| Thuộc tính | Kiểu | Mô tả |
|---|---|---|
| `employeeManager` | `EmployeeManager` | Lấy thông tin nhân viên |
| `attendanceManager` | `AttendanceManager` | Lấy dữ liệu chấm công |
| `LOW_ATTENDANCE_THRESHOLD` | `int` (static final) | Ngưỡng nghỉ: **3 ngày** (BR12) |

**Constructor:**

| Constructor | Mô tả |
|---|---|
| `ReportManager(EmployeeManager, AttendanceManager)` | Nhận 2 manager |

**Methods:**

| Method | Mô tả |
|---|---|
| `lowAttendanceReport(Scanner sc)` | **BR12: Nhân viên nghỉ nhiều.** Nhập tháng/năm → duyệt tất cả nhân viên → đếm ngày `"Absent"` → nếu `> 3` thì hiển thị |
| `highestPaidReport(Scanner sc)` | **BR13: Lương cao nhất.** Nhập tháng/năm → duyệt nhân viên active → tính lương → so sánh tìm max. Hỗ trợ **nhiều người cùng lương cao nhất** (dùng `List<String>` lưu kết quả, clear khi tìm thấy max mới) |

**Methods — Menu:**

| Method | Mô tả |
|---|---|
| `showMenu(Scanner sc)` | 3 lựa chọn: Low Attendance / Highest Paid / Back |

---

### 5.4 Class `Main` — Điểm vào chương trình

#### 📄 `Main.java`

**Vai trò:** Chứa hàm `main()` — điểm bắt đầu chạy chương trình. Khởi tạo tất cả Manager và hiển thị menu chính.

**Flow thực thi:**

```
1. Tạo Scanner sc
2. Tạo EmployeeManager      → load employees.txt
3. Tạo AttendanceManager     → load attendance.txt (cần EmployeeManager)
4. Tạo SalaryManager         → cần EmployeeManager + AttendanceManager
5. Tạo ReportManager         → cần EmployeeManager + AttendanceManager
6. Vòng lặp do-while:
   - Hiển thị menu chính (5 lựa chọn)
   - switch-case:
     - 1 → employeeManager.showMenu(sc)
     - 2 → attendanceManager.showMenu(sc)
     - 3 → salaryManager.showMenu(sc)
     - 4 → reportManager.showMenu(sc)
     - 5 → saveToFile() cả 2 manager → in "Data saved." → thoát
7. sc.close()
```

**Thứ tự khởi tạo quan trọng:**
- `EmployeeManager` phải tạo **trước** vì `AttendanceManager` cần nó để kiểm tra nhân viên tồn tại
- `SalaryManager` và `ReportManager` cần **cả hai** manager trên

---

## 6. Dữ liệu lưu trữ (File I/O)

### `employees.txt` — Dữ liệu nhân viên

Mỗi dòng = 1 nhân viên, các trường phân tách bằng `|`:

```
E01|Nguyen Van An|IT|Developer|Full-time|15/01/2024|1.5E7|true
E02|Tran Thi Bao|HR|Manager|Part-time|20/03/2023|1.2E7|true
```

**Format:** `id|name|department|jobTitle|type|dateOfJoining|basicSalary|active`

> `1.5E7` = `15,000,000` (Java tự chuyển double sang dạng khoa học khi lưu)

### `attendance.txt` — Dữ liệu chấm công

Mỗi dòng = 1 bản ghi chấm công:

```
E01|01/02/2026|Present|2
E01|02/02/2026|Absent|0
E02|01/02/2026|Leave|0
```

**Format:** `employeeId|date|status|overtimeHours`

### Cơ chế lưu/đọc

- **Lưu:** Dùng `BufferedWriter` → `FileWriter` → ghi từng dòng bằng `toFileString()`
- **Đọc:** Dùng `BufferedReader` → `FileReader` → đọc từng dòng → `split("\\|")` → tạo object
- **Thời điểm lưu:** Sau mỗi thao tác thêm/sửa/xóa + khi thoát chương trình
- **Thời điểm đọc:** Khi khởi tạo Manager (trong constructor)
- File tự tạo khi lần đầu lưu, không cần tạo tay

---

## 7. Business Rules (BR)

| Rule | Mô tả | Nơi implement |
|---|---|---|
| **BR1** | Employee ID phải **duy nhất**, không thể sửa | `EmployeeManager.addEmployee()`, `updateEmployee()` |
| **BR2** | Name và Department **không được rỗng** | `Validator.readNonEmptyString()` |
| **BR3** | Employee phải **tồn tại** trước khi ghi chấm công | `AttendanceManager.recordAttendance()` |
| **BR4** | Mỗi nhân viên mỗi ngày chỉ **1 bản ghi** chấm công | `AttendanceManager.isDuplicateAttendance()` |
| **BR5** | Trạng thái chấm công chỉ: **Present / Absent / Leave** | `Validator.readAttendanceStatus()` |
| **BR7** | Lương = Basic + OT Pay - Deduction | `calculateSalary()` ở FullTime/PartTime |
| **BR8** | OT: Full-time **80,000 VND/h**, Part-time **50,000 VND/h** | Hằng `OT_RATE` ở mỗi class |
| **BR9** | Khấu trừ nghỉ: **100,000 VND/ngày** | Hằng `ABSENCE_DEDUCTION` |
| **BR10** | Chỉ tính lương cho nhân viên **Active** | `SalaryManager.calculateSalary()` |
| **BR11** | **Validate** tất cả input, yêu cầu nhập lại nếu sai | Toàn bộ `Validator.java` |
| **BR12** | Báo cáo nhân viên nghỉ **> 3 ngày**/tháng | `ReportManager.lowAttendanceReport()` |
| **BR13** | Báo cáo nhân viên **lương cao nhất** | `ReportManager.highestPaidReport()` |

---

## 8. Các khái niệm OOP được sử dụng

| Khái niệm | Nơi sử dụng | Chi tiết |
|---|---|---|
| **Encapsulation** (Đóng gói) | `Employee.java`, `Attendance.java` | Tất cả thuộc tính `private`, truy cập qua `getter/setter` |
| **Inheritance** (Kế thừa) | `FullTimeEmployee extends Employee`, `PartTimeEmployee extends Employee` | Lớp con kế thừa thuộc tính + constructor từ lớp cha |
| **Polymorphism** (Đa hình) | `emp.calculateSalary(...)` trong `SalaryManager` | Cùng 1 lời gọi nhưng chạy method khác nhau tùy đối tượng thực tế |
| **Abstraction** (Trừu tượng) | `abstract class Employee`, `abstract calculateSalary()` | Định nghĩa "hợp đồng" — lớp con bắt buộc implement |
| **Collections** | `ArrayList<Employee>`, `ArrayList<Attendance>` | Lưu danh sách động, thêm/xóa/duyệt |
| **Exception Handling** | `try-catch` trong File I/O, Validator | Bắt `IOException`, `NumberFormatException`, tránh crash |
| **File I/O** | `BufferedWriter/Reader` + `FileWriter/Reader` | Đọc/ghi file text, dữ liệu persist giữa các lần chạy |

---

> **✅ Dự án đã hoàn thiện và sẵn sàng chạy. Chúc bạn demo thành công!**
