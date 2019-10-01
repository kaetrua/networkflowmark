package ss.pku.attacktraceproject.networkFlowWatermarking.wmCheck.receivedPacketHandler.handler;


import jpcap.packet.ARPPacket;

public class DatalinkPacketHandler {
    public DatalinkPacketHandler() {

    }

    public void handler(ARPPacket arpPacket) {
        //预留数据链路层
        System.out.println("---------------DatalinkPacketHandler.handler()----------------");
    }
}
