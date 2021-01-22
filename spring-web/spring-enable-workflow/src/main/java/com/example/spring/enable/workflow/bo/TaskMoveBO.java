package com.example.spring.enable.workflow.bo;

import lombok.Data;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/12/30 10:09
 */
@Data
public class TaskMoveBO {


    private String taskDefinitionKey;

    private String targetTaskDefinitionKey;


}
