package br.com.avaz.demo.thinkfast;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO )
    private Long id;
    private String description;
    @ManyToMany(cascade= {CascadeType.PERSIST, 
                          CascadeType.MERGE , 
                          CascadeType.REFRESH },
                fetch= FetchType.EAGER )
    @JoinTable(name="questions_answers")
    private List<Answer> answers;
    @OneToOne(fetch= FetchType.EAGER )
    private Answer answer;

    public Question() {
    }

    public Question( String description, 
                     List<Answer> answers, 
                     Answer answer ) {
        this.description = description;
        this.answers = answers;
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
    
    @JsonIgnore
    public Answer getAnswer() {
        return answer;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setAnswers( List<Answer> answers ) {
        this.answers = answers;
    }

    public void setAnswer( Answer answer ) {
        this.answer = answer;
    }

}