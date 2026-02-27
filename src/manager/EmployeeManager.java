package manager;

import model.Employee;
import model.FullTimeEmployee;
import model.PartTimeEmployee;
import util.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManager {
    private ArrayList<Employee> employees;
    private static final String FILE_NAME = "employees.txt";

    public EmployeeManager() {
        employees = new ArrayList<>();
        loadFromFile();
    }


    public ArrayList<Employee> getEmployees() {
        return employees;
    }


    public Employee findById(String id) {
        for (Employee emp : employees) {
            if (emp.getId().equalsIgnoreCase(id)) {
                return emp;
            }
        }
        return null;
    }

    public boolean isIdExist(String id) {
        return findById(id) != null;
    }

    public void addEmployee(Scanner sc) {
        System.out.println("\n----------- ADD EMPLOYEE -----------");

        String id;
        while (true) {
            id = Validator.readNonEmptyString(sc, "Employee ID: ");
            if (!isIdExist(id)) {
                break;
            }
            System.out.println("Error: Employee ID '" + id + "' already exists. Please enter a different ID.");
        }

        String name = Validator.readNonEmptyString(sc, "Full Name: ");
        String department = Validator.readNonEmptyString(sc, "Department: ");
        String jobTitle = Validator.readNonEmptyString(sc, "Job Title: ");
        String type = Validator.readEmployeeType(sc, "Type");
        String dateOfJoining = Validator.readDate(sc, "Date of Joining (dd/MM/yyyy): ");
        double basicSalary = Validator.readPositiveDouble(sc, "Basic Salary: ");

        if (Validator.readSaveOrCancel(sc)) {
            Employee emp;
            if (type.equals("Full-time")) {
                emp = new FullTimeEmployee(id, name, department, jobTitle, dateOfJoining, basicSalary);
            } else {
                emp = new PartTimeEmployee(id, name, department, jobTitle, dateOfJoining, basicSalary);
            }
            employees.add(emp);
            saveToFile();
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    public void updateEmployee(Scanner sc) {
        System.out.println("\n----------- UPDATE EMPLOYEE -----------");
        String id = Validator.readNonEmptyString(sc, "Enter Employee ID to update: ");
        Employee emp = findById(id);

        if (emp == null) {
            System.out.println("Error: Employee not found.");
            return;
        }

        System.out.println("Current Information:");
        System.out.println("  Name: " + emp.getName());
        System.out.println("  Department: " + emp.getDepartment());
        System.out.println("  Job Title: " + emp.getJobTitle());
        System.out.printf("  Basic Salary: %,.0f%n", emp.getBasicSalary());
        System.out.println("  Status: " + (emp.isActive() ? "Active" : "Inactive"));
        System.out.println();

        String newName = Validator.readOptionalString(sc, "Enter new Name (leave blank to skip): ");
        String newDept = Validator.readOptionalString(sc, "Enter new Department (leave blank to skip): ");
        String newJob = Validator.readOptionalString(sc, "Enter new Job Title (leave blank to skip): ");
        String newSalary = Validator.readOptionalString(sc, "Enter new Basic Salary (leave blank to skip): ");
        String newStatus = Validator.readOptionalString(sc, "Enter new Status (Active/Inactive, leave blank to skip): ");

        if (Validator.readUpdateOrCancel(sc)) {
            if (!newName.isEmpty()) {
                emp.setName(newName);
            }
            if (!newDept.isEmpty()) {
                emp.setDepartment(newDept);
            }
            if (!newJob.isEmpty()) {
                emp.setJobTitle(newJob);
            }
            if (!newSalary.isEmpty()) {
                try {
                    double salary = Double.parseDouble(newSalary.replace(",", ""));
                    if (salary > 0) {
                        emp.setBasicSalary(salary);
                    } else {
                        System.out.println("Warning: Invalid salary value, salary not updated.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid salary format, salary not updated.");
                }
            }
            if (!newStatus.isEmpty()) {
                if (newStatus.equalsIgnoreCase("Active")) {
                    emp.setActive(true);
                } else if (newStatus.equalsIgnoreCase("Inactive")) {
                    emp.setActive(false);
                } else {
                    System.out.println("Warning: Invalid status, status not updated.");
                }
            }
            saveToFile();
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    public void removeEmployee(Scanner sc) {
        System.out.println("\n----------- REMOVE EMPLOYEE -----------");
        String id = Validator.readNonEmptyString(sc, "Enter Employee ID to remove: ");
        Employee emp = findById(id);

        if (emp == null) {
            System.out.println("Error: Employee not found.");
            return;
        }

        System.out.println("Employee: " + emp.getName() + " (" + emp.getId() + ")");
        System.out.print("Are you sure you want to remove? (y/n): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("y")) {
            employees.remove(emp);
            saveToFile();
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    public void viewAllEmployees(Scanner sc) {
        System.out.println("\n--------------- EMPLOYEE LIST -----------------------------------------");
        System.out.printf("%-6s %-20s %-12s %-20s %-10s %-12s %15s  %-6s%n",
                "ID", "Name", "Department", "Job Title", "Type", "Joining", "Salary", "Status");
        System.out.println("-----------------------------------------------------------------------");

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp.toString());
            }
        }
        System.out.println("-----------------------------------------------------------------------");
        Validator.pressEnterToContinue(sc);
    }

    public void searchEmployees(Scanner sc) {
        System.out.println("\n----------- SEARCH EMPLOYEE -----------");
        System.out.println("Search by: 1. Name  2. Department  3. Job Title");
        int choice = Validator.readMenuChoice(sc, "Choose: ", 1, 3);
        String keyword = Validator.readNonEmptyString(sc, "Enter keyword: ");

        List<Employee> results = new ArrayList<>();
        for (Employee emp : employees) {
            switch (choice) {
                case 1:
                    if (emp.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        results.add(emp);
                    }
                    break;
                case 2:
                    if (emp.getDepartment().toLowerCase().contains(keyword.toLowerCase())) {
                        results.add(emp);
                    }
                    break;
                case 3:
                    if (emp.getJobTitle().toLowerCase().contains(keyword.toLowerCase())) {
                        results.add(emp);
                    }
                    break;
            }
        }

        System.out.println("\n--- Search Results ---");
        if (results.isEmpty()) {
            System.out.println("No employees found matching '" + keyword + "'.");
        } else {
            System.out.printf("%-6s %-20s %-12s %-20s %-10s %-12s %15s  %-6s%n",
                    "ID", "Name", "Department", "Job Title", "Type", "Joining", "Salary", "Status");
            System.out.println("-----------------------------------------------------------------------");
            for (Employee emp : results) {
                System.out.println(emp.toString());
            }
        }
        Validator.pressEnterToContinue(sc);
    }
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                bw.write(emp.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
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
                if (parts.length < 8) continue;

                String id = parts[0];
                String name = parts[1];
                String department = parts[2];
                String jobTitle = parts[3];
                String type = parts[4];
                String dateOfJoining = parts[5];
                double basicSalary = Double.parseDouble(parts[6]);
                boolean active = Boolean.parseBoolean(parts[7]);

                Employee emp;
                if (type.equalsIgnoreCase("Full-time")) {
                    emp = new FullTimeEmployee(id, name, department, jobTitle, dateOfJoining, basicSalary);
                } else {
                    emp = new PartTimeEmployee(id, name, department, jobTitle, dateOfJoining, basicSalary);
                }
                emp.setActive(active);
                employees.add(emp);
            }
        } catch (IOException e) {
            System.out.println("Error loading employees: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing employee data: " + e.getMessage());
        }
    }

    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n====================================");
            System.out.println("      EMPLOYEE MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. View All Employees");
            System.out.println("5. Search Employees");
            System.out.println("6. Back to Main Menu");
            System.out.println("------------------------------------");

            choice = Validator.readMenuChoice(sc, "Choose an option: ", 1, 6);

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    updateEmployee(sc);
                    break;
                case 3:
                    removeEmployee(sc);
                    break;
                case 4:
                    viewAllEmployees(sc);
                    break;
                case 5:
                    searchEmployees(sc);
                    break;
                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
            }
        } while (choice != 6);
    }
}
