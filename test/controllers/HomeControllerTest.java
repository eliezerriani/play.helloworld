package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import org.junit.Test;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/produtos/0/build");

        Result result = route(app, request);
        
        System.out.println( result.body() );
        
        assertTrue( contentAsString(result).contains("Welcome") );
        
        assertEquals(OK, result.status());
    }

}
