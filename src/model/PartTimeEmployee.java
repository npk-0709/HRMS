package model;

public class PartTimeEmployee extends Employee {

    private static final double OT_RATE = 50000;
    private static final double ABSENCE_DEDUCTION = 100000;

    public PartTimeEmployee() {
        super();
    }

    public PartTimeEmployee(String id, String name, String department, String jobTitle,
                            String dateOfJoining, double basicSalary) {
        super(id, name, department, jobTitle, "Part-time", dateOfJoining, basicSalary);
    }

    @Override
    public double calculateSalary(int workingDays, int overtimeHours, int absenceDays) {
        double overtimePay = overtimeHours * OT_RATE;
        double deduction = absenceDays * ABSENCE_DEDUCTION;
        double totalSalary = getBasicSalary() + overtimePay - deduction;
        return Math.max(totalSalary, 0);
    }
}
