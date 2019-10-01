package ss.pku.attacktraceproject.networkFlowWatermarking.codec;

import java.util.ArrayList;
import java.util.List;

public class Encoder {
    public static String startEncoder(String wminfo){
        int[] register={0,0};
        StringBuffer sb=new StringBuffer();

        for (int i=0;i<wminfo.length();i++){
            int wmInt=StringUtils.str2Int(String.valueOf(wminfo.charAt(i)));
            int c1=register[0]^register[1]^wmInt;
            int c2=register[0]^wmInt;

            sb.append(StringUtils.Int2Str(c1)).append(StringUtils.Int2Str(c2));
            System.out.println("out1="+c1+",out2="+c2);
            register[0]=register[1];
            register[1]=wmInt;
        }

        return sb.toString();
    }

    //测试通过 for test
    public static void main(String[] args) {
       // System.out.println(startEncoder("11011")); //1101010001
        System.out.println(startEncoder("01100"));//0011010111
    }
}
