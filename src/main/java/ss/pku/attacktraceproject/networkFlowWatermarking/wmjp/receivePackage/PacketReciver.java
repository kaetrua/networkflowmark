package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.receivePackage;

import com.alibaba.fastjson.JSON;
import jpcap.*;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.TcpUdpReceiver;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler.IpPacketHandler;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler.TcpPacketHandler;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler.UdpPacketHandler;

import java.io.IOException;
import java.util.Scanner;

public class PacketReciver implements PacketReceiver {
    public int model;
    public static JpcapCaptor jpcapCaptor;
    JpcapWriter jpcapWriter;

    public PacketReciver(int n) {
        //1:TCP   2:UDP
        this.model = n;
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
            jpcapCaptor = JpcapCaptor.openDevice(devices[index], 100, false, 100);

            //本项目只需要捕获tcp数据包,这部分setfilter过滤条件的原语在官方文档中并没有说明，condition设置可以参考下文档
            // https://www.cnblogs.com/sun-flower1314/p/10630424.html
            jpcapCaptor.setFilter("tcp", true);

            jpcapCaptor.loopPacket(100, new PacketReciver(1));
//            JpcapWriter jpcapWriter=JpcapWriter.openDumpFile(jpcapCaptor,"localFile");

/*            for (int j=0;j<10;j++){
               Packet packet= jpcapCaptor.getPacket();
               jpcapWriter.writePacket(packet);
            }*/
            // JpcapWriter writer=
        } catch (IOException e) {
            System.out.println("异常点：startCaptor()-->" + e.getMessage());
        }
/*        finally {
            jpcapCaptor.close();
        }*/
    }

    public void stopCaptor() {
        jpcapCaptor.breakLoop();
    }

    //目前支持tcp和udp
    public void receivePacket(Packet packet) {
        JpcapWriter jpcapWriter = null;
        //参考链接 https://blog.csdn.net/m0_37433067/article/details/71159321

        //tcp
        if (packet instanceof jpcap.packet.TCPPacket && model == 1) {
            TCPPacket p = (TCPPacket) packet;
            TcpUdpReceiver receiver = new TcpUdpReceiver(p.src_ip.toString(), p.src_port, p.dst_ip.toString(), p.dst_port, p.len);
            //打印
            //System.out.println(JSON.toJSONString(receiver));
            //System.out.println("unix毫秒数:"+System.currentTimeMillis()+"，包数据"+JSON.toJSONString(p));
            try {
                if (jpcapCaptor == null) {
                    System.out.println("jpcapCaptor Object is null.");
                }
                jpcapWriter = JpcapWriter.openDumpFile(jpcapCaptor, "localsave");
                // System.out.println("<--------------------------------->");
                jpcapWriter.writePacket(p);
            } catch (IOException e) {
                System.out.println("发生了异常.");
                e.printStackTrace();
            }
            /*finally {
                jpcapWriter.close();
            }*/
            //对满足要求的数据包单独进行处理
            TcpPacketHandler tcpPacketHandler = new TcpPacketHandler();
            TCPPacket tcpPacket = (TCPPacket) packet;
            tcpPacketHandler.handler(tcpPacket);

            //暂未使用到
            IPparser(packet);
        }

        //udp: 暂未使用到
        else if (packet instanceof jpcap.packet.UDPPacket && model == 2) {
            UDPPacket p = (UDPPacket) packet;
            TcpUdpReceiver receiver = new TcpUdpReceiver(p.src_ip.toString(), p.src_port, p.dst_ip.toString(), p.dst_port, p.len);
            //打印
            System.out.println(JSON.toJSONString(receiver));

            UdpPacketHandler handler = new UdpPacketHandler();
            UDPPacket udpPacket = (UDPPacket) packet;
            handler.handler(udpPacket);

            IPparser(packet);
        }

        IPparser(packet);
    }

    public void IPparser(Packet packet) {
        IpPacketHandler ipPacketHandler = new IpPacketHandler();
        IPPacket ipPacket = (IPPacket) packet;
        ipPacketHandler.handler(ipPacket);
    }

    public static void main(String[] args) {
        PacketReciver reciver = new PacketReciver(1);
        reciver.startCaptor();
    }

}
