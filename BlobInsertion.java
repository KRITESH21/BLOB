import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

public class BlobInsertion {

	public static void main(String[] args) throws SQLException {
		
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement prstmt=null;
		Scanner sc=new Scanner(System.in);
		
		MysqlConnectionPoolDataSource datasource=new MysqlConnectionPoolDataSource();
		datasource.setURL("jdbc:mysql://localhost:3306/nathan_sir");
		datasource.setUser("root");
		datasource.setPassword("Kritesh21@");
		System.out.println("enter the name");
		String name=sc.next();
		try {
			con=datasource.getConnection();
		if(con!=null) {
			
			String insertQuery="insert into Person(`name`,`image`)values(?,?)";
			prstmt=con.prepareStatement(insertQuery);
		}
			if(prstmt!=null) {
				prstmt.setString(1, name);
			
			File file=new File("Sachin-Tendulkar_(cropped).jpg");
			FileInputStream fis=new FileInputStream(file);
			prstmt.setBlob(2, fis);
			System.out.println("inserting image from "+file.getAbsolutePath());
			
			int noOfrows=prstmt.executeUpdate();
			
			if(noOfrows==1) {
				System.out.println("inserted successfully");
			}else {
				System.out.println("file not found");
			}
		}
		

	}catch(SQLException se) {
		se.printStackTrace();
	}catch(FileNotFoundException fe) {
		fe.printStackTrace();
	}finally {
		if(sc!=null) {
			sc.close();
		}
		if(con!=null) {
			con.close();
		}
	}

}
}