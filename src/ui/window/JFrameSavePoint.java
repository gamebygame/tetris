package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import control.GameControl;
import util.FrameUtil;


@SuppressWarnings("serial")
public class JFrameSavePoint extends JFrame{
	
	
	
	/**
	 * 声明变量
	 */
	private JLabel LbPoint = null;
	private JLabel errMsg = null;
	private JTextField txName= null;
	private JButton btnOK = null;
	// 我们这里当然需要本类（也就是分数添加保存窗口）和控制器类建立关系  现在在这里声明变量吧
	private GameControl gameControl = null;
	
	
	/**
	 * 构造器
	 */
	public JFrameSavePoint(GameControl gameControl){
		// 初始化送进来的控制器参量
		this.gameControl = gameControl;
		this.setTitle("保存记录");
		this.setSize(300, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		// 调用下面的各部分组建
		this.createCom();
		this.createAction();
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	/**
	 * 显示窗口
	 */
	public void showWindow(int point){
		this.LbPoint.setText("您的本次得分为: " + point);
		this.setVisible(true);
	}
	
	
	/**
	 * 创建该保存记录窗口的事件监听
	 */
	private void createAction() {
		this.btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 给输入姓名加一个输入字符串长度的判断   当然还可以加入其他名字输入限制验证
				String name = txName.getText();
				if(name.length()>16 || name==null || "".equals(name)){
					errMsg.setText("你的名字..  输入的没问题么");
				}else{
					// 把分数保存起来之前先把记录分数的对话框关闭
					setVisible(false);
					gameControl.savePoint(name);
				}
			}
		});
	}
	
	
	/**
	 * 该方法把整个窗体所用到的面板和组件都创建并组装
	 */
	private void createCom() {
		/**
		 * 北部面板
		 */
		// 创建北部面板对象 传入参数为流式布局（这里我们需要左对齐）
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// 初始化分数文字
		this.LbPoint = new JLabel();
		// 北部面板上组装标签
		north.add(this.LbPoint);
		// 初始化错误信息控件
		this.errMsg = new JLabel();
		// 设置前景色
		this.errMsg.setForeground(Color.red);
		// 添加错误信息到北部面板
		north.add(this.errMsg);
		// 大窗体上再组装北部面板 面板整体边界布局到北部
		this.add(north, BorderLayout.NORTH);
		/**
		 * 中间面板
		 */
		// 创建中间面板对象 传入参数为流式布局（这里我们需要左对齐）
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// 初始化输入文本框对象
		this.txName = new JTextField(12);
		// 中间面板上组装标签（）
		center.add(new JLabel("请输入您的大名吧: "));
		// 中间面板上组装输入框
		center.add(this.txName);
		// 大窗体上再组装中间面板
		this.add(center, BorderLayout.CENTER);
		/**
		 * 南部面板
		 */
		// 初始化按钮对象
		this.btnOK = new JButton("确定");
		// 创建南部面板对象  传入参数为流式布局（这里我们需要按钮居中对齐）
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// 南部面板上面组装按钮 
		south.add(btnOK);
		// 大窗体上面再组装面板   面板整体边界布局到南边填充窗体
		this.add(south, BorderLayout.SOUTH);
	}
	


}
