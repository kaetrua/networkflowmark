package ss.pku.attacktraceproject.networkFlowWatermarking.wmInsertSender;


import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;

import java.util.Random;

public class GenerateOriginalwmInfo {

    //生成原始0-1比特序列
    public String generate() {
        String originalwmInfo = "";
        for (int i = 0; i < ProjectConstants.WM_SENDER_BITS_AMOUNT; i++) {
            int temp = new Random().nextInt(2);
            originalwmInfo += temp;
        }
        return originalwmInfo;
    }

    //for test
    public static void main(String[] args) {
        GenerateOriginalwmInfo info = new GenerateOriginalwmInfo();
        String res = info.generate();
        System.out.println(res + ":" + res.length());
    }

}
