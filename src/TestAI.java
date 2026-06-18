import ai.GeminiService;

public class TestAI {

    public static void main(String[] args) {

        GeminiService ai =
                new GeminiService();

        String answer =
                ai.askAI(
                        "How can I become a VLSI Engineer?"
                );

        System.out.println(answer);
    }
}