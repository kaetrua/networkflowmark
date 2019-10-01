package ss.pku.attacktraceproject.utils.test;

//海明距离测试
public class HammingDistanceTest {


    public static void main(String[] args) {
        String a="1000011111";
        String b="0000011111";

        byte[] bytea=a.getBytes();
        byte[] byteb=b.getBytes();

        byte[] md5a=Md5Util.encode(bytea);
        byte[] md5b=Md5Util.encode(byteb);

        int count=0;
        for (int i=0;i<md5a.length;i++){
            if (md5a[i]!=md5b[i]){
                count++;
            }
        }
        System.out.println("count="+count);
    }

}
