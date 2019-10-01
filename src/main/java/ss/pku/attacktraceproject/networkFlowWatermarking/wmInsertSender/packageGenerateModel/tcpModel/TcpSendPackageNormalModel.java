package ss.pku.attacktraceproject.networkFlowWatermarking.wmInsertSender.packageGenerateModel.tcpModel;

import com.alibaba.fastjson.JSON;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import ss.pku.attacktraceproject.utils.InetAddressIpUtils;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;

import java.net.InetAddress;

public class TcpSendPackageNormalModel {
    //默认通用模板五元组信息
    public TCPPacket generateTcpPacket() {
        TCPPacket p = new TCPPacket(50758, 444, 418049707, 2086027412, false, false, false, false, true, true, true, false, 31, 0);
       /* p.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,100, IPPacket.IPPROTO_TCP,
                InetAddress.getByName("www.microsoft.com"),InetAddress.getByName("www.google.com"));*/
        InetAddress src = null;
        InetAddress dst = null;

        //src=InetAddress.getLocalHost();
        //本机ip
        String src_ip = ProjectConstants.ORIGINAL_SENDER_SOURCE_BYTE_IP;
        src = InetAddressIpUtils.ip2InetAddress(src_ip);
        dst = InetAddressIpUtils.ip2InetAddress(ProjectConstants.ORIGIINAL_SENDER_DESTINATION_IP);
        System.out.println("src=" + src.toString());
        System.out.println("dst=" + dst.toString());

        //测试为本机给本机发送数据包
        p.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_TCP,
                src, dst);

        p.data = ("data").getBytes();

        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_IP;
        ether.src_mac = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
        ether.dst_mac = new byte[]{(byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10};
        p.datalink = ether;
        return p;
    }

    public static void main(String[] args) {
        TcpSendPackageNormalModel packageModel1 = new TcpSendPackageNormalModel();
        TCPPacket tcpPacket = packageModel1.generateTcpPacket();
        System.out.println(JSON.toJSONString(tcpPacket));
    }
}
