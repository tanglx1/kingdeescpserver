package pers.tlx.kingdeescpserver.model;

import java.util.List;

/**
 * 高德天气API响应模型类
 */
public class WeatherResponse {
    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<WeatherInfo> lives;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<WeatherInfo> getLives() {
        return lives;
    }

    public void setLives(List<WeatherInfo> lives) {
        this.lives = lives;
    }
} 