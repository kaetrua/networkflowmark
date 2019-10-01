package ss.pku.attacktraceproject.honeytoken.honeytokenserver;

import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import ss.pku.attacktraceproject.consoleDesk.TraceControl;
import ss.pku.attacktraceproject.honeytoken.bean.HtTraceInfo;
import ss.pku.attacktraceproject.mapperService.HtTraceInfoMapperService;
import ss.pku.attacktraceproject.utils.DBUtils;
import ss.pku.attacktraceproject.utils.LocalHostIpUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TraceProcessor {

    @Autowired
    private HtTraceInfoMapperService htTraceInfoMapperService;

    public void startTrace(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //浏览器
        Browser browser = userAgent.getBrowser();
        //浏览器版本号
        Version version = userAgent.getBrowserVersion();
        //操作系统
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        String requestURL = request.getRequestURI();
        //
        String tokenId = requestURL.substring(10, requestURL.length());

        //攻击者IP
        String clientIp = request.getRemoteAddr();
        if (clientIp.equals("0:0:0:0:0:0:0:1")) {
            clientIp = LocalHostIpUtil.getLocalHostIp();
        }

        System.out.println("browser info=" + browser.toString());
        System.out.println("version=" + version.toString());
        System.out.println("os=" + operatingSystem.toString());
        System.out.println("clientIp=" + clientIp);

        System.out.println("requestURL=" + requestURL);
        System.out.println("tokenId=" + tokenId);

        HtTraceInfo traceInfo = new HtTraceInfo();
        traceInfo.tokenId = tokenId;
        traceInfo.ip = clientIp;
        traceInfo.os = operatingSystem.toString();
        traceInfo.other = browser.toString() + "::" + version.toString();

        String jsonTraceInfo = JSON.toJSONString(traceInfo);

        // System.out.println("TraceInfo="+ JSON.toJSONString(traceInfo));

        //插入数据库,废弃之前使用的mybatis注解方式，改用传统的mysql增删改查方式
        //htTraceInfoMapperService.insertHtTraceInfo(traceInfo);
        Connection connection = DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }
        String sql = "insert into ht_trace_result(token_id,ip,os,other) values('"+traceInfo.tokenId+"','"+traceInfo.ip+"','"+traceInfo.os+"','"+traceInfo.other+"')";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sendTraceResultToConsoleDesk(jsonTraceInfo);
    }

    private static void sendTraceResultToConsoleDesk(String jsonTraceInfo) {
        TraceControl control = new TraceControl();
        control.receiveMsgFromHoneyTokenServer(jsonTraceInfo);
    }

}

