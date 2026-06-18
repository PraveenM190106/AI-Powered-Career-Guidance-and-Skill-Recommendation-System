package service;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserService {

    Scanner sc = new Scanner(System.in);

    public void registerUser() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Name : ");
            String name = sc.nextLine();

            System.out.print("Enter Email : ");
            String email = sc.nextLine();

            System.out.print("Enter Password : ");
            String password = sc.nextLine();

            String query =
                    "INSERT INTO users(name,email,password) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            int rows = ps.executeUpdate();

            if(rows > 0) {
                System.out.println("Registration Successful!");
            }

        } catch (Exception e) {
            System.out.println("Email already exists!");
        }
    }

    public int loginUser() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Email : ");
            String email = sc.nextLine();

            System.out.print("Enter Password : ");
            String password = sc.nextLine();

            String query =
                    "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                System.out.println(
                        "Welcome " + rs.getString("name")
                );

                return rs.getInt("id");
            }

            System.out.println("Invalid Email or Password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}