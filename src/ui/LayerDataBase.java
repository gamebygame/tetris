package ui;

import java.awt.Graphics;


public class LayerDataBase extends LayerData {

		
	/**
	 * 构造方法
	 */
	public LayerDataBase(int x,int y,int w,int h){
		super(x,y,w,h);
	}
	
	/**
	 * "数据库"汉字
	 */
	public void paint(Graphics g){
		this.createWindow(g);
		/**
		 * 继承  已经继承Layer的  LayerData  
		 * 和 LayerDisk 分别继承 LayerData
		 */
		this.showData(Img.DB, this.dto.getDbRecode(), g);
	}

	
}
