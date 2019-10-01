package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db;

import jpcap.packet.TCPPacket;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler.TcpPacketThreadHandler;
import ss.pku.attacktraceproject.utils.DBUtils;
import ss.pku.attacktraceproject.utils.InetAddressIpUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WmCheckStorePacketThreadInfo {

    public static void storePacketInfo(TCPPacket tcpPacket,String receivedPacketTimeStr){
        Connection connection = DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("WmCheckStorePacketThreadInfo.storePacketInfo()没有获取到connection，无法完成数据库操作");
        }
        String tcp_src_ip= InetAddressIpUtils.inetAddress2IP(tcpPacket.src_ip);
        //String tcp_src_ip="192.168.199.148";
        String tcp_src_port=String.valueOf(tcpPacket.src_port);
        String tcp_dst_ip= InetAddressIpUtils.inetAddress2IP(tcpPacket.dst_ip);
        //String tcp_dst_ip="192.168.0.112";
        String tcp_dst_port=String.valueOf(tcpPacket.dst_port);


        //拼接五元组(排除了版本号,目前测试使用到的都是ipv4，version=4)
        String quintupleInfo=tcp_src_ip+tcp_src_port+tcp_dst_ip+tcp_dst_port;
        String tcp_sequence=String.valueOf(tcpPacket.sequence);
        String tcp_ack_num=String.valueOf(tcpPacket.ack_num);
        String tcp_ack=String.valueOf(tcpPacket.ack);
        String tcp_urg=String.valueOf(tcpPacket.urg);
        String tcp_psh=String.valueOf(tcpPacket.psh);
        String tcp_rst=String.valueOf(tcpPacket.rst);
        String tcp_syn=String.valueOf(tcpPacket.syn);
        String tcp_fin=String.valueOf(tcpPacket.fin);
        String tcp_rsv1=String.valueOf(tcpPacket.rsv1);
        String tcp_rsv2=String.valueOf(tcpPacket.rsv2);
        String tcp_window=String.valueOf(tcpPacket.window);
        String tcp_urgent_pointer=String.valueOf(tcpPacket.urgent_pointer);

        String sql = "insert into wm_check_received_tcp_packet(quintuple_info,received_time,tcp_sequence,tcp_ack_num,tcp_urg,tcp_ack,tcp_psh,tcp_rst,tcp_syn,tcp_fin,tcp_rsv1,tcp_rsv2,tcp_window,tcp_urgent_pointer) values('"+quintupleInfo+"','"+receivedPacketTimeStr+"',"+tcp_sequence+","+tcp_ack_num+","+tcp_urg+","+tcp_ack+","+tcp_psh+","+tcp_rst+","+tcp_syn+","+tcp_fin+","+tcp_rsv1+","+tcp_rsv2+","+tcp_window+","+tcp_urgent_pointer+")";
        PreparedStatement statement = null;

        try {
            if (connection.isClosed()){
                System.out.println("connection is closed.");
                connection=DBUtils.getDBConn().getCon();
                if (connection!=null && !connection.isClosed()){
                    System.out.println("connection is not null and is open.");
                }
            }
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("WmCheckStorePacketThreadInfo.storePacketInfo()发生了异常:"+e.getCause()+":"+e.getMessage());
            e.printStackTrace();
        }
    }

    //only fot test
    public static void main(String[] args) {
        TCPPacket tcpPacket=new TCPPacket(1000,2000,1234,344,false,false,false,false,false,false,false,false,234,23);

        String timeStr=String.valueOf(System.currentTimeMillis());
        WmCheckStorePacketThreadInfo.storePacketInfo(tcpPacket,timeStr);
    }
}
