package config;


/**
 * 该类存在的意义是在系统启动时自动被加载并通过static静态代码块获得对象GAME_CONFIG
 * 然后调用GameConfig（）方法取得参数
 * 工厂模式思想可以有效降低创建对象的冗余
 * 就像本类一样以GAME_CONFIG为对象取代所有本应创建的对象获得参数
 * 
 * @author J
 */
public class ConfigFactory {
	
//	private static GameConfig GAME_CONFIG=null;
//	static{
//		try {
//			GAME_CONFIG=new GameConfig();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	//该方法返回类型为GameConfig的对象
//	public static GameConfig getGameConfig(){
//		return GAME_CONFIG;
//	}
	
}
