package com.example.user.myapp.weatherInfo;

import com.example.user.myapp.weatherInfo.Clouds;
import com.example.user.myapp.weatherInfo.Coord;
import com.example.user.myapp.weatherInfo.Main;
import com.example.user.myapp.weatherInfo.Sys;
import com.example.user.myapp.weatherInfo.Weather;
import com.example.user.myapp.weatherInfo.Wind;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class FactWeather {
    public FactWeather() {
        super();
    }

    public String base;
    private String id;
    private String name;
    private int cod;
    private Weather weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    public Coord coord;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(ObjectMapper objectMapper, JsonNode node) {
        try {
            ObjectMapper mapper = new ObjectMapper();


            //coord = mapper.reader().forType(Coord.class).readValue(node.get("coord").toString());
            coord = mapper.readValue(node.get("coord").toString(), Coord.class);


        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("com.example.user.myapp.FactWeather{");
        sb.append("base='").append(base).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", cod=").append(cod);
        sb.append(", weather=").append(weather);
        sb.append(", main=").append(main);
        sb.append(", wind=").append(wind);
        sb.append(", clouds=").append(clouds);
        sb.append(", sys=").append(sys);
        sb.append(", coord=").append(coord);
        sb.append('}');
        return sb.toString();
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
    public void setWeather(ObjectMapper objectMapper, JsonNode node) {
        try {
            weather = objectMapper.readValue(node.get("weather").toString(), Weather.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    public void setMain(ObjectMapper objectMapper, JsonNode node) {
        try {
            main = objectMapper.readValue(node.get("main").toString(), Main.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public void setWind(ObjectMapper objectMapper, JsonNode node) {
        try {
            wind = objectMapper.readValue(node.get("wind").toString(), Wind.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
    public void setClouds(ObjectMapper objectMapper, JsonNode node) {
        try {
            clouds = objectMapper.readValue(node.get("clouds").toString(), Clouds.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
    public void setSys(ObjectMapper objectMapper, JsonNode node) {
        try {
            sys = objectMapper.readValue(node.get("sys").toString(), Sys.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setBase(String base) {
        this.base = base;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getBase() {
        return base;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }




   /* rain
    rain.3h Rain volume for the last 3 hours
            snow
    snow.3h Snow volume for the last 3 hours
    dt Time of data calculation, unix, UTC
           */



}
