package service;

import ai.GeminiService;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ReportService {

    private final GeminiService geminiService = new GeminiService();
    private final CareerService careerService;

    public ReportService(CareerService careerService) {
        this.careerService = careerService;
    }

    public void generateReport(int userId) {
        String name = "N/A";
        String email = "N/A";
        try {
            Connection con = DBConnection.getConnection();
            if (con != null) {
                String query = "SELECT name, email FROM users WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
                    email = rs.getString("email");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> skills = careerService.getUserSkills(userId);
        List<String> certificates = careerService.getUserCertificates(userId);

        System.out.println("\n[AI Assistant] Compiling your comprehensive career report...");

        String prompt = "Generate a complete, professional Career Guidance and Skill Recommendation Report for the following user profile:\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Current Skills: " + (skills.isEmpty() ? "None listed yet" : String.join(", ", skills)) + "\n" +
                "Current Certificates: " + (certificates.isEmpty() ? "None listed yet" : String.join(", ", certificates)) + "\n\n" +
                "The report must contain the following sections formatted clearly:\n" +
                "1. USER PROFILE SUMMARY: A professional summary of the user's current standing.\n" +
                "2. CAREER PATH PREDICTIONS: Recommend 3 suitable career paths with brief descriptions of why they fit.\n" +
                "3. SKILL GAP ANALYSIS: Identify critical skills missing from their profile for the most recommended path.\n" +
                "4. LEARNING ROADMAP: A phase-wise roadmap (Beginner -> Intermediate -> Advanced) to bridge the skill gaps.\n" +
                "5. PROJECT RECOMMENDATIONS: Suggest 3 practical, resume-worthy projects they can work on.\n\n" +
                "Ensure the report is highly professional, well-structured, and easy to read.";

        String report = geminiService.askAI(prompt);

        System.out.println("\n========================================================");
        System.out.println("            CAREER GUIDANCE SYSTEM REPORT               ");
        System.out.println("========================================================");
        System.out.println("Name  : " + name);
        System.out.println("Email : " + email);
        System.out.println("--------------------------------------------------------");
        System.out.println(report);
        System.out.println("========================================================");
    }
}
