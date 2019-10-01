package ss.pku.attacktraceproject.consoleDesk.db;

import ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.bean.Quintuple;
import ss.pku.attacktraceproject.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreWmQuintuple {
    public static void storeWmQuintuple(Quintuple netFlowQuintuple, String wmInfo){
        Connection connection = DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }
        String sql = "insert into wm_netflow_quintuple(original_wm_info,src_ip,src_port,dst_ip,dst_port) values('"+wmInfo+"','"+netFlowQuintuple.src_ip+"','"+netFlowQuintuple.src_port+"','"+netFlowQuintuple.dst_ip+"','"+netFlowQuintuple.dst_port+"')";
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
