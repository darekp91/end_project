package pl.coderslab.end_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.end_project.model.Question;
import pl.coderslab.end_project.model.QuestionData;
import pl.coderslab.end_project.repository.QuestionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.findRandomQuestion();
        return questions.isEmpty() ? null : questions.get(0);
    }
    public QuestionData getRandomQuestionData(List<Long> eraId) {
        Question question;
        if (eraId != null && !eraId.isEmpty()) {
            question = getRandomQuestionFromEra(eraId);
        } else {
            question = getRandomQuestion();
        }

        if (question == null) {
            return null;
        }

        List<String> answers = Arrays.asList(
                question.getGood_answer(),
                question.getBad1_answer(),
                question.getBad2_answer(),
                question.getBad3_answer()
        );
        Collections.shuffle(answers);

        return new QuestionData(question.getQuestionId(), question.getQuestion(), answers, question.getHint());
    }

    public List<String> getHalfOnHalfOptions(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return Collections.emptyList();
        }

        List<String> wrongAnswers = new ArrayList<>();
        wrongAnswers.add(question.getBad1_answer());
        wrongAnswers.add(question.getBad2_answer());
        wrongAnswers.add(question.getBad3_answer());
        Collections.shuffle(wrongAnswers);

        // Zwracamy dwie błędne odpowiedzi
        return wrongAnswers.subList(0, 2);
    }
    public boolean checkAnswer(Long questionId, String selectedAnswer) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return false;
        }
        return question.getGood_answer().equals(selectedAnswer);
    }
    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    public Question getRandomQuestionFromEra(List<Long> eraIds) {
        List<Question> questions = questionRepository.findByEraIdIn(eraIds);
        if (questions.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }

    public List<Question> getRandomQuestions(List<Long> eraIds, int count) {
        List<Question> questions;
        if (eraIds == null || eraIds.isEmpty()) {
            // Losowanie pytań z całej puli
            questions = questionRepository.findRandomQuestions(count);
        } else {
            // Losowanie pytań z określonych epok
            questions = questionRepository.findByEraIdIn(eraIds);
            Collections.shuffle(questions);
            if (questions.size() > count) {
                return questions.subList(0, count);
            }
        }
        return questions;
    }


    public List<QuestionData> getRandomQuestionsData(List<Long> eraIds, int count) {
        List<Question> questions = getRandomQuestions(eraIds, count);
        return questions.stream().map(question -> {
            List<String> answers = Arrays.asList(question.getGood_answer(),
                    question.getBad1_answer(),
                    question.getBad2_answer(),
                    question.getBad3_answer());
            Collections.shuffle(answers);
            return new QuestionData(question.getQuestionId(), question.getQuestion(), answers, question.getHint());
        }).collect(Collectors.toList());
    }





}
