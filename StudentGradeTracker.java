import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private ArrayList<Double> grades;

    // Constructor
    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    // Add grade
    public void addGrade(double grade) {
        grades.add(grade);
    }

    // Get student name
    public String getName() {
        return name;
    }

    // Calculate average
    public double getAverage() {
        if (grades.isEmpty()) {
            return 0;
        }

        double sum = 0;

        for (double grade : grades) {
            sum += grade;
        }

        return sum / grades.size();
    }

    // Find highest grade
    public double getHighest() {
        if (grades.isEmpty()) {
            return 0;
        }

        double highest = grades.get(0);

        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }

        return highest;
    }

    // Find lowest grade
    public double getLowest() {
        if (grades.isEmpty()) {
            return 0;
        }

        double lowest = grades.get(0);

        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }

        return lowest;
    }

    // Check pass or fail
    public String getStatus() {
        if (getAverage() >= 50) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}

public class StudentGradeTracker {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Display Student Report");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    addGrade();
                    break;

                case 3:
                    displayStudentReport();
                    break;

                case 4:
                    displayAllStudents();
                    break;

                case 5:
                    System.out.println("Thank you for using Student Grade Tracker!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Add student
    static void addStudent() {

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        students.add(new Student(name));

        System.out.println("Student added successfully!");
    }

    // Add grade
    static void addGrade() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        displayStudentNames();

        System.out.print("Select student number: ");
        int index = readInt() - 1;

        if (index < 0 || index >= students.size()) {
            System.out.println("Invalid student number!");
            return;
        }

        System.out.print("Enter grade (0-100): ");
        double grade = readDouble();

        if (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100!");
            return;
        }

        students.get(index).addGrade(grade);

        System.out.println("Grade added successfully!");
    }

    // Display one student's report
    static void displayStudentReport() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        displayStudentNames();

        System.out.print("Select student number: ");
        int index = readInt() - 1;

        if (index < 0 || index >= students.size()) {
            System.out.println("Invalid student number!");
            return;
        }

        Student student = students.get(index);

        System.out.println("\n----- STUDENT REPORT -----");

        System.out.println("Name: " + student.getName());

        System.out.printf("Average: %.2f%n",
                student.getAverage());

        System.out.printf("Highest: %.2f%n",
                student.getHighest());

        System.out.printf("Lowest: %.2f%n",
                student.getLowest());

        System.out.println("Status: " + student.getStatus());
    }

    // Display all students
    static void displayAllStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        System.out.println("\n----- ALL STUDENTS -----");

        for (Student student : students) {

            System.out.printf(
                "%s | Average: %.2f | Highest: %.2f | Lowest: %.2f | %s%n",
                student.getName(),
                student.getAverage(),
                student.getHighest(),
                student.getLowest(),
                student.getStatus()
            );
        }
    }

    // Display student names
    static void displayStudentNames() {

        System.out.println("\nStudents:");

        for (int i = 0; i < students.size(); i++) {

            System.out.println(
                (i + 1) + ". " + students.get(i).getName()
            );
        }
    }

    // Read integer
    static int readInt() {

        while (true) {

            try {

                return Integer.parseInt(
                    scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                    "Please enter a valid number: "
                );
            }
        }
    }

    // Read double
    static double readDouble() {

        while (true) {

            try {

                return Double.parseDouble(
                    scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                    "Please enter a valid grade: "
                );
            }
        }
    }
}