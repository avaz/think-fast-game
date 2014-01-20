package br.com.avaz.demo.thinkfast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThinkFastGame {

    private final ConcurrentHashMap<String, Participant> participants;
    private final Lock lock;
    @Autowired
    private QuestionRepository questionRepository;

    public ThinkFastGame() {
        this.participants = new ConcurrentHashMap<String, Participant>();
        this.lock = new ReentrantLock();
    }

    public Result play( String id, String name, Screen screen ) {
        lock.lock();
        Result result = null;
        try {
            Participant participant =
                    new Participant( id, name, screen );
            participants.put( id, participant );
            result = new Result( questionRepository.getCurrent(), "Welcome!", new ArrayList<Participant>(participants.values()) );
        }
        finally {
            lock.unlock();
        }
        return result;
    }

    public void bind( String id, Screen screen ) {
        Participant participant = participants.get( id );
        participant.setScreen( screen );
    }

    public Result answer( String id, Long questionId, Answer answer ) {
        lock.lock();
        Result result = null;
        try {
            Question question = questionRepository.findOne( questionId );
            Question current = questionRepository.getCurrent();
            if ( current.getAnswer().equals( answer ) ) {
                current = questionRepository.
                        findNext( current );
                final List<Participant> all = new ArrayList<Participant>( participants.values() );
                Participant winner = participants.remove( id );
                winner.incrementScore();
                winner.notify(
                        new Result( current,
                                    "Congratzzzz!! :)", all ) );
                for ( Participant participant :
                        participants.values() ) {
                    participant.notify(
                            new Result( current,
                                        String.format( "O partcipante %s respondeu "
                            + "mais rapido, tente novamente", winner.getName() ), all ) );
                }
                participants.put( id, winner );

            }
            else {
                result = new Result( "Nope!! :(" );

            }
        }
        finally {
            lock.unlock();
        }
        return result;
    }

    @PostConstruct
    public void init() {
        Answer correct = new Answer( "Washington DC" );
        questionRepository.save( new Question( "Qual a capital dos EUA?", Arrays.asList( new Answer[]{ correct, new Answer( "California" ), new Answer( "Nevada" ) } ), correct ) );
        correct = new Answer( "Moscou" );
        questionRepository.save( new Question( "Qual a capital da Russia?", Arrays.asList( new Answer[]{ new Answer( "Berlin" ), new Answer( "Paris" ), correct } ), correct ) );
    }
}