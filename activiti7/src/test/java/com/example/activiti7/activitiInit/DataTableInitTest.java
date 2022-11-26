package com.example.activiti7.activitiInit;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author sunhp
 * @desc
 * @date 2022/11/23
 **/
@Slf4j
@SpringBootTest
public class DataTableInitTest {

    private static final String PROCESS_DEFINITION_KEY = "leaveProcess";

    /**
     * 初始化数据库
     */
    @Test
    public void generateTable() {
        //默认创建方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        log.info("初始化工作流数据库：{}", processEngine);
        //通用的创建方式，指定配置文件名和Bean名称
//        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml","processEngineConfiguration");
//        ProcessEngine processEngine1 = processEngineConfiguration.buildProcessEngine();
    }

    /**
     * 部署流程定义
     */
    @Test
    public void deployment() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/Leave.bpmn")
                .addClasspathResource("bpmn/Leave.png")
                .name("请假申请流程")
                .deploy();
        log.info("流程部署Id:{}", deployment.getId());
        log.info("流程部署名称:{}", deployment.getName());
    }

    /**
     * zip压缩文件上传方式
     */
    @Test
    public void deployProcessByZip() {
        //定义zip输入流
        InputStream inputStream = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/Leave.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        log.info("流程部署Id:{}", deployment.getId());
        log.info("流程部署名称:{}", deployment.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcess() {
        //流程定义的实例key
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取runtimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //根据流程定义Id启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

        log.info("流程定义Id:{}", processInstance.getProcessDefinitionId());
        log.info("流程实例Id:{}", processInstance.getId());
        log.info("当前活动Id:{}", processInstance.getActivityId());
    }

    /**
     * 查询当前个人待执行的任务
     */
    @Test
    public void findPersonalTaskList() {
        String assignee = "worker";
        String assigneeManager = "manager";
        //流程定义的实例key
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .taskAssignee(assigneeManager)
                .list();
        list.stream().forEach(task -> {
                    log.info("当前人的流程实例Id：{}", task.getProcessInstanceId());
                    log.info("当前人的任务Id：{}", task.getId());
                }
        );
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        /**
         * 当前两个需要完成的任务
         */
        String taskId = "15002";
        String taskId2 = "12505";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        taskService.complete(taskId);
    }

    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        processDefinitionList.forEach(processDefinition -> {
            log.info("流程定义 Id:{}", processDefinition.getId());
            log.info("流程定义 name:{}", processDefinition.getName());
            log.info("流程定义 key:{}", processDefinition.getKey());
            log.info("流程定义 version:{}", processDefinition.getVersion());
            log.info("流程部署Id:{}", processDefinition.getDeploymentId());
            log.info("============================================");
            log.info("                                             ");
        });
    }

    /**
     * 删除流程实例
     */
    @Test
    public void deleteDeployment() {
        //流程部署id
        String deploymentId = "10001";
        String deploymentId1 = "2501";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

//        repositoryService.deleteDeployment(deploymentId1);
//        设置true 级联删除流程定义，即使有流程实例启动也可以删除，设置为false非级联删除
        repositoryService.deleteDeployment(deploymentId1, true);
    }

    /**
     * 下载bpmn文件
     */
    @Test
    public void downloadBpmn() throws IOException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .orderByProcessDefinitionVersion()
                .desc()
                .singleResult();
        //通过查询流程定义方法得到
        String deploymentId = processDefinition.getDeploymentId();
        log.info("==========>{}", processDefinition.getDiagramResourceName());
        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
        log.info("==========>{}", processDefinition.getResourceName());
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());
        File file_png = new File("F:/IDEA/workspace1/mq/activiti7/leaveProcess.png");
        File file_bpmn = new File("F:/IDEA/workspace1/mq/activiti7/leaveProcess.bpmn");
        FileOutputStream pngOut = new FileOutputStream(file_png);
        FileOutputStream bpmnOut = new FileOutputStream(file_bpmn);

        IOUtils.copy(pngInput, pngOut);
        IOUtils.copy(bpmnInput, bpmnOut);

        pngOut.close();
        bpmnOut.close();
        pngInput.close();
        bpmnInput.close();
    }

    /**
     * 查看历史信息
     */
    @Test
    public void findHistoryInfo() {
        String processInstanceId = "12501";

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();

        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //查询actinst表 根据InstanceId查询一个流程的所有历史信息
        historicActivityInstanceQuery.processInstanceId(processInstanceId);
        //增加排序操作
        historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();

        //输出内容
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();

        list.stream().forEach(historicActivityInstance -> {
            log.info("负责人：{}", historicActivityInstance.getAssignee());
            log.info("活动名：{}", historicActivityInstance.getActivityName());
            log.info("CalledProcessInstanceId：{}", historicActivityInstance.getCalledProcessInstanceId());
            log.info("ProcessDefinitionId：{}", historicActivityInstance.getProcessDefinitionId());
            log.info("=================================");
        });
    }
}
