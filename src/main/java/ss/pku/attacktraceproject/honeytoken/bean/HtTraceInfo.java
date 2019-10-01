package ss.pku.attacktraceproject.honeytoken.bean;

import java.io.Serializable;

public class HtTraceInfo implements Serializable {
    public String tokenId;
    public String ip;
    public String os;
    public String other;

    public HtTraceInfo(){

    }

    public HtTraceInfo(String tokenId, String ip, String os, String other) {
        this.tokenId = tokenId;
        this.ip = ip;
        this.os = os;
        this.other = other;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "TraceInfo{" +
                "tokenId='" + tokenId + '\'' +
                ", ip='" + ip + '\'' +
                ", os='" + os + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
