package com.domain_expansion.integrity.delivery.application.client.response;

import java.time.LocalDateTime;
import java.util.List;

public record DirectionResponseDto(
        Integer code,
        String message,
        LocalDateTime currentDateTime,
        DirectionResponseDtoRoute route
) {

    public record DirectionResponseDtoRoute(
            List<DirectionResponseDtoRouteTraoptimal> traoptimal
    ) {

        public record DirectionResponseDtoRouteTraoptimal(
                DirectionResponseDtoRouteTraoptimalSummary summary
        ) {

            public record DirectionResponseDtoRouteTraoptimalSummary(
                    DirectionResponseDtoRouteTraoptimalSummaryStart start,
                    DirectionResponseDtoRouteTraoptimalSummaryGoal goal,
                    Integer distance,
                    Integer duration,
                    LocalDateTime departureTime
            ) {

                public record DirectionResponseDtoRouteTraoptimalSummaryStart(
                        List<Double> location
                ) {

                }

                public record DirectionResponseDtoRouteTraoptimalSummaryGoal(
                        List<Double> location,
                        Integer dir
                ) {

                }
            }

        }
    }
}