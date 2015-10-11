package config;

import java.io.Serializable;


@SuppressWarnings("serial")
public class LayerConfig implements Serializable{
	
	
	
	/**
	 * 设置  xml 中 layer 元素的相应的属性参数
	 */
	private String className;
	int x;
	int y;
	int w;
	int h;
	
	
	/**
	 * 构造方法初始化所有属性参数的值
	 * @param className
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public LayerConfig(String className, int x, int y, int w, int h) {
		this.className = className;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	
	/**
	 * 为取得的所有属性值设置 get 方法
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}

	
	
}
