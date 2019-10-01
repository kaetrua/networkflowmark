package ss.pku.attacktraceproject.networkFlowWatermarking.constants;

public class ProjectConstants {
    //两者相差值为1
    public static final int PACKAGE_USE_CASE_PACKAGE_TOTAL_AMOUNT = 21;

    public static final int WM_SENDER_BITS_AMOUNT = 10;

    //发送时间延迟标准时间
//    public static final int WM_SENDER_DELAY_STANDARD_TIME_MILLS = 100;
    public static final int WM_SENDER_DELAY_STANDARD_TIME_MILLS = 10;

    //水印嵌入的时间延迟倍率
//    public static final float WM_SENDER_DELAY_STANDARD_TIME_RATIO = 2f;
    public static final float WM_SENDER_DELAY_STANDARD_TIME_RATIO = 25f;//200f

    //水印还原时设置的标准时间的倍率
    public static final float WM_RESTORE_TIME_RATIO = 13f;
//    public static final float WM_RESTORE_TIME_RATIO = 1.5f;

    public static final String ORIGINAL_SENDER_SOURCE_IP = "192.168.199.148";

    public static final String WM_INSERT_PACKET_RECEIVER_DESTINATION_HOST_IP = "192.168.199.148";

    //水印嵌入方，即追踪者
    public static final String ORIGINAL_SENDER_SOURCE_BYTE_IP = "192.168.199.148";

    //水印信息发送的目的地
    public static final String ORIGIINAL_SENDER_DESTINATION_IP = "192.168.199.201";

    //相似度检测的阈值
    public static final int WM_SIM_THRESHOLD = 10;

    //use knn
    public static final boolean  USE_KNN = true;

}
