package model;

public class FullTimeEmployee extends Employee {

    private static final double OT_RATE = 80000;
    private static final double ABSENCE_DEDUCTION = 100000;

    public FullTimeEmployee() {
        super();
    }

    public FullTimeEmployee(String id, String name, String department, String jobTitle,
                            String dateOfJoining, double basicSalary) {
        super(id, name, department, jobTitle, "Full-time", dateOfJoining, basicSalary);
    }

    @Override
    public double calculateSalary(int workingDays, int overtimeHours, int absenceDays) {
        double overtimePay = overtimeHours * OT_RATE;
        double deduction = absenceDays * ABSENCE_DEDUCTION;
        double totalSalary = getBasicSalary() + overtimePay - deduction;
        return Math.max(totalSalary, 0);
    }
}
