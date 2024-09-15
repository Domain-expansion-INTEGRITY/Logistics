package com.domain_expansion.integrity.ai.domain.model.weather;

import com.domain_expansion.integrity.ai.domain.model.weather.response.WeatherResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherConfig weatherConfig;
    private final WeatherClient weatherClient;

    /**
     * 날씨 정보들 중에서 필요하다고 여겨지는 부분만 추출해온다.
     */
    List<String> pickContent = List.of(new String[]{"POP", "PCP", "REH", "SKY", "TMP"});


    @Cacheable(cacheNames = "weather", key = "args[2]")
    public String getDailyWeatherInfo(String x, String y, String hubId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = LocalDate.now().format(formatter);

        try {
            WeatherResponse weatherInfo = weatherClient.findWeatherInfo(weatherConfig.key(), today,
                x, y);

            return weatherInfo.getResponse().getBody().getItems().getItem()
                .stream().filter(item -> pickContent.contains(item.getCategory()))
                .map(WeatherPick::from).toList()
                .toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

}
