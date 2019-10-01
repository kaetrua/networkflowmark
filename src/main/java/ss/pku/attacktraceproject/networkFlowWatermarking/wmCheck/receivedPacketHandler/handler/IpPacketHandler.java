package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;

import jpcap.packet.IPPacket;

import java.net.InetAddress;

public class IpPacketHandler {
    public IpPacketHandler() {

    }

    //本实验中使用的都是IPv4地址，暂不考虑v6
    public void handler(IPPacket ipPacket) {
        //ip报文的格式，参考链接： https://www.cnblogs.com/kakaxisir/p/4187706.html
        //  System.out.println("---------------IpPacketHandler.handler().start----------------");
        //setIPv4Parameter(int priority, boolean d_flag, boolean t_flag,
        //			boolean r_flag, int rsv_tos, boolean rsv_frag, boolean dont_frag,
        //			boolean more_frag, int offset, int ident, int ttl, int protocol,
        //			InetAddress src, InetAddress dst)
        int ip_priority = ipPacket.priority;
        boolean ip_d_flag = ipPacket.d_flag;
        boolean ip_t_flag = ipPacket.t_flag;
        boolean ip_r_flag = ipPacket.r_flag;
        int ip_rsv_tos = ipPacket.rsv_tos;
        boolean ip_rsv_frag = ipPacket.rsv_frag;
        boolean ip_dont_frag = ipPacket.dont_frag;
        boolean ip_more_frag = ipPacket.more_frag;
        int ip_offset = ipPacket.offset;
        int ip_ident = ipPacket.ident;
        //ip ttl字段无法直接从IpPacket报文中提取到，设置一个自定义的默认值:16，可修改
        int ip_ttl = 16;
        int ip_protocol = ipPacket.protocol;
        InetAddress ip_src_ip = ipPacket.src_ip;
        InetAddress ip_dst_ip = ipPacket.dst_ip;

        //  System.out.println("---------------IpPacketHandler.handler().end----------------");
    }
}
