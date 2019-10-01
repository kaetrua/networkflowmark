package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;


import jpcap.packet.ICMPPacket;

public class IcmpPacketHandler {
    public IcmpPacketHandler() {

    }

    public void handler(ICMPPacket icmpPacket) {
        //预留icmp
        System.out.println("---------------IcmpPacketHandler.handler()----------------");
    }
}
