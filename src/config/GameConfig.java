package config;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 游戏配置器
 * @author J
 */
@SuppressWarnings({ "serial", "resource" })
public class GameConfig implements Serializable{
	
	
	
	private static FrameConfig FRAME_GONFIG = null;
	private static SystemConfig SYSTEM_CONFIG = null;
	private static DataConfig DATA_CONFIG = null;
	
	private static final boolean ISDEBUG = false;
	
	
	/**
	 * 静态代码块读取配置
	 */
	static{
		try {
			// 下面的意思是在开发阶段      false 的时候不会去读  xml 文件
			// 如果需要改代码  就把  ISDEBUG 改成  true
			if(ISDEBUG){
				// 创建XML读取器
				SAXReader reader = new SAXReader();
				// 读取XML文件
				Document doc = reader.read("data/cfg.xml");
				// 获得 xml 文件中的根节点 game 元素
				Element game = doc.getRootElement();
				// 创建界面配置对象 （读取 game 元素的 frame 子元素）
				FRAME_GONFIG = new FrameConfig(game.element("frame"));
				// 创建系统对象 （读取 game 元素的 system 子元素）
				SYSTEM_CONFIG = new SystemConfig(game.element("system"));
				// 创建界面访问对象 （读取 game 元素的 data 子元素）
				DATA_CONFIG = new DataConfig(game.element("data"));
			}else{
				// 读出已经存储的配置文件信息
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/framecfg.dat"));
				FRAME_GONFIG = (FrameConfig)ois.readObject();
				
				ois = new ObjectInputStream(new FileInputStream("data/systemcfg.dat"));
				SYSTEM_CONFIG = (SystemConfig)ois.readObject();
				
				ois = new ObjectInputStream(new FileInputStream("data/datacfg.dat"));
				DATA_CONFIG = (DataConfig)ois.readObject();
				ois.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 构造方法
	 * 
	 * 构造器私有化之后无法创建对象  所以之后的方法调用全部使用 static 静态方法直接创建
	 */
	private GameConfig(){
		
	}
	
	
	/**
	 * 提供 FrameConfig SystemConfig DataConfig 三个配置类的 get 方法
	 * 这样可以有效封装 xml 配置信息不被外界改变
	 * @return
	 */
	public static FrameConfig getFrameConfig() {
		return FRAME_GONFIG;
	}
	public static SystemConfig getSystemConfig() {
		return SYSTEM_CONFIG;
	}
	public static DataConfig getDataConfig() {
		return DATA_CONFIG;
	}
	
	
	// ===================== 测试对象流 =============================
	/**
	 * 我们并不想让用户更改配置文件里的信息  所以我们不要让用户看到配置文件
	 * 
	 * 这里将配置文件用对象流形式写入存储起来   游戏能够照常加载到配置文件  但是用户将无法看到
	 * 
	 * 然后的工作是把游戏中所有用到的类全部实现序列化接口  这样才能被正确的写入
	 */
//	public static void main(String[] args) throws Exception {
//		@SuppressWarnings("resource")
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/framecfg.dat"));
//		oos.writeObject(FRAME_GONFIG);
//		
//		oos = new ObjectOutputStream(new FileOutputStream("data/systemcfg.dat"));
//		oos.writeObject(SYSTEM_CONFIG);
//		
//		oos = new ObjectOutputStream(new FileOutputStream("data/datacfg.dat"));
//		oos.writeObject(DATA_CONFIG);
//	}
	
	
	
}
