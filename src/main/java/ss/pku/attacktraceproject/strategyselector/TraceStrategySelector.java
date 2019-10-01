package ss.pku.attacktraceproject.strategyselector;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;

public class TraceStrategySelector {

    public static int startSelector(List<String> iptables) {
        //返回-1，错误
        //返回1 代表稳定性差，使用蜜标溯源方案
        //返回0，代表稳定性好，使用网络流水印溯源方案
        int result=-1;

        int totalLost=0;
        for (String ip:iptables){
            totalLost+=pingIp(ip);
        }
        if ( (iptables.size()*StrategySelectorConstants.PING_PACKAGE_NUM*StrategySelectorConstants.STABLE_STANDARD)>totalLost){
            //稳定性好
            result=0;
        }else {
            result=1;
        }
        return result;
    }

    private static int  pingIp(String ip){
        //返回丢包数
        int lostNum=0;

      try {
          InetAddress address=InetAddress.getByName(ip);
          if (!address.isReachable(5000)){
              //主机不可达
              lostNum=StrategySelectorConstants.PING_PACKAGE_NUM;
              return lostNum;
          }
          Process p=Runtime.getRuntime().exec("ping "+ip+" -n "+StrategySelectorConstants.PING_PACKAGE_NUM);
          BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));
          String line;
          StringBuilder sb=new StringBuilder();
          while((line = reader.readLine()) != null ){
              sb.append(line);
          }
          lostNum=SelectorParserUtil.parser(sb.toString());
      }catch (Exception e){
          e.printStackTrace();
      }
      return lostNum;
    }
    //for test
    public static void main(String[] args) {
        //192.168.199.190
        //123.125.114.114
        //123.125.114.114
        //System.out.println(TraceStrategySelector.pingIp("123.125.114.114"));
    }

}
