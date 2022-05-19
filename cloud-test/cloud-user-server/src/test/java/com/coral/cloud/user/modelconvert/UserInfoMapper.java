package com.coral.cloud.user.modelconvert;

import com.coral.cloud.user.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huss
 * @version 1.0
 * @className UserInfoMapper
 * @description 转换测试
 * @date 2022/5/18 18:38
 */
@Mapper
public interface UserInfoMapper {
    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    UserInfoVO convert(UserInfoModel userInfoModel);


}
