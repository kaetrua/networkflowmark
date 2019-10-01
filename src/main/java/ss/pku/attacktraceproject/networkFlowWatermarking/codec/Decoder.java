package ss.pku.attacktraceproject.networkFlowWatermarking.codec;

public class Decoder {

    public static String decoder(StringBuffer received) {
        //  记录四种状态的路径
        StringBuffer route1 = new StringBuffer("0000");  // x00
        StringBuffer route2 = new StringBuffer("0011");  // x10
        StringBuffer route3 = new StringBuffer("1110");  // x01
        StringBuffer route4 = new StringBuffer("1101");  // x11

        StringBuffer realway1 = new StringBuffer("00");
        StringBuffer realway2 = new StringBuffer("01");
        StringBuffer realway3 = new StringBuffer("10");
        StringBuffer realway4 = new StringBuffer("11");

        System.out.println("接收到的数据长度为： " + received.length());
        System.out.println("接收到的数据为 ： " + received);

        int[] distance = {0, 0, 0, 0, 0};
        ;  //记录4种状态的最小码距

        int length = received.length();

        distance[0] = 0;
        distance[1] += (route1.charAt(0) ^ received.charAt(0)) + (route1.charAt(1) ^ received.charAt(1))
                + (route1.charAt(2) ^ received.charAt(2)) + (route1.charAt(3) ^ received.charAt(3));
        distance[2] += (route2.charAt(0) ^ received.charAt(0)) + (route2.charAt(1) ^ received.charAt(1))
                + (route2.charAt(2) ^ received.charAt(2)) + (route2.charAt(3) ^ received.charAt(3));
        distance[3] += (route3.charAt(0) ^ received.charAt(0)) + (route3.charAt(1) ^ received.charAt(1))
                + (route3.charAt(2) ^ received.charAt(2)) + (route3.charAt(3) ^ received.charAt(3));
        distance[4] += (route4.charAt(0) ^ received.charAt(0)) + (route4.charAt(1) ^ received.charAt(1))
                + (route4.charAt(2) ^ received.charAt(2)) + (route4.charAt(3) ^ received.charAt(3));


        int i;
        System.out.println();
        for (i = 4; i < length; i++) {
            int temp1 = distance[1];
            int temp2 = distance[2];
            int temp3 = distance[3];
            int temp4 = distance[4];
            System.out.println("i =" + i + "最短路径分别为：" + temp1 + temp2 + temp3 + temp4);
            String copy1 = route1.toString();
            String copy2 = route2.toString();
            String copy3 = route3.toString();
            String copy4 = route4.toString();
            String copyway1 = realway1.toString();
            String copyway2 = realway2.toString();
            String copyway3 = realway3.toString();
            String copyway4 = realway4.toString();

            /*   选择下一跳最短码距的路径   */

            // jump to S1
            // 1/000 --> 1/00
            // 3/001 --> 1/11
            if (temp1 + ('0' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1)) >
                    temp3 + ('1' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1))) {
                route1.replace(0, i, copy3);
                route1.append("11");
                realway1.replace(0, i / 2, copyway3);
                realway1.append("0");
                distance[1] = temp3 + ('1' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1));
            } else {
                route1.replace(0, i, copy1);
                route1.append("00");
                realway1.replace(0, i / 2, copyway1);
                realway1.append("0");
                distance[1] = temp1 + ('0' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1));
            }
            System.out.println("此时i = " + i + " ，路径1的原码：" + route1 + " ，路径1的译码结果：" + realway1);


            // jump to S2
            // 1/100 --> 2/10
            // 3/101 --> 2/00
            if (temp1 + ('1' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1)) >
                    temp3 + ('0' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1))) {
                route2.replace(0, i, copy3);
                route2.append("00");
                realway2.replace(0, i / 2, copyway3);
                realway2.append("1");
                distance[2] = temp3 + ('0' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1));
            } else {
                route2.replace(0, i, copy1);
                route2.append("11");
                realway2.replace(0, i / 2, copyway1);
                realway2.append("1");
                distance[2] = temp1 + ('1' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1));
            }
            System.out.println("此时i = " + i + " ，路径2的原码：" + route2 + " ，路径2的译码结果：" + realway2);

            //jump to S3
            // 2/010 --> 3/10
            // 4/011 --> 3/01
            if (temp2 + ('1' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1)) >
                    temp4 + ('0' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1))) {
                route3.replace(0, i, copy4);
                route3.append("01");
                realway3.replace(0, i / 2, copyway4);
                realway3.append("0");
                distance[3] = temp4 + ('0' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1));
            } else {
                route3.replace(0, i, copy2);
                route3.append("10");
                realway3.replace(0, i / 2, copyway2);
                realway3.append("0");
                distance[3] = temp2 + ('1' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1));
            }
            System.out.println("此时i = " + i + " ，路径3的原码：" + route3 + " ，路径3的译码结果：" + realway3);

            //jump to S4
            // 2/101 --> 4/01
            // 4/111 --> 4/10
            if (temp2 + ('0' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1)) >
                    temp4 + ('1' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1))) {
                route4.replace(0, i, copy4);
                route4.append("10");
                realway4.replace(0, i / 2, copyway4);
                realway4.append("1");
                distance[4] = temp4 + ('1' ^ received.charAt(i)) + ('0' ^ received.charAt(i + 1));
            } else {
                route4.replace(0, i, copy2);
                route4.append("01");
                realway4.replace(0, i / 2, copyway2);
                realway4.append("1");
                distance[4] = temp2 + ('0' ^ received.charAt(i)) + ('1' ^ received.charAt(i + 1));
            }
            System.out.println("此时i = " + i + " ，路径4的原码：" + route4 + " ，路径4的译码结果：" + realway4);

            ++i;
        }

        System.out.println(received.length() + "bit的最短距离分别是" + distance[1] + "  " + distance[2] + "  " + distance[3] + "  " + distance[4]);
        int minIndex=findMin(distance[1],distance[2],distance[3],distance[4]);
        System.out.println("minIndex="+minIndex);
        String originalWmInfo="";
        if (minIndex==1){
            originalWmInfo=realway1.toString();
        }else if (minIndex==2){
            originalWmInfo=realway2.toString();
        }else if (minIndex==3){
            originalWmInfo=realway3.toString();
        }else {
            originalWmInfo=realway4.toString();
        }

        return originalWmInfo;
    }

    private static int findMin(int d1, int d2, int d3, int d4) {
        int minIndex=1;
        int min=d1;
        if (d2<min){
            min=d2;
            minIndex=2;
        }
        if (d3<min){
            min=d3;
            minIndex=3;
        }
        if (d4<min){
            min=d4;
            minIndex=4;
        }
        return minIndex;
    }

    public static void main(String[] args) {
        StringBuffer sb=new StringBuffer("1101010001");//11011
        //StringBuffer sb = new StringBuffer("0011010111");//01100
        System.out.println(decoder(sb));
    }
}
