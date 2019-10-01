package ss.pku.attacktraceproject.consoleDesk.db;

import ss.pku.attacktraceproject.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindStartAndEndIps {

    public static List<String> findIps(String wmInfo){
        List<String> ips=new ArrayList<String>();
        Connection connection = DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }
        //'"+wmInfo+"','"+netFlowQuint
        String sql = "select * from wm_netflow_quintuple where original_wm_info='"+wmInfo+"'";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs=statement.executeQuery(sql);
            while (rs.next()){
                System.out.println("start ip="+rs.getString(2));
                System.out.println("end ip="+rs.getString(4));
                ips.add(rs.getString(2));
                ips.add(rs.getString(4));

            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ips;
    }

    //for test
    public static void main(String[] args) {
        findIps("1010101010");
    }
}
