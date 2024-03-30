package com.jaideep.webfluxdemo.controller;

import com.jaideep.webfluxdemo.dto.Response;
import com.jaideep.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
public class ReactiveMathController {

    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return reactiveMathService.multiplicationTable(input);
    }

    @GetMapping(value = "/table/{input}/stream",produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return reactiveMathService.multiplicationTable(input);
    }


}
