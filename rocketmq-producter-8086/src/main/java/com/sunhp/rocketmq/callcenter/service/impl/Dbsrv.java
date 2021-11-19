package com.sunhp.rocketmq.callcenter.service.impl;

import com.sunhp.rocketmq.callcenter.commons.Constants;
import com.sunhp.rocketmq.callcenter.service.DataBak;

import java.io.IOException;

/**
 * @Description
 **/
public class Dbsrv implements DataBak {

    /**
     * 导出数据
     */
    @Override
    public void exportData(String tabName, String condition) {
        Runtime runtime = Runtime.getRuntime();
        String command = getExportCommand(tabName, condition);
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取导出命令
     *
     * @return
     */
    private String getExportCommand(String tabName, String condition) {
        StringBuffer command = new StringBuffer();
        String username = Constants.DBSRV_T_IVR_CALLLOG_USERNAME;//用户名
        String password = Constants.DBSRV_T_IVR_CALLLOG_PASSWORD;//用户密码
        String exportDatabaseName = Constants.DBSRV_T_IVR_CALLLOG_DATABASENAME;//需要导出的数据库名
        String host = Constants.DBSRV_T_IVR_CALLLOG_HOST;//从哪个主机导出数据库，如果没有指定这个值，则默认取localhost
        String port = Constants.DBSRV_T_IVR_CALLLOG_PORT;//使用的端口号
        String exportPath = Constants.DBSRV_T_IVR_CALLLOG_EXPORTPATH;//导出路径
        //这里如果想直接使用mysqldump的话需要在环境变量path下配mysql的bin目录的路径，不然的话就得用绝对路径
        command.append("mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。
                .append(" -h").append(host).append(" -P").append(port).append(" -t ").append(exportDatabaseName)
                .append(" ").append(tabName).append(" --where=\"").append(condition).append("\"")
                .append(Constants.DUMP_PARAM).append(" -r ").append(exportPath);
        System.out.println(command.toString());
        return command.toString();
    }

    /**
     * 获取删除命令
     *
     * @return
     */
    private String getDeleteCommand(String tabName, String condition) {
        StringBuffer command = new StringBuffer();
        String username = Constants.DBSRV_T_IVR_CALLLOG_USERNAME;//用户名
        String password = Constants.DBSRV_T_IVR_CALLLOG_PASSWORD;//用户密码
        String exportDatabaseName = Constants.DBSRV_T_IVR_CALLLOG_DATABASENAME;//需要导出的数据库名
        String host = Constants.DBSRV_T_IVR_CALLLOG_HOST;//从哪个主机导出数据库，如果没有指定这个值，则默认取localhost
        String port = Constants.DBSRV_T_IVR_CALLLOG_PORT;//使用的端口号
        //这里如果想直接使用mysqldump的话需要在环境变量path下配mysql的bin目录的路径，不然的话就得用绝对路径
        command.append("mysql -u").append(username).append(" -p").append(password)
                .append(" -h").append(host).append(" -P").append(port).append(" -D ").append(exportDatabaseName)
                .append(" -se \"delete from ").append(tabName).append(" where ").append(condition).append("\"");
        System.out.println(command.toString());
        return command.toString();
    }

    /**
     * 删除备份文件
     */
    private void deleteBakFile() {
        String exportPath = Constants.DBSRV_T_IVR_CALLLOG_EXPORTPATH;//导出路径
    }

    public static void main(String[] args) {
        Dbsrv dbsrvCalllog = new Dbsrv();
        dbsrvCalllog.getExportCommand("course", "id >= 2 limit 1");
        dbsrvCalllog.getDeleteCommand("course", "id >= 2 limit 1");
    }
}
