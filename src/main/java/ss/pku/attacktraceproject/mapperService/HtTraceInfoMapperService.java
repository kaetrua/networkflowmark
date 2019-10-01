package ss.pku.attacktraceproject.mapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ss.pku.attacktraceproject.honeytoken.bean.HtTraceInfo;
import ss.pku.attacktraceproject.mapper.HtTraceInfoMapper;

import java.util.List;

@Service
public class HtTraceInfoMapperService {

    @Autowired
    private HtTraceInfoMapper traceInfoMapper;

    public void insertHtTraceInfo(HtTraceInfo traceInfo){
        traceInfoMapper.insertTraceInfo(traceInfo);
    }

    public List<HtTraceInfo> findTraceInfo(String ht_token_id){
        return traceInfoMapper.findTraceInfo(ht_token_id);
    }

}
