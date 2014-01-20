package br.com.avaz.demo.thinkfast;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Answer {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO )
    private Long id;
    private String description;

    public Answer() {
    }

    public Answer( String description ) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Override
    public boolean equals( Object o ) {
        return super.equals( o ) || 
                (this.getClass().isInstance( o ) 
                && this.hashCode() == o.hashCode() );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + ( this.id != null ? this.id.hashCode() : 0 );
        return hash;
    }
    
    
    
    
}
