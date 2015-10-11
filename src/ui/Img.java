package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import config.GameConfig;


/**
 * 这里面全是图片
 * 
 * @author J
 */
public class Img {
	
	
	
	private Img(){}
	
	
	/**
	 * 图片路径 
	 */
	public static final String GRAPHICS_PATH = "Graphics/";
	private static final String DEFAULT_PATH = "default/";
	
	
	/**
	 * 这么设计路径为一个变量的想法在于后期可以随时改变皮肤   实现方法便是改换路径
	 * @param path
	 */
	public static void setSkin(String path){
		String skinPath = GRAPHICS_PATH + path;
		
		// 个人签名
		SIGN = new ImageIcon(skinPath + "" + "string/zuozhe.png").getImage();
		// 各个模块窗体
		WINDOW = new ImageIcon(skinPath + "window/Windows.png").getImage();
		// 值槽
		RECT = new ImageIcon(skinPath + "window/rect.png").getImage();
		// "0~9"数字图片     260 36
		NUMBER = new ImageIcon(skinPath + "string/num.png").getImage();
		// 窗口标题  数据库
		DB = new ImageIcon(skinPath + "string/shujuku.png").getImage();
		// 窗口标题  本地记录
		DISK = new ImageIcon(skinPath + "string/bendijilu.png").getImage();
		// 取得有9个小方块的图片（一横排）
		ACT = new ImageIcon(skinPath + "game/rects.png").getImage();
		// 窗口标题  等级
		LEVEl = new ImageIcon(skinPath + "string/dengji.png").getImage();
		// 窗口标题  分数
		POINT = new ImageIcon(skinPath + "string/fenshu.png").getImage();
		// 窗口标题  消行
		RMLINE = new ImageIcon(skinPath + "string/xiaohang.png").getImage();
		// 阴影（1像素）
		SHADOW = new ImageIcon(skinPath + "game/shadows.png").getImage();
		
		// "开始"按钮   创建 Button 对象的时候返回类名为 ImageIcon ，所以后面不用 getImage（）
		BTN_START = new ImageIcon(skinPath + "string/kaishi1.png");
		// "设置"按钮  这里同上
		BTN_CONFIG = new ImageIcon(skinPath + "string/shezhi1.png");
		// 暂停
		PAUSE = new ImageIcon(skinPath + "string/zanting.png").getImage();
		
		// 从配置文件中拿到数组长度参数
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		// 下一个方块图片
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon(skinPath + "game/"+ i +".png").getImage();
		}
		
		// 背景图片数组
		// 获得文件夹
		File dir = new File(skinPath + "background");
		// 文件数组
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File file : files) {
			// 把该文件里文件夹都剔除掉，只显示图片
			if(!file.isDirectory()){
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
	    }
		
    }
	
	
	/**
	 * 个人签名
	 */
	public static Image SIGN = null;
	/**
	 * 各个模块窗体
	 */
	public static Image WINDOW = null;
	/**
	 * 值槽
	 */
	public static Image RECT = null;
	/**
	 * "0~9"数字图片     260 36
	 */
	public static Image NUMBER = null;
	/**
	 * 窗口标题  数据库
	 */
	public static Image DB = null;
	/**
	 * 窗口标题  本地记录
	 */
	public static Image DISK = null;
	/**
	 * 取得有9个小方块的图片（一横排）
	 */
	public static Image ACT = null;
	/**
	 * 窗口标题  等级
	 */
	public static Image LEVEl = null;
	/**
	 * 窗口标题  分数
	 */
	public static Image POINT = null;
	/**
	 * 窗口标题  消行
	 */
	public static Image RMLINE = null;
	/**
	 * 阴影（1像素）
	 */
	public static Image SHADOW = null;
	/**
	 * "开始"按钮
	 * 创建 Button 对象的时候返回类名为 ImageIcon ，所以后面不用 getImage（）
	 */
	public static ImageIcon BTN_START = null;
	/**
	 * "设置"按钮
	 * 这里同上
	 */
	public static ImageIcon BTN_CONFIG = null;
	/**
	 * 暂停
	 */
	public static Image PAUSE = null;

	/**
	 * 下一个图片数组
	 */
	public static Image[] NEXT_ACT = null;
	
	/**
	 * 背景
	 */
	public static List<Image> BG_LIST = null;
	
	
	/**
	 * 初始化图片
	 */
	static {
		setSkin(DEFAULT_PATH);
	}
	
	
	
}
