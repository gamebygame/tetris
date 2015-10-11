package config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;


@SuppressWarnings("serial")
public class FrameConfig implements Serializable{
	
	
	
	private final String title; 
	private final int windowUp;
	private final int width;
	private final int height;
	private final int padding;
	private final int border;
	private final int loseIdx;
	/**
	 * 图层属性
	 */
	private final List<LayerConfig> LayersConfig;
	/**
	 * 按钮属性
	 */
	private final ButtonConfig buttonConfig;
	
	
	public FrameConfig(Element frame){
		// 配置常量 将从xml中传来的字符型数据强制转化为整数型
		// 获取窗口宽度
		this.width = Integer.parseInt(frame.attributeValue("width"));
		// 获取窗口高度
		this.height = Integer.parseInt(frame.attributeValue("height"));
		// 获取边框内边距
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		// 获取边框粗细
		this.border = Integer.parseInt(frame.attributeValue("border"));
		// 获取标题
		this.title = frame.attributeValue("title");
		// 获取窗口拔高
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		// 输的情况调用的碎裂方格图片
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		
		/**
		 * 获取 frame 的窗体子元素 layer
		 */
		@SuppressWarnings("unchecked")
		List<Element> Layers = frame.elements("layer");
		LayersConfig = new ArrayList<LayerConfig>();
		// 取得单个窗体属性
		for (Element Layer : Layers) {
			LayerConfig lc = new LayerConfig(Layer.attributeValue("className"),
					Integer.parseInt(Layer.attributeValue("x")),
					Integer.parseInt(Layer.attributeValue("y")),
					Integer.parseInt(Layer.attributeValue("w")),
					Integer.parseInt(Layer.attributeValue("h")));
			LayersConfig.add(lc);
		}
		//初始化按钮属性
		buttonConfig = new ButtonConfig(frame.element("button"));
	}


	/**
	 * 提供 get 方法
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	public int getWindowUp() {
		return windowUp;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getPadding() {
		return padding;
	}
	public int getBorder() {
		return border;
	}
	public List<LayerConfig> getLayersConfig() {
		return LayersConfig;
	}
	public int getLoseIdx() {
		return loseIdx;
	}
	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}
	
	
	
}
