package dto;

import java.io.Serializable;


/**
 * 玩家数据信息（名字，分数）
 * 
 * @author J
 */
@SuppressWarnings("serial")
public class Player implements Comparable<Player>, Serializable{
	
	
	
	private String name;
	private int point;
	
	
	public Player(String name, int point) {
		super();
		this.name = name;
		this.point = point;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

	
	@Override
	public int compareTo(Player pla) {
		/**
		 * 小于0就会认为小  然后排到下面去
		 */
		return pla.point - this.point;
	}
	
	
}
