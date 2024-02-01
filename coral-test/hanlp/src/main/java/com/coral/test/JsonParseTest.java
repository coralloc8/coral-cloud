package com.coral.test;

import com.coral.base.common.IOUtil;
import com.coral.base.common.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonParseTest {

    public static void main(String[] args) {
        List<ModuleInfo> moduleInfos = JsonUtil.parseArray(MODULE_INFO, ModuleInfo.class);
        Set<String> modules = moduleInfos.stream().map(ModuleInfo::getModule).collect(Collectors.toSet());
        System.out.println(modules);
        AllInfo allInfo = JsonUtil.parse(USER_INFO, AllInfo.class);
        System.out.println(allInfo);

        /**
         * @formatter: on
        // module映射
        // 多个时
        {
        "user_info": "user_info",
        "address_info": "address_info",
        "address_infos": "address_info"
        }
        // 单个时
        {
        "none": "user_info"
        }
         @formatter: off
         */
    }

    private static final String MODULE_INFO;

    private static final String USER_INFO;

    @Data
    static class AllInfo {
        @JsonProperty("user_info")
        private List<UserInfo> userInfo;
        @JsonProperty("address_infos")
        private List<AddressInfo> addressInfos;
    }

    @Data
    static class UserInfo {
        @JsonProperty("user_code")
        private String userCode;

        @JsonProperty("user_name")
        private String userName;

        private String age;

        private String sex;

        @JsonProperty("address_info")
        private List<AddressInfo> addressInfo;
    }

    @Data
    static class AddressInfo {

        @JsonProperty("address_code")
        private String addressCode;

        @JsonProperty("address_name")
        private String addressName;

        private String detail;
    }

    @Data
    static class ModuleInfo {
        private String module;
        @JsonProperty("module_name")
        private String moduleName;

        private String field;
        @JsonProperty("field_name")
        private String fieldName;
    }

    static {
        try {
            MODULE_INFO = IOUtil.readStream(JsonParseTest.class.getClassLoader().getResourceAsStream("json_parse/module_info.json"));
            USER_INFO = IOUtil.readStream(JsonParseTest.class.getClassLoader().getResourceAsStream("json_parse/user.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
