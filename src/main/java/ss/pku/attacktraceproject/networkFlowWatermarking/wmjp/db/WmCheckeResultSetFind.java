package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db;

import com.alibaba.fastjson.JSON;
import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.WmCheckResult;
import ss.pku.attacktraceproject.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WmCheckeResultSetFind {
    public static List<WmCheckResult> findWmCheckResultSet(String originalWmInfo){
        List<WmCheckResult> wmCheckResultList=new ArrayList<>();
        Connection connection = DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }
        //'"+wmInfo+"','"+netFlowQuint
        String sql = "select original_wm_info,check_time,check_ip,check_result from wm_check_result where original_wm_info='"+originalWmInfo+"'";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs=statement.executeQuery(sql);
            while (rs.next()){
                WmCheckResult checkResult=new WmCheckResult();
                checkResult.setCheckResult(rs.getString(4));
                checkResult.setMyIp(rs.getString(3));
                checkResult.setOriginalWmInfo(rs.getString(1));
                checkResult.setTime(rs.getString(2));
                wmCheckResultList.add(checkResult);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wmCheckResultList;
    }

    //only for test
    public static void main(String[] args) {
        List<WmCheckResult> results=findWmCheckResultSet("0100001001");
        for (WmCheckResult result:results){
            System.out.println(JSON.toJSONString(result));
        }
    }
}
