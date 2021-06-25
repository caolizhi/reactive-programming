package top.caolizhi.example.reative.config;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import top.caolizhi.example.reative.dto.Person;

/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/6/16 9:39
 *@Version 1.0
 **/
@Component
public class PersonHandler {

	public Mono<ServerResponse> route1(ServerRequest request) {
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(Person.builder().name("caolizhi").id("cn17911").build()));
	}

	public Mono<ServerResponse> route2(ServerRequest request) {
		return ServerResponse.accepted()
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue("created successfully !"));
	}

}
