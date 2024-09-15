package com.domain_expansion.integrity.ai.domain.model.weather.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
public class WeatherResponse {

    private WeatherRes response;


    @Getter
    public static class WeatherRes {

        private HeaderResponse header;
        private BodyResponse body;

    }

    @Getter
    public static class HeaderResponse {

        private String resultCode;
        private String resultMsg;

    }

    @Getter
    public static class BodyResponse {

        private String dataType;
        private Item items;

    }

    @Getter
    public static class Item {

        private List<WeatherItem> item;

    }

    @ToString
    @Getter
    public static class WeatherItem {

        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private Integer nx;
        private Integer ny;

    }
}
