package model;

public abstract class Employee {
    private String id;
    private String name;
    private String department;
    private String jobTitle;
    private String type; // "Full-time" or "Part-time"
    private String dateOfJoining; // dd/MM/yyyy
    private double basicSalary;
    private boolean active;

    public Employee() {
        this.active = true;
    }

    public Employee(String id, String name, String department, String jobTitle,
                    String type, String dateOfJoining, double basicSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.jobTitle = jobTitle;
        this.type = type;
        this.dateOfJoining = dateOfJoining;
        this.basicSalary = basicSalary;
        this.active = true;
    }

    public abstract double calculateSalary(int workingDays, int overtimeHours, int absenceDays);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-12s %-20s %-10s %-12s %,15.0f  %-6s",
                id, name, department, jobTitle, type, dateOfJoining, basicSalary,
                active ? "Active" : "Inactive");
    }

    public String toFileString() {
        return id + "|" + name + "|" + department + "|" + jobTitle + "|"
                + type + "|" + dateOfJoining + "|" + basicSalary + "|" + active;
    }
}
