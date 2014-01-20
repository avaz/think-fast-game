package br.com.avaz.demo.thinkfast;

import java.util.Collections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class QuestionRepositoryImpl
        extends QueryDslRepositorySupport
        implements QuestionRepositoryExtension {

    private Question current;
    
    public QuestionRepositoryImpl() {
        super( Question.class );
    }

    @Override
    public Question findNext( Question answered ) {
        QQuestion question = QQuestion.question;
        List<Question> questions = from( question ).
                where( question.ne( answered ) ).
                limit( 10 ).list( question );
        Collections.shuffle( questions );
        this.current =  questions.get( 0 );
        return this.current;
    }

    @Override
    public Question getCurrent() {
        if ( this.current == null ) {
            QQuestion question =  QQuestion.question;
            this.current = from( question ).
                    singleResult( question);
        }
        return this.current;
    }
}
