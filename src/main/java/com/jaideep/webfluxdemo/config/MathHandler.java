package com.jaideep.webfluxdemo.config;

import com.jaideep.webfluxdemo.dto.InputValidationFailedResponse;
import com.jaideep.webfluxdemo.dto.MultipleDto;
import com.jaideep.webfluxdemo.dto.Response;
import com.jaideep.webfluxdemo.exception.InputValidationException;
import com.jaideep.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class MathHandler {

    @Autowired
    private ReactiveMathService reactiveMathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));

        if (input <10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        return ServerResponse.ok()
                .body(reactiveMathService.findSquare(input), Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        return ServerResponse.ok()
                .body(reactiveMathService.multiplicationTable(input), Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        return ServerResponse.ok().contentType(org.springframework.http.MediaType.TEXT_EVENT_STREAM)
                .body(reactiveMathService.multiplicationTable(input), Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultipleDto> requestDTOMono = serverRequest.bodyToMono(MultipleDto.class);
        return ServerResponse.ok()
                .body(reactiveMathService.multiply(requestDTOMono), Response.class);
    }


    public Mono<ServerResponse> simpleCalculator(ServerRequest serverRequest) {
        int firstValue = Integer.parseInt(serverRequest.pathVariable("first"));
        int secondValue = Integer.parseInt(serverRequest.pathVariable("second"));

        Optional<String> operation = serverRequest.headers().header("OPERATION").stream().findFirst();

        if (!operation.isPresent()) {
            return ServerResponse.badRequest().bodyValue("Operation is mandatory");
        }else{
            if(operation.get().equalsIgnoreCase("ADD")){
                return ServerResponse.ok()
                        .bodyValue(firstValue + secondValue);
            }else if(operation.get().equalsIgnoreCase("SUB")){
                return ServerResponse.ok()
                        .bodyValue(firstValue - secondValue);
            }else if (operation.get().equalsIgnoreCase("MUL")){
                return ServerResponse.ok()
                        .bodyValue(firstValue * secondValue);
            }else if(operation.get().equalsIgnoreCase("DIV")){
                return ServerResponse.ok()
                        .bodyValue(firstValue / secondValue);
            }else{
                return ServerResponse.badRequest().bodyValue("Invalid Operation");
            }

        }

    }

}

