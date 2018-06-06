package com.example.user.myapp;

import java.util.concurrent.TimeUnit;

public class FactWeather {
 private int   temp;//Температура (°C).	Число
    private int  feels_like;//	Ощущаемая температура (°C).	Число
    private int temp_water;//	Температура воды (°C). Параметр возвращается для регионов, где данная информация актуальна.	Число
    private String icon;//	Код иконки погоды. Иконка доступна по адресу https://yastatic.net/weather/i/icons/blueye/color/svg/<значение из поля icon>.svg.	Строка
    private String condition;//	Код расшифровки погодного описания. Расшифровка доступна в объекте l10n или ответе на запрос Переводы.	Строка
    private int wind_speed;//	Скорость ветра (в м/с).	Число
    private int wind_gust;//	Скорость порывов ветра (в м/с).	Число
    private enum wind_dir {  //	Направление ветра. Возможные значения:
                    nw,// — северо-западное.
                    n,// — северное.
                    ne,// —северо-восточное.
                    e ,//—восточное.
                    se,// —юго-восточное.
                    s,// —южное.
                    sw, //—юго-западное.
                    w,// —западное.
                    с/// —штиль.
    };
    private int pressure_mm;//	Давление (в мм рт. ст.).	Число
    private int pressure_pa;//	Давление (в гектопаскалях).	Число
    private int humidity;//	Влажность воздуха (в процентах).	Число
    private enum daytime {//	Светлое или темное время суток. Возможные значения:
        d,//— светлое время суток.
        n// — темное время суток.
    };

    private boolean polar;//	Признак полярного дня или ночи.	Логический
    private enum season {//	Время года в данном регионе. Возможные значения:
        summer,// лето.
        autumn,// осень.
        winter,// зима.
        spring// весна.
    };
    private TimeUnit obs_time;//	Время замера погодных данных в формате Unixtime.	Число
    private int prec_type;/*{ //Тип осадков. Возможные значения:
            0,// без осадков.
            1,// дождь.
            2,// дождь со снегом.
             3 // снег.
                };
            Число
            */
    private float prec_strength;/*	Сила осадков. Возможные значения:
            0 — без осадков.
            0.25 — слабый дождь/слабый снег.
            0.5 — дождь/снег.
            0.75 — сильный дождь/сильный снег.
            1 — сильный ливень/очень сильный снег.
            Число
            */
    private float cloudness;//	Облачность. Возможные значения:

    public String getCondition() {
        return condition;
    }

    /*        0 — ясно.
                    0.25 — малооблачно.
                    0.5 — облачно с прояснениями.
                    0.75 — облачно с прояснениями.
                    1 — пасмурно.

                Число
                */
    private String temp_color;//	Цвет для отображения значения температуры.	Строка

    public FactWeather(String cloudness) {
        this.condition = cloudness;
    }
}
