package org.gvp.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import org.gvp.manager.pojo.SecurityUser;
/**
* 用户信息表 mp映射接口
*/
@Mapper
public interface SecurityUserMapper extends BaseMapper<SecurityUser> {

}
