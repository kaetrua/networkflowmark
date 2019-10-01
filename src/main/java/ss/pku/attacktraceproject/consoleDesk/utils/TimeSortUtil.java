package ss.pku.attacktraceproject.consoleDesk.utils;

import com.alibaba.fastjson.JSON;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.WmCheckResult;

import java.util.*;

public class TimeSortUtil {
    //时间为long型，但数据库存储统一使用string类型，在使用的时候需要进行转化
    public static WmCheckResult[] sort(WmCheckResult[] wmCheckResults){

       Arrays.sort(wmCheckResults, new Comparator<WmCheckResult>() {
           @Override
           public int compare(WmCheckResult o1, WmCheckResult o2) {
               long time1=Long.parseLong(o1.getTime());
               long time2=Long.parseLong(o2.getTime());
               if (time1==time2) return 0;
               else if (time1<time2) return -1;
               else return 1;
           }
       });
       return wmCheckResults;
    }


    //for test
    public static void main(String[] args) {
        WmCheckResult result1=new WmCheckResult();
        result1.setCheckResult("false");
        result1.setMyIp("192.168.100.1");
        result1.setOriginalWmInfo("100000");
        result1.setTime("1234");

        WmCheckResult result2=new WmCheckResult();
        result2.setCheckResult("false");
        result2.setMyIp("192.168.100.2");
        result2.setOriginalWmInfo("100000");
        result2.setTime("123");

        WmCheckResult[] results=new WmCheckResult[]{result1,result2};
        WmCheckResult[] newResults=sort(results);
        for (WmCheckResult result:newResults){
            System.out.println(JSON.toJSONString(result));
        }
    }
}
