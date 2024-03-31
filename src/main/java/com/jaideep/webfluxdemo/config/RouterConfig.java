package com.jaideep.webfluxdemo.config;

import com.jaideep.webfluxdemo.dto.InputValidationFailedResponse;
import com.jaideep.webfluxdemo.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    MathHandler handler;


    // Path based routing is not working after upgrading the spring boot version to 3.2.x
//    public RouterFunction<ServerResponse> highLevelRouterFunction(){
//        return RouterFunctions.route()
//                .path("router", this::mathRouterFunction)
//                .build();
//    }

    @Bean
    public RouterFunction<ServerResponse> mathRouterFunction() {
        return RouterFunctions.route()
                .GET("/router/square/{input}", handler::squareHandler)
                .GET("/router/table/{input}", handler::tableHandler)
                .GET("/router/table/stream/{input}", handler::tableStreamHandler)
                .POST("/router/multiply", handler::multiplyHandler)
                .GET("/router/square/exception/{input}", handler::squareHandler)
                .GET("/router/calculator/{first}/{second}", handler::simpleCalculator)
                .onError(InputValidationException.class, exceptionHandler())
                .build();

    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (throwable, request) -> {
            InputValidationException ex = (InputValidationException) throwable;
            InputValidationFailedResponse response = new InputValidationFailedResponse();
            response.setInput(ex.getInput());
            response.setMessage("Updated Err Message: " + ex.getMessage() + " for input: " + ex.getInput() + " from RouterConfig");
            response.setErrorCode(ex.getErrorCode());

            return ServerResponse.badRequest().bodyValue(response);
        };
    }

}
