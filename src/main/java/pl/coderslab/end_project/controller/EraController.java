package pl.coderslab.end_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.end_project.model.Question;
import pl.coderslab.end_project.model.QuestionData;
import pl.coderslab.end_project.model.QuestionResult;
import pl.coderslab.end_project.service.EraService;
import pl.coderslab.end_project.service.QuestionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/eras")
public class EraController {

    @Autowired
    private EraService eraService;

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String getAllEras(Model model) {
        model.addAttribute("eras", eraService.getAllEras());
        return "eras"; // Zwraca nazwę szablonu Thymeleaf
    }

    @GetMapping("/random-question")
    public String randomQuestion(@RequestParam(required = false) List<Long> eraIds, Model model) {
        // Dodaj logowanie, aby sprawdzić otrzymane eraIds
        System.out.println("Received eraIds: " + eraIds);
        QuestionData questionData = questionService.getRandomQuestionData(eraIds);
        model.addAttribute("questionData", questionData);
        return "randomQuestion";
    }

    @GetMapping("/half-on-half/{questionId}")
    @ResponseBody
    public List<String> halfOnHalf(@PathVariable Long questionId) {
        return questionService.getHalfOnHalfOptions(questionId);
    }

    @PostMapping("/check-answer")
    public String checkAnswer(@RequestParam("selectedAnswer") String selectedAnswer,
                              @RequestParam("questionId") Long questionId,
                              Model model) {
        Question question = questionService.getQuestionById(questionId);
        boolean isCorrect = question != null && question.getGood_answer().equals(selectedAnswer);

        model.addAttribute("question", question); // Dodano obiekt question do modelu
        model.addAttribute("selectedAnswer", selectedAnswer); // Dodano wybraną odpowiedź do modelu
        model.addAttribute("isCorrect", isCorrect);

        if (!isCorrect && question != null) {
            model.addAttribute("correctAnswer", question.getGood_answer());
        }

        return "resultPage";
    }


    @GetMapping("/quiz")
    public String quiz(@RequestParam(required = false) List<Long> eraIds,
                       @RequestParam(defaultValue = "5") int questions,
                       Model model) {
        List<QuestionData> quizQuestions = questionService.getRandomQuestionsData(eraIds, questions);
        model.addAttribute("quizQuestions", quizQuestions);
        return "quizPage";
    }

    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam Map<String, String> allParams, Model model) {
        int correctCount = 0;
        int totalQuestions = Integer.parseInt(allParams.get("totalQuestions"));
        List<QuestionResult> questionResults = new ArrayList<>();

        for (int i = 0; i < totalQuestions; i++) {
            Long questionId = Long.parseLong(allParams.get("questionId" + i));
            String selectedAnswer = allParams.get("question" + i);
            Question question = questionService.getQuestionById(questionId);

            boolean isCorrect = question != null && question.getGood_answer().equals(selectedAnswer);
            if (isCorrect) {
                correctCount++;
            }

            // Uzyskanie listy wszystkich odpowiedzi i oddzielenie błędnych
            List<String> allAnswers = Arrays.asList(
                    question.getGood_answer(),
                    question.getBad1_answer(),
                    question.getBad2_answer(),
                    question.getBad3_answer()
            );
            List<String> incorrectAnswers = allAnswers.stream()
                    .filter(ans -> !ans.equals(question.getGood_answer()))
                    .collect(Collectors.toList());

            questionResults.add(new QuestionResult(question.getQuestion(), question.getGood_answer(), selectedAnswer, isCorrect, incorrectAnswers));
        }

        model.addAttribute("questionResults", questionResults);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalQuestions", totalQuestions);

        return "quizResults";
    }
}

