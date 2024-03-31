package com.jaideep.webfluxdemo.service;

import com.jaideep.webfluxdemo.dto.MultipleDto;
import com.jaideep.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                    .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                    .delayElements(java.time.Duration.ofSeconds(1))
                    .doOnNext(i -> System.out.println("reactive-math-service processing : " + i))
                    .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultipleDto> dtoMono) {
        return dtoMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }

}
