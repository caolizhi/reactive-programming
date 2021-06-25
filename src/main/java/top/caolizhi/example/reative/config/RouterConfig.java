package top.caolizhi.example.reative.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/6/16 9:30
 *@Version 1.0
 **/

@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(PersonHandler personHandler) {

		RouterFunction<ServerResponse> route = RouterFunctions.route(
			GET("04").and(accept(MediaType.APPLICATION_JSON)), personHandler::route1)
			.andRoute(GET("05"), personHandler::route1);

		return RouterFunctions.route()
			.path("/person",
				builder -> builder
					.GET("/{id}", accept(MediaType.APPLICATION_JSON), personHandler::route1)
					.GET(accept(MediaType.APPLICATION_JSON), personHandler::route1)
					.POST("/person", personHandler::route2)
				)
			.add(route)
			.build();
	}
}
