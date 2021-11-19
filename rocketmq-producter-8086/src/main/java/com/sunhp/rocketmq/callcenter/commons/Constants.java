package com.sunhp.rocketmq.callcenter.commons;

/**
 * @Description
 **/
public class Constants {
    public static final String DUMP_PARAM = " --max_allowed_packet=512M --set-gtid-purged=OFF --master-data=2 --single-transaction --compress";

    //dbsrv t_ivr_calllog
    public static final String DBSRV_T_IVR_CALLLOG_HOST = "127.0.0.1";
    public static final String DBSRV_T_IVR_CALLLOG_PORT = "3306";
    public static final String DBSRV_T_IVR_CALLLOG_DATABASENAME = "test";
    public static final String DBSRV_T_IVR_CALLLOG_USERNAME = "root";
    public static final String DBSRV_T_IVR_CALLLOG_PASSWORD = "123456";
//    public static final String DBSRV_T_IVR_CALLLOG_EXPORTPATH = "C:\\Users\\jc\\Desktop\\ucn\\callcenter_bak\\" + "测试" + ".sql";
    public static final String DBSRV_T_IVR_CALLLOG_EXPORTPATH = "src/main/resources/databak" + "测试" + ".sql";

    //freelink t_ivr_calllog
    public static final String FREELINK_T_IVR_CALLLOG_HOST = "127.0.0.1";
    public static final String FREELINK_T_IVR_CALLLOG_PORT = "3306";
    public static final String FREELINK_T_IVR_CALLLOG_DATABASENAME = "test";
    public static final String FREELINK_T_IVR_CALLLOG_USERNAME = "root";
    public static final String FREELINK_T_IVR_CALLLOG_PASSWORD = "123456";
    public static final String FREELINK_T_IVR_CALLLOG_EXPORTPATH = "";

    //orderdb business_log
    public static final String ORDERDB_BUSINESS_LOG_HOST = "127.0.0.1";
    public static final String ORDERDB_BUSINESS_LOG_PORT = "3306";
    public static final String ORDERDB_BUSINESS_LOG_DATABASENAME = "test";
    public static final String ORDERDB_BUSINESS_LOG_USERNAME = "root";
    public static final String ORDERDB_BUSINESS_LOG_PASSWORD = "123456";
    public static final String ORDERDB_BUSINESS_LOG_EXPORTPATH = "";
}
