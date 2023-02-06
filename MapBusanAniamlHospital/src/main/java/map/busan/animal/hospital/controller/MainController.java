package map.busan.animal.hospital.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import map.busan.animal.hospital.vo.ItemVO;
import map.busan.animal.hospital.vo.ResultVO;

@Controller
public class MainController {
	
	@GetMapping(value = {"/", "/index"})
	public String index(Model model) {
		
		// API 정보
		String apiURL = "http://apis.data.go.kr/6260000/BusanAnimalHospService/getTblAnimalHospital";
		String serviceKey = "8WywrrW%2BnxRW2OCWoi8JIr%2FgOJyTW46kx0LzefqFBsaOoenVcnKD39h9Qt8spXCQNHYN3kVzrwu6nZSvZO%2FJVA%3D%3D";
		String resultType = "json";
		String pageNo = "1";
		String numOfRows = "1000";
		
		URI uri = UriComponentsBuilder
					.fromUriString(apiURL)
					.queryParam("serviceKey", serviceKey)
					.queryParam("resultType", resultType)
					.queryParam("pageNo", pageNo)
					.queryParam("numOfRows", numOfRows)
					.encode()
					.build(true)
					.toUri();
		
		RequestEntity<Void> req = RequestEntity.get(uri).build();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(req, String.class);
		
		// JSON 문자열
		String jsonData = result.getBody();
		
		// JSON 파싱
		ObjectMapper om = new ObjectMapper();
		
		try {
			ResultVO resultVO = om.readValue(jsonData, ResultVO.class);
			List<ItemVO> items = resultVO.getGetTblAnimalHospital().getBody().getItems().getItem();

			model.addAttribute("items", items);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "index";
	}
}
