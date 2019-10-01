package ss.pku.attacktraceproject.mapperService.test;

import ss.pku.attacktraceproject.honeytoken.bean.HtTraceInfo;
import ss.pku.attacktraceproject.mapperService.HtTraceInfoMapperService;

import java.util.List;

public class HtTraceInfoServiceTest {
    public static void main(String[] args) {
        HtTraceInfo info=new HtTraceInfo();
        info.setIp("192.168.1.1");
        info.setOs("windows_7");
        info.setTokenId("123456");
        info.setOther("UserAgent:Chrome74");

        HtTraceInfoMapperService service=new HtTraceInfoMapperService();
        service.insertHtTraceInfo(info);

        List<HtTraceInfo> infos=service.findTraceInfo("123456");
        for (HtTraceInfo everyInfo:infos){
            System.out.println(everyInfo.toString());
        }
    }
}
