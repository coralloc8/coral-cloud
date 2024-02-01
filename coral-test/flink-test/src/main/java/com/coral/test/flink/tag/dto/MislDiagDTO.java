package com.coral.test.flink.tag.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 诊断记录
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MislDiagDTO {

    /**
     * 住院流水号
     */
    private String iptSn;

    /**
     * 诊断类型标准编码（精准）
     */
    private String diagTypeCodeStdDerive;

    /**
     * 诊断类型标准名称（精准）
     */
    private String diagTypeNameStdDerive;

    /**
     * 诊断标准编码
     */
    private String diagCodeStd;

    /**
     * 诊断标准名称
     */
    private String diagNameStd;


    public static List<MislDiagDTO> init() {
        List<MislDiagDTO> diags = new ArrayList<>();
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("020101")
                        .diagTypeNameStdDerive("西医出院主要诊断")
                        .diagCodeStd("K27.503")
                        .diagNameStd("上消化道穿孔")
                        .build()
        );
        //
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("020102")
                        .diagTypeNameStdDerive("西医出院其他诊断")
                        .diagCodeStd("K27.401")
                        .diagNameStd("应激性溃疡伴出血")
                        .build()
        );
        //
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("020102")
                        .diagTypeNameStdDerive("西医出院其他诊断")
                        .diagCodeStd("M81.900")
                        .diagNameStd("骨质疏松")
                        .build()
        );
        //
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("0101")
                        .diagTypeNameStdDerive("西医门（急）诊诊断")
                        .diagCodeStd("M06.900")
                        .diagNameStd("类风湿性关节炎")
                        .build()
        );
        //
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("020102")
                        .diagTypeNameStdDerive("西医出院其他诊断")
                        .diagCodeStd("I10.x05")
                        .diagNameStd("高血压3级")
                        .build()
        );
        //
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("020102")
                        .diagTypeNameStdDerive("西医出院其他诊断")
                        .diagCodeStd("M17.900x002")
                        .diagNameStd("膝关节退行性病变")
                        .build()
        );
        //
        diags.add(
                MislDiagDTO.builder()
                        .iptSn("0001064463003")
                        .diagTypeCodeStdDerive("020102")
                        .diagTypeNameStdDerive("西医出院其他诊断")
                        .diagCodeStd("J84.900")
                        .diagNameStd("间质性肺病")
                        .build()
        );
        return diags;
    }
}
