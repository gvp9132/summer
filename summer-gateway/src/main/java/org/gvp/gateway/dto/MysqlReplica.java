package org.gvp.gateway.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mysql 主从复制状态信息
 */
@Data
public class MysqlReplica implements Serializable {

    /**
     * 从机IO状态
     */
    private String replicaIOState ;
    /**
     * 从机host地址
     */
    private Object sourceHost ;
    /**
     * 从机用户名
     */
    private Object sourceUser ;
    /**
     * 从机端口
     */
    private Integer sourcePort ;
    /**
     * 从机连接重试次数
     */
    private Integer connectRetry ;
    /**
     * 从机日志文件
     */
    private String sourceLogFile ;
    /**
     * 从机读取日志位置
     */
    private Integer readSourceLogPos ;
    /**
     * 从机中继日志文件
     */
    private String relayLogFile ;
    /**
     * 从机中继日志位置
     */
    private Integer relayLogPos ;
    /**
     * 从机中继日文件名称
     */
    private String relaySourceLogFile ;
    /**
     * 从机IO状态
     */
    private String slaveIORunning ;
    /**
     * 从机SQL状态
     */
    private String slaveSQLRunning ;
    /**
     * 执行主从复制的数据库
     */
    private Object replicateDoDB ;
    /**
     * 忽略主从复制的数据库
     */
    private Object replicateIgnoreDB ;
    /**
     * 执行主从复制的表
     */
    private Object replicateDoTable ;
    /**
     * 忽略主从复制的表
     */
    private Object replicateIgnoreTable ;
    /**
     * 执行复制的日志定位
     */
    private Integer execSourceLogPos ;
    /**
     * 中继日志空间
     */
    private Integer relayLogSpace ;
    /**
     * 从机中继UUID
     */
    private String sourceUUID ;
    /**
     * 从机SQL运行状态
     */
    private String replicaSQLRunningState ;
    /**
     * 源重试计数
     */
    private Integer sourceRetryCount ;
    /**
     * 从机最后IO连接错误时间
     */
    private Object lastIOErrorTimestamp ;
    /**
     * 从机最后SQL连接错误时间
     */
    private Object lastSQLErrorTimestamp ;
    /**
     * 从机最后IO错误编号
     */
    private Integer lastIOErrno ;
    /**
     * 从机最后IO错误
     */
    private Object lastIOError ;
    /**
     * 从机最后SQL错误编号
     */
    private Integer lastSQLErrno ;
    /**
     * 从机最后SQL错误
     */
    private Object lastSQLError ;
    /**
     * 时间戳
     */
    private LocalDateTime timestamp;
}
