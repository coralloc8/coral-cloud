package com.coral.test.contract.client;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * @author huss
 * @version 1.0
 * @className UserClientTest
 * @description todo
 * @date 2021/7/7 14:57
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureStubRunner(
        ids = {"com.coral.test:contract-server:1.0.0-SNAPSHOT:stubs:18088"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class UserClientTest {

//    @Test
//    public void createUser() throws IOException {
//        String getUsersUrl = userCenterUrl + "/users/" + userId;
//
//        RestTemplate template = new RestTemplate();
//        ResponseEntity<Map> result = template.getForEntity(getUsersUrl, Map.class);
//        return result;
//
//    }
}