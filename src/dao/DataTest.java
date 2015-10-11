package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dto.Player;


public class DataTest implements Data{
	
	
	public DataTest(HashMap<String,String> param){
		
	}
	
	@Override
	public List<Player> loadData() {
		List<Player> players=new ArrayList<Player>();
		players.add(new Player("ÁõÃ÷",100));
		players.add(new Player("ºÕ¶û",2200));
		players.add(new Player("ºú¸è",1088));
		players.add(new Player("ÕÅÏÀ",2475));
		players.add(new Player("ÁõÃ÷",1800));
		return players;
	}

	@Override
	public void saveData(Player players) {
		
	}

	
}
