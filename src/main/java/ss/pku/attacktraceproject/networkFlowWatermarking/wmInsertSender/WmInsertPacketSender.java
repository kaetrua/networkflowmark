package ss.pku.attacktraceproject.networkFlowWatermarking.wmInsertSender;

import com.alibaba.fastjson.JSON;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import ss.pku.attacktraceproject.consoleDesk.TraceControl;
import ss.pku.attacktraceproject.networkFlowWatermarking.codec.Encoder;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.Quintuple;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmInsertSender.packageGenerateModel.tcpModel.TcpSendPackageNormalModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WmInsertPacketSender {

    /*
     *  wmInfo   :需要嵌入的水印信息，是一个经过扩展的0-1比特序列
     * */
    public void senderPackets(NetworkInterface networkInterface, String wmInfo, List<TCPPacket> packets) {
        int largeTimeInterval = (int) (ProjectConstants.WM_SENDER_DELAY_STANDARD_TIME_RATIO * ProjectConstants.WM_SENDER_DELAY_STANDARD_TIME_MILLS);
        System.out.println("标准包延迟时间为:" + ProjectConstants.WM_SENDER_DELAY_STANDARD_TIME_MILLS + "毫秒");
        System.out.println("倍率包延迟时间为:" + largeTimeInterval + "毫秒");
        char[] wmInfoChar = wmInfo.toCharArray();
        if (wmInfo.length() + 1 != packets.size()) {
            System.out.println("嵌入位置与带嵌入的数据包数量不相称，无法完成数据包发送过程");
            return;
        }
        try {
            JpcapSender sender = JpcapSender.openDevice(networkInterface);
            for (int i = 0; i < packets.size()-1; i++) {
                sender.sendPacket(packets.get(i));
                //根据规则调制水印信息  比特1：延迟 standardTimeInterval ，比特0：延迟largeTimeInterval ，比例参数可调整
                try {
                    if (wmInfoChar[i] == '1') {
                        System.out.println("第" + i + "个水印信息位为1，延迟" + ProjectConstants.WM_SENDER_DELAY_STANDARD_TIME_MILLS + "毫秒");
                        Thread.sleep(ProjectConstants.WM_SENDER_DELAY_STANDARD_TIME_MILLS);
                    } else if (wmInfoChar[i] == '0') {
                        System.out.println("第" + i + "水印信息位为0，延迟" + largeTimeInterval + "毫秒");
                        Thread.sleep(largeTimeInterval);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//for
            sender.sendPacket(packets.get(packets.size()-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(NetworkInterface networkInterface, Packet packet) {
        try {
            JpcapSender sender = JpcapSender.openDevice(networkInterface);
            sender.sendPacket(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从本地读包数据
    public void readSavePackets() {
        JpcapCaptor captor;
        int count = 1;
        try {
            captor = JpcapCaptor.openFile("localsave");
            while (true) {
                Packet packet = captor.getPacket();
                if (packet == null) {
                    break;
                }
                //todo ：handler
                System.out.println("count=" + count++ + "-->" + JSON.toJSONString(packet));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgToTraceControl(Quintuple traceQunituple, String originalWmInfo) {
        TraceControl control = new TraceControl();
        control.receiveMsgFromWmInsert(traceQunituple, originalWmInfo);
    }


    //for test
    public static void main(String[] args) {
        WmInsertPacketSender sender = new WmInsertPacketSender();
        TcpSendPackageNormalModel packageModel1 = new TcpSendPackageNormalModel();

        //严重注意:此处如果要使用wmInsertReceiver中捕获的攻击者ip数据，那么就需要使用自定义的数据包模板
        //此次使用的是通过的数据包模板，好处在于方便测试
        //实际使用过程中需要切换
        TCPPacket tcpPacket = packageModel1.generateTcpPacket();
        List<TCPPacket> tcpPackets = new ArrayList<TCPPacket>();

        //1、生成待发送的数据包。PACKAGE_USE_CASE_PACKAGE_TOTAL_AMOUNT=11
        for (int j= 0;j<100;j++){//todo:  完善写法 注意包数量及encodedWmInfo 数据
        for (int i = 0; i < ProjectConstants.PACKAGE_USE_CASE_PACKAGE_TOTAL_AMOUNT-1; i++) {
            tcpPackets.add(tcpPacket);
        }}
        tcpPackets.add(tcpPacket);
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        //2、生成水印信息
        GenerateOriginalwmInfo generateOriginalwmInfo = new GenerateOriginalwmInfo();
        String originalWmInfo = generateOriginalwmInfo.generate();
        System.out.println("生成的wmInfo=" + originalWmInfo + ",其长度为:" + originalWmInfo.length());

        //3、水印信息扩展（卷积编码）
        String encodedWmInfo = "";//TODO    原来的写法     String encodedWmInfo = Encoder.startEncoder(originalWmInfo);
        String str =Encoder.startEncoder(originalWmInfo);

        System.out.println("水印扩展后的信息序列为:"+str);
        System.out.println("水印扩展后的信息序列为:"+encodedWmInfo);
        for (int i =0 ;i<100 ;i++){
            encodedWmInfo+= str ;
        }
        encodedWmInfo= simul(encodedWmInfo,1,100);// 参数2 代表加扰类型 1为包分裂，2为包合并。 参数3代表加扰次数。
        System.out.println("水印扩展后的信息序列为:"+encodedWmInfo);
        //4、完成水印信息嵌入，然后发送消息到网络，devices[0]在根据具体环境进行修改
        System.out.println("水印嵌入方将把嵌入了水印信息的网络数据流发送至网络中");
        sender.senderPackets(devices[5], encodedWmInfo, tcpPackets);

        //4、先把消息发给控制台
        Quintuple quintuple=new Quintuple(ProjectConstants.ORIGINAL_SENDER_SOURCE_IP,ProjectConstants.ORIGIINAL_SENDER_DESTINATION_IP);
        sender.sendMsgToTraceControl(quintuple,originalWmInfo);

      /*  try{
            //休眠一下，以防延迟
            Thread.sleep(500);
        }catch (InterruptedException e){
            System.out.println("线程休眠出现了异常:"+e.getMessage());
        }*/


    }
    public static String  simul(String str , int  tag, int num) {
        if(tag==0)
            return str;
        StringBuffer stringBuilder1=new StringBuffer(str);
        int len = stringBuilder1.length();
        Random rand = new Random();
        int rad =0;
        if (tag==1){
            for(int i=0;i<num;i++){
                rad =rand.nextInt(1970) + 20;
                System.out.println("水印加扰位为:"+rad/20);
                stringBuilder1.insert(rad,0);
            }
        }
        if (tag==2){
            stringBuilder1.append(stringBuilder1);
            for(int i=0;i<num;i++){
                rad =rand.nextInt(1950) + 20;
                System.out.println("水印加扰位为:"+rad/20);
                stringBuilder1.deleteCharAt(rad);
            }
        }
        return stringBuilder1.substring(0,len);
    }
}
