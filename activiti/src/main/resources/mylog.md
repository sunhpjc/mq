### logback xml节点配置说明
参考地址 https://blog.51cto.com/u_15067225/2896251

##### 1、根节点<configuration>，包含下面三个属性：
　　　　scan: 当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
　　　　scanPeriod: 设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
　　　　debug: 当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
```
<configuration scan="true" scanPeriod="60 seconds" debug="false"> 
　　  <!--其他配置省略--> 
</configuration>　
```

##### 2.子节点<appender>：负责写日志的组件，它有两个必要属性name和class。name指定appender名称，class指定appender的全限定名。

##### 3.xsd文件地址
https://vgxitdata-1256295173.cos.ap-chengdu.myqcloud.com/logback.xsd


