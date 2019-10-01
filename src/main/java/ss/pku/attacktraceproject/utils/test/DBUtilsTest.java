package ss.pku.attacktraceproject.utils.test;

import ss.pku.attacktraceproject.utils.DBUtils;

import java.sql.Connection;

public class DBUtilsTest {
    public static void main(String[] args) throws Exception{
      Connection con=DBUtils.getDBConn().getCon();
      if (con==null || con.isClosed()){
          System.out.println("conn is null or closed");
      }

    }
}
