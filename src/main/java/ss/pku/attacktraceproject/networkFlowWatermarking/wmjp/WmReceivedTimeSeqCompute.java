package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp;

import org.springframework.core.codec.StringDecoder;
import ss.pku.attacktraceproject.networkFlowWatermarking.codec.Decoder;
import ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ss.pku.attacktraceproject.networkFlowWatermarking.constants.ProjectConstants.*;

//计算水印检测方在接收到数据包序列后计算各个包的到达时间，然后统计相邻包之间的时间差，得到一个时间差序列。
public class WmReceivedTimeSeqCompute {
/*    public void getReceivedPackageArriveTime() {
        //查文件或者查库获取数据
        //
        return;
    }*/

    public static List<Long> computeReceivedPackageDelay(List<Long> arriveTime) {
        System.out.print("WmReceivedTimeSeqCompute.computeReceivedPackageDelay中arriveTime值序列为:");
        for (long everyTime:arriveTime){
            System.out.print(everyTime+"-->");
        }
        System.out.println();

        List<Long> timeDelay = new ArrayList<Long>();
        for (int i = 1; i < arriveTime.size(); i++) {
            timeDelay.add(arriveTime.get(i) - arriveTime.get(i - 1));
        }
        return timeDelay;
    }

    //还原出水印信息
    public static String computeWmInfo(List<Long> timeDelay) {

        StringBuffer sb = new StringBuffer();

        if(USE_KNN) {//是否使用KNN
            sb = kcluster(timeDelay, 2);  //todo kmeans性能优化
        }
        else{
//              2019 8 14 change

        //todo 使用Kmeans重写代码逻辑
        for (int i = 20; i < timeDelay.size(); i++) {
            //todo 需要重新检查\
            if (timeDelay.get(i) <= (int) (WM_SENDER_DELAY_STANDARD_TIME_MILLS * ProjectConstants.WM_RESTORE_TIME_RATIO)) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }}
//todo  增加分割识别
        String sb2 = findOri(sb.toString(),20);
        return sb2;
    }

    //卷积解码
    public static String computeOriginalWmInfo(String wmInfo){
        StringBuffer sb=new StringBuffer(wmInfo);
        String newOriginalInfo=Decoder.decoder(sb);
        return newOriginalInfo;
    }

    //相似度计算
    public static boolean similarityCompute(String a, String b) {
        System.out.println("还原出的水印信息序列为:"+b);
        boolean r;
        int result = 0;
        if (a.length() != b.length()) {
            //System.out.println("长度不一致，请检查后重新输入.还原出的水印信息为="+b+",其长度为:"+b.length());
            return false;
        } else {
            char[] ac = a.toCharArray();
            char[] bc = b.toCharArray();
            for (int i = 0; i < a.length(); i++) {
                if (ac[i] != bc[i]) {
                    result++;
                }
            }
            System.out.println();

            if (result<=ProjectConstants.WM_SIM_THRESHOLD){
                //相似
                System.out.println("相似度计算结果:海明距离为"+result+"相似度小于阈值，判定为同一水印信息");
                System.out.println();
                return true;
            }else {
                System.out.println("相似度计算结果大于阈值，水印信息不相似");
                return false;
            }
        }
    }
//暴力聚类
public static StringBuffer kcluster(List<Long> timeDelay, int k) {
    StringBuffer sb = new StringBuffer();
    Long a1,b1;
    for (Long tim:timeDelay){
        a1=Math.abs(tim-WM_SENDER_DELAY_STANDARD_TIME_MILLS);
        b1=Math.abs(tim-(long)(WM_SENDER_DELAY_STANDARD_TIME_MILLS*WM_SENDER_DELAY_STANDARD_TIME_RATIO));
        if(a1>b1)
            sb.append("0");
        else
            sb.append("1");
    }
    return sb;
}

