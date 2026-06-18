package service;
import database.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class SkillService {

    Scanner sc = new Scanner(System.in);

    public void addSkill(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Skill Name : ");
            String skill = sc.nextLine();

            String query =
                    "INSERT INTO skills(user_id,skill_name) VALUES(?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setString(2, skill);

            int rows = ps.executeUpdate();

            if(rows > 0) {
                System.out.println("Skill Added Successfully!");
            }

        } catch (Exception e) {
            System.out.println("Skill Already Exists!");
        }
    }

    public void deleteSkill(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            viewSkills(userId);

            System.out.print("Enter Skill ID to Delete : ");
            int skillId = sc.nextInt();
            sc.nextLine();

            String query =
                    "DELETE FROM skills WHERE id=? AND user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, skillId);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();

            if(rows > 0) {
                System.out.println("Skill Deleted Successfully!");
            }
            else {
                System.out.println("Skill Not Found!");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSkills(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT * FROM skills WHERE user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== YOUR SKILLS =====");

            while(rs.next()) {

                System.out.println(
                        rs.getInt("id") + " - " +
                                rs.getString("skill_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}