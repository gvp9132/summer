package org.gvp.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gvp.common.http.Result;
import org.gvp.gateway.dto.MysqlReplica;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MySqlInfoService {
    private final DatabaseClient client ;

    /**
     * 获取mysql主从复制状态信息
     */
    public Mono<Result<MysqlReplica>> getMysqlInfo(){
        log.debug("获取mysql主从复制状态信息");
        return this.client.sql("SHOW REPLICA STATUS")
                .fetch()
                .one()
                .map(this::mapToMysqlReplica);
    }
    private Result<MysqlReplica> mapToMysqlReplica(Map<String,Object> map){
//        map.forEach((k,v) -> log.warn("key: {} , value: {}", k, v));
        MysqlReplica replica = new MysqlReplica();
        replica.setReplicaIOState(map.get("Replica_IO_State").toString());
        replica.setSourceHost(map.get("Source_Host"));
        replica.setSourceUser(map.get("Source_User"));
        replica.setSourcePort(Integer.valueOf(map.get("Source_Port").toString()));
        replica.setConnectRetry(Integer.valueOf(map.get("Connect_Retry").toString()));
        replica.setSourceLogFile(map.get("Source_Log_File").toString());
        replica.setReadSourceLogPos(Integer.valueOf(map.get("Read_Source_Log_Pos").toString()));
        replica.setRelayLogFile(map.get("Relay_Log_File").toString());
        replica.setRelayLogPos(Integer.valueOf(map.get("Relay_Log_Pos").toString()));
        replica.setRelaySourceLogFile(map.get("Relay_Source_Log_File").toString());
        replica.setSlaveIORunning(map.get("Replica_IO_Running").toString());
        replica.setSlaveSQLRunning(map.get("Replica_SQL_Running").toString());
        replica.setReplicateDoDB(map.get("Replicate_Do_DB"));
        replica.setReplicateIgnoreDB(map.get("Replicate_Ignore_DB"));
        replica.setReplicateDoTable(map.get("Replicate_Do_Table"));
        replica.setReplicateIgnoreTable(map.get("Replicate_Ignore_Table"));
        replica.setExecSourceLogPos(Integer.valueOf(map.get("Exec_Source_Log_Pos").toString()));
        replica.setRelayLogSpace(Integer.valueOf(map.get("Relay_Log_Space").toString()));
        replica.setSourceUUID(map.get("Source_UUID").toString());
        replica.setRelayLogSpace(Integer.valueOf(map.get("Relay_Log_Space").toString()));
        replica.setReplicaSQLRunningState(map.get("Replica_SQL_Running_State").toString());
        replica.setSourceRetryCount(Integer.valueOf(map.get("Source_Retry_Count").toString()));
        replica.setLastIOErrorTimestamp(map.get("Last_IO_Error_Timestamp"));
        replica.setLastSQLErrorTimestamp(map.get("Last_SQL_Error_Timestamp"));
        replica.setLastIOError(map.get("Last_IO_Error"));
        replica.setLastSQLError(map.get("Last_SQL_Error"));
        replica.setLastIOErrno(Integer.valueOf(map.get("Last_IO_Errno").toString()));
        replica.setLastSQLErrno(Integer.valueOf(map.get("Last_SQL_Errno").toString()));
        replica.setTimestamp(LocalDateTime.now());
//        log.debug("获取mysql主从复制状态信息: {}" , replica);
        return Result.ok(replica);
    }
}
