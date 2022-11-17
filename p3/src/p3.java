/*
Name: Mikaela Milch, Bridget Redgate, Sasha Daraskevich
 */

import java.util.Scanner;
import java.sql.*;


public class p3 {

    private static final String USERID = "sjdaraskevich";
    private static final String PASSWORD = "JOY55sass17!3055";

    public static void main(String[] args) {

        System.out.println("-------Oracle JDBC COnnection Testing ---------");
        try {
            // Register the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e){
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

        try {
            // create the connection string
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl", USERID, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle JDBC Driver Connected!");

        // Performing the query
        try {
            Statement stmt = connection.createStatement();
            Scanner myObj = new Scanner(System.in);

            if (args.length == 0) {
                System.out.println("You need to include your UserID and Password parameters on the command line");
                return;
            }
            if (args.length < 3) {
                System.out.println("Include the number of the following menu item as the third parameter on the command line.");
                System.out.println("1 – Report Patient Information \n2 – Report Employee Information \n3 – Update Employee’s Password");
                return;
            }

            if (args[2].equals("1")) {
                System.out.println("Enter Patient First Name :");
                String patientFirstName = myObj.nextLine();
                System.out.println("Enter Patient Last Name :");
                String patientLastName = myObj.nextLine();

                String str = "SELECT * FROM Patient WHERE ((Patient.firstName = '"+ patientFirstName +"') AND (Patient.lastName = '"+patientLastName+"'))";
                ResultSet rset = stmt.executeQuery(str);

                int patientID = 0;
                String patientName="", address="";

                System.out.println("Patient Information");
                while (rset.next()) {
                    patientID = rset.getInt("patientID");
                    patientName = rset.getString("firstName") + " " + rset.getString("lastName");
                    address = rset.getString("city") + ", " + rset.getString("state");
                    System.out.println("Patient ID: " + patientID + "\nPatient Name: " + patientName + "\nAddress: " + address);
                } // end while
                rset.close();
            }
            else if (args[2].equals("2")) {
                System.out.println("Enter Employee ID :");
                String userEmployeeID = myObj.nextLine();

                String str = "SELECT * FROM Employee WHERE (Employee.employeeID = '"+ userEmployeeID+"')";
                ResultSet rset = stmt.executeQuery(str);

                int employeeID = 0, salaryGrade= 0;
                double NPI = 0;
                String employeeName="", username="", password="", securityClearance="";

                System.out.println("Employee Information");
                while (rset.next()) {
                    employeeID = rset.getInt("employeeID");
                    NPI = rset.getDouble("NPI");
                    salaryGrade = rset.getInt("salaryGrade");
                    employeeName = rset.getString("firstName") + " " + rset.getString("lastName");
                    username = rset.getString("username");
                    password = rset.getString("password");
                    securityClearance = rset.getString("securityClearance");
                    System.out.println("Employee ID: " + employeeID);
                    if(NPI != 0){
                        System.out.println("NPI: " + NPI);
                        employeeName = "Dr. " + employeeName;
                    }
                    System.out.println("EmployeeName: " + employeeName);
                    System.out.println("Username: " + username + "\nPassword: " + password);
                    if(salaryGrade != 0) System.out.println("Salary Grade: " + salaryGrade);
                    if(securityClearance != null) System.out.println("Security Clearance: " + securityClearance);
                } // end while
                rset.close();
                return;
            }
            else if (args[2].equals("3")) {
                System.out.println("Enter the employee ID:");
                String userEmployeeID = myObj.nextLine();
                System.out.println("Enter the updated password:");
                String newPassword = myObj.nextLine();

                String str = "UPDATE Employee SET password = '"+ newPassword + "' WHERE (Employee.employeeID = '"+ userEmployeeID+"')";
                int updateSet = stmt.executeUpdate(str);

                if(updateSet == 1) System.out.println("Your password has been updated");

            }

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Get Data Failed! Check output console");
            e.printStackTrace();
            return;
        }

    }
}