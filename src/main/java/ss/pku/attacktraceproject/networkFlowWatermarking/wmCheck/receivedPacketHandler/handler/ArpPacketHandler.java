package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;

import jpcap.packet.ARPPacket;

public class ArpPacketHandler {
    public ArpPacketHandler() {

    }

    public void handler(ARPPacket arpPacket) {
        //预留arp
        System.out.println("---------------ArpPacketHandler.handler()----------------");
    }

}
