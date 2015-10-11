package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import ui.Img;
import util.FrameUtil;
import control.GameControl;


@SuppressWarnings("serial")
public class JFrameConfig extends JFrame{
	
	
	
	private JButton btnOK = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JButton btnUser = new JButton("应用");
	private JLabel errorMsg = new JLabel();
	// 皮肤列表
	@SuppressWarnings("rawtypes")
	private JList skinList = null;
	// 皮肤图片的预览图
	private JPanel skinView = null;
	// 皮肤数据
	@SuppressWarnings("rawtypes")
	private DefaultListModel skinData = new DefaultListModel(); 
	private GameControl gameControl;
	
	
	/**
	 * 设置按键改变的位置   
	 */
	private TextCtrl[] keyText = new TextCtrl[8];
	/**
	 * PSP的图片
	 */
	private static final Image IMG_PSP = new ImageIcon("data/PSP.png").getImage();
	/**
	 * 皮肤预览图
	 */
	private Image[] skinViewList = null;
//	private Image temp_skinImg = new ImageIcon("Graphics/eva.png").getImage();
	/**
	 * 数组里放置游戏方向键和功能键
	 */
	private static final String[] METHOD_NAMES = {
		"keyRight", "keyUp", "keyLeft", "keyDown",
		"keyFunLeft", "keyFunUp", "keyFunRight", "keyFunDown"
	};
	/**
	 * 存取方向键和功能键信息的 .dat 文件
	 */
	private static final String PATH = "data/control.dat";
	
	
	/**
	 * 构造器
	 */
	public JFrameConfig(GameControl gameControl) {
		// 获得游戏控制器对象
		this.gameControl = gameControl;
		// 边界布局
		this.setLayout(new BorderLayout());
		// 设置标题
		this.setTitle("键位设置");
		// 初始化按键输入框
		this.initKeyText();
		/**
		 * 添加主面板
		 */
		this.add(createMainPanel(), BorderLayout.CENTER);
		/**
		 * 添加按钮面板
		 */
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		//不允许用户改变窗口大小
		this.setResizable(false);
		//设置窗口大小
		this.setSize(533,300);
		//居中
		FrameUtil.setFrameCenter(this);
		
//		// TODO p)测试用
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setVisible(true);
	}

	
	/**
	 * 初始化按键输入框
	 */
	private void initKeyText(){
		int x = 0;
		int y = 35;
		int w = 55;
		int h = 20;
		//左边一竖列按键
		for (int i = 0; i < 4; i++) {
			keyText[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 28;
		}
		//右边一竖列按键
		x += 467;
		y = 35;
		for (int i = 4; i < 8; i++) {
			keyText[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 28;
		}
		try {
			//读取已经存入的按键信息（包括功能键和方向键）
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>)ois.readObject();
			ois.close();
			//将 HashMap 转换集合
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for (Entry<Integer, String> e: entryset) {
				for (TextCtrl tc: keyText) {
					//比较从 HashMap 转换而来的集合中 Value 是否与 String 即方法名相等，是的话就把 key 值 set 到 keyText 中
					if(tc.getMethodName().equals(e.getValue())){
						tc.setKeyCode(e.getKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 创建主面板（选项卡面板）
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		// 下面两个选项卡调用相应的方法显示内容
		jtp.addTab("控制设置", this.createControlPanel());
		jtp.addTab("皮肤设置", this.createSkinPanel());
		return jtp;
	}
	/**
	 * 玩家控制设置面板
	 */
	private JPanel createControlPanel() {
		// 内部类（匿名类 没有具体类名） 表示 new 了一个继承JPanel 的类的对象 jp （并非传统概念中直接 new 了一个 JPanel
		// 对象 jp）
		// 因为要重写 JPanelGame 中的 paintComponent 方法
		// 简单理解的话就是下面是一个类 类中类好吧 所以把它按照一般类对待就好啦
		JPanel jp = new JPanel() {
			// 下面的就是构造器了 因为 new 了一个没有具体类名的匿名类，所以构造器也没有名字
			{
			}
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		// 设置布局管理器
		jp.setLayout(null);
		for (int i = 0; i < keyText.length; i++) {
			jp.add(keyText[i]);
		}
		return jp;
	}
	/**
	 * 玩家皮肤面板
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Component createSkinPanel(){
		// 初始化面板   传入参量边界布局
		JPanel panel = new JPanel(new BorderLayout());
		// 通过文件夹路径访问到文件
		File dir = new File(Img.GRAPHICS_PATH);
		// 上面访问的路径里包含的所有皮肤包名通过列表显示
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		// 把皮肤包名添加到皮肤列表面板里
		for(int i=0; i<files.length; i++){
			// 增加选项
			this.skinData.addElement(files[i].getName());
			// 增加预览图（传入预览图路径）
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "\\eva.png").getImage();
		}
		// 将皮肤的内容添加到列表中
		this.skinList = new JList(skinData);
		// 设置默认选中第一个  也就是default
		this.skinList.setSelectedIndex(0);
		// 增加鼠标监听  点到哪一个皮肤包名就显示出来相应的图片  （鼠标适配器  MouseAdapter() 已经实现了所有动作）
		this.skinList.addMouseListener(new MouseAdapter() {
			@Override
			// 我们这里让鼠标松开的时候只做一个刷新动作   就可以把略缩图切换成功
			public void mouseReleased(MouseEvent e) {
				repaint();
			}
		});
		
		// 继承类   
		this.skinView = new JPanel() {
			// 这里在创建皮肤预览图对象的时候就打印一张图片作为参量进入显示在面板上
			@Override
			public void paintComponent(Graphics g) {
//				// 下面的方法是略缩图片居中的方法  
//				Image showImg = skinViewList[skinList.getSelectedIndex()];
//				int x = this.getWidth() - showImg.getWidth(null) >> 1;
//				int y = this.getHeight() - showImg.getHeight(null) >> 1;
//		        g.drawImage(showImg, x, y, null);
				// 选中哪一个皮肤包名就把相应图片打印出来
				g.drawImage(skinViewList[skinList.getSelectedIndex()], 0, 0, null);
			}
		};
		
		// 面板上添加皮肤列表  边界布局到西边   
		// 进入的皮肤列表参量被一个滚动条包装  这样会更加好看一些
		panel.add(new JScrollPane(this.skinList), BorderLayout.WEST);
		// 添加皮肤预览图到面板上
		panel.add(this.skinView, BorderLayout.CENTER);
		return panel;
	}


	/**
	 * 创建按钮面板
	 */
	private JPanel createButtonPanel() {
		//创建按钮面板  流式布局（右对齐）
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		/**
		 * "确定"按钮发生事件
		 * 给按钮添加事件监听（匿名类/内部类）
		 * 注意点:内部类不能写 this
		 */
		this.btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//按"确定"的时候调用下面的方法（前提 writeConfig 要成功）
				if (writeConfig()) {
					okButtonEvent();
					//刷新画面的方法
					gameControl.setOver();
				}
			}
		});
		//给用户的错误提示
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOK);
		/**
		 * "取消"按钮发生事件
		 */
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//按"取消"的时候调用下面的方法
				cancelButtonEvent();
				//刷新画面的方法
				gameControl.setOver();
			}
		});
		jp.add(this.btnCancel);
		/**
		 * "应用"按钮发生事件
		 */
		this.btnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 同上这里是点击"应用"的时候调用发生的事件
				writeConfig();
				// "应用"后刷新一下模拟器的窗口
				gameControl.repaint();
			}
		});
		jp.add(this.btnUser);
		return jp;
	}
	
	/**
	 * 点击"确定"按钮所调用的事件  
	 * 之所以和"应用"分开写是因为点击"确定"按钮后需要关闭窗口
	 */
	private void okButtonEvent(){
		this.writeConfig();
		this.setVisible(false);
	}
	/**
	 * 点击"取消"按钮所调用事件
	 * 这里只是关闭窗口，并非结束程序
	 */
	private void cancelButtonEvent(){
		this.setVisible(false);
	}
	/**
	 * 点击"应用"按钮所调用的事件
	 * （写入游戏配置）
	 */
	private boolean writeConfig(){
		// 创建映射对象
		HashMap<Integer,String> keySet = new HashMap<Integer,String>();
		// 写入配置
		for (int i = 0; i < keyText.length; i++) {
			int keyCode = this.keyText[i].getKeyCode();
			// keCode 为 0 的时候返回为 false
			if(keyCode == 0){
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		// 如果没有这条判断当用户输入两个相同 keyCode 键位的时候，后面的键位对应的 String 会覆盖前面的键位对应的 String
		if(keySet.size() != 8){
			this.errorMsg.setText("按键必须不同");
			return false;
		}
		try {
			/**
			 * 注意  这里很重要   以后更新的皮肤包在这里可以选择更改
			 * 切换皮肤 （括号里的参数表示上面的  getSelectedIndex 选到了第几个皮肤）
			 */
			Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString() + "/");
//			this.skinData.get(this.skinList.getSelectedIndex());
			// 写入控制配置
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;
	}
	
	
	
}
