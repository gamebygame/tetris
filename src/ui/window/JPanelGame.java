package ui.window;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.Img;
import ui.Layer;
import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;


@SuppressWarnings("serial")
public class JPanelGame extends JPanel{
	
	
	
	/**
	 * 声明所需变量
	 */
	//配置文件拿到按钮配置的属性参数  
	private static final int BTN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
	private static final int BTN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
	//用List不用数组是因为List可以使用add
	private List<Layer> Layers = null;
	private JButton btnStart;
	private JButton btnConfig;
	private GameControl gameControl = null;
	
	
	/**
	 * 构造器
	 * @param dto
	 */
	public JPanelGame(GameControl gameControl, GameDto dto){
		//连接游戏控制器
		this.gameControl = gameControl;
		//设置布局管理器为自由布局
		this.setLayout(null);
		//初始化组建
		this.initComponent();
		//初始化层
		this.initLayer(dto);
		//安装键盘监听器
		this.addKeyListener(new PlayerControl(gameControl));
		
		// 方法反射  就是钩子罢了
//		Method m = this.getClass().getMethod("init", null);
//		m.invoke(this, null);
		
	}
	
	
	/**
	 * 初始化组建（按钮"开始" "设置"）
	 */
	private void initComponent(){
		// 初始化开始按钮
		this.btnStart = new JButton(Img.BTN_START);
		// 设置开始按钮位置（从配置文件 get 方法里拿数据咯）
		this.btnStart.setBounds(GameConfig.getFrameConfig().getButtonConfig()
				.getStartX(), GameConfig.getFrameConfig().getButtonConfig()
				.getStartY(), BTN_SIZE_W, BTN_SIZE_H);
		// 给开始按钮增加事件监听
		this.btnStart.addActionListener(new ActionListener() {
			// 开始事件发生
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				// 返回焦点（必须要有这句话  不然玩家在点击了开始之后就会无法控制游戏的所有行为了  因为没有焦点了嘛）
				requestFocus();
			}
		});
		// 添加按钮到面板
		this.add(btnStart);
		// 初始化设置按钮
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		// 设置设置按钮位置（从配置文件 get 方法里拿数据咯）
		this.btnConfig.setBounds(GameConfig.getFrameConfig().getButtonConfig()
				.getUserConfigX(), GameConfig.getFrameConfig()
				.getButtonConfig().getUserConfigY(), BTN_SIZE_W, BTN_SIZE_H);
		// 给"开始"和"设置"按钮设置监听
		this.btnConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
			}
		});
		// 添加按钮到面板
		this.add(btnConfig);
	}
	
	/**
	 * 初始化层（即每个模块窗口）
	 */
	private void initLayer(GameDto dto){
		try{
			/**
			 * 取得 xml 配置文件中 game 子元素 frame 的所有配置 
			 * （已经在 GameConfig 创建了所有配置类的 get 方法，直接调用该方法取得配置好的参数值）
			 */
			FrameConfig fCfg = GameConfig.getFrameConfig();
			//取得其中一个LayerConfig元素的内容
			List<LayerConfig> LayersCfg = fCfg.getLayersConfig();
			//配置了几个Layer就创建几个长度的数组
			Layers=new ArrayList<Layer>(LayersCfg.size());
			//循环调用每个Layer（创建所有层的对象）
			for (LayerConfig LayerCfg : LayersCfg) {
				//获得类对象
				Class<?> cls = Class.forName(LayerCfg.getClassName());
				//获得取得类的构造函数
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				//调用构造函数创建类的对象，强调该对象一定返回Layer
				Layer layer = (Layer)ctr.newInstance(
						LayerCfg.getX(),
						LayerCfg.getY(),
						LayerCfg.getW(),
						LayerCfg.getH()
					);
				//在每个Layer对象放入之前设置游戏数据对象
				layer.setDto(dto);
				//把创建的Layer对象放入集合
				Layers.add(layer);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		// 调用本方法刷新画面确保无误
		super.paintComponent(g);
		// 绘制游戏画面
		// 刷新层窗口 paint意为调用每个具体对象的paint方法，例如调用LayButton中的paint方法
		for (int i = 0; i < Layers.size(); Layers.get(i++).paint(g));
	}

	
	/**
	 * 控制按钮是否可点击
	 */
	public void buttonSwich(boolean onOff){
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}

	
	
}
