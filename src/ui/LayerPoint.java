package ui;

import java.awt.Graphics;
import config.GameConfig;


public class LayerPoint extends Layer {
	
	
	
	/**
	 * 分数最大位数
	 */
	private final int POINT_BIT = 5;
	/**
	 * 消行次数（决定消多少行升一级）
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	/**
	 * 消行y坐标
	 */
	private final int rmlineY;
	/**
	 * 分数y坐标
	 */
	private final int  pointY;
	/**
	 * 共用的x坐标
	 */
	private final int comX;
	/**
	 * 经验值y坐标
	 */
	private final int expY;
	
	
	/**
	 * 构造方法
	 */
	public LayerPoint(int x,int y,int w,int h){
		super(x,y,w,h);
		//初始化共通的x坐标 
		this.comX=this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		//初始化分数显示的y坐标 
		this.pointY=PADDING;
		//初始化消行显示的y坐标 
		this.rmlineY=this.pointY + Img.POINT.getHeight(null)+PADDING;
		//初始化exp显示的y坐标
		this.expY=this.rmlineY + Img.RMLINE.getHeight(null)+PADDING*2;
	}
	
	
	public void paint(Graphics g){
		this.createWindow(g);
		//窗口标题（分数）
		g.drawImage(Img.POINT,this.x+PADDING*2, this.y+pointY, null);
		//显示分数
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);

		//窗口标题（消行）
		g.drawImage(Img.RMLINE,this.x+PADDING*2, this.y+rmlineY, null);
		//显示消行数
		this.drawNumberLeftPad(comX, rmlineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
		//绘制值槽（经验值）
		int rmline=this.dto.getNowRemoveLine();
		//计算比值作为参数传入
		this.drawRect(expY, "下一级", null, (double)(rmline % LEVEL_UP) / (double)LEVEL_UP,g);
		
//		//给血条画个漂亮的颜色吧
//		Color MY_COLOR = new Color(0x90EE90);
//		g.setColor(MY_COLOR);
		//血槽颜色过渡效果（老方法）
//		g.setColor(this.getNowColor((double)(rmline%LEVEL_UP), (double)LEVEL_UP));
//		g.fillRect(this.x+PADDING*2 + 2, this.y+expY-PADDING + 2, w, h-4);
	}
	
	
	/**
	 * 值槽颜色渐变方法（血条为例）
	 */
//	private Color getNowColor(double hp,double maxHp){
//		int colorR=0;
//		int colorG=255;
//		int colorB=0;
//		double hpHarf=maxHp/2;
//		if(hp>hpHarf){
//			colorR=255-(int)((hp-hpHarf)/(maxHp/2)*255);
//			colorG=255;
//		}else{
//			colorR=255;
//			colorG=(int)(hp/(maxHp/2)*255);
//		}
//		return new Color(colorR,colorG,colorB);
//	}
	
	

}
