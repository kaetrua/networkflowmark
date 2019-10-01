package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean;

public class WmCheckResult {
    public String originalWmInfo;
    public String time; //to long
    public String myIp;
    public String checkResult; //to boolean

    public WmCheckResult() {

    }

    public WmCheckResult(String originalWmInfo, String time, String myIp, String checkResult) {
        this.originalWmInfo = originalWmInfo;
        this.time = time;
        this.myIp = myIp;
        this.checkResult = checkResult;
    }

    public WmCheckResult(String originalWmInfo, String myIp, String checkResult) {
        this.originalWmInfo = originalWmInfo;
        this.time=String.valueOf(System.currentTimeMillis());
        this.myIp = myIp;
        this.checkResult = checkResult;
    }

    public String getOriginalWmInfo() {
        return originalWmInfo;
    }

    public void setOriginalWmInfo(String originalWmInfo) {
        this.originalWmInfo = originalWmInfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMyIp() {
        return myIp;
    }

    public void setMyIp(String myIp) {
        this.myIp = myIp;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    @Override
    public String toString() {
        return "WmCheckResult{" +
                "originalWmInfo='" + originalWmInfo + '\'' +
                ", time=" + time +
                ", myIp='" + myIp + '\'' +
                ", checkResult=" + checkResult +
                '}';
    }
}
