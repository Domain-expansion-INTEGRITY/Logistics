# Logistics

## 🦉 프로젝트 개요

## 🐶 구성원

| 이름                                    | 역할 분담                |
|---------------------------------------|----------------------|
| [조원호](https://github.com/wonowonow)   | 상품, 주문, 배송           |
| [김형철](https://github.com/shurona)     | 유저, Gateway, Ai, 슬랙  |
| [봉대현](https://github.com/bongdaehyun) | 허브, 배송 담당, 이동 정보, 업체 |

## 💻 기술 스택

- Language: Java 17
- Framework: Spring boot 3.x
- Repository: PostgreSQL, Redis
- Build Tool: Gradle 8.x

## 서비스 구성 및 실행방법

### 서비스 구성

| 서버 이름     | 역할           | 포트    |
|-----------|--------------|-------|
| Eureka 서버 | Eureka 메인 서버 | 19090 |
| Gateway   | Gateway      | 19091 |
| User      | 유저           | 19092 |
| Hub       | 허브           | 19093 |
| Company   | 업체           | 19094 |
| Product   | 상품           | 19095 |
| Order     | 주문           | 19096 |
| Delivery  | 배달           | 19097 |
| AI        | AI           | 19098 |
| Slack     | 슬랙           | 19099 |
| Auth      | 인증           | 19100 |

### Swagger

http://localhost:19091/swagger-ui.html

### 필요 인프라

- PostgreSQL
- Redis
- Zipkin

### 구성하는 env 파일

```dotenv
# DB 정보
DEV_DB_URL=
DEV_DB_USERNAME=
DEV_DB_PASSWORD=

# Redis 정보
DEV_REDIS_HOST=
DEV_REDIS_USERNAME=
DEV_REDIS_PASSWORD=

# JWT 인증 키
JWT_SECRET_KEY=

# 외부 api
DEV_GEMINI_KEY=
DEV_WEATHER_KEY=
SLACK_WEBHOOK_URL=
DIRECTION_SECRET_KEY=
DIRECTION_CLIENT_ID=

# 인프라
KAFKA_BOOTSTRAP_SERVERS=
EUREKA_DEFAULTZONE=
ZIPKIN_URL=
```

### 실행방법

Docker로 각 모듈들을 구성해서 묶어놓은 compose 파일을 실행하면 된다.
> **주의:** 실행 전에 인프라 설정 및 ENV 설정 확인부탁드립니다.

```shell
docker-compose up -d
```

## ERD Diagram

![ERD v1.png](..%2F..%2F..%2F..%2F..%2FDownloads%2FERD%20v1.png)

## 트러블 슈팅

### RestClient에서 interceptor 및 url encoding 처리

전송하는 URL을 보기 위한 로깅을 처리 interceptor 추가    
및 특수문자의 강제 encoding을 막기 위해서 EncodingMode 설정

```java
public WeatherClient weatherRestClient() {

    DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
        weatherConfig.baseurl());
    uriBuilderFactory.setEncodingMode(EncodingMode.NONE);

    RestClient restClient = RestClient.builder()
        .uriBuilderFactory(uriBuilderFactory)
        .requestInterceptor(logRequestInterceptor())
        .build();

    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(WeatherClient.class);
}

private ClientHttpRequestInterceptor logRequestInterceptor() {
    return new ClientHttpRequestInterceptor() {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
            log.info("Request URL: {}", request.getURI()); // 출력 url 확인
            HttpHeaders headers = request.getHeaders();
            return execution.execute(request, body);
        }
    };
}

```

### 페이징 캐싱

기존의 Page객체를 JSON으로 변환할 시 오류가 발생
이를 해결하기 위해서 별도로 변환 메서드를 만들어서 처리

```java

@Builder(access = AccessLevel.PRIVATE)
public record HubRoutePaginatedResponseDto(
    List<HubRouteResponseDto> content,
    int currentPage,
    int totalPages,
    long totalElements
) {

    public static HubRoutePaginatedResponseDto of(Page<HubRouteResponseDto> page) {
        return HubRoutePaginatedResponseDto.builder()
            .content(page.getContent())
            .currentPage(page.getNumber())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();
    }



```

### Interceptor에서 HttpRequest 처리

외부에서 받은 JWT Token을 내부 통신으로 전달하기 위해 FeignClient Interceptor에서 Request 객체를 외부에서 주입 받는 형태로 구성하였으나   
Scheduler에서 동작 시 Request가 없어서 문제가 발생하는 것을 확인.   
이를 해결하기 위해서 의존성 주입이 아니라 RequestContextHolder에서 갖고 오는 방식으로 변경

```java

public void apply(RequestTemplate requestTemplate) {
    // 현재 HttpServletRequest 가져오기
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    String jwtToken = request.getHeader("Authorization");
    requestTemplate.header("Authorization", jwtToken);
}
```