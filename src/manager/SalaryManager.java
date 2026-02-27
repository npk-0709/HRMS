package manager;

import model.Attendance;
import model.Employee;
import util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalaryManager {
    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;

    public SalaryManager(EmployeeManager employeeManager, AttendanceManager attendanceManager) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
    }


    public void calculateSalary(Scanner sc) {
        System.out.println("\n----------- CALCULATE SALARY -----------");
        String empId = Validator.readNonEmptyString(sc, "Employee ID: ");

        Employee emp = employeeManager.findById(empId);
        if (emp == null) {
            System.out.println("Error: Employee not found.");
            return;
        }

        if (!emp.isActive()) {
            System.out.println("Error: Cannot calculate salary for inactive employee.");
            return;
        }

        int month = Validator.readMenuChoice(sc, "Month (1-12): ", 1, 12);
        int year = Validator.readPositiveInt(sc, "Year: ");

        List<Attendance> records = attendanceManager.getByEmployeeAndMonth(empId, month, year);

        int workingDays = 0;
        int overtimeHours = 0;
        int absenceDays = 0;

        for (Attendance att : records) {
            switch (att.getStatus()) {
                case "Present":
                    workingDays++;
                    overtimeHours += att.getOvertimeHours();
                    break;
                case "Absent":
                    absenceDays++;
                    break;
            }
        }

        double totalSalary = emp.calculateSalary(workingDays, overtimeHours, absenceDays);

        System.out.println("\nSalary calculated successfully.");
        System.out.println("Employee: " + emp.getName() + " (" + empId + ") - " + emp.getType());
        System.out.println("Month/Year: " + month + "/" + year);
        System.out.println("  Total Working Days: " + workingDays);
        System.out.println("  Overtime Hours: " + overtimeHours);
        System.out.println("  Absence Days: " + absenceDays);
        System.out.printf("  Basic Salary: %,.0f VND%n", emp.getBasicSalary());
        System.out.printf("  Total Salary: %,.0f VND%n", totalSalary);
        Validator.pressEnterToContinue(sc);
    }

    public void viewSalaryDetails(Scanner sc) {
        System.out.println("\n----------- SALARY DETAILS -----------");
        String empId = Validator.readNonEmptyString(sc, "Employee ID: ");

        Employee emp = employeeManager.findById(empId);
        if (emp == null) {
            System.out.println("Error: Employee not found.");
            return;
        }

        int month = Validator.readMenuChoice(sc, "Month (1-12): ", 1, 12);
        int year = Validator.readPositiveInt(sc, "Year: ");

        List<Attendance> records = attendanceManager.getByEmployeeAndMonth(empId, month, year);

        int workingDays = 0;
        int overtimeHours = 0;
        int absenceDays = 0;
        int leaveDays = 0;

        for (Attendance att : records) {
            switch (att.getStatus()) {
                case "Present":
                    workingDays++;
                    overtimeHours += att.getOvertimeHours();
                    break;
                case "Absent":
                    absenceDays++;
                    break;
                case "Leave":
                    leaveDays++;
                    break;
            }
        }

        double otRate = emp.getType().equals("Full-time") ? 80000 : 50000;
        double overtimePay = overtimeHours * otRate;
        double deduction = absenceDays * 100000;
        double totalSalary = emp.calculateSalary(workingDays, overtimeHours, absenceDays);

        System.out.println("\n============ SALARY DETAILS ============");
        System.out.println("Employee: " + emp.getName() + " (" + empId + ")");
        System.out.println("Type: " + emp.getType());
        System.out.println("Period: " + month + "/" + year);
        System.out.println("----------------------------------------");
        System.out.println("Working Days:     " + workingDays);
        System.out.println("Absence Days:     " + absenceDays);
        System.out.println("Leave Days:       " + leaveDays);
        System.out.println("Overtime Hours:   " + overtimeHours);
        System.out.println("----------------------------------------");
        System.out.printf("Basic Salary:     %,15.0f VND%n", emp.getBasicSalary());
        System.out.printf("Overtime Pay:   + %,15.0f VND%n", overtimePay);
        System.out.printf("Deduction:      - %,15.0f VND%n", deduction);
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL SALARY:     %,15.0f VND%n", totalSalary);
        System.out.println("========================================");
        Validator.pressEnterToContinue(sc);
    }

    public void generateSalaryReport(Scanner sc) {
        System.out.println("\n----------- SALARY REPORT -----------");
        int month = Validator.readMenuChoice(sc, "Month (1-12): ", 1, 12);
        int year = Validator.readPositiveInt(sc, "Year: ");

        ArrayList<Employee> allEmployees = employeeManager.getEmployees();

        System.out.println("\n============ SALARY REPORT " + month + "/" + year + " ============");
        System.out.printf("%-6s %-20s %-10s %8s %5s %5s %15s%n",
                "ID", "Name", "Type", "WorkDays", "OT", "Abs", "Total Salary");
        System.out.println("--------------------------------------------------------------------------");

        boolean hasData = false;
        for (Employee emp : allEmployees) {
            if (!emp.isActive()) continue;

            List<Attendance> records = attendanceManager.getByEmployeeAndMonth(emp.getId(), month, year);

            int workingDays = 0;
            int overtimeHours = 0;
            int absenceDays = 0;

            for (Attendance att : records) {
                switch (att.getStatus()) {
                    case "Present":
                        workingDays++;
                        overtimeHours += att.getOvertimeHours();
                        break;
                    case "Absent":
                        absenceDays++;
                        break;
                }
            }

            double totalSalary = emp.calculateSalary(workingDays, overtimeHours, absenceDays);

            System.out.printf("%-6s %-20s %-10s %8d %5d %5d %,15.0f%n",
                    emp.getId(), emp.getName(), emp.getType(),
                    workingDays, overtimeHours, absenceDays, totalSalary);
            hasData = true;
        }

        if (!hasData) {
            System.out.println("No active employees found.");
        }
        System.out.println("--------------------------------------------------------------------------");
        Validator.pressEnterToContinue(sc);
    }

    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n====================================");
            System.out.println("      SALARY MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Calculate Salary");
            System.out.println("2. View Salary Details");
            System.out.println("3. Generate Salary Report");
            System.out.println("4. Back to Main Menu");
            System.out.println("------------------------------------");

            choice = Validator.readMenuChoice(sc, "Choose an option: ", 1, 4);

            switch (choice) {
                case 1:
                    calculateSalary(sc);
                    break;
                case 2:
                    viewSalaryDetails(sc);
                    break;
                case 3:
                    generateSalaryReport(sc);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 4);
    }
}
