package com.coral.cloud.user.controller;

import com.coral.base.common.IOUtil;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.user.common.constants.DefConstant;
import com.coral.cloud.user.entity.HospitalUserRecordMzDocument;
import com.coral.cloud.user.entity.PersonDocument;
import com.coral.cloud.user.repository.HospUserRecordMzRepository;
import com.coral.cloud.user.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className ElasticSearchController
 * @description es控制器
 * @date 2022/4/28 9:52
 */
@RestController
@RequestMapping("/elastics")
@Slf4j
public class ElasticSearchController implements ElasticSearchApi {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private HospUserRecordMzRepository hospUserRecordMzRepository;


    /**
     * 查询用户文档信息
     *
     * @return
     */
    @GetMapping
    @Override
    public ResponseEntity<List<PersonDocument>> findPersons(String personName) {
        log.info(">>>>username:{}", personName);
        List<PersonDocument> documents = repository.findByPersonNameLike(personName);
        log.info(">>>>>documents:{}", documents);
        return ResponseEntity.ok(documents);
    }

    /**
     * 保存用户文档信息
     *
     * @param p
     * @return
     */
    @PostMapping
    @Override
    public ResponseEntity<PersonDocument> savePerson(@RequestBody PersonDocument p) {
        p.setId(System.currentTimeMillis());
        repository.save(p);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/mz/records")
    @Override
    public ResponseEntity<String> initUserRecordMzs() {
        String jsonPath = "/mock/hospital_user_record_mz.json";
        try {
            String json = IOUtil.readStream(this.getClass().getResourceAsStream(jsonPath));
            hospUserRecordMzRepository.saveAll(JsonUtil.parseArray(json, HospitalUserRecordMzDocument.class));
        } catch (IOException e) {
            log.error(">>>>>解析数据异常", e);
        }
        return ResponseEntity.ok(DefConstant.SUCCESS);
    }


}
