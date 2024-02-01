package com.coral.test.flink.tag;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.StrFormatter;
import com.coral.base.common.StringUtils;
import com.coral.test.flink.tag.dto.*;
import com.coral.test.flink.tag.service.MislDiagQuerySerevice;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class R000022_2Tag {

    @Getter
    private StreamExecutionEnvironment env;

    @Getter
    private PatientInfoDTO patientInfo;

    private final static String ERROR_MSG_KEY = "ERROR_MSGS";

    public R000022_2Tag(StreamExecutionEnvironment env, PatientInfoDTO patientInfo) {
        this.env = env;
        this.patientInfo = patientInfo;
    }

    /**
     * 执行
     */
    public void execute() throws Exception {
        log.info("### R000022_2Tag run.");
        getEnv().fromElements(getPatientInfo())
                .keyBy(PatientInfoDTO::getIptSn)
                .map(new DiagRichFunction())
                .print();
        //执行
        getEnv().execute("R000022Tag");
    }

    static class DiagRichFunction extends RichMapFunction<PatientInfoDTO, ResultDTO<DiagResultDTO>> {

        @Getter
        private MislDiagQuerySerevice mislDiagQuerySerevice;


//        @Getter
//        private ListState<String> errorMsgStates;


        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            this.mislDiagQuerySerevice = new MislDiagQuerySerevice();
//            this.errorMsgStates = getRuntimeContext().getListState(new ListStateDescriptor<>(ERROR_MSG_KEY, Types.STRING));
        }

        @Override
        public ResultDTO<DiagResultDTO> map(PatientInfoDTO value) throws Exception {
            ResultDTO<DiagResultDTO> result = ResultDTO.create();
            List<MislDiagDTO> diags = getMislDiagQuerySerevice().queryDiags(value);
            String errorMsg = "";
            if (CollectionUtil.isBlank(diags)) {
                errorMsg = StrFormatter.format("患者(住院流水号:{})对应的[诊断记录]数据为空.", value.getIptSn());
                result.getErrors().add(errorMsg);
                return result;
            }
            diags = diags.stream().filter(e ->
                            //只要主诊和其他诊断
                            "020101" .equalsIgnoreCase(e.getDiagTypeCodeStdDerive()) ||
                                    "020102" .equalsIgnoreCase(e.getDiagTypeCodeStdDerive())
                    )
                    .collect(Collectors.toList());
            if (CollectionUtil.isBlank(diags)) {
                errorMsg = StrFormatter.format("患者(住院流水号:{})对应的[诊断记录]下的[主要诊断、其他诊断]数据为空.", value.getIptSn());
                result.getErrors().add(errorMsg);
                return result;
            }
            Set<String> diagCodeStds = diags.stream()
                    .filter(e -> StringUtils.isNotBlank(e.getDiagCodeStd()))
                    .map(MislDiagDTO::getDiagCodeStd)
                    .collect(Collectors.toSet());
            Set<String> groupCodes = new HashSet<>(32);
            List<KnowledgeCodeDiseaseGroupContraryDTO> codeDiseaseGroupContrarys = KnowledgeCodeDiseaseGroupContraryDTO.init()
                    .stream().filter(e -> {
                        boolean filter = diagCodeStds.contains(e.getDiseaseCode());
                        if (filter) {
                            groupCodes.add(e.getGroupCode());
                        }
                        return filter || groupCodes.contains(e.getGroupCode());
                    })
                    .collect(Collectors.toList());
            if (CollectionUtil.isBlank(codeDiseaseGroupContrarys)) {
                errorMsg = StrFormatter.format("患者(住院流水号:{})对应的[编码知识库-临床表现相反的诊断编码知识库]数据为空.", value.getIptSn());
                result.getErrors().add(errorMsg);
                return result;
            }
            List<DiagResultDTO> diagResults = new ArrayList<>();
            codeDiseaseGroupContrarys.stream().collect(Collectors.groupingBy(KnowledgeCodeDiseaseGroupContraryDTO::getGroupCode))
                    .forEach((key, val) -> {
                        Map<Integer, List<KnowledgeCodeDiseaseGroupContraryDTO>> relas = val.stream()
                                .collect(Collectors.groupingBy(KnowledgeCodeDiseaseGroupContraryDTO::getGroupRelation));

                        boolean match = relas.entrySet().stream().allMatch(
                                e3 -> e3.getValue().stream().anyMatch(e4 -> diagCodeStds.contains(e4.getDiseaseCode()))
                        );
                        if (match) {
                            List<String> diagCodes = val.stream().filter(e -> diagCodeStds.contains(e.getDiseaseCode()))
                                    .map(KnowledgeCodeDiseaseGroupContraryDTO::getDiseaseCode)
                                    .collect(Collectors.toList());
                            diagResults.add(new DiagResultDTO(key, diagCodes));

                        }
                    });
            if (CollectionUtil.isBlank(diagResults)) {
                errorMsg = StrFormatter.format("患者(住院流水号:{})没有查找到匹配的[[编码知识库-临床表现相反的诊断编码知识库]].", value.getIptSn());
                result.getErrors().add(errorMsg);
                return result;
            }
            result.getData().addAll(diagResults);

            return result;
        }

        @Override
        public void close() throws Exception {
            super.close();
        }
    }
}
