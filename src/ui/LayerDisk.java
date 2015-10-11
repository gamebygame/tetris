package ui;

import java.awt.Graphics;


public class LayerDisk extends LayerData {
	
	
	public LayerDisk(int x,int y,int w,int h){
		super(x,y,w,h);
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		/**
		 * 继承  已经继承Layer的  LayerData  
		 * 和 LayerDataBase 分别继承 LayerData
		 */
		this.showData(Img.DISK, this.dto.getDiskRecode(), g);
	}
	
	
}
