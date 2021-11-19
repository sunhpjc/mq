package com.sunhp.rocketmq.callcenter;

import java.io.File;
import java.io.IOException;

/**
 * @author sunhp
 * @Description 导出数据方法
 * @Date 2021/11/16 10:35
 **/
public class ExportData {

    public static void main(String[] args) {
        //获取当前系统时间
        try {
            export();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void export() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String command = getExportCommand();
        command = "mysqldump -uroot -p123456 -h127.0.0.1 -P3306 -t springboot course --where=\"id >= 2 limit 1\" --result-file=E:\\IDEA\\workspace2\\mq\\rocketmq-producter-8085\\src\\main\\dataBak.sql";//导出存储过程和自定义函数--routines, -R
        runtime.exec(command);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1.先上传金山云

        //2.删除数据
        String deleteCommand = "mysql -uroot -p123456 -h 127.0.0.1 -P 3306  -D springboot -se \"delete from course where id >= 2 limit 1\"";
        runtime.exec(deleteCommand);

        File file = new File("src/main/resources/databak/" + "测试" + ".sql");
        file.delete();
    }

    private static String getExportCommand() {
        StringBuffer command = new StringBuffer();
        String username = "root";//用户名
        String password = "123456";//用户密码
        String exportDatabaseName = "springboot";//需要导出的数据库名
        String host = "127.0.0.1";//从哪个主机导出数据库，如果没有指定这个值，则默认取localhost
        String port = "3306";//使用的端口号
        String exportPath = "src/main/resources/databak/" + "测试" + ".sql";//导出路径
        //这里如果想直接使用mysqldump的话需要在环境变量path下配mysql的bin目录的路径，不然的话就得用绝对路径
        command.append("mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。
                .append(" -h").append(host).append(" -P").append(port).append(" -t ").append(exportDatabaseName).append(" course --where=\"id >= 2 limit 1\"").append(" -r ").append(exportPath);
        System.out.println(command.toString());
        return command.toString();
    }
}
