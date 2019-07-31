package com.clare.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Clare
 * @create: 2019-05-07 09:13
 * @version: 1.0
 **/
public class ActivitiTest1 {
    /**
     * 部署流程
     */
    @Test
    public void startDeployTest(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程：情况二")
                .addClasspathResource("new/shenqing2.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     *    可以设置一个流程变量
     */
    @Test
    public void testStartPI(){
        /**
         * 流程变量
         *   给<userTask id="请假申请" name="请假申请" activiti:assignee="#{student}"></userTask>
         *     的student赋值
         */
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("student", "小张");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("shenqing2:1:50004",variables);
    }

    /**
     * 在完成请假申请的任务的时候，给班主任审批的节点赋值任务的执行人
     */
    @Test
    public void testFinishTask_Teacher(){
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("teacher", "我是小明的班主任");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        processEngine.getTaskService()
//                .complete("32505", variables); //完成任务的同时设置流程变量
        processEngine.getTaskService()
                .complete("52505"); //完成任务的同时设置流程变量
    }

    /**
     * 在完成班主任审批的情况下，给教务处节点赋值
     */
    @Test
    public void testFinishTask_Manager(){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("manager", "我是小明的教务处处长");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("55002", variables); //完成任务的同时设置流程变量
    }

    /**
     * 结束流程实例
     */
    @Test
    public void testFinishTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("57503");
    }
}
