package pl.coderslab.end_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "era_id")
    private Era era;

    private String question;
    private String good_answer;
    private String bad1_answer;
    private String bad2_answer;
    private String bad3_answer;
    private String hint;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Era getEra() {
        return era;
    }

    public void setEra(Era era) {
        this.era = era;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getGood_answer() {
        return good_answer;
    }

    public void setGood_answer(String good_answer) {
        this.good_answer = good_answer;
    }

    public String getBad1_answer() {
        return bad1_answer;
    }

    public void setBad1_answer(String bad1_answer) {
        this.bad1_answer = bad1_answer;
    }

    public String getBad2_answer() {
        return bad2_answer;
    }

    public void setBad2_answer(String bad2_answer) {
        this.bad2_answer = bad2_answer;
    }

    public String getBad3_answer() {
        return bad3_answer;
    }

    public void setBad3_answer(String bad3_answer) {
        this.bad3_answer = bad3_answer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
