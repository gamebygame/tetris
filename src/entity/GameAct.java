package entity;

import java.awt.Point;
import java.util.List;
import config.GameConfig;


/**
 * 该类封装了俄罗斯方块实体
 * 
 * @author J
 */
public class GameAct {
	
	
	
	/**
	 * 方块数组
	 */
	private Point[] actPoints=null;
	/**
	 * 方块编号
	 */
	private int typeCode;
	/**
	 * 地图数组最大最小边界常量
	 */
	private final static int MIN_X = GameConfig.getSystemConfig().getMinX();
	private final static int MAX_X = GameConfig.getSystemConfig().getMaxX();
	private final static int MIN_Y = GameConfig.getSystemConfig().getMinY();
	private final static int MAX_Y = GameConfig.getSystemConfig().getMaxY();
	/**
	 * 创建一个数组放很多个俄罗斯方块
	 */
	private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	/**
	 * 通过编号判定是否能够旋转
	 */
	private final static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();
	
	
	public GameAct(int typeCode){
		this.init(typeCode);
	}
	
	
	/**
	 * 取到一种俄罗斯方块
	 */
	public void init(int typeCode){
		this.typeCode = typeCode;
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		//其中的一个俄罗斯方块数组里存在四个小方格的绘制坐标，循环打印四个小方格成为一个完整的俄罗斯方块
		for (int i = 0; i < points.length; i++) {
			actPoints[i] = new Point(points[i].x, points[i].y);
		}
	}
	
	
	/**
	 * 封装取到的一种俄罗斯方块实体
	 */
	public Point[] getActPoints() {
		return actPoints;
	}
	
	
	/**
	 * 方块移动的方法
	 * 
	 * @param moveX:x轴偏移量
	 * @param moveY:y轴偏移量
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap){
		//让方块动起来
		for(int i=0;i<actPoints.length;i++){
			int newX = actPoints[i].x + moveX;
			int newY = actPoints[i].y + moveY;
			//移动边界判定
			if(isOverZone(newX, newY, gameMap)){
				return false;
			}
		}
		for(int i=0;i<actPoints.length;i++){
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}
		return true;
	}
	
	
	/**
	 * 方块旋转的方法
	 * 
	 * 顺时针（注意屏幕坐标系与笛卡尔坐标系相反）
	 * A.x=O.y+O.x-B.y
	 * A.y=O.y-O.x+B.x
	 * 逆时针
	 * A.x=O.x-O.y+B.y
	 * A.y=O.x+O.y-B.x
	 */
	public void round(boolean[][] gameMap){
		//typeCode 作为下标访问 TYPE_ROUND 数组判断是否旋转    按照配置文件里 true 便旋转  false 便不旋转
		if(!TYPE_ROUND.get(typeCode)){
			return;
		}
		/*注意i从1开始而不是0，
		因为不需要算中心点的坐标变化
		只需要算从actPoints[1]开始到actPoints[3]的小方块坐标旋转变化*/
		for(int i=1; i<actPoints.length; i++){
			//顺时针（俄罗斯方块只需向一个方向旋转）
			int newX=actPoints[0].y+actPoints[0].x-actPoints[i].y;
			int newY=actPoints[0].y-actPoints[0].x+actPoints[i].x;
			//旋转边界判定
			if(this.isOverZone(newX, newY, gameMap)){
				return;
			}
		}
		for(int i=1; i<actPoints.length; i++){
			//边界判定无误后执行旋转
		    int newX=actPoints[0].y+actPoints[0].x-actPoints[i].y;
			int newY=actPoints[0].y-actPoints[0].x+actPoints[i].x;
			actPoints[i].x=newX;
			actPoints[i].y=newY;
		}
	}
	
	
	/**
	 * 边界判定在这个方法里共同判定
	 * 移动和旋转共用一套边界判定方法
	 * 判断是否超出边界
	 */
	private boolean isOverZone(int x,int y,boolean[][] gameMap){
		return x<MIN_X || x>MAX_X || y<MIN_Y || y>MAX_Y || gameMap[x][y];
	}


	/**
	 * 获得方块类型编号
	 */
	public int getTypeCode() {
		return typeCode;
	}

	
	
}
