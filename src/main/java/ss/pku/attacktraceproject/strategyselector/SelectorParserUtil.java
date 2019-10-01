package ss.pku.attacktraceproject.strategyselector;

public class SelectorParserUtil {

    public static int parser(String str){
//正在 Ping 192.168.199.148 具有 32 字节的数据:来自 192.168.199.148 的回复: 字节=32 时间<1ms TTL=64来自 192.168.199.148 的回复: 字节=32 时间<1ms TTL=64来自 192.168.199.148 的回复: 字节=32 时间<1ms TTL=64192.168.199.148 的 Ping 统计信息:    数据包: 已发送 = 3，已接收 = 3，丢失 = 0 (0% 丢失)，往返行程的估计时间(以毫秒为单位):    最短 = 0ms，最长 = 0ms，平均 = 0ms
    int lost=0;
    String temp= str.substring(str.indexOf("丢失 ="),str.indexOf("，往返行程的估计时间(以毫秒为单位)"));
    String res=temp.substring(temp.indexOf("=")+2,temp.indexOf(" ("));
    return  Integer.valueOf(res).intValue();
    }

    public static void main(String[] args) {
        String s="正在 Ping 192.168.199.148 具有 32 字节的数据:来自 192.168.199.148 的回复: 字节=32 时间<1ms TTL=64来自 192.168.199.148 的回复: 字节=32 时间<1ms TTL=64来自 192.168.199.148 的回复: 字节=32 时间<1ms TTL=64192.168.199.148 的 Ping 统计信息:    数据包: 已发送 = 3，已接收 = 3，丢失 = 0 (0% 丢失)，往返行程的估计时间(以毫秒为单位):    最短 = 0ms，最长 = 0ms，平均 = 0ms\n" ;
        System.out.println(parser(s));
    }
}
