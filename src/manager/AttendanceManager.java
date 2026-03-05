package manager;

import model.Attendance;
import model.Employee;
import util.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AttendanceManager {
    private ArrayList<Attendance> attendanceList;
    private EmployeeManager employeeManager;
    private static final String FILE_NAME = "attendance.txt";

    public AttendanceManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        attendanceList = new ArrayList<>();
        loadFromFile();
    }

    public ArrayList<Attendance> getAttendanceList() {
        return attendanceList;
    }

    private boolean isDuplicateAttendance(String employeeId, String date) {
        for (Attendance att : attendanceList) {
            if (att.getEmployeeId().equalsIgnoreCase(employeeId)
                    && att.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    private Attendance findAttendance(String employeeId, String date) {
        for (Attendance att : attendanceList) {
            if (att.getEmployeeId().equalsIgnoreCase(employeeId)
                    && att.getDate().equals(date)) {
                return att;
            }
        }
        return null;
    }

    public List<Attendance> getByEmployeeId(String employeeId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance att : attendanceList) {
            if (att.getEmployeeId().equalsIgnoreCase(employeeId)) {
                result.add(att);
            }
        }
        return result;
    }

    public List<Attendance> getByEmployeeAndMonth(String employeeId, int month, int year) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance att : attendanceList) {
            if (att.getEmployeeId().equalsIgnoreCase(employeeId)) {
                int attMonth = Validator.getMonthFromDate(att.getDate());
                int attYear = Validator.getYearFromDate(att.getDate());
                if (attMonth == month && attYear == year) {
                    result.add(att);
                }
            }
        }
        return result;
    }

    public void recordAttendance(Scanner sc) {
        System.out.println("\n----------- RECORD ATTENDANCE -----------");

        String empId = Validator.readNonEmptyString(sc, "Employee ID: ");

        Employee emp = employeeManager.findById(empId);
        if (emp == null) {
            System.out.println("Error: Employee '" + empId + "' not found.");
            return;
        }

        String date = Validator.readDate(sc, "Date (dd/MM/yyyy): ");

        if (isDuplicateAttendance(empId, date)) {
            System.out.println("Error: Attendance for '" + empId + "' on " + date + " already exists.");
            return;
        }

        String status = Validator.readAttendanceStatus(sc, "Status");

        int overtimeHours = 0;
        if (status.equals("Present")) {
            overtimeHours = Validator.readNonNegativeInt(sc, "Overtime Hours: ");
        }

        Attendance att = new Attendance(empId, date, status, overtimeHours);
        attendanceList.add(att);
        saveToFile();
        System.out.println("Attendance recorded successfully.");
    }

    // ======================== Update Attendance ========================
    public void updateAttendance(Scanner sc) {
        System.out.println("\n----------- UPDATE ATTENDANCE -----------");
        String empId = Validator.readNonEmptyString(sc, "Employee ID: ");

        Employee emp = employeeManager.findById(empId);
        if (emp == null) {
            System.out.println("Error: Employee not found.");
            return;
        }

        String date = Validator.readDate(sc, "Date to update (dd/MM/yyyy): ");
        Attendance att = findAttendance(empId, date);

        if (att == null) {
            System.out.println("Error: No attendance record found for " + empId + " on " + date + ".");
            return;
        }

        System.out.println("Current: " + att.toString());

        String newStatus = Validator.readAttendanceStatus(sc, "New Status");
        int newOT = 0;
        if (newStatus.equals("Present")) {
            newOT = Validator.readNonNegativeInt(sc, "New Overtime Hours: ");
        }

        if (Validator.readUpdateOrCancel(sc)) {
            att.setStatus(newStatus);
            att.setOvertimeHours(newOT);
            saveToFile();
            System.out.println("Attendance updated successfully.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    public void viewAttendanceHistory(Scanner sc) {
        System.out.println("\n----------- ATTENDANCE HISTORY -----------");
        String empId = Validator.readNonEmptyString(sc, "Employee ID: ");

        Employee emp = employeeManager.findById(empId);
        if (emp == null) {
            System.out.println("Error: Employee not found.");
            Validator.pressEnterToContinue(sc);
            return;
        }

        List<Attendance> records = getByEmployeeId(empId);

        System.out.println("Employee: " + emp.getName() + " (" + empId + ")");
        System.out.println("-----------------------------------------");
        System.out.printf("%-12s %-10s %-10s%n", "Date", "Status", "Overtime");
        System.out.println("-----------------------------------------");

        if (records.isEmpty()) {
            System.out.println("No attendance records found.");
        } else {
            for (Attendance att : records) {
                System.out.println(att.toString());
            }
        }
        System.out.println("-----------------------------------------");
        Validator.pressEnterToContinue(sc);
    }

    public void viewWorkingSummary(Scanner sc) {
        System.out.println("\n----------- WORKING SUMMARY -----------");
        String empId = Validator.readNonEmptyString(sc, "Employee ID: ");

        Employee emp = employeeManager.findById(empId);
        if (emp == null) {
            System.out.println("Error: Employee not found.");
            Validator.pressEnterToContinue(sc);
            return;
        }

        int month = Validator.readMenuChoice(sc, "Month (1-12): ", 1, 12);
        int year = Validator.readPositiveInt(sc, "Year: ");

        List<Attendance> records = getByEmployeeAndMonth(empId, month, year);

        int workingDays = 0;
        int absenceDays = 0;
        int leaveDays = 0;
        int totalOT = 0;

        for (Attendance att : records) {
            switch (att.getStatus()) {
                case "Present":
                    workingDays++;
                    totalOT += att.getOvertimeHours();
                    break;
                case "Absent":
                    absenceDays++;
                    break;
                case "Leave":
                    leaveDays++;
                    break;
            }
        }

        System.out.println("\nSummary for " + emp.getName() + " - " + month + "/" + year);
        System.out.println("  Total Working Days: " + workingDays);
        System.out.println("  Absence Days: " + absenceDays);
        System.out.println("  Leave Days: " + leaveDays);
        System.out.println("  Total Overtime Hours: " + totalOT);
        Validator.pressEnterToContinue(sc);
    }

    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Attendance att : attendanceList) {
                bw.write(att.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                String empId = parts[0];
                String date = parts[1];
                String status = parts[2];
                int ot = Integer.parseInt(parts[3]);

                attendanceList.add(new Attendance(empId, date, status, ot));
            }
        } catch (IOException e) {
            System.out.println("Error loading attendance: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing attendance data: " + e.getMessage());
        }
    }

    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n====================================");
            System.out.println("    ATTENDANCE MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Record Attendance");
            System.out.println("2. Update Attendance");
            System.out.println("3. View Attendance History");
            System.out.println("4. View Working Summary");
            System.out.println("5. Back to Main Menu");
            System.out.println("------------------------------------");

            choice = Validator.readMenuChoice(sc, "Choose an option: ", 1, 5);

            switch (choice) {
                case 1:
                    recordAttendance(sc);
                    break;
                case 2:
                    updateAttendance(sc);
                    break;
                case 3:
                    viewAttendanceHistory(sc);
                    break;
                case 4:
                    viewWorkingSummary(sc);
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 5);
    }


}
