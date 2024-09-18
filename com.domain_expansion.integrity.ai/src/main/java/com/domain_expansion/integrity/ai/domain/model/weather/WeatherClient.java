package com.domain_expansion.integrity.ai.domain.model.weather;

import com.domain_expansion.integrity.ai.domain.model.weather.response.WeatherResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface WeatherClient {

    @GetExchange("?pageNo=1"
        + "&numOfRows=200"
        + "&dataType=JSON"
        + "&base_time=0550")
    WeatherResponse findWeatherInfo(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("base_date") String baseDate,
        @RequestParam("nx") String nx,
        @RequestParam("ny") String ny
    );

}
