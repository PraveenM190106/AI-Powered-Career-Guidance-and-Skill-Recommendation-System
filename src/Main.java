import service.UserService;
import service.SkillService;
import service.CertificateService;
import service.CareerService;
import service.ReportService;
import java.util.*;
import ai.GeminiService;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService =
                new UserService();

        SkillService skillService =
                new SkillService();

        CertificateService certificateService =
                new CertificateService();

        GeminiService geminiService =
                new GeminiService();

        CareerService careerService =
                new CareerService();

        ReportService reportService =
                new ReportService(careerService);

        while(true) {

            System.out.println("\n===== AI Career Assistant =====");

            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:
                    userService.registerUser();
                    break;

                case 2:

                    int userId =
                            userService.loginUser();

                    if(userId != -1) {

                        while(true) {

                            System.out.println(
                                    "\n===== DASHBOARD =====");

                            System.out.println("1. Add Skill");
                            System.out.println("2. View Skills");
                            System.out.println("3. delete Skills");
                            System.out.println();
                            System.out.println("4. Add Certificate");
                            System.out.println("5. View Certificates");
                            System.out.println("6. delete certificate");
                            System.out.println();
                            System.out.println("7. ASK AI");
                            System.out.println();
                            System.out.println("8. Career Prediction");
                            System.out.println("9. Skill Gap Analysis");
                            System.out.println("10. Learning Roadmap");
                            System.out.println("11. Project Recommendation");
                            System.out.println();
                            System.out.println("12. Generate Report");
                            System.out.println();
                            System.out.println("13. Logout");
                            System.out.print(
                                    "Enter Choice : ");

                            int option =
                                    sc.nextInt();

                            sc.nextLine();

                            switch(option) {

                                case 1:
                                    skillService.addSkill(userId);
                                    break;

                                case 2:
                                    skillService.viewSkills(userId);
                                    break;

                                case 3:
                                    skillService.deleteSkill(userId);
                                    break;

                                case 4:
                                    certificateService.addCertificate(userId);
                                    break;

                                case 5:
                                    certificateService.viewCertificates(userId);
                                    break;

                                case 6:
                                    certificateService.deleteCertificate(userId);
                                    break;

                                case 7:

                                    System.out.print("Ask AI : ");
                                    String question = sc.nextLine();

                                    String answer =
                                            geminiService.askAI(question);

                                    System.out.println("\n===== AI RESPONSE =====");
                                    System.out.println(answer);

                                    break;

                                case 8:
                                    careerService.predictCareer(userId);
                                    break;

                                case 9:
                                    careerService.analyzeSkillGap(userId, sc);
                                    break;

                                case 10:
                                    careerService.generateRoadmap(userId, sc);
                                    break;

                                case 11:
                                    careerService.recommendProjects(userId);
                                    break;

                                case 12:
                                    reportService.generateReport(userId);
                                    break;

                                case 13:
                                    System.out.println("Logged Out");
                                    break;

                                default:
                                    System.out.println("Invalid Choice");
                            }

                            if(option == 13) {
                                break;
                            }
                        }
                    }

                    break;

                case 3:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}