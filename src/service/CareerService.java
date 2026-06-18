package service;

import ai.GeminiService;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CareerService {

    private final GeminiService geminiService = new GeminiService();
    private String lastPredictedCareer = "";

    public String getLastPredictedCareer() {
        return lastPredictedCareer;
    }

    public List<String> getUserSkills(int userId) {
        List<String> skills = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return skills;

            String query = "SELECT skill_name FROM skills WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                skills.add(rs.getString("skill_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skills;
    }

    public List<String> getUserCertificates(int userId) {
        List<String> certificates = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return certificates;

            String query = "SELECT certificate_name FROM certificates WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                certificates.add(rs.getString("certificate_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificates;
    }

    public void predictCareer(int userId) {
        List<String> skills = getUserSkills(userId);
        List<String> certificates = getUserCertificates(userId);

        if (skills.isEmpty() && certificates.isEmpty()) {
            System.out.println("\n[Warning] Your profile is currently empty. Add skills or certificates first for personalized recommendations.");
        }

        System.out.println("\n[AI Assistant] Analyzing your profile...");
        String prompt = "Based on the following profile:\n" +
                "Skills: " + (skills.isEmpty() ? "None listed yet" : String.join(", ", skills)) + "\n" +
                "Certificates: " + (certificates.isEmpty() ? "None listed yet" : String.join(", ", certificates)) + "\n\n" +
                "Predict 3 suitable career paths. For each path, include:\n" +
                "1. Career Title\n" +
                "2. Why it fits your profile\n" +
                "3. Key tools/technologies to focus on next\n\n" +
                "Also, clearly output the TOP recommended career title in a line starting with 'TOP RECOMMENDATION: [Career Title]' at the very end.";

        String answer = geminiService.askAI(prompt);
        System.out.println("\n===== CAREER PREDICTION =====");
        System.out.println(answer);

        parseTopRecommendation(answer);
    }

    public void analyzeSkillGap(int userId, Scanner sc) {
        List<String> skills = getUserSkills(userId);
        List<String> certificates = getUserCertificates(userId);

        String targetCareer = "";
        if (lastPredictedCareer != null && !lastPredictedCareer.isEmpty()) {
            System.out.print("Enter target career path [Default: " + lastPredictedCareer + "]: ");
            targetCareer = sc.nextLine().trim();
            if (targetCareer.isEmpty()) {
                targetCareer = lastPredictedCareer;
            }
        } else {
            System.out.print("Enter target career path: ");
            targetCareer = sc.nextLine().trim();
            if (targetCareer.isEmpty()) {
                System.out.println("Career path cannot be empty.");
                return;
            }
        }

        System.out.println("\n[AI Assistant] Analyzing skill gap for: " + targetCareer + "...");
        String prompt = "Compare my current profile with the requirements for the career: \"" + targetCareer + "\".\n" +
                "Current Skills: " + (skills.isEmpty() ? "None listed yet" : String.join(", ", skills)) + "\n" +
                "Current Certificates: " + (certificates.isEmpty() ? "None listed yet" : String.join(", ", certificates)) + "\n\n" +
                "Please perform a detailed Skill Gap Analysis:\n" +
                "1. Identify the essential skills required for this career that I do NOT currently possess (Missing Skills).\n" +
                "2. Provide a brief explanation for why each missing skill is critical for this role.\n" +
                "3. Offer general suggestions on how to bridge this gap.";

        String answer = geminiService.askAI(prompt);
        System.out.println("\n===== SKILL GAP ANALYSIS =====");
        System.out.println(answer);
    }

    public void generateRoadmap(int userId, Scanner sc) {
        List<String> skills = getUserSkills(userId);
        List<String> certificates = getUserCertificates(userId);

        String targetCareer = "";
        if (lastPredictedCareer != null && !lastPredictedCareer.isEmpty()) {
            System.out.print("Enter target career for roadmap [Default: " + lastPredictedCareer + "]: ");
            targetCareer = sc.nextLine().trim();
            if (targetCareer.isEmpty()) {
                targetCareer = lastPredictedCareer;
            }
        } else {
            System.out.print("Enter target career for roadmap: ");
            targetCareer = sc.nextLine().trim();
            if (targetCareer.isEmpty()) {
                System.out.println("Target career cannot be empty.");
                return;
            }
        }

        System.out.println("\n[AI Assistant] Generating phase-wise roadmap for: " + targetCareer + "...");
        String prompt = "Create a structured, phase-wise learning roadmap to transition from my current profile to the career: \"" + targetCareer + "\".\n" +
                "Current Skills: " + (skills.isEmpty() ? "None listed" : String.join(", ", skills)) + "\n" +
                "Current Certificates: " + (certificates.isEmpty() ? "None listed" : String.join(", ", certificates)) + "\n\n" +
                "Divide the roadmap clearly into the following three phases:\n" +
                "- Beginner Phase: Fundamental concepts, syntax, and essential tools/frameworks I must learn first.\n" +
                "- Intermediate Phase: Building functional applications, learning advanced libraries, standard patterns, and tools.\n" +
                "- Advanced Phase: Complex systems design, architecture, advanced optimizations, and domain specialization.\n\n" +
                "Format each phase with learning topics and concrete action items.";

        String answer = geminiService.askAI(prompt);
        System.out.println("\n===== LEARNING ROADMAP =====");
        System.out.println(answer);
    }

    public void recommendProjects(int userId) {
        List<String> skills = getUserSkills(userId);
        List<String> certificates = getUserCertificates(userId);

        System.out.println("\n[AI Assistant] Analyzing your profile to recommend projects...");
        String prompt = "Analyze my current skills: " + (skills.isEmpty() ? "None listed" : String.join(", ", skills)) + "\n" +
                "and certificates: " + (certificates.isEmpty() ? "None listed" : String.join(", ", certificates)) + "\n\n" +
                "Recommend 3-5 relevant projects I can build to strengthen my portfolio, showcase my abilities, or learn new essential skills.\n" +
                "For each project, please provide:\n" +
                "- Project Title\n" +
                "- Target Audience / Use Case\n" +
                "- Key Technologies & Tools to use\n" +
                "- Difficulty Level (Beginner, Intermediate, Advanced)\n" +
                "- Learning Outcomes / Features to implement";

        String answer = geminiService.askAI(prompt);
        System.out.println("\n===== PROJECT RECOMMENDATIONS =====");
        System.out.println(answer);
    }

    private void parseTopRecommendation(String answer) {
        try {
            String[] lines = answer.split("\n");
            for (String line : lines) {
                if (line.toUpperCase().contains("TOP RECOMMENDATION:")) {
                    String candidate = line.substring(line.toUpperCase().indexOf("TOP RECOMMENDATION:") + "TOP RECOMMENDATION:".length()).trim();
                    candidate = candidate.replaceAll("\\*", "").trim();
                    if (!candidate.isEmpty()) {
                        lastPredictedCareer = candidate;
                        break;
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
