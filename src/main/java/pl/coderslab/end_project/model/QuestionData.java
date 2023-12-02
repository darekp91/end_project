package pl.coderslab.end_project.model;

import java.util.List;

public class QuestionData {

    private Long id;
    private String question;
    private List<String> answers;
    private String hint;

    // Dodanie konstruktora z argumentami
    public QuestionData(Long id,String question, List<String> answers, String hint) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.hint = hint;
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
