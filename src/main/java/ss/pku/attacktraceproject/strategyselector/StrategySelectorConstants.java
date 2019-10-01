package ss.pku.attacktraceproject.strategyselector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StrategySelectorConstants {
    //安装了水印提取检测模块的主机的ip地址集合
    public static final List<String> iptables= Arrays.asList("192.168.199.148");

    //PING  ip的总次数 ,次数测试时使用的是3，实际使用参考论文
    public static final int PING_PACKAGE_NUM=20;

    public static final double STABLE_STANDARD=0.05;
}
