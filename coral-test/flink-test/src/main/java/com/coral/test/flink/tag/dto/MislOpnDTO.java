package com.coral.test.flink.tag.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class MislOpnDTO {

    /**
     * 住院流水号
     */
    private String iptSn;

    /**
     * 手术或操作标准编码
     */
    private String opnCodeStd;

    /**
     * 手术或操作标准名称
     */
    private String opnNameStd;

    public MislOpnDTO(String iptSn, String opnCodeStd, String opnNameStd) {
        this.iptSn = iptSn;
        this.opnCodeStd = opnCodeStd;
        this.opnNameStd = opnNameStd;
    }


    public static List<MislOpnDTO> init() {
        List<MislOpnDTO> opns = new ArrayList<>(32);
        for (int i = 0; i < 10; i++) {
            long time = System.currentTimeMillis();
            opns.add(new MislOpnDTO("0001064463003", String.valueOf(time), "name:" + time));
        }
        return opns;
    }
}
