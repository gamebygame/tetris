package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;


/**
 * 接受玩家键盘事件
 * 调用游戏逻辑
 * 
 * @author J
 */
public class GameControl {
	
	
	
	/**
	 * 数据访问接口A
	 */
	private Data dataA;
	/**
	 * 数据访问接口B
	 */
	private Data dataB;
	
	/**
	 * 调用GameService类（游戏逻辑）
	 */
	private GameService gameService;
	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;
	/**
	 * 游戏控制窗口（就是模拟器的那个类啦）
	 */
	private JFrameConfig frameConfig;
	/**
	 * 保存分数的窗口
	 */
	private JFrameSavePoint frameSavePoint;
	/**
	 * 通过映射控制游戏具体行为 
	 * Integer 相当于 keyCode 
	 */
	private Map<Integer,Method> actionList;
	/**
	 * 游戏数据源
	 */
	private GameDto dto = null;
	/**
	 * 声明线程变量（游戏线程）
	 */
	private Thread gameThread = null;
	
	
	/**
	 * 构造方法
	 */
	public GameControl(){
		// 创建GameDto对象（主函数拿到游戏所有数据）
		this.dto = new GameDto();
		// 创建GameService对象（载入游戏数据源）
		this.gameService = new GameTetris(dto);
		// 创建接口A对象
		this.dataA = createGameObject(GameConfig.getDataConfig().getDataA());
		// 设置数据库记录到游戏
		this.dto.setDbRecode(dataA.loadData());
		// 创建接口A对象
		this.dataB = createGameObject(GameConfig.getDataConfig().getDataB());
		// 设置本地磁盘记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		// 创建一个JPanelGame对象（主函数里拿到游戏面板）
		this.panelGame = new JPanelGame(this, dto);
		// 读取用户控制键设置
		this.setControlConfig();
		// 初始化用户配置窗口
		this.frameConfig = new JFrameConfig(this);
		// 初始化保存记录窗口
		this.frameSavePoint = new JFrameSavePoint(this);
		// 创建游戏主窗口，安装游戏面板
		new JFrameGame(this.panelGame);
	}
	
	
	/**
	 * 读取用户控制键设置
	 */
	private void setControlConfig(){
		//创建 键盘码 与 方法名 的映射数组
		this.actionList = new HashMap<Integer, Method>();
		//读取已经存入的按键信息（包括功能键和方向键）
		try {
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>)ois.readObject();
			//获得 HashMap 转化的集合
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			//迭代获得方法名以获取相应方法
			//注意在转化的  Set<Entry<Integer, String>> 集合中 String 存放的是方法名，而 HashMap<Integer, String> 中 String 存放的是方法
			for(Entry<Integer, String> e: entryset){
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 创建数据对象
	 * 
	 * @param cfg
	 * @return
	 */
	private Data createGameObject(DataInterfaceConfig cfg) {
		try {
			// 获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			// 获得构造器
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			// 创建对象
			return (Data)ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 根据玩家键入具体控制响应行为
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			// 下面方法判定玩家键入的按键是否在配置文件里存在，只有存在了才会调用具体响应方法
			// 如果没有这条判定当用户手滑点到其他按键的话游戏会报错，这种情况最好不要出现为好。。
			if (this.actionList.containsKey(keyCode)) {
				// 下面这条通过玩家按键直接调用方法的语句和下面注释掉的语句效果相同  不过更加简单
				this.actionList.get(keyCode).invoke(this.gameService);
//				// 获得方法名
//				String methodName = this.actionList.get(keyCode);
//				// 获得方法对象
//				Method actionMethod = this.gameService.getClass().getMethod(methodName);
//				// 调用方法
//				actionMethod.invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
		this.panelGame.repaint();
	}


	/**
	 * 显示玩家控制窗口（显示模拟器）
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}


	/**
	 * 刷新一下画面（模拟器窗口关闭事件）
	 */
	public void setOver() {
		this.panelGame.repaint();
		// 设置好模拟器键位后再加载一次（相当于再读取一次用户键位设置）
		this.setControlConfig();
	}


	/**
	 * 开始按钮事件
	 * JPanelGame 调用  GameControl 对象 gameControl 调用 start 方法
	 */
	public void start() {
		// 点击开始启动游戏之后无法继续点击按钮
		this.panelGame.buttonSwich(false);
		// 游戏开始后关闭模拟器和保存分数记录窗口（这是为了防止有些人在开启模拟器窗口的情况下直接开始游戏）
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		// 游戏数据初始化
		this.gameService.startGame();
		// 创建线程对象（内部类可以访问到以上对象）
		this.gameThread = new MainThread(){};
		// 启动线程对象
		this.gameThread.start();
		// 刷新画面
		this.panelGame.repaint();
	}
	
	
	/**
	 * 游戏失败之后的处理（显示保存记录得分的窗口和重新开始游戏）
	 */
	private void afterLose(){
		// 这里就是跟大家开的玩笑了  如果你点击过作弊按钮  分数将无法保存
		if (!this.dto.isCheat()) {
			// 显示 "保存和得分的窗口"
			this.frameSavePoint.showWindow(this.dto.getNowPoint());
		}
		// 使按钮可以点击
		this.panelGame.buttonSwich(true);
	}
	
	
	/**
	 * 刷新画面的方法
	 */
	public void repaint() {
		this.panelGame.repaint();
	}
	
	
	/**
	 * 保存分数的方法
	 * @param name
	 */
	public void savePoint(String name) {
		Player pla = new Player(name, this.dto.getNowPoint());
		// 把分数记录写入数据库和本地记录之中
		this.dataA.saveData(pla);
		this.dataB.saveData(pla);
		// 设置数据库记录到游戏 （把分数存进了数据库和本地之后我们再读一遍数据）
		this.dto.setDbRecode(dataA.loadData());
		// 设置本地记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		// 刷新画面
		this.panelGame.repaint();
	}	
	
	
	/**
	 * 下面的依然是内部类  可以看做是  GameControl 该类的一个成员
	 */
	private class MainThread extends Thread {
		@Override
		public void run() {
			// 上面的对象创建完成后刷一次画面
			panelGame.repaint();
			// 主循环
			while (dto.isStart()) {
				try {
					// 线程睡眠时间
					Thread.sleep(dto.getSleepTime());
					// 暂停状态的时候就 continue 到上面的语句  Thread.sleep(500); 这里不断的停止画面
					if(dto.isPause()){
						continue;
					}
					// 调用游戏主行为（在实现类方法中调用向下的降落）
					gameService.mainAction();
					// 走一次刷一次画面
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 线程不再走的时候（也就是游戏失败了）   我们调用下面的方法处理之后的动作
			afterLose();
		}
	}


	
}
