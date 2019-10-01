package ss.pku.attacktraceproject.consoleDesk;

import com.alibaba.fastjson.JSON;
import ss.pku.attacktraceproject.consoleDesk.db.FindStartAndEndIps;
import ss.pku.attacktraceproject.consoleDesk.db.StoreWmQuintuple;
import ss.pku.attacktraceproject.consoleDesk.utils.TimeSortUtil;
import ss.pku.attacktraceproject.honeytoken.bean.HtTraceInfo;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.WmCheckAnalysis;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.Quintuple;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.WmCheckResult;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db.WmCheckeResultSetFind;

import java.util.List;

public class TraceControl {

    //水印嵌入方的ip地址和原始水印信息发送给控制台
    public void receiveMsgFromWmInsert(Quintuple netFlowQuintuple, String wmInfo) {
        System.out.println("溯源控制分析平台：TraceControl.receiveMsgFromWmInsert()开始存库操作。");
        //1、存库操作
        StoreWmQuintuple.storeWmQuintuple(netFlowQuintuple,wmInfo);

        System.out.println("溯源控制分析平台：starting TraceControl.receiveMsgFromWmInsert()");
        //2、收到溯源请求，发送消息给水印提取检测方
        TraceControl control = new TraceControl();
        control.sendTraceMsgToWmCheck(netFlowQuintuple, wmInfo);

    }

    public void sendTraceMsgToWmCheck(Quintuple netFlowQuintuple, String originalWmInfo) { 
        System.out.println("starting TraceControl.sendTraceMsgToWmCheck()");
        WmCheckAnalysis analysis=new WmCheckAnalysis();
        analysis.CheckAnalysis(netFlowQuintuple,originalWmInfo);

        //在发送命令一段时间后，检测数据库
       try {
           Thread.sleep(5000);
       }catch (InterruptedException e){
           e.printStackTrace();
       }
       //根据五元组查库
        receiveCheckResultFromWmCheck(originalWmInfo);
    }

    //水印方案检测结果
    public static  void receiveCheckResultFromWmCheck(String wmInfo) {
        System.out.println("starting TraceControl.receiveCheckResultFromWmCheck()");
        //查库
        List<WmCheckResult> wmCheckResultList=WmCheckeResultSetFind.findWmCheckResultSet(wmInfo);
        WmCheckResult[] wmCheckResults=new WmCheckResult[wmCheckResultList.size()];
        wmCheckResultList.toArray(wmCheckResults);
        //水印流路径分析
        analsisTracePath(wmCheckResults);
    }

    //水印方案路径分析
    public static void analsisTracePath(WmCheckResult[] wmCheckResults) {
        System.out.println("starting TraceControl.analsisTracePath()");
        //首先去找水印信息对应的五元组起止ip
        List<String> start_end_ips=FindStartAndEndIps.findIps(wmCheckResults[0].getOriginalWmInfo());
        WmCheckResult[] sortedResults= TimeSortUtil.sort(wmCheckResults);
        //输出水印传输路径
        System.out.println();
        System.out.println("溯源控制分析平台接收并分析了来自网络流水印溯源子策略追踪到的路径信息,网络流水印信息的传输路径结果为:");
 /*       System.out.print(start_end_ips.get(0)+"-->");
        System.out.println(ProjectConstants.ORIGIINAL_SENDER_DESTINATION_IP);
*/
        for (WmCheckResult result:sortedResults){
            System.out.print(result.getMyIp()+"-->");
            System.out.println(ProjectConstants.ORIGIINAL_SENDER_DESTINATION_IP);
        }
        //System.out.print(start_end_ips.get(1));
    }

    //水印方案
/*    public static void storeWmQuintuple(Quintuple netFlowQuintuple, String wmInfo){
        Connection connection = DBUtils.getConnection();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }
        String sql = "insert into wm_netflow_quintuple(original_wm_info,src_ip,src_port,dst_ip,dst_port) values('"+wmInfo+"','"+netFlowQuintuple.src_ip+"','"+netFlowQuintuple.src_port+"','"+netFlowQuintuple.dst_ip+"','"+netFlowQuintuple.dst_port+"')";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    //蜜标方案检测结果
    public void receiveMsgFromHoneyTokenServer(String honeytokenTraceResult) {
        HtTraceInfo traceResult = JSON.parseObject(honeytokenTraceResult, HtTraceInfo.class);
        String tokenId = traceResult.tokenId;
        String attackerIp = traceResult.ip;
        String attackerOs = traceResult.os;
        String otherInfo = traceResult.other;

        //直接show结果
        System.out.println("溯源控制分析平台接收到来自蜜标溯源子策略追踪到的攻击者指纹信息,信息结果为:");
        System.out.println("蜜标honeytokenId=" + tokenId);
        System.out.println("攻击者指纹信息为=" + "[" + "IP:"  + attackerIp + "," + "os:" + attackerOs + "," + "other:" + otherInfo + "]");
    }


}
