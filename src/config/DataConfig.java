package config;

import java.io.Serializable;
import org.dom4j.Element;


@SuppressWarnings("serial")
public class DataConfig implements Serializable{
	
	

	private final int maxRow;
	private DataInterfaceConfig dataA;
	private DataInterfaceConfig dataB;
	
	public DataConfig(Element data){
		this.maxRow = Integer.parseInt(data.attributeValue("maxRow"));
		this.dataA = new DataInterfaceConfig(data.element("dataA"));
		this.dataB = new DataInterfaceConfig(data.element("dataB"));
		data.element("dataB");
		
	}

	/**
	 * 提供 DataInterfaceConfig 配置类的 get 方法
	 * （这里与 GameConfig 类里提供的配置类 get 方法不同，因为我们不希望改变窗口诸如标题、宽度或者高度这些信息，但是 data 元素的子元素   dataA 和 dataB 的信息是要随时改变的）
	 * @return
	 */
	public DataInterfaceConfig getDataA() {
		return dataA;
	}
	public DataInterfaceConfig getDataB() {
		return dataB;
	}
	public int getMaxRow() {
		return maxRow;
	}

	
}
