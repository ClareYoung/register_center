package com.clare.activiti;
import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessDefinitionEntityTest {
    /**
     * 根据pdid得到processDefinitionEntity
     */
    @Test
    public void testProcessDefinitionEntity(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /**
         * 根据pdid得到ProcessDefinitionEntry
         */
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)processEngine.getRepositoryService()
                .getProcessDefinition("shenqing:1:25004");

    }

    /**
     * 根据pdid得到processDefinitionEntity中的activityimpl
     */
    @Test
    public void testGetActivityImpl(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /**
         * 根据pdid得到ProcessDefinitionEntry
         */
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)processEngine.getRepositoryService()
                .getProcessDefinition("shenqing:1:25004");
        /**
         * ActivityImpl是一个对象
         * 一个activityImpl代表processDefinitionEntity中的一个节点
         */
        List<ActivityImpl> activityImpls = processDefinitionEntity.getActivities();
        for (ActivityImpl activityImpl : activityImpls) {
            System.out.println(activityImpl.getId());
            System.out.print("hegiht:"+activityImpl.getHeight());
            System.out.print("width:"+activityImpl.getWidth());
            System.out.print(" x:"+activityImpl.getX());
            System.out.println(" y:"+activityImpl.getY());
        }
    }

    /**
     * 得到ProcessDefinitionEntity中的所有的ActivityImpl的所有的PvmTransition
     */
    @Test
    public void testSequenceFlow(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /**
         * 根据pdid得到ProcessDefinitionEntry
         */
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)processEngine.getRepositoryService()
                .getProcessDefinition("shenqing:1:25004");

        /**
         * ActivityImpl是一个对象
         * 一个activityImpl代表processDefinitionEntity中的一个节点
         */
        List<ActivityImpl> activityImpls = processDefinitionEntity.getActivities();
        for (ActivityImpl activityImpl : activityImpls) {
            /**
             * 得到一个activityimpl的所有的outgoing
             */
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                System.out.println("sequenceFlowId:"+pvmTransition.getId());
            }
        }
    }

    /**
     * 得到当前正在执行的流程实例的activityimpl-->PvmTransition
     */
    @Test
    public void testQueryActivityImpl_Ing(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)processEngine.getRepositoryService()
                .getProcessDefinition("shenqing:1:25004");
        //根据piid获取到activityId
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId("27501")
                .singleResult();
        //根据流程实例得到当前正在执行的流程实例的正在执行的节点
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId());
        System.out.print("流程实例ID:"+pi.getId());
        System.out.print(" 当前正在执行的节点:"+activityImpl.getId());
        System.out.print(" hegiht:"+activityImpl.getHeight());
        System.out.print(" width:"+activityImpl.getWidth());
        System.out.print(" x:"+activityImpl.getX());
        System.out.println(" y:"+activityImpl.getY());
    }
}