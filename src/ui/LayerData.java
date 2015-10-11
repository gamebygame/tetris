package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import config.GameConfig;
import dto.Player;


/**
 * 向前继承Layer   向后被 LayerDataBase 和 LayerDisk 所继承
 * 
 * 这种对象流的继承方法可以有效减少高类似代码的多余书写
 */
public abstract class LayerData extends Layer{
	
	
	
	/**
	 * TODO  最大数据行  配置文件
	 */
		private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();
	/**
	 *  起始Y坐标
	 */
	private static int START_Y = 0;
	/**
	 *  值槽外径
	 */
	private static int RECT_H = IMG_RECT_H;
	/**
	 * 值槽间距
	 */
	private static int SPA=0;
	
	
	/**
	 * 构造方法初始化
	 */
	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPA=(this.h - RECT_H*5 - PADDING*2 - Img.DB.getHeight(null)) / MAX_ROW;
		START_Y=PADDING+5 + Img.DB.getHeight(null) + SPA;
	}

	
	/**
	 * 绘制所有值槽
	 * 
	 * @param imgTitle  标题图片
	 * @param players  数据源
	 * @param g  画笔
	 */
	public void showData(Image imgTitle, List<Player> players, Graphics g){
		g.drawImage(imgTitle,this.x+PADDING, this.y+PADDING, null);
		//数据最终被游戏画面所获得
//		List<Player> players=this.dto.getDbRecode();
		int nowPoint=this.dto.getNowPoint();
		for (int i = 0; i < MAX_ROW; i++) {
			Player pla=players.get(i);
			//取得最大分数
			int recodePoint=pla.getPoint();
			//当前分数比最大分数求出比值
			double percent=(double)nowPoint / recodePoint;
			percent=percent>1 ? 1.0 : percent;
			//循环打印从gameDto中获得的各个参数（现在的等级，分数，消行数等等）
			//nowPoints代表分子   p.getPoint()代表分母
			String strPoint = recodePoint==0 ? null : Integer.toString(recodePoint);
			this.drawRect(START_Y + i*(RECT_H+SPA), 
					pla.getName(), strPoint, 
					percent, g);
		}
	}
	
	
	/**
	 * 抽象类无法被实例化
	 */
	@Override
	abstract public void paint(Graphics g);


	
}
