package ui;

import java.awt.Graphics;


public class LayerLevel extends Layer {
	
	
	//"等级"两个字图片的宽度，因为经常用到所以用以常量定义
	private static final int IMG_LV_W = Img.LEVEl.getWidth(null);

	//构造方法
	public LayerLevel(int x,int y,int w,int h){
		super(x,y,w,h);
	}
	
	/**
	 * 打印数字
	 */
	public void paint(Graphics g){
		this.createWindow(g);
		int centerX=this.w - IMG_LV_W>>1;
		//绘制"等级"两个字在窗体相应位置
		g.drawImage(Img.LEVEl,this.x+centerX, this.y+PADDING-5, null);
		//显示等级
		this.drawNumberLeftPad(centerX, 40, this.dto.getNowLevel(), 2, g);
	}
	
	
}
