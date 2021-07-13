package com.coral.test.contract.server;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import java.util.Map;



/**
 * @author huss
 * @version 1.0
 * @className UserBase
 * @description todo
 * @date 2021/7/7 10:09
 */
//@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ContractServerApplication.class)
public abstract class ContractBase {

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.webAppContextSetup(context);
//        given(userService.userList(1L)).willReturn(user);
    }

    protected void assertIsTrue(Object object) {
        Map map = (Map) object;
        Assertions.assertThat(map.get("success")).isEqualTo(true);
    }
}