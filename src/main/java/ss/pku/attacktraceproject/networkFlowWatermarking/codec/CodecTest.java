package ss.pku.attacktraceproject.networkFlowWatermarking.codec;

public class CodecTest {
    public static void main(String[] args) {
        //卷积编码测试
        String originalWmInfo="010011";
        String encodedInfo=Encoder.startEncoder(originalWmInfo);
        System.out.println("encodedInfo="+encodedInfo);

        //viterbi解码测试
        StringBuffer sb=new StringBuffer(encodedInfo);
        String newWmInfo=Decoder.decoder(sb);
        System.out.println("newWmInfo="+newWmInfo);

        if (originalWmInfo.equals(newWmInfo)){
            System.out.println("解码数据与原数据一致,算法编码正确");
        }
    }
}
