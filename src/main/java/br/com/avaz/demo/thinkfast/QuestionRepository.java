package br.com.avaz.demo.thinkfast;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends
        QuestionRepositoryExtension,
        JpaRepository<Question, Long> {

    List<Question> findByDescriptionAndAnswersDescription( String description, String answerDescription );
}