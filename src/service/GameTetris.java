package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;
import config.GameConfig;
import dto.GameDto;
import entity.GameAct;


/**
 * 控制器 接口 GameService 的实现类
 * 
 * @author J
 */
public class GameTetris implements GameService {

	
	
	/**
	 * 游戏数据对象
	 */
	private GameDto dto;
	/**
	 * 随机数生成器
	 */
	private Random random = new Random();
	/**
	 * 定义常量（方块种类个数）
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size();
	/**
	 * 消行次数（决定消多少行升一级）
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	/**
	 * 获得连续消行的加分配置
	 */
	private static final Map<Integer, Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();

	
	/**
	 *  构造方法
	 */
	public GameTetris(GameDto dto) {
		this.dto = dto;
	}

	
	/**
	 * 控制器方向键会触发的行为
	 */
	// 上（0代表不动，-1代表向上）
	public boolean keyUp() {
		if(!this.dto.isStart()){
			return true;
		}
		// 点击暂停了之后不再允许向上方向的按键进行旋转操作
		if (dto.isPause()) {
			return true;
		}
		// 为防止两个线程同一时间共同访问同一个数据对象我们使用同步化方法 这样做的好处是提供监视和互斥机制，一次只允许一个线程访问该数据对象
		synchronized (this.dto) {
			// 俄罗斯方块不需要向上移动，所以直接调用旋转公式
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	// 下
	public boolean keyDown() {
		if(!this.dto.isStart()){
			return false;
		}
		// 点击暂停了之后不再允许向下方向的按键进行加速移动操作
		if (dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// 方块到底向下无法移动的时候获得地图对象
			boolean[][] map = this.dto.getGameMap();
			// 取得方块实体对象
			Point[] act = this.dto.getGameAct().getActPoints();
			// 在地图上打印落至底层的方块
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// 判断消行并计算消行的数量并获得经验值
			int plusExp = this.plusExp();
			// 增长的一格经验值存在（那就是大于0）的时候调用增加经验值的方法
			// 说白一点就是如果发生消行的话才会调用以下增加经验值的方法
			if (plusExp > 0) {
				// 增加经验值
				this.plusPoint(plusExp);
			}
			// 创建下一个方块（刷新新的方块）
			this.dto.getGameAct().init(this.dto.getNext());
			// 随机生成再下一个方块
			this.dto.setNext(random.nextInt(MAX_TYPE));
			// 检查游戏是否失败
			if (this.isLose()) {
				// 输掉之后直接结束游戏（停止线程运作）
				this.dto.setStart(false);
			}
		}
		return true;
	}
	/**
	 * 增加分数和升级的方法
	 */
	private void plusPoint(int plusExp) {
		int lv = this.dto.getNowLevel();
		int rmLine = this.dto.getNowRemoveLine();
		// 这里获得现在的分数，后面要记得  set 加过之后的分数
		int point = this.dto.getNowPoint();
		// 消掉的行数 占 升级行数 的几分之20  加上 新获得的经验   大于等于   升级所需行数的话（就是大于20的话）
		if(rmLine % LEVEL_UP + plusExp >= LEVEL_UP){
			// 等级+1  并重新 set 到 dto 里去
			this.dto.setNowLevel(++lv);
		}
		this.dto.setNowRemoveLine(rmLine + plusExp);
		this.dto.setNowPoint(point + PLUS_POINT.get(plusExp));
	}
	/**
	 * 游戏失败的方法
	 */
	private boolean isLose() {
		// 获得现在的活动方块
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		// 获得现在的游戏地图
		boolean[][] map = this.dto.getGameMap();
		// 判断俄罗斯方块上的4个单元格是否和地图单元格相重合
		for (int i = 0; i < actPoints.length; i++) {
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
	}

	// 左
	public boolean keyLeft() {
		// 点击暂停了之后不再允许向左方向的按键进行移动操作
		if (dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
			return true;
		}
	}

	// 右
	public boolean keyRight() {
		// 点击暂停了之后不再允许向右方向的按键进行移动操作
		if (dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
			return true;
		}
	}

	
	/**
	 * 绑定功能键方法
	 */
	// 作弊键（每次增加10分）
	@Override
	public boolean keyFunUp() {
		// 游戏开始了之后才允许玩家使用作弊加分的功能
		if (this.dto.isStart()) {
			// 这里是一个娱乐设定  如果玩家在玩游戏的过程中使用过作弊  将设置为true
			this.dto.setCheat(true);
			this.plusPoint(4);
//			 // 下面的方法是测试作弊键的方法 点击一次加10分
//			 int point = this.dto.getNowPoint();
//			 int rmline = this.dto.getNowRemoveLine();
//			 int lv = this.dto.getNowLevel();
//			 point += 10;
//			 rmline += 1;
//			 if (rmline % 20 == 0) {
//			 lv += 1;
//			 }
//			 this.dto.setNowPoint(point);
//			 this.dto.setNowLevel(lv);
//			 this.dto.setNowRemoveLine(rmline);
		}
		return true;
	}
	
	// 叉叉
	// 瞬间下落
	@Override
	public boolean keyFunDown() {
		// 游戏开始了之后才允许玩家使用瞬间下落的功能
		if (this.dto.isStart()) {
			// 点击暂停了之后不再允许瞬间下落的功能按键进行操作
			if (dto.isPause()) {
				return true;
			}
			while (!keyDown());
		}
		return true;
	}
	
	// 方块
	// 阴影开关
	@Override
	public boolean keyFunLeft() {
		this.dto.changeShowShadow();
		return true;
	}
	
	// 圆圈
	// 游戏暂停
	@Override
	public boolean keyFunRight() {
		// 只有当游戏开始的时候（也就是启动了线程的时候）才允许玩家调用暂停方法 
		if (this.dto.isStart()) {
			this.dto.changePause();
		}
		return true;
	}

	
	/**
	 * 计算消行的数量并获得经验值的方法
	 */
	private int plusExp() {
		// 先拿到数组（获得游戏地图）
		boolean map[][] = this.dto.getGameMap();
		int exp = 0;
		// 沿着Y扫描地图，查看是否可以消行
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			// 调用判断是否可以消行的方法 分开写避免 for 和 if 过多的嵌套
			if (isCanRemoveLine(y, map)) {
				// 可以消行的话就勇敢的消行吧!
				this.removeLine(y, map);
				// 消完行增加经验值
				exp++;
			}
		}
		return exp;
	}
	/**
	 * 判断某一行是否可消       传进来 y （第几行） 和 游戏地图 map
	 */
	private boolean isCanRemoveLine(int y, boolean map[][]) {
		// 对地图上每一个单元格扫描 （23*23）像素
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if (!map[x][y]) {
				// 如果有一个单元格是空隙的话 结束这一次循环继续判定嘛
				return false;
			}
		}
		return true;
	}
	/**
	 * 消行操作
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			// 从第 y 行开始做操作，例如是第 14 行可以消行的话就按照13行给14  12给13的顺序进行数组赋值
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y-1];
			}
			map[x][0] = false;
		}
	}
	

	/**
	 * 主线程启动游戏的实现
	 */
	@Override
	public void startGame() {
		// 随机生成下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		// 随机生成现在方块
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		// 设置游戏状态为开始
		this.dto.setStart(true);
		// 重新开始游戏的时候要把  dto 初始化
		this.dto.dtoInit();
	}

	
	/**
	 * 实现游戏主行为
	 */
	@Override
	public void mainAction(){
		this.keyDown();
	}
	
	
}
