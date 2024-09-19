package com.domain_expansion.integrity.hub;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import com.domain_expansion.integrity.hub.domain.repository.HubRepository;
import com.domain_expansion.integrity.hub.infrastructure.repository.JpaHubRepository;
import com.github.ksuid.Ksuid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class HubInsertHubDataTests {

	//@Autowired
	private JpaHubRepository hubRepository;

	@Test
	void insertTestDataInHub(){

		List<HubTestData> testData = Arrays.asList(
				new HubTestData("서울특별시 센터", "서울특별시 송파구 송파대로 55", 37.555556, 127.127222),
				new HubTestData("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570", 37.666667, 126.833333),
				new HubTestData("경기 남부 센터", "경기도 이천시 덕평로 257-21", 37.333333, 127.250000),
				new HubTestData("부산광역시 센터", "부산 동구 중앙대로 206", 35.150000, 129.050000),
				new HubTestData("대구광역시 센터", "대구 북구 태평로 161", 35.883333, 128.583333),
				new HubTestData("인천광역시 센터", "인천 남동구 정각로 29", 37.450000, 126.650000),
				new HubTestData("광주광역시 센터", "광주 서구 내방로 111", 35.166667, 126.916667),
				new HubTestData("대전광역시 센터", "대전 서구 둔산로 100", 36.333333, 127.383333),
				new HubTestData("울산광역시 센터", "울산 남구 중앙로 201", 35.516667, 129.216667),
				new HubTestData("세종특별자치시 센터", "세종특별자치시 한누리대로 2130", 36.500000, 127.150000),
				new HubTestData("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1 ", 37.883333, 127.716667),
				new HubTestData("충청북도 센터", "충북 청주시 상당구 상당로 82", 36.633333, 127.516667),
				new HubTestData("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21", 36.550000, 126.650000),
				new HubTestData("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225", 35.833333, 127.150000),
				new HubTestData("전라남도 센터", "전남 무안군 삼향읍 오룡길 1", 34.950000, 126.450000),
				new HubTestData("경상북도 센터", "경북 안동시 풍천면 도청대로 455", 36.650000, 128.850000),
				new HubTestData("경상남도 센터", "경남 창원시 의창구 중앙대로 300", 35.216667, 128.683333)
		);

		double index = 1;

		for(HubTestData data : testData){
			data.addIndex(index++);
		}

		List<Hub> hubs = testData.stream()
				.map(data -> new Hub(Ksuid.newKsuid().toString(),100L,data.getName(), data.getAddress(),data.getIndex(),new HubLatitude(data.getLatitude()), new HubLongitude(data.getLongitude())))
				.collect(Collectors.toList());

		hubRepository.saveAll(hubs);

	}

	public class HubTestData {
		String name;
		String address;
		double latitude;
		double longitude;
		double index;

		public HubTestData(String name, String address, double latitude, double longitude) {
			this.name = name;
			this.address = address;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public String getName()
		{
			return this.name;
		}
		public String getAddress()
		{
			return this.address;
		}

		public double getLatitude()
		{
			return this.latitude;
		}

		public double getLongitude()
		{
			return this.longitude;
		}

		public double getIndex(){
			return this.index;
		}

		public void addIndex(double index)
		{
			this.index = index;
		}
	}

}
