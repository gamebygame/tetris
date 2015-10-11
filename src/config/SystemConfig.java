package config;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;


@SuppressWarnings("serial")
public class SystemConfig implements Serializable{
	
	
	
	/**
	 * 地图数组最大最小边界常量
	 */
	private final int minX;
	private final int maxX;
	private final int minY;
	private final int maxY;
	private final int levelUp;
	private final List<Point[]> typeConfig;
	private final List<Boolean> typeRound;
	private final Map<Integer, Integer> plusPoint;

	
	public SystemConfig(Element system){
		this.minX = Integer.parseInt(system.attributeValue("minX"));
		this.maxX = Integer.parseInt(system.attributeValue("maxX"));
		this.minY = Integer.parseInt(system.attributeValue("minY"));
		this.maxY = Integer.parseInt(system.attributeValue("maxY"));
		this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
		
		@SuppressWarnings("unchecked")
		// xml 配置文件中在 system 元素下有很多个 rect 子元素，我们在这里一一获得
		List<Element> rects = system.elements("rect");
		typeConfig = new ArrayList<Point[]>(rects.size());
		typeRound = new ArrayList<Boolean>(rects.size());
		
		for (Element rect: rects) {
			// 是否旋转
			this.typeRound.add(Boolean.parseBoolean(rect.attributeValue("round")));
			// 获得坐标对象
			@SuppressWarnings("unchecked")
			// 获得每一个 rect 子元素的同时，要获得每一个 rect 子元素中的 point 子元素（叫子子元素也可以啦）
			List<Element> pointConfig = rect.elements("point");
			// 创建 Point 对象数组
			Point[] points = new Point[pointConfig.size()];
			// 获得子子元素 point 中的 x y 属性
			for (int i = 0; i < points.length; i++) {
				int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
				int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
				points[i] = new Point(x,y);
			}
			// 把 Point 对象数组放到 typeConfig 中
			typeConfig.add(points);
		}
		// 连续消行的加分配置
		this.plusPoint = new HashMap<Integer, Integer>();
		@SuppressWarnings("unchecked")
		List<Element> plusPointCfg = system.elements("plusPoint");
		for (Element Cfg: plusPointCfg) {
			int rm = Integer.parseInt(Cfg.attributeValue("rm"));
			int point = Integer.parseInt(Cfg.attributeValue("point"));
			this.plusPoint.put(rm, point);
		}
	}


	/**
	 * 生成以上配置属性参数的  get 方法
	 * @return
	 */
	public int getMinX() {
		return minX;
	}
	public int getMaxX() {
		return maxX;
	}
	public int getMinY() {
		return minY;
	}
	public int getMaxY() {
		return maxY;
	}
	public List<Point[]> getTypeConfig() {
		return typeConfig;
	}
	public int getLevelUp() {
		return levelUp;
	}
	public List<Boolean> getTypeRound() {
		return typeRound;
	}
	public Map<Integer, Integer> getPlusPoint() {
		return plusPoint;
	}
	

	
}
