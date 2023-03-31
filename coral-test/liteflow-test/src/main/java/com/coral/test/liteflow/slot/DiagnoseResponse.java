package com.coral.test.liteflow.slot;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className DiagnoseResponse
 * @description 诊断响应
 * @date 2023/3/24 11:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DiagnoseResponse {
    /**
     * 患者ID
     */
    private String patientId;

    private String patientName;

    private String sex;

    private Integer age;

    /**
     * 人群分组
     */
    private String personGroup;

    /**
     * 症状
     */
    private List<String> symptoms;

    /**
     * 药品
     */
    private List<String> drugs;

    private String diagnose;

    /**
     * 后续动态扩展的参数
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    @Builder.Default
    private Map<String, Object> dys = new HashMap<>(16);


    public void put(@NonNull String key, Object obj) {
        dys.put(key, obj);
    }

    public <T> T find(@NonNull String key) {
        Object obj = dys.get(key);
        try {
            return (T) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
