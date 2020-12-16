package org.felix.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.felix.model.entity.ExportLoginLogBean;
import org.felix.model.entity.SysLoginLog;
import org.felix.model.ro.LoginLogPageRO;
import org.felix.model.vo.resp.LoginLogVO;

import java.util.List;

/**
 * @author Felix
 */
public interface SysLoginLogMapper {

    int insertSelective(SysLoginLog record);

    List<LoginLogVO> selectAll(LoginLogPageRO ro);

    List<ExportLoginLogBean> selectAllLog();

    void batchDeletedLog(List<String> logIds);

}
