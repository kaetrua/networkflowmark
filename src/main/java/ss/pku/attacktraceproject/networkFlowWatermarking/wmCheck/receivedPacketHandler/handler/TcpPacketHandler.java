package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;

import jpcap.packet.TCPPacket;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;

import java.util.ArrayList;
import java.util.List;

public class TcpPacketHandler {
    static List<Long> receivedTimeSet = new ArrayList<Long>();

    public TcpPacketHandler() {

    }

    public void handler(TCPPacket tcpPacket) {
        //解析json数据，分离出需要的所有数据并存入数据库
/*        System.out.println("---------------TcpPacketHandler.handler().start----------------");
        //TCPPacket(int src_port,int dst_port,long sequence,long ack_num,
        //					 boolean urg,boolean ack,boolean psh,boolean rst,
        //					 boolean syn,boolean fin,boolean rsv1,boolean rsv2,
        //					 int window,int urgent)
        System.out.println(JSON.toJSONString(tcpPacket));

        System.out.println("src_port:"+tcpPacket.src_port);
        System.out.println("dst_port:"+tcpPacket.dst_port);
        System.out.println("sequence:"+tcpPacket.sequence);
        System.out.println("ack_num:"+tcpPacket.ack_num);
        System.out.println("urg:"+tcpPacket.urg);
        System.out.println("ack:"+tcpPacket.ack);
        System.out.println("psh:"+tcpPacket.psh);
        System.out.println("rst:"+tcpPacket.rst);
        System.out.println("syn:"+tcpPacket.syn);
        System.out.println("fin:"+tcpPacket.fin);
        System.out.println("rsv1:"+tcpPacket.rsv1);
        System.out.println("rsv2:"+tcpPacket.rsv2);
        System.out.println("window:"+tcpPacket.window);
        System.out.println("urgent:"+tcpPacket.urgent_pointer);

        System.out.println("需要持久化的数据------------------->start---------------------");
        System.out.println("src_ip="+tcpPacket.src_ip.toString());
        System.out.println("src_port="+tcpPacket.src_port);
        System.out.println("dst_ip="+tcpPacket.dst_ip);
        System.out.println("dst_port="+tcpPacket.dst_port);
        System.out.println("tcpPacketVersion="+tcpPacket.version);
        System.out.println("需要持久化的数据------------------->end---------------------");
        System.out.println("---------------TcpPacketHandler.handler().end----------------");*/

        if (tcpPacket.version == 4 && tcpPacket.src_ip.toString().equals(ProjectConstants.ORIGINAL_SENDER_SOURCE_IP)) {
            //
            System.out.println("-------------------------------");
            receivedTimeSet.add(System.currentTimeMillis());
            System.out.println(System.currentTimeMillis());

          /*  for (long a:receivedTimeSet){
                System.out.print(a+"<--->");
            }*/
        }
    }

    public List<Long> showReceivedTimeSet() {
        return receivedTimeSet;
    }
}
