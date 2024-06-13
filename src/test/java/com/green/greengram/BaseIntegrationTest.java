package com.green.greengram;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Import({ CharEncodingConfiguration.class })
@ActiveProfiles("tdd")

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 통합 테스트때 쓰는 어노테이션

@AutoConfigureMockMvc       // 포스트맨 같은 것
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseIntegrationTest {

    @Autowired protected MockMvc mvc;           // MockMvc 는 Spring MVC 웹 애플리케이션의 컨트롤러를 테스트하기 위한 도구
                                                // 이를 사용하면 실제 서블릿 컨테이너를 구동하지 않고도 Spring MVC 애플리케이션의 엔드포인트를 테스트할 수 있음
                                                // MockMvc 를 통해 HTTP 요청을 시뮬레이션하고 응답을 검증할 수 있음

    @Autowired protected ObjectMapper om;       // String(Json) -> 자바 객체 and 자바 객체 -> String(Json) 할 수 있는 라이브러리

}
