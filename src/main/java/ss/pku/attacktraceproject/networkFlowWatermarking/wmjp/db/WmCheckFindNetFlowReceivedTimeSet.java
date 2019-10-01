package ss.pku.attacktraceproject.networkFlowWatermarking.wmjp.db;

import ss.pku.attacktraceproject.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WmCheckFindNetFlowReceivedTimeSet {
    public static List<String> findNetFlowReceivedTimeSet(String quintupleInfo){
        List<String> timeSet=new ArrayList<String>();
        Connection connection = DBUtils.getDBConn().getCon();
        if (connection == null) {
            System.out.println("没有获取到connection，无法完成数据库操作");
        }
        //'"+wmInfo+"','"+netFlowQuint
        //查询语句中的参数10要根据发包的水印信息位数来调整
        String sql = "select *from (select * from wm_check_received_tcp_packet where quintuple_info='"+quintupleInfo+"'order by received_time desc limit 2001) as a order by id";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs=statement.executeQuery(sql);
            while (rs.next()){
                timeSet.add(rs.getString(3));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeSet;
    }
    //only for test
    public static void main(String[] args) {
        List<String> stringList=WmCheckFindNetFlowReceivedTimeSet.findNetFlowReceivedTimeSet("192.168.199.1481000192.168.0.1122000");
        for (String s:stringList){
            System.out.print(s+"--");
        }
    }
}
