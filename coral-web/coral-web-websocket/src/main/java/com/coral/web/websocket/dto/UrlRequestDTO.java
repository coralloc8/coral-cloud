package com.coral.web.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className UrlRequestDTO
 * @description 入参dto
 * @date 2022/11/7 9:07
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlRequestDTO {
    /**
     * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cen="http://webservice.websocket.web.coral.com/">
     *     <soapenv:Header/>
     *     <soapenv:Body>
     *         <cen:findUrl>
     *             <request>
     *                 <businessType>ywz</businessType>
     *                 <function>2wwwe2</function>
     *                 <params>333</params>
     *                 <age>23</age>
     *                 <money>5000.26</money>
     *                 <isMale>true</isMale>
     *                 <children>
     *                     <no>11</no>
     *                     <name>11_na</name>
     *                 </children>
     *                 <children>
     *                     <no>22</no>
     *                     <name>22_na</name>
     *                 </children>
     *                 <son>
     *                     <no>33</no>
     *                     <name>33_name</name>
     *                 </son>
     *             </request>
     *         </cen:findUrl>
     *     </soapenv:Body>
     * </soapenv:Envelope>
     *
     *
     *
     */

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 功能点
     */
    private String function;

    /**
     * 入参
     */
    private String params;

    private Double money;

    private Integer age;

    /**
     * 是否男性
     */
    private Boolean isMale;

    private List<UrlSonRequestDTO> children;

    private UrlSonRequestDTO son;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UrlSonRequestDTO {

        private String name;

        private Long no;

    }
}
