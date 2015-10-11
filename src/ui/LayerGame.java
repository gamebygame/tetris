package ui;

import java.awt.Graphics;
import java.awt.Point;
import config.GameConfig;
import entity.GameAct;


public class LayerGame extends Layer {
	
	
	
	/**
	 * 配置常量
	 */
	private static final int ACT_SIZE = 23;
	/**
	 * 左边距
	 */
	private static final int LEFT_SIDE = 0;
	/**
	 * 右边距
	 */
	private static final int RIGHT_SIDE = GameConfig.getSystemConfig().getMaxX();
	/**
	 * 拿到碎裂方块的配置
	 */
	private static final int LOSE_IDX = GameConfig.getFrameConfig().getLoseIdx();
	
	
	/**
	 * 构造函数
	 */
	public LayerGame(int x,int y,int w,int h){
		super(x,y,w,h);
	}

	
	/**
	 * 主方法绘制方块
	 */
	public void paint(Graphics g) {
		// 画出窗口
		this.createWindow(g);
//		// 如果是开始状态就绘制东西
//		if(this.dto.isStart()){
		// 拿到方块数组集合
		GameAct act = this.dto.getGameAct();
		if (act != null) {
			Point[] points = act.getActPoints();
			// 显示阴影
			this.drawShadow(points, g);
			// 绘制活动方块
			this.drawMainAct(points, g);
		}
		// 绘制游戏地图
		this.drawMap(g);
		// 暂停
		if(this.dto.isPause()){
			this.drawImageAtCenter(Img.PAUSE, g);
		}
	}
	
	
	/**
	 * 绘制活动方块
	 * @param g
	 */
	private void drawMainAct(Point[] points, Graphics g) {
		// 获得方块类型编号（0~6）
		int typeCode = this.dto.getGameAct().getTypeCode();
//		typeCode = this.dto.isStart() ? typeCode : LOSE_IDX;
		// 绘制完整的俄罗斯方块
		for (int i = 0; i < points.length; i++) {
			this.drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
		}
	}
	/**
	 * 绘制游戏地图
	 * @param g
	 */
	private void drawMap(Graphics g) {
		// 绘制地图
		boolean[][] map = this.dto.getGameMap();
		// 计算当前堆积颜色
		int lv = this.dto.getNowLevel();
		// 判断等级大于 0 级的时候不再出现堆积颜色为白色 使颜色循环只在红色开始到紫色循环
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;
		// TODO 输的情况下 imgIdx = 8 （碎裂方格）
		// 一列一列打印地图
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					// 如果是开始状态就显示 imgIdx 否则显示 LOSE_IDX
					this.drawActByPoint(x, y, imgIdx, g);
				}
			}
		}
	}


	/**
	 * 绘制阴影的方法
	 */
	private void drawShadow(Point[] points, Graphics g) {
		//如果不是阴影，便返回该方法重新判定，直到符合能够打印阴影的情况便执行 if 之后
		if(!this.dto.isShowShadow()){
			return;
		}
		int leftX = RIGHT_SIDE;
		int rightX = LEFT_SIDE;
		for (Point p: points) {
			leftX = p.x<leftX ? p.x : leftX;
			rightX = p.x>rightX ? p.x : rightX;
		}
		g.drawImage(Img.SHADOW, 
				this.x + BORDER + leftX*ACT_SIZE, 
				this.y + BORDER, 
				(rightX-leftX+1) * ACT_SIZE, 
				this.h - BORDER*2,
				null);
	}


	/**
	 * 绘制正方形块
	 * 从取得的图片切出一个小方块
	 * 其中ImgIdx表示第几张方格图片（从第0张开始）
	 */
	private void drawActByPoint(int x, int y, int ImgIdx, Graphics g){
		ImgIdx = this.dto.isStart() ? ImgIdx : LOSE_IDX;
			g.drawImage(Img.ACT,
					this.x + x * ACT_SIZE + BORDER, 
					this.y + y * ACT_SIZE + BORDER, 
					this.x + x * ACT_SIZE + ACT_SIZE + BORDER, 
					this.y + y * ACT_SIZE + ACT_SIZE + BORDER, 
					ImgIdx * ACT_SIZE, 0,
					ImgIdx * ACT_SIZE + ACT_SIZE, ACT_SIZE, null);
	}

	
	
}
