package com.example.activiti7.activitiInit;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author sunhp
 * @desc
 * @date 2022/11/25
 **/
@SpringBootTest
@Slf4j
public class ActivitiBusinessKeyTest {
    private static final String PROCESS_DEFINITION_KEY = "leaveProcess";

    /**
     * 添加业务key
     */
    @Test
    public void addBusinessKey() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, "myBusinessKey");
        log.info("========================================");
        log.info("=====>businessKey :{}", processInstance.getBusinessKey());
    }

    /**
     * 挂起流程
     */
    @Test
    public void suspendAllProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //查询流程定义，获取流程定义的查询对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .singleResult();
        //获取当前实例是否为挂起状态
        boolean suspended = processDefinition.isSuspended();
        if (suspended) {
            //挂起状态，改为激活状态
            //参数 流程定义id 是否激活 激活时间
            repositoryService.activateProcessDefinitionById(processDefinition.getId(), true, null);
            log.info("===========>{}已激活", processDefinition.getId());
        } else {
            //改为激活状态
            //参数 流程定义id 是否暂停 暂停时间
            repositoryService.suspendProcessDefinitionById(processDefinition.getId(), true, null);
            log.info("===========>{}已挂起", processDefinition.getId());
        }
    }

    /**
     * 单个流程挂起与激活
     */
    @Test
    public void suspendSingleProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .orderByProcessInstanceId()
                .desc()
                .list();
        String processInstanceId = processInstanceList.get(0).getProcessInstanceId();
        log.info("===========>processInstanceId: {}", processInstanceId);

        ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        boolean suspended = processInstance1.isSuspended();
        if (suspended) {
            //挂起状态，改为激活状态
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("===========>{}已激活", processInstanceId);
        } else {
            //改为挂起状态
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("===========>{}已挂起", processInstanceId);
        }
    }

    /**
     * 通过流程实例id设置全局变量，该流程实例必须未执行完成
     */
    @Test
    public void setGlobalVariableByExecutionId() {
        //当前流程实例执行id 通常设置为当前执行的流程实例
        String executionId = "";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //创建pojo对象

//        runtimeService.setVariable();
    }

    /**
     * 候选人主动拾取任务
     */
    @Test
    public void claimTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        taskService.createTaskQuery()
                .processDefinitionKey("key")
                .taskCandidateUser("候选人")
                .list();

        taskService.claim("taskId", "候选人");
    }

    /**
     * 退还任务
     */
    @Test
    public void returnTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        taskService.createTaskQuery()
                .taskId("taskId")
                .taskAssignee("候选人")
                .singleResult();
        //归还任务，将assign设置为空即可
        taskService.setAssignee("taskId", null);
    }
}
