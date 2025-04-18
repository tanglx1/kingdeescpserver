package pers.tlx.kingdeescpserver;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pers.tlx.kingdeescpserver.service.KingdeeService;
import pers.tlx.kingdeescpserver.service.WeatherService;

@SpringBootApplication
public class KingdeescpserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(KingdeescpserverApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
	}

	@Bean
	public ToolCallbackProvider kingdeeBillTools(KingdeeService kingdeeService) {
		return MethodToolCallbackProvider.builder().toolObjects(kingdeeService).build();
	}

}
