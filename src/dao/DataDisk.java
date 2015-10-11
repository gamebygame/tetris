package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import dto.Player;


public class DataDisk implements Data{

	
	
	public final String filePath;
	
	
	/**
	 * 构造函数
	 */
	public DataDisk(HashMap<String, String> param){
		this.filePath = param.get("path");
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> loadData() {
		ObjectInputStream ois=null;
		List<Player> players = null;
		try {
			ois= new ObjectInputStream(new FileInputStream(filePath));
			players = (List<Player>)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	
	@Override
	public void saveData(Player pla) {
		//先取出数据
		List<Player> players = this.loadData();
		//追加新纪录
		players.add(pla);
		//重新写入
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePath));
			oos.writeObject(players);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//================================== main方法测试用 ===========================================
//	public static void main(String[] args){
//		DataDisk jj=new DataDisk();
//		List<Player> players=new ArrayList<Player>();
//		players.add(new Player("saber",100));
//		players.add(new Player("archer",2200));
//		players.add(new Player("rider",1088));
//		players.add(new Player("Jovi",2475));
//		players.add(new Player("lancer",875));
//		jj.saveData(players);
//		System.out.println("保存完毕");
//		
//		List<Player> dataFromDisk = jj.loadData();
//		for(Player p: dataFromDisk){
//			System.out.println(p.getName() + ":" + p.getPoint());
//		}
//	}
	
	

}
