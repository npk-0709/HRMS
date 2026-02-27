import manager.AttendanceManager;
import manager.EmployeeManager;
import manager.ReportManager;
import manager.SalaryManager;
import util.Validator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EmployeeManager employeeManager = new EmployeeManager();
        AttendanceManager attendanceManager = new AttendanceManager(employeeManager);
        SalaryManager salaryManager = new SalaryManager(employeeManager, attendanceManager);
        ReportManager reportManager = new ReportManager(employeeManager, attendanceManager);

        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("    HUMAN RESOURCE MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Manage Employees");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("--------------------------------------");

            choice = Validator.readMenuChoice(sc, "Choose an option: ", 1, 5);

            switch (choice) {
                case 1:
                    employeeManager.showMenu(sc);
                    break;
                case 2:
                    attendanceManager.showMenu(sc);
                    break;
                case 3:
                    salaryManager.showMenu(sc);
                    break;
                case 4:
                    reportManager.showMenu(sc);
                    break;
                case 5:
                    // Save all data before exit
                    employeeManager.saveToFile();
                    attendanceManager.saveToFile();
                    System.out.println("Data saved.");
                    break;
            }
        } while (choice != 5);

        sc.close();
    }
}
