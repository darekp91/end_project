package pl.coderslab.end_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.end_project.model.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT 1", nativeQuery = true)
    List<Question> findRandomQuestion();

    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Question> findRandomQuestions(int count);

    @Query("SELECT q FROM Question q WHERE q.era.id IN :eraIds")
    List<Question> findByEraIdIn(List<Long> eraIds);
}
