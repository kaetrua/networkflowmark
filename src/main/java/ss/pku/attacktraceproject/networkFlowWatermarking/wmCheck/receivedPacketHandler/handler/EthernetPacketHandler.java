package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;


import jpcap.packet.ARPPacket;

public class EthernetPacketHandler {
    public EthernetPacketHandler() {

    }

    public void handler(ARPPacket arpPacket) {
        //预留ethernet
        System.out.println("---------------EthernetPacketHandler.handler()----------------");
    }


}
