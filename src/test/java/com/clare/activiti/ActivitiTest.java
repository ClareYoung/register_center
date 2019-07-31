package com.clare.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author: Clare
 * @create: 2019-05-06 14:53
 * @version: 1.0
 **/
public class ActivitiTest {
    /**
     * 1、部署流程
     * 2、启动流程实例
     * 3、请假人发出请假申请
     * 4、班主任查看任务
     * 5、班主任审批
     * 6、最终的教务处Boss审批
     */

    /**
     * 1：部署一个Activiti流程
     * 运行成功后，查看之前的数据库表，就会发现多了很多内容
     */
    @Test
    public void creatActivitiTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("shenqing.bpmn")
                .addClasspathResource("shenqing.png")
                .deploy();
    }

    /**
     * 2：启动流程实例
     */
    @Test
    public void testStartProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("shenqing:1:25004");//这个是查看数据库中act_re_procdef表
    }

    /**
     * 完成请假申请
     */
    @Test
    public void  testQingjia() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("12502");//查看act_ru_task表
    }

    /**
     * 小明学习的班主任小毛查询当前正在执行任务
     */
    @Test
    public void testQueryTask(){
        //下面代码中的小毛，就是我们之前设计那个流程图中添加的班主任内容
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("小毛")
                .list();
        for (Task task:tasks) {
            System.out.println("==========" + task.getName());
        }
    }

    /**
     * 班主任小毛完成任务
     */
    @Test
    public void testFinishTask_manager() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("20002");//查看act_ru_task数据表
    }

    /**
     * 教务处的大毛完成的任务
     */
    @Test
    public void testFinishTask_Boss() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("302");//查看act_ru_task数据表

    }


    /**
     * 删除已经部署的Activiti流程
     */
    @Test
    public void testDelete() {
        //得到流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //第一个参数是部署的流程的ID，第二个true表示是进行级联删除
        processEngine.getRepositoryService()
                .deleteDeployment("42501", true);

    }


    /**
     * 根据名称查询流程部署
     */
    @Test
    public void testQueryDeploymentByName() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime()//按照部署时间排序
                .desc()//按照降序排序
                .deploymentName("请假流程")
                .list();
        for (Deployment deployment : deployments) {
            System.out.println(deployment.getId());
        }
    }


    /**
     * 查询所有的部署流程
     */
    @Test
    public void queryAllDeplyoment() {
        //得到流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> lists = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime()//按照部署时间排序
                .desc()//按照降序排序
                .list();
        for (Deployment deployment : lists) {
            System.out.println(deployment.getId() + "    部署名称" + deployment.getName());
        }

    }

    /**
     * 查询所有的流程定义
     */
    @Test
    public void testQueryAllPD(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> pdList = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition pd : pdList) {
            System.out.println(pd.getName());
        }
    }

    /**
     * 查看流程图
     * 根据deploymentId和name(在act_ge_bytearray数据表中)
     */
    @Test
    public void testShowImage() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                /**
                 * deploymentID
                 * 文件的名称和路径
                 */
                .getResourceAsStream("25001","shenqing.png");
        OutputStream outputStream3 = new FileOutputStream("e:/processimg.png");
        int b = -1 ;
        while ((b=inputStream.read())!=-1){
            outputStream3.write(b);
        }
        inputStream.close();
        outputStream3.close();
    }

    /**
     * 根据pdid查看图片(在act_re_procdef数据表中)
     * @throws Exception
     */
    @Test
    public void testShowImage2() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessDiagram("shenqing:1:25004");
        OutputStream outputStream = new FileOutputStream("e:/processimg.png");
        int b = -1 ;
        while ((b=inputStream.read())!=-1){
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * 查看bpmn文件(在act_re_procdef数据表中)
     */
    @Test
    public void testShowBpmn() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessModel("shenqing:1:25004");
        OutputStream outputStream = new FileOutputStream("e:/processimg.bpmn");
        int b = -1 ;
        while ((b=inputStream.read())!=-1){
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.close();
    }
}
