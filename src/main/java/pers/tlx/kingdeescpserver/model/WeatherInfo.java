package pers.tlx.kingdeescpserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 天气信息模型类
 */
public class WeatherInfo {
    private String province;
    private String city;
    private String adcode;
    private String weather;
    private String temperature;
    private String winddirection;
    private String windpower;
    private String humidity;
    private String reporttime;
    
    @JsonProperty("temperature_float")
    private String temperatureFloat;
    
    @JsonProperty("humidity_float")
    private String humidityFloat;

    // Getters and Setters
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getTemperatureFloat() {
        return temperatureFloat;
    }

    public void setTemperatureFloat(String temperatureFloat) {
        this.temperatureFloat = temperatureFloat;
    }

    public String getHumidityFloat() {
        return humidityFloat;
    }

    public void setHumidityFloat(String humidityFloat) {
        this.humidityFloat = humidityFloat;
    }
} 