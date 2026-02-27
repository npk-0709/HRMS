package manager;

import model.Attendance;
import model.Employee;
import util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportManager {
    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;
    private static final int LOW_ATTENDANCE_THRESHOLD = 3;

    public ReportManager(EmployeeManager employeeManager, AttendanceManager attendanceManager) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
    }


    public void lowAttendanceReport(Scanner sc) {
        System.out.println("\n----------- LOW ATTENDANCE REPORT -----------");
        int month = Validator.readMenuChoice(sc, "Month (1-12): ", 1, 12);
        int year = Validator.readPositiveInt(sc, "Year: ");

        System.out.println("\nEmployees with more than " + LOW_ATTENDANCE_THRESHOLD
                + " absent days in " + month + "/" + year + ":");
        System.out.println("---------------------------------------------");

        boolean found = false;
        for (Employee emp : employeeManager.getEmployees()) {
            List<Attendance> records = attendanceManager.getByEmployeeAndMonth(emp.getId(), month, year);

            int absentDays = 0;
            for (Attendance att : records) {
                if (att.getStatus().equals("Absent")) {
                    absentDays++;
                }
            }

            if (absentDays > LOW_ATTENDANCE_THRESHOLD) {
                System.out.println(emp.getId() + "  " + emp.getName() + "  " + absentDays + " absent days");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees with low attendance found.");
        }
        System.out.println("---------------------------------------------");
        Validator.pressEnterToContinue(sc);
    }


    public void highestPaidReport(Scanner sc) {
        System.out.println("\n----------- HIGHEST PAID EMPLOYEES -----------");
        int month = Validator.readMenuChoice(sc, "Month (1-12): ", 1, 12);
        int year = Validator.readPositiveInt(sc, "Year: ");

        ArrayList<Employee> allEmployees = employeeManager.getEmployees();

        double maxSalary = -1;
        List<String> results = new ArrayList<>();

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

            if (totalSalary > maxSalary) {
                maxSalary = totalSalary;
                results.clear();
                results.add(emp.getId() + "  " + emp.getName() + "  " + String.format("%,.0f VND", totalSalary));
            } else if (totalSalary == maxSalary) {
                results.add(emp.getId() + "  " + emp.getName() + "  " + String.format("%,.0f VND", totalSalary));
            }
        }

        System.out.println("\nHighest paid employee(s) in " + month + "/" + year + ":");
        System.out.println("----------------------------------------------");
        if (results.isEmpty()) {
            System.out.println("No active employees found.");
        } else {
            for (String line : results) {
                System.out.println(line);
            }
        }
        System.out.println("----------------------------------------------");
        Validator.pressEnterToContinue(sc);
    }


    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n====================================");
            System.out.println("          REPORTS");
            System.out.println("====================================");
            System.out.println("1. Low Attendance Report");
            System.out.println("2. Highest Paid Employees");
            System.out.println("3. Back to Main Menu");
            System.out.println("------------------------------------");

            choice = Validator.readMenuChoice(sc, "Choose an option: ", 1, 3);

            switch (choice) {
                case 1:
                    lowAttendanceReport(sc);
                    break;
                case 2:
                    highestPaidReport(sc);
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 3);
    }
}
