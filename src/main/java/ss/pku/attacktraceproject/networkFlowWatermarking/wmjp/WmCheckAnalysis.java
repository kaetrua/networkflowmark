package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp;

import ss.pku.attacktraceproject.consoleDesk.TraceControl;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.Quintuple;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.WmCheckResult;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db.WmCheckFindNetFlowReceivedTimeSet;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.WmCheckPacketReceiverThread;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db.WmCheckResultStore;
import ss.pku.attacktraceproject.utils.LocalHostIpUtil;

import java.util.ArrayList;
import java.util.List;

//水印检测方
public class WmCheckAnalysis {
    public void CheckAnalysis(Quintuple netFlowQuintuple, String originalWmInfo){
        //收包-->包过滤+记录收包时间+包存库处理
/*        WmCheckPacketReceiverThread thread = new WmCheckPacketReceiverThread();
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            System.out.println("CheckAnalysis- thread.join()发生了异常:"+e.getMessage());
        }*/

      /*  try {
            //休眠一定时间
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/

        String quintupleInfo=netFlowQuintuple.src_ip+netFlowQuintuple.src_port+netFlowQuintuple.dst_ip+netFlowQuintuple.dst_port;

        //待检测到结果并存库之后，执行接收检测（查询各自的数据库，找到符合要求的最新数据包集合信息）
        List<String> timeSetStrList= WmCheckFindNetFlowReceivedTimeSet.findNetFlowReceivedTimeSet(quintupleInfo);

        //转化成Long
        List<Long> timeSetLongList=new ArrayList<Long>();
        for (String s:timeSetStrList){
            timeSetLongList.add(Long.parseLong(s));
        }
        String myIp= LocalHostIpUtil.getLocalHostIp();
        //计算包间时间延迟
        List<Long> timeDelay= WmReceivedTimeSeqCompute.computeReceivedPackageDelay(timeSetLongList);
        System.out.println("水印检测方"+myIp+"接收的数据包间隔时间分别是:");
        for (Long erveryPacketDelay:timeDelay){
            System.out.print(erveryPacketDelay+"-->");
        }
        System.out.println();


        //还原水印信息
        System.out.println("水印检测方ip地址"+ ProjectConstants.ORIGIINAL_SENDER_DESTINATION_IP +" ");
        String wmCheck_info=WmReceivedTimeSeqCompute.computeWmInfo(timeDelay);
        String decodedWmInfo=WmReceivedTimeSeqCompute.computeOriginalWmInfo(wmCheck_info);

        boolean isSim= WmReceivedTimeSeqCompute.similarityCompute(originalWmInfo,decodedWmInfo);

        WmCheckResult checkResult=new WmCheckResult();
        checkResult.setTime(String.valueOf(System.currentTimeMillis()));
        checkResult.setCheckResult(String.valueOf(isSim));

        checkResult.setMyIp(myIp);
        checkResult.setOriginalWmInfo(originalWmInfo);

        //检测结果入库
        WmCheckResultStore resultStore=new WmCheckResultStore();
        System.out.println("水印检测方:"+myIp+" 开始存储检测结果");
        resultStore.storeWmCheckResult(checkResult);

    }
}