    /*
     * 聚类函数主体。
     * 针对一维 double 数组。指定聚类数目 k。
     * 将数据聚成 k 类。
     */
    public static StringBuffer cluster(List<Long> timeDelay, int k) {
        System.out.println("len ="+timeDelay.size());
        //最终结果物
        StringBuffer sb = new StringBuffer();
        // 存放聚类旧的聚类中心
        long[] c = new long[k];
        // 存放新计算的聚类中心
        long[] nc = new long[k];
        // 存放放回结果
        long[][] g;
        // 初始化聚类中心
        // 经典方法是随机选取 k 个
        // 本例中采用前 k 个作为聚类中心
        // 聚类中心的选取不影响最终结果
        for (int i = 0; i < k; i++)
            c[i] = timeDelay.get(i);
        // 循环聚类，更新聚类中心
        // 到聚类中心不变为止
        while (true) {
            // 根据聚类中心将元素分类
            g = group(timeDelay, c);
            // 计算分类后的聚类中心
            for (int i = 0; i < g.length; i++) {
               // System.out.println( " g.length="+g.length);
                nc[i] = center(g[i]);
            }
            // 如果聚类中心不同
            if (!equal(nc, c)) {
                // 为下一次聚类准备
                c = nc;
                nc = new long[k];
            } else // 聚类结束
                break;
        }
        // 返回聚类结果,将其转换为map
        HashMap hashmap     =     new HashMap();
        //todo :xiuzheng 01
        boolean fix01 = false;
        if (g[0][0]>g[1][0]){
            fix01 = true;
        }

        if(fix01){ //todo; 验证 01修正是否生效
            for (int i = 0; i < k; i++) {
                for ( int j = 0;j<g[i].length;j++) {
                    System.out.println("gij="+ g[i][j]);
                    hashmap.put(g[i][j], (i+1)%k);
                   // System.out.println("j ="+ j);
                }
            }
        }
        else {
            for (int i = 0; i < k; i++) {
                for ( int j = 0;j<g[i].length;j++) {
                    System.out.println("gij="+ g[i][j]);
                    hashmap.put(g[i][j], i);
                  //  System.out.println("j ="+ j);
                }
            }
        }


        for (int i = 0; i < timeDelay.size(); i++){
            sb.append(hashmap.get(timeDelay.get(i)));//todo  0,1可能颠倒？？
        }
        System.out.println("SB="+ sb);
        return sb;
    }
    /*
     * 聚类中心函数
     * 简单的一维聚类返回其算数平均值
     * 可扩展
     */
    public static long center(long[] p) {
        return sum(p) / p.length;
    }
    /*
     * 给定 double 型数组 p 和聚类中心 c。
     * 根据 c 将 p 中元素聚类。返回二维数组。
     * 存放各组元素。
     */
    public static long[][] group(List<Long> timeDelay, long[] c) {
        // 中间变量，用来分组标记
        int[] gi = new int[timeDelay.size()];
        // 考察每一个元素 pi 同聚类中心 cj 的距离
        // pi 与 cj 的距离最小则归为 j 类
        for (int i = 0; i < timeDelay.size(); i++) {
            // 存放距离
            long[] d = new long[c.length];
            // 计算到每个聚类中心的距离
            for (int j = 0; j < c.length; j++) {
                d[j] = distance(timeDelay.get(i), c[j]);
            }
            // 找出最小距离
            int ci = min(d);
            // 标记属于哪一组
            gi[i] = ci;
        }
        // 存放分组结果
        long[][] g = new long[c.length][];
        // 遍历每个聚类中心，分组
        for (int i = 0; i < c.length; i++) {
            // 中间变量，记录聚类后每一组的大小
            int s = 0;
            // 计算每一组的长度
            for (int j = 0; j < gi.length; j++)
                if (gi[j] == i)
                    s++;
            // 存储每一组的成员
            g[i] = new long[s];
            s = 0;
            // 根据分组标记将各元素归位
            for (int j = 0; j < gi.length; j++)
                if (gi[j] == i) {
                    g[i][s] = timeDelay.get(i);
                    s++;
                }
        }
        //TODO  现在的问题，当有个特别大的离群值时，会将其分为一类，其他分为一类，分类形式及其不好，考虑改正方式，，，且kmeans代码可能写的有问题，需要重写
//        for(int i=0;i<2;i++){
//            for(int j=0;j<g[i].length;j++){
//                System.out.println(g[i].length+"  ,length  ");
//                if(g[i][j]!=0)
//                    System.out.println(g[i][j]+"  ,  ");
//            }}
        // 返回分组结果
        return g;
    }

    /*
     * 计算两个点之间的距离， 这里采用最简单得一维欧氏距离， 可扩展。
     */
    public static long distance(long x, long y) {
        return Math.abs(x - y);
    }

    /*
     * 返回给定 double 数组各元素之和。
     */
    public static long sum(long[] p) {
        long sum = 0;
        for (int i = 0; i < p.length; i++)
            sum += p[i];
        return sum;
    }

    /*
     * 给定 double 类型数组，返回最小值得下标。
     */
    public static int min(long[] p) {
        int i = 0;
        long m = p[0];
        for (int j = 1; j < p.length; j++) {
            if (p[j] < m) {
                i = j;
                m = p[j];
            }
        }
        return i;
    }

    /*
     * 判断两个 double 数组是否相等。 长度一样且对应位置值相同返回真。
     */
    public static boolean equal(long[] a, long[] b) {
        if (a.length != b.length)
            return false;
        else {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i])
                    return false;
            }
        }
        return true;
    }

    /*
     * 找到原始串。
     */
    public static String findOri(String a, int orilen) {
        ArrayList<String> strlist = new ArrayList<>();
        Map<String, Integer> strmap = new HashMap();
        //分割字符串
        for(int i=0 ;i<a.length()/orilen; i++){
            int index = i*orilen;
            if((index+orilen)>a.length()){
                String childStr = a.substring(index);
                strlist.add(childStr);
            }else {
                String childStr = a.substring(index,index+orilen);
                strlist.add(childStr);
            }
        }
        String oristr ="";
        int count =0;
        for (String str :strlist){
            if (count == 0){
                oristr = str;
                strmap.put(oristr,++count);

                continue;
            }
            if(str.equals(oristr)){
                strmap.replace(oristr,++count);
            }
            else if (issimilarstr(str,oristr)){
                strmap.replace(oristr,++count);
            }
            else
                strmap.put(str,strmap.getOrDefault(str,0)+1);
        }
        int defvalue=1;
        for (Integer stvaluew:strmap.values()
             ) {if(stvaluew!=defvalue)
            return oristr;
        }
        for(String key : strmap.keySet()){
            return  key;
        }
        return oristr;
    }
    /*
     * 找到原始串。
     */
    public static boolean issimilarstr(String a, String b) {
        int strlen = b.length();
        if (strlen!=a.length())
            return false;
        for (int k=1;k<strlen/2;k++){
            if(a.substring(0,strlen-k-1).equals(b.substring(k))&&a.substring(strlen-k).equals(b.substring(0,k-1)))
                return true;
            if(b.substring(0,strlen-k-1).equals(a.substring(k))&&b.substring(strlen-k).equals(a.substring(0,k-1)))
                return true;
        }
        return false;
    }
}
