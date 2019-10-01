package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck;

import com.alibaba.fastjson.JSON;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler.TcpPacketThreadHandler;
import ss.pku.attacktraceproject.utils.InetAddressIpUtils;

public class WmCheckPacketReceiverThreadHandler implements PacketReceiver {
    @Override
    public void receivePacket(Packet packet) {
        if (packet instanceof jpcap.packet.TCPPacket) {
            TCPPacket tcpPacket = (TCPPacket) packet;
            int version=tcpPacket.version;
            String src_ip=InetAddressIpUtils.inetAddress2IP(tcpPacket.src_ip);
            String dst_ip=InetAddressIpUtils.inetAddress2IP(tcpPacket.dst_ip);
            int src_port=tcpPacket.src_port;
            int dst_port=tcpPacket.dst_port;

            System.out.println("水印检测方收到数据包,其五元组信息分别为:version="+version+",src_ip="+src_ip+",src_port="+src_port+",dst_ip="+dst_ip+",dst_port="+dst_port);

            if (
                    //1、过滤杂包
                    version == 4
                    && src_ip.equals(ProjectConstants.ORIGINAL_SENDER_SOURCE_IP)
                    && dst_ip.equals(ProjectConstants.ORIGIINAL_SENDER_DESTINATION_IP)
                    && src_port == 50758
                    && dst_port == 444
            ) {
                //对每个包都单独进行处理
                TcpPacketThreadHandler threadHandler = new TcpPacketThreadHandler();
                TCPPacket tcpp = (TCPPacket) packet;
                threadHandler.handler(tcpp);
            }
        }
    }
}
