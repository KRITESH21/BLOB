import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

public class BlobRetrieval {

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub

		Connection con=null;
		PreparedStatement prstmt=null;
		ResultSet rs=null;
		FileOutputStream fos=null;
		
		MysqlConnectionPoolDataSource DataSource=new MysqlConnectionPoolDataSource();
		DataSource.setURL("jdbc:mysql://localhost:3306/nathan_sir");
		DataSource.setUser("root");
		DataSource.setPassword("Kritesh21@");
		
		try {
			con=DataSource.getConnection();
			if(con!=null) {
				String selectQuery="select name,image from Person where name=?";
				prstmt = con.prepareStatement(selectQuery);
				if(prstmt!=null) {
					prstmt.setString(1, "Sachin");
					rs=prstmt.executeQuery();
				}
				if(rs!=null) {
					if(rs.next()) {
						String s1=rs.getString(1);
						InputStream is=rs.getBinaryStream(2);
						
						File f=new File("Sachin.jpg");
						fos=new FileOutputStream(f);
						
						IOUtils.copy(is,fos);
						System.out.println(s1);
						System.out.println("picture stored in "+f.getAbsolutePath());
					}
				}
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}finally {
			if(con!=null) {
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
