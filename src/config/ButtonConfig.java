package config;

import java.io.Serializable;
import org.dom4j.Element;


@SuppressWarnings("serial")
public class ButtonConfig implements Serializable{
	
	
	
	private final int buttonW;
	private final int buttonH; 
	private final int startX;
	private final int startY;
	private final int userConfigX;
	private final int userConfigY;
	
	
	/**
	 * 取得 button 元素属性
	 * 取得 start 元素属性
	 * 取得 userConfig 元素属性
	 */
	public ButtonConfig(Element button){
		this.buttonW = Integer.parseInt(button.attributeValue("w"));
		this.buttonH = Integer.parseInt(button.attributeValue("h"));
		this.startX = Integer.parseInt(button.element("start").attributeValue("x"));
		this.startY = Integer.parseInt(button.element("start").attributeValue("y"));
		this.userConfigX = Integer.parseInt(button.element("userConfig").attributeValue("x"));
		this.userConfigY = Integer.parseInt(button.element("userConfig").attributeValue("y"));
	}


	/**
	 * 生成 get 方法
	 * @return
	 */
	public int getButtonW() {
		return buttonW;
	}
	public int getButtonH() {
		return buttonH;
	}
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int getUserConfigX() {
		return userConfigX;
	}
	public int getUserConfigY() {
		return userConfigY;
	}

	
	
}
