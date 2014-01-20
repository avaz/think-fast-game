package br.com.avaz.demo.thinkfast;

import org.springframework.web.context.request.async.DeferredResult;

public class WebScreen implements Screen {

    private DeferredResult<Result> deferredResult;

    public WebScreen( DeferredResult<Result> deferredResult ) {
        this.deferredResult = deferredResult;
    }

    public void show( Result result ) {
        deferredResult.setResult( result );
    }
}
