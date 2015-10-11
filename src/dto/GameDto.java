package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.GameFunction;
import config.GameConfig;
import entity.GameAct;


/**
 * 游戏中所有要访问的数据
 * 
 * @author J
 */
public class GameDto {
	
	
	
	/**
	 * 定义变量
	 */
	// 游戏窗口宽度和高度
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;
	// 数据库 游戏左上角数据
	private List<Player> dbRecode;
	// 本地数据 数据库下方本地数据
	private List<Player> diskRecode;
	// 游戏地图
	private boolean[][] gameMap;
	// 主窗体中二维数组数据（表示下落的小方块）
	private GameAct gameAct;
	// 下一个要出现的方块
	private int next;
	// 等级数据
	private int nowLevel;
	// 现在的成绩
	private int nowPoint;
	// 移除了多少行
	private int nowRemoveLine;
	// 表示游戏是否是开始状态
	private boolean start;
	// 是否显示阴影
	private boolean showShadow;
	// 暂停
	private boolean pause;
	// 判断是否使用过作弊
	private boolean cheat;
	// 线程睡眠时间
	private long sleepTime;
	
	
	/**
	 * 构造函数
	 */
	public GameDto(){
		dtoInit();
	}
	/**
	 * dto 初始化 
	 * 这里很重要  因为结束一次游戏之后所有数据必须重置
	 */
	public void dtoInit(){
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		// 初始化的时候等级、分数、消行数据全部清零
		this.nowLevel = 0;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
		this.cheat = false;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
	}
	
	
	/**
	 * 构造以上所有需要访问数据的get() set() 方法
	 */
	public List<Player> getDbRecode() {
		return dbRecode;
	}
	public void setDbRecode(List<Player> dbRecode) {
		//调用填满和排序方法然后 set 进 dbRecode 之中
		this.dbRecode = this.setFillRecode(dbRecode);
	}
	public List<Player> getDiskRecode() {
		return diskRecode;
	}
	public void setDiskRecode(List<Player> diskRecode) {
		//调用填满和排序方法然后 set 进 DiskRecode 之中
		this.diskRecode = this.setFillRecode(diskRecode);
	}
	
	
	/**
	 * 判断玩家数据是否满足5个  如果不满足就填满为5个     并在之后排序的方法
	 * @param players
	 * @return players
	 * 
	 * 该方法被  setDbRecode 和  setDiskRecode 所调用
	 */
	private List<Player> setFillRecode(List<Player> players){
		//进来空玩家信息的话就创建
		if(players==null){
			players = new ArrayList<Player>();
		}
		//如果不足5个玩家信息  就自动填满空数据
		while(players.size()<5){
			players.add(new Player("No Data", 0));
		}
		// 将最大成绩排到窗口最上边 排序完毕后 set 入 players 之中
		Collections.sort(players);
		return players;
	}
	
	
	public boolean[][] getGameMap() {
		return gameMap;
	}
	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}
	
	public GameAct getGameAct() {
		return gameAct;
	}
	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}
	
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	
	public int getNowLevel() {
		return nowLevel;
	}
	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
		// 计算线程睡眠时间 
		//（把通过升级计算线程睡眠时间写到这里是有好处的  我们不需要再控制类当中的线程方法中一次挨一次调用计算  只需要在这里计算等级的时候再计算出所需要改变的线程睡眠时间）
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
	}
	
	// 线程睡眠时间只提供  get 方法   我们不让外界可以操纵它
	public long getSleepTime() {
		return sleepTime;
	}
	
	public int getNowPoint() {
		return nowPoint;
	}
	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}
	
	public int getNowRemoveLine() {
		return nowRemoveLine;
	}
	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}
	
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public boolean isShowShadow() {
		return showShadow;
	}
	// 注意这里  翻转了布尔属性   如果真就为 false   如果假就为true
	// 用于阴影和暂停开启关闭 转换
	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}
	
	public boolean isPause() {
		return pause;
	}
	public void changePause() {
		this.pause = !this.pause;
	}
	
	public boolean isCheat() {
		return cheat;
	}
	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}
	
	
	
}
