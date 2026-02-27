package util;

import java.util.Scanner;

public class Validator {

    public static String readNonEmptyString(Scanner sc, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }

    public static String readOptionalString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static double readPositiveDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input.replace(",", ""));
                if (value > 0) {
                    return value;
                }
                System.out.println("Error: Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please try again.");
            }
        }
    }

    public static int readNonNegativeInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Error: Value must be >= 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number. Please try again.");
            }
        }
    }

    public static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Error: Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number. Please try again.");
            }
        }
    }

    public static int readMenuChoice(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Error: Please choose between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
            }
        }
    }

    public static String readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (isValidDate(input)) {
                return input;
            }
            System.out.println("Error: Invalid date format. Use dd/MM/yyyy.");
        }
    }

    public static boolean isValidDate(String date) {
        if (date == null || !date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        try {
            String[] parts = date.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            if (year < 1900 || year > 2100) return false;
            if (month < 1 || month > 12) return false;

            int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            // Leap year check
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                daysInMonth[2] = 29;
            }

            return day >= 1 && day <= daysInMonth[month];
        } catch (Exception e) {
            return false;
        }
    }

    public static String readAttendanceStatus(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (Present/Absent/Leave): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Present") || input.equalsIgnoreCase("Absent")
                    || input.equalsIgnoreCase("Leave")) {
                // Capitalize first letter
                return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
            }
            System.out.println("Error: Status must be Present, Absent, or Leave.");
        }
    }

    public static String readEmployeeType(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (Full-time/Part-time): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Full-time") || input.equalsIgnoreCase("Fulltime")) {
                return "Full-time";
            }
            if (input.equalsIgnoreCase("Part-time") || input.equalsIgnoreCase("Parttime")) {
                return "Part-time";
            }
            System.out.println("Error: Type must be Full-time or Part-time.");
        }
    }

    public static boolean readSaveOrCancel(Scanner sc) {
        while (true) {
            System.out.print("[1] Save  [2] Cancel: ");
            String input = sc.nextLine().trim();
            if (input.equals("1")) return true;
            if (input.equals("2")) return false;
            System.out.println("Error: Please enter 1 or 2.");
        }
    }

    public static boolean readUpdateOrCancel(Scanner sc) {
        while (true) {
            System.out.print("[1] Update  [2] Cancel: ");
            String input = sc.nextLine().trim();
            if (input.equals("1")) return true;
            if (input.equals("2")) return false;
            System.out.println("Error: Please enter 1 or 2.");
        }
    }


    public static void pressEnterToContinue(Scanner sc) {
        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }


    public static int getMonthFromDate(String date) {
        String[] parts = date.split("/");
        return Integer.parseInt(parts[1]);
    }


    public static int getYearFromDate(String date) {
        String[] parts = date.split("/");
        return Integer.parseInt(parts[2]);
    }
}
