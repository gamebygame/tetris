package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dto.Player;


public class DataBase implements Data{
	
	
	
	private final String dbUrl;
	private final String dbUser;
	private final String dbPwd;
	private static String LOAD_SQL = "SELECT name,point FROM eluosi WHERE typeId=1 ORDER BY point";
	private static String SAVE_SQL = "INSERT INTO eluosi(name, point, typeId) VALUES (?, ?, ?)";
	
	
	public DataBase(HashMap<String, String> param){
		this.dbUrl = param.get("dbUrl");
		this.dbUser = param.get("dbUser");
		this.dbPwd = param.get("dbPwd");
		try {
			Class.forName(param.get("driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获得数据
	 */
	public List<Player> loadData(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Player> players = new ArrayList<Player>();
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = conn.prepareStatement(LOAD_SQL);
			rs = stmt.executeQuery();
			while(rs.next()){
				players.add(new Player(rs.getString(1), rs.getInt(2)));
				System.out.println();
			}
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(conn != null) conn.close();
				if(conn != null) stmt.close();
				if(conn != null) rs.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return players;
	}
	
	
	/**
	 * 存储数据
	 */
	public void saveData(Player players){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = conn.prepareStatement(SAVE_SQL);
			stmt.setObject(1, players.getName());
			stmt.setObject(2, players.getPoint());
			stmt.setObject(3, 1);
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(conn != null) conn.close();
				if(conn != null) stmt.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	

	
}
