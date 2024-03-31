package com.jaideep.webfluxdemo.controller;

import com.jaideep.webfluxdemo.dto.Response;
import com.jaideep.webfluxdemo.exception.InputValidationException;
import com.jaideep.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math-validation")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("/square/{input}")
    public Mono<Response>   findSquare(@PathVariable int input) {
        if(input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("/reactive-square/{input}")
    public Mono<Response>  findReactiveSquare(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer,sink) ->{
                   if(integer < 10 || integer > 20) {
                       sink.error(new InputValidationException(integer));
                   } else {
                       sink.next(integer);
                   }
                })
                .cast(Integer.class)
                .flatMap(i -> this.reactiveMathService.findSquare(input));
    }

}
