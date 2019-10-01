package ss.pku.attacktraceproject.utils;

import java.sql.*;

public class DBUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/trace?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&allowMultiQueries=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static DBUtils DBConn = null;

    private DBUtils() {

    }

    static {
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("注册JDBC驱动发生了异常:" + e.getCause());
        }
    }

    public static DBUtils getDBConn() {
        if (DBConn == null) {
            synchronized (DBUtils.class) {
                if (DBConn == null) {
                    DBConn = new DBUtils();
                }
            }
        }
        return DBConn;
    }

    //返回一个Connection连接
    public Connection getCon()  {
        Connection connection=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (Exception e){
            System.out.println("DBUtils.getCon()发生了异常:"+e.getMessage());
        }
       return connection;
    }

    //释放资源
    public void closeAll(ResultSet rs, Statement st, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

      /*  private static Connection conn=null;

        static {
            try {
                //1.加载驱动程序
                Class.forName("com.mysql.cj.jdbc.Driver");
                //2.获得数据库的连接
                conn=DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //将获得的数据库与java的链接返回（返回的类型为Connection）
        public static Connection getConnection(){
            return conn;
        }
*/
}
