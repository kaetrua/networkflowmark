package ss.pku.attacktraceproject.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressIpUtils {
    public static InetAddress ip2InetAddress(String ip) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String inetAddress2IP(InetAddress address) {
        String newIP = address.toString().substring(address.toString().lastIndexOf("/") + 1);
        return newIP;
    }
}
