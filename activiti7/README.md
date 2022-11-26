
### 1.流程变量设置
启动流程时设置变量
```
ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
```
任务办理时设置变量
```java
//注意如果当前执行的任务id不存在，则会抛出异常，流程变量也会设置失败
taskService.complete(task.getId, map)
```

通过当前流程实例设置
```java
//通过流程实例id设置全局变量，该流程实例必须未执行完成
/**
     * 通过流程实例id设置全局变量，该流程实例必须未执行完成
     */
    @Test
    public void setGlobalVariableByExecutionId(){
        //当前流程实例执行id 通常设置为当前执行的流程实例
        String executionId = "";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //创建pojo对象
        
        runtimeService.setVariable();
    }
```
通过当前任务实现设置 参考通过当前流程实例设置

### 2.注意事项
##### 1.UEL表达式变量名匹配
##### 2.使用UEL表达式 流程变量不能为空
##### 3.UEL表达式都不符合条件 流程结束
##### 4.如果连线不设置条件，会走folw较小的那一条线
##### 5.设置变量会在当前执行流程变量表act_ru_variable中插入记录，同时也会在历史流量变量表act_hi_varinst中插入记录

### 3.设置Local变量
##### 1.任务办理时设置
比如可以实时设置下一步的审核人等
```java
taskService.setVariablesLocal(taskId, variables);
taskService.complate(taskId);
```
##### 2.通过当前任务设置
```java
taskService.setVariablesLocal(taskId, variables);
```

### 4.网关
##### 1.注意
并行网关会忽略配置的条件，并且并行网关一般两个配合使用

### 5.任务分配
##### 设置个人处理
${assignee}

##### 设置组
候选人在Candidate Users配置：如zahngsan,lisi

候选组可以指定Candidate groups