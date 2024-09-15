package com.domain_expansion.integrity.ai.domain.model.weather;

import com.domain_expansion.integrity.ai.domain.model.weather.response.WeatherResponse.WeatherItem;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record WeatherPick(
    String baseDate,
    String baseTime,
    String category,
    String fcstTime,
    String fcstValue
) {

    public static WeatherPick from(WeatherItem weatherItem) {
        return WeatherPick.builder()
            .baseDate(weatherItem.getBaseDate())
            .baseTime(weatherItem.getBaseTime())
            .category(weatherItem.getCategory())
            .fcstTime(weatherItem.getFcstTime())
            .fcstValue(weatherItem.getFcstValue())
            .build();
    }

}
