import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    public QuizQuestion(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

class Quiz {
    private QuizQuestion[] questions;
    private int currentQuestionIndex;
    private int score;

    public Quiz(QuizQuestion[] questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void start() {
        if (currentQuestionIndex < questions.length) {
            displayQuestion();
        } else {
            displayResult();
        }
    }

    private void displayQuestion() {
        QuizQuestion currentQuestion = questions[currentQuestionIndex];
        System.out.println("Question: " + currentQuestion.getQuestion());
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");
                processAnswer(-1); // -1 indicates no answer within time
            }
        }, 10000); // Timer set to 10 seconds

        Scanner scanner = new Scanner(System.in);
        System.out.print("Your answer (1-" + options.length + "): ");
        int userAnswer = scanner.nextInt();
        timer.cancel();
        processAnswer(userAnswer - 1);
    }

    private void processAnswer(int userAnswer) {
        QuizQuestion currentQuestion = questions[currentQuestionIndex];
        if (userAnswer == currentQuestion.getCorrectAnswerIndex()) {
            System.out.println("Correct!");
            score++;
        } else if (userAnswer == -1) {
            System.out.println("No answer submitted. Moving to the next question.");
        } else {
            System.out.println("Incorrect!");
        }
        currentQuestionIndex++;
        start();
    }

    private void displayResult() {
        System.out.println("Quiz finished!");
        System.out.println("Your score: " + score + "/" + questions.length);
    }
}

public class Main {
    public static void main(String[] args) {
        QuizQuestion[] questions = new QuizQuestion[5];
        questions[0] = new QuizQuestion("What is the capital of France?", 
                                         new String[]{"Paris", "Rome", "Berlin", "Madrid"}, 0);
        questions[1] = new QuizQuestion("What is the largest planet in our solar system?", 
                                         new String[]{"Jupiter", "Saturn", "Mars", "Earth"}, 0);
        questions[2] = new QuizQuestion("Who wrote 'To Kill a Mockingbird'?", 
                                         new String[]{"Harper Lee", "J.K. Rowling", "Stephen King", "George Orwell"}, 0);
        questions[3] = new QuizQuestion("What is the chemical symbol for water?", 
                                         new String[]{"H2O", "CO2", "O2", "NaCl"}, 0);
        questions[4] = new QuizQuestion("Which year did the Titanic sink?", 
                                         new String[]{"1912", "1905", "1920", "1935"}, 0);

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
