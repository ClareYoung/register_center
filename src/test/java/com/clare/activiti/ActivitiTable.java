package com.clare.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @author: Clare
 * @create: 2019-05-06 14:45
 * @version: 1.0
 **/
public class ActivitiTable {
    /*
    * 创建Activiti流的相关的数据库表
    * */
    @Test
    public void creatTable() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                .buildProcessEngine();
    }

}
