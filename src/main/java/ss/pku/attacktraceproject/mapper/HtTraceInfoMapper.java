package ss.pku.attacktraceproject.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import ss.pku.attacktraceproject.honeytoken.bean.HtTraceInfo;

import java.util.List;

@Mapper
@Component
//蜜标溯源方案
public interface HtTraceInfoMapper {

    @Insert("insert into ht_trace_result(token_id,ip,os,other) values(#{tokenId},#{ip},#{os},#{other})")
    void insertTraceInfo(HtTraceInfo traceInfo);

    //@Select("select token_id,ip,os,other from ht_trace_result where token_id=#{tokenId} limit 1")
    @Select("select token_id,ip,os,other from ht_trace_result where token_id=#{tokenId}")
    List<HtTraceInfo> findTraceInfo(@Param("tokenId") String tokenId);

}
