package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db;

import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.WmCheckResult;
import ss.pku.attacktraceproject.utils.DBUtils;
import ss.pku.attacktraceproject.utils.InetAddressIpUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WmCheckResultStore {
    public void storeWmCheckResult(WmCheckResult wmCheckResult) {
        //Connection connection = DBUtils.getConnection();
        Connection connection=DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }

        String originalWmInfo = wmCheckResult.getOriginalWmInfo();
        String checkerIp = wmCheckResult.getMyIp();
        String checkTime = wmCheckResult.getTime();
        String checkResult = wmCheckResult.getCheckResult();

        String sql = "insert into wm_check_result(original_wm_info,check_time,check_ip,check_result)" +
                " values('" + originalWmInfo + "','" + checkTime + "','" + checkerIp + "','" + checkResult + "')";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
