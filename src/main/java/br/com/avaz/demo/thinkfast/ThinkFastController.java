package br.com.avaz.demo.thinkfast;

import javax.servlet.http.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping( value = "/thinkfast",
                 produces = { "application/json" } )
public class ThinkFastController {

    @Autowired
    private ThinkFastGame game;

    @RequestMapping( value = "/play", method = RequestMethod.GET )
    public @ResponseBody
    Result play( @Valid Participant participant, HttpSession session ) {
        String id = session.getId();
        DeferredResult<Result> deferredResult = new DeferredResult<Result>();
        Screen screen = new WebScreen( deferredResult );
        return game.play( id, participant.getName(), screen );
    }

    @RequestMapping( value = "/bind", method = RequestMethod.GET )
    public @ResponseBody
    DeferredResult<Result> bind( HttpSession session ) {
        DeferredResult<Result> deferredResult = new DeferredResult<Result>();
        Screen screen = new WebScreen( deferredResult );
        game.bind( session.getId(), screen );
        return deferredResult;
    }

    @RequestMapping( value = "/questions/{id}/answer",
                     method = RequestMethod.POST,
                     consumes={"application/json"})
    public @ResponseBody
    Result answer( @PathVariable Long id, @RequestBody Answer answer, HttpSession session ) {
        return game.answer( session.getId(), id, answer );
    }
}