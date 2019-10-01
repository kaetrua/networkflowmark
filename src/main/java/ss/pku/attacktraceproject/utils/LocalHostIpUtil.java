package ss.pku.attacktraceproject.utils;

import java.net.InetAddress;

//获取本机ip地址
public class LocalHostIpUtil {
    public static String getLocalHostIp() {
        InetAddress ia = null;
        String localip = "";
        try {
            ia = ia.getLocalHost();

            String localname = ia.getHostName();
            localip = ia.getHostAddress();
            System.out.println("本机名称是：" + localname);
            System.out.println("本机的ip是 ：" + localip);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return localip;
    }

    public static void main(String[] args) {
        LocalHostIpUtil ip = new LocalHostIpUtil();
        System.out.println(ip.getLocalHostIp());
    }
}

