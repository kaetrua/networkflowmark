package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean;

public class TcpUdpReceiver {
    public String sourceIp;
    public int sourcePort;
    public String dstIp;
    public int dstPort;
    public int packetLen;

    public TcpUdpReceiver(String sourceIp, int sourcePort, String dstIp, int dstPort, int packetLen) {
        this.sourceIp = sourceIp;
        this.sourcePort = sourcePort;
        this.dstIp = dstIp;
        this.dstPort = dstPort;
        this.packetLen = packetLen;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public int getPacketLen() {
        return packetLen;
    }

    public void setPacketLen(int packetLen) {
        this.packetLen = packetLen;
    }
}
