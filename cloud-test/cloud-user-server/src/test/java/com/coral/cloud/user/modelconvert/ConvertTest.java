package com.coral.cloud.user.modelconvert;

import com.coral.cloud.user.vo.UserInfoVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huss
 * @version 1.0
 * @className ConvertTest
 * @description 转换测试
 * @date 2022/5/18 18:37
 */
public class ConvertTest {

    @Test
    @DisplayName("model转换测试")
    public void convert() {
        UserInfoModel userInfoModel = UserInfoModel.builder()
                .username("测试")
                .userNo("001")
                .build();

        System.out.println(userInfoModel);

        UserInfoVO vo = UserInfoMapper.INSTANCE.convert(userInfoModel);

        System.out.println(vo);


    }
}
