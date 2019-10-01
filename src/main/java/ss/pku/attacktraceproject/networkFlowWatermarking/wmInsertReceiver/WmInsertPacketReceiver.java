package ss.pku.attacktraceproject.networkFlowWatermarking.wmInsertReceiver;

import jpcap.*;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmInsertReceiver.bean.WmInsertPacketReceivedQuintupleBean;
import ss.pku.attacktraceproject.utils.InetAddressIpUtils;

import java.io.IOException;
import java.util.*;

//水印嵌入方抓包，提取数据流五元组，确定唯一的数据流
//独立模块
public class WmInsertPacketReceiver implements PacketReceiver {
    public static String WmInsertPacketReceiver_IP=ProjectConstants.WM_INSERT_PACKET_RECEIVER_DESTINATION_HOST_IP;

    public static List<String> attackerSourceIpList=new ArrayList<>();
    public static JpcapCaptor jpcapCaptor;

    public WmInsertPacketReceiver() {

    }

    public void startCaptor() {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        if (devices.length == 0) {
            System.out.println("没有网卡");
            return;
        }

        for (int i = 0; i < devices.length; i++) {
            System.out.println("网卡 " + i + " 名称: " + devices[i].name);
            for (NetworkInterfaceAddress address : devices[i].addresses) {
                System.out.println(address.address + "  ");
            }
            System.out.println("\n");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("选择需要监控的网卡序号:");
        int index = scanner.nextInt();
/*        int index=0;
        if (devices.length>1){  //去掉虚拟网卡
            index=1;
        }*/
        try {
            /*
            * 第一个参数 类型:NetworkInterface  想要打开的网卡设备实例
              第二个参数 类型 int               一次捕捉数包的最大的字节
              第三个参数 类型 boolean           是否设置网卡为混乱模式
              第四个参数  类型 int              捕捉包的超时时间,一般为20ms
            * */
            jpcapCaptor = JpcapCaptor.openDevice(devices[index], 10000, false, 100);

            //本项目只需要捕获tcp数据包,这部分setfilter过滤条件的原语在官方文档中并没有说明，condition设置可以参考下文档
            // https://www.cnblogs.com/sun-flower1314/p/10630424.html
            jpcapCaptor.setFilter("tcp", true);

            jpcapCaptor.loopPacket(-1, new WmInsertPacketReceiver());

        } catch (IOException e) {
            System.out.println("异常点：startCaptor()-->" + e.getMessage());
        }

    }

    public void stopCaptor() {
        jpcapCaptor.breakLoop();
    }

    //目前支持tcp和udp
    public void receivePacket(Packet packet) {
        //JpcapWriter jpcapWriter = null;
        //参考链接 https://blog.csdn.net/m0_37433067/article/details/71159321
       // System.out.println(JSON.toJSONString(packet));
        //tcp
        if (packet instanceof jpcap.packet.TCPPacket && InetAddressIpUtils.inetAddress2IP(((TCPPacket) packet).dst_ip).equals(WmInsertPacketReceiver_IP)) {
            TCPPacket tcpPacket = (TCPPacket) packet;
            //打印
            //System.out.println(JSON.toJSONString(receiver));
            //System.out.println("unix毫秒数:"+System.currentTimeMillis()+"，包数据"+JSON.toJSONString(p));
            try {
                if (jpcapCaptor == null) {
                    System.out.println("jpcapCaptor Object is null.");
                }
                //对满足要求的数据包单独进行处理
                WmInsertPacketReceivedQuintupleBean quintupleBean=new WmInsertPacketReceivedQuintupleBean();
                //接收数据网卡的ip地址
                quintupleBean.dst_ip= WmInsertPacketReceiver_IP;
                quintupleBean.dst_port=tcpPacket.dst_port;
                quintupleBean.src_ip=InetAddressIpUtils.inetAddress2IP(tcpPacket.src_ip);
                quintupleBean.src_port=tcpPacket.src_port;
                quintupleBean.version=tcpPacket.version;

                //在一段时间内，来自于统一源地址ip的不同流数据虽然数据不一样，但是在此处对于它们的处理只回复一条网络水印流，
                //因为对每一条流都回复一条水印流，对于该ip的追踪并没有太多的实际意义。
               /* int hashcode=quintupleBean.hashCode();
                if (!receivedQuintupleBeanMap.containsKey(hashcode)){
                    receivedQuintupleBeanMap.put(hashcode,quintupleBean);
                    System.out.println("--------------");
                    receivedQuintupleBeanMap.forEach((key,value)->{
                        System.out.println("key="+key+",value="+value);
                    });
                }*/
                System.out.println("水印嵌入方捕获到数据流:五元组（src_ip,src_port,dst_ip,dst_port,version）信息为"+quintupleBean.src_ip+":"+quintupleBean.src_port+":"+quintupleBean.dst_ip+":"+quintupleBean.dst_port+":"+quintupleBean.version);
/*                if (!attackerSourceIpList.contains(quintupleBean.src_ip)){
                    attackerSourceIpList.add(quintupleBean.src_ip);
                    for (String attackerIp:attackerSourceIpList){
                        System.out.print(attackerIp+"-->");
                    }
                    System.out.println();
                    System.out.println("----------------");
                }*/

            } catch (Exception e) {
                System.out.println("发生了异常.");
                e.printStackTrace();
            }

        }

    }

    //only for test
    public static void main(String[] args) {
        WmInsertPacketReceiver reciver = new WmInsertPacketReceiver();
        reciver.startCaptor();
    }

}
