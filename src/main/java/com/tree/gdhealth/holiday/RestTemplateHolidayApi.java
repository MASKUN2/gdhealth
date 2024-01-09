package com.tree.gdhealth.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**RestTemplate을 활용한 휴일정보 공공데이터 API리퀘스터.
 * <p>URL로 .getForEntity 등의 메소드를 사용해 요청을 보낼 경우 RestTemplate가 자동으로 인코딩을 수행하므로
 * 서비스키가 인코딩되어 전달되는 문제가 있었습니다. 이 문제는 encode를 수행하지 않는 URI로 미리 파싱함으로
 * 해결했습니다. .build(true)를 지정해야 encoding을 수행하지 않습니다.
 * 더이상 xml을 처리하지 않고 json으로 responsebody를 받습니다.
 * key "item"의 value가 1. none ("") 2. object array([{},{}...]) 3. single object ({}) 경우로 조건분기하여
 * 자바 객체로 언마샬링했습니다.</p> 공공API이기 때문에 지연응답이 되는 경우가 있습니다. 타임아웃 및 한글 인코딩 은 restTemplate 스프링 빈에 사전 설정했습니다.
 * @author 정인호
 * */
@Slf4j
@RequiredArgsConstructor
@Component
public class RestTemplateHolidayApi implements HolidayApi{
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
    private final String serviceKey = "ZbmPZ9Fmf4lkuzc5QHJOW5eGFvcOxN52X%2FBMmG6A5SBVoVwogSlJ035lgjUPXAZFwZiqmXRhMAqBGAryiHatIQ%3D%3D";
    private final String pageNo = "1";
    private final String numOfRows = "10";

    @Override
    public List<HolidayApiVo> getHolidayList(YearMonth yearMonth) throws JsonProcessingException {

        String solYear = yearMonth.format(DateTimeFormatter.ofPattern("yyyy"));
        String solMonth = yearMonth.format(DateTimeFormatter.ofPattern("MM"));

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("serviceKey",serviceKey)
            .queryParam("pageNo", pageNo)
            .queryParam("numOfRows", numOfRows)
            .queryParam("solYear", solYear)
            .queryParam("solMonth", solMonth).build(true).toUri();
        log.debug("생성된 URI.toString() = {}",uri.toString());

        log.debug("restTemplate 메세지 컨버터 우선순위출력---------높음");
        restTemplate.getMessageConverters().forEach(c -> log.debug(c.toString()));
        log.debug("restTemplate 메세지 컨버터 우선순위출력---------낮음");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        String responseBodytext = responseEntity.getBody().toString();
        log.debug("responseBody = {}", responseBodytext);
        JsonNode rootNode = objectMapper.readTree(responseBodytext);
        log.debug("변환시킨 JsonNode.toString() = {}", rootNode.toString());
        List<HolidayApiVo> list = new ArrayList<>();
        JsonNode itemNode = rootNode.findValue("item");
        if(itemNode == null){
            log.debug("'item' 프로퍼티 이름의 노드를 찾을 수 없습니다. 해당 월에 휴일이 없어서 그럴 수도 있습니다.");
            return list;
        }
        log.debug("'item' 프로퍼티 이름으로 찾은 노드 itemNode.toString() = {}", itemNode.toString());
        if(itemNode.isArray()) {
            for(JsonNode n : itemNode) {
                HolidayApiVo v = new HolidayApiVo();
                v = objectMapper.readValue(n.toString(), HolidayApiVo.class);
                list.add(v);
            }
        }else if(!itemNode.isEmpty()){
            HolidayApiVo v = new HolidayApiVo();
            v = objectMapper.readValue(itemNode.toString(), HolidayApiVo.class);
            list.add(v);
        }
        list.forEach( v -> log.debug( "객체에 매핑하여 리스트에 담은 찾은 휴일정보 {}",v.toString()));
        return list;

    }
}
