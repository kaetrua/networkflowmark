package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;

import jpcap.packet.UDPPacket;

public class UdpPacketHandler {
    public UdpPacketHandler() {

    }

    public void handler(UDPPacket udpPacket) {
        //预留的UDP
        System.out.println("---------------UdpPacketHandler.handler()----------------");
    }
}
