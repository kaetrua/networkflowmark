package ss.pku.attacktraceproject.honeytoken.generateHtFile;


import java.util.UUID;

public class GenerateTokenId {
    private static final String urlPrefix = "localhost:8080/web/";
    private static final String urlEndFix = "/index.php";

    //确保每一个tokenId都是唯一的
    public static String generateId() {
        String uuid = UUID.randomUUID().toString().substring(24);

        return uuid;
    }

    public String generateUrl() {
        return urlPrefix + generateId() + urlEndFix;
    }

    //for test
    public static void main(String[] args) {
        GenerateTokenId id = new GenerateTokenId();
        System.out.println(id.generateUrl());
    }

}
