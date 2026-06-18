package service;

import database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class CertificateService {

    Scanner sc = new Scanner(System.in);

    public void addCertificate(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Certificate Name : ");
            String certificate = sc.nextLine();

            String query =
                    "INSERT INTO certificates(user_id,certificate_name) VALUES(?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setString(2, certificate);

            int rows = ps.executeUpdate();

            if(rows > 0) {
                System.out.println("Certificate Added Successfully!");
            }

        } catch (Exception e) {
            System.out.println("Certificate Already Exists!");
        }
    }

    public void deleteCertificate(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            viewCertificates(userId);

            System.out.print("Enter Certificate ID to Delete : ");
            int certificateId = sc.nextInt();
            sc.nextLine();

            String query =
                    "DELETE FROM certificates WHERE id=? AND user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, certificateId);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();

            if(rows > 0) {
                System.out.println(
                        "Certificate Deleted Successfully!"
                );
            }
            else {
                System.out.println(
                        "Certificate Not Found!"
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void viewCertificates(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT * FROM certificates WHERE user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== YOUR CERTIFICATES =====");

            while(rs.next()) {

                System.out.println(
                        rs.getInt("id") + " - " +
                                rs.getString("certificate_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}