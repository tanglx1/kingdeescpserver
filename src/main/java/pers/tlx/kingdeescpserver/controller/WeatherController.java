package pers.tlx.kingdeescpserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.tlx.kingdeescpserver.service.WeatherService;

/**
 * 天气控制器
 */
@RestController
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 获取指定城市的天气信息
     * 
     * @param city 城市名称
     * @return 格式化的天气信息字符串
     */
    @GetMapping("/weather")
    public String getWeather(@RequestParam(value = "city", defaultValue = "北京") String city) {
        String result = weatherService.getWeatherInfo(city);
        return result;
    }
} 