package br.com.avaz.demo.thinkfast;

public interface QuestionRepositoryExtension {
    
    
    Question findNext(Question answered);
    
    Question getCurrent();
    
}
