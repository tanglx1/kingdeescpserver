package pers.tlx.kingdeescpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.tlx.kingdeescpserver.model.WeatherInfo;
import pers.tlx.kingdeescpserver.model.WeatherResponse;
import org.springframework.ai.tool.annotation.Tool;

import java.io.IOException;

/**
 * 天气服务实现类
 */
@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${weather.api.key:2bc5e73369ac12cdc4942a42f71db690}")
    private String apiKey;

    @Value("${weather.api.url:https://restapi.amap.com/v3/weather/weatherInfo}")
    private String apiUrl;

    @Tool(description = "根据城市名称查询当前天气信息")
    public String getWeatherInfo(@ToolParam(description = "城市名称，例如：北京") String city) {
        try {
            // 构建API URL
            String url = String.format("%s?city=%s&key=%s", apiUrl, city, apiKey);
            
            // 记录日志
             logger.info("调用高德天气API，完整URL: {}", url);
            
            // 调用API
            String response = restTemplate.getForObject(url, String.class);
            
            // 记录响应日志
             logger.info("高德天气API响应: {}", response);
            
            // 解析响应
            WeatherResponse weatherResponse = objectMapper.readValue(response, WeatherResponse.class);
            
            // 检查响应状态
            if (!"1".equals(weatherResponse.getStatus()) || weatherResponse.getLives() == null || weatherResponse.getLives().isEmpty()) {
                // logger.error("获取天气信息失败: {}", weatherResponse.getInfo());
                return "获取天气信息失败：" + weatherResponse.getInfo();
            }
            
            // 获取天气信息
            WeatherInfo weatherInfo = weatherResponse.getLives().get(0);
            
            // 格式化输出
            return formatWeatherInfo(weatherInfo);
        } catch (IOException e) {
            return "解析天气信息失败：" + e.getMessage();
        } catch (Exception e) {
             logger.error("获取天气信息失败", e);
            return "获取天气信息失败：" + e.getMessage();
        }
    }
    
    /**
     * 格式化天气信息
     * 
     * @param weatherInfo 天气信息对象
     * @return 格式化的天气信息字符串
     */
    private String formatWeatherInfo(WeatherInfo weatherInfo) {
        StringBuilder result = new StringBuilder();
        result.append("城市：").append(weatherInfo.getCity()).append("\n");
        result.append("天气：").append(weatherInfo.getWeather()).append("\n");
        result.append("温度：").append(weatherInfo.getTemperature()).append("度\n");
        result.append("风向：").append(weatherInfo.getWinddirection()).append("\n");
        result.append("风力：").append(weatherInfo.getWindpower()).append("\n");
        result.append("湿度：").append(weatherInfo.getHumidity()).append("\n");
        result.append("报道时间：").append(weatherInfo.getReporttime());
        return result.toString();
    }
} 