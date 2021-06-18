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
 *@Author 小志哥
 *@Date 2021/6/16 9:30
 *@Version 1.0
 **/

@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(PersonHandler personHandler) {
		return RouterFunctions.route(GET("02").and(accept(MediaType.APPLICATION_JSON)), personHandler::route1);
	}
}
