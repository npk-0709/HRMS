package model;

public class Attendance {
    private String employeeId;
    private String date;       // dd/MM/yyyy
    private String status;     // Present, Absent, Leave (BR5)
    private int overtimeHours;

    public Attendance() {
    }

    public Attendance(String employeeId, String date, String status, int overtimeHours) {
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
        this.overtimeHours = overtimeHours;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    @Override
    public String toString() {
        return String.format("%-12s %-10s %d", date, status, overtimeHours);
    }

    public String toFileString() {
        return employeeId + "|" + date + "|" + status + "|" + overtimeHours;
    }
}
