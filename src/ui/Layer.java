package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;


/**
 * 绘制窗口
 * @param g 画笔
 * @author J
 */
public abstract class Layer {
	
	
	
	/**
	 * 内边距
	 */
	protected static final int PADDING;
	/**
	 * 边框宽度
	 */
	protected static final int BORDER;
	
	
	static{
		/**
		 * 取得 xml 配置文件中 game 子元素 frame 的     内边距   和   边框宽度      配置 
		 * （已经在 GameConfig 创建了所有配置类的 get 方法，直接调用该方法取得配置好的参数值）
		 */
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	
	
	/**
	 * 游戏8个窗体外框  宽度和高度
	 */
	private static int WINDOW_W = Img.WINDOW.getWidth(null);
	private static int WINDOW_H = Img.WINDOW.getHeight(null);
	/**
	 * 数字切片的宽度和高度
	 */
	protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null)/10;
	private static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);
	/**
	 * 值槽高度
	 */
	protected static final int IMG_RECT_H=15;
	/**
	 * 值槽宽度（含外框）
	 */
	private static final int IMG_RECT_W = Img.RECT.getWidth(null);
	/**
	 * 经验值槽宽度
	 */
	private final int RectW;
	/**
	 * "下一级"字体
	 */
	private static final Font DEF_FONT = new Font("黑体", Font.BOLD, 15);
	/**
	 * 变量x y w h
	 */
	/*窗口左上角x坐标*/
	protected final int x;
	/*窗口左上角y坐标*/
	protected final int y;
	/*窗口宽度*/
	protected final int w;
	/*窗口高度*/
	protected final int h;
	/*游戏数据*/
	protected GameDto dto=null;
	
	
	/**
	 * 构造方法初始化
	 */
	protected Layer(int x,int y,int w,int h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		RectW=this.w - PADDING*3;
	}
	
	
	/**
	 * 绘制窗口
	 * @param g 画笔对象
	 */
	protected void createWindow(Graphics g){
		/**
		 * 切片方法
		 * 前四个数值代表显示的位置坐标起末
		 * 后四个数值代表样本图片切片的坐标起末
		 */
		//左上
		g.drawImage(Img.WINDOW, x, y, x+BORDER, y+BORDER, 0, 0, BORDER, BORDER, null);
		//中上
		g.drawImage(Img.WINDOW, x+BORDER, y, x+w-BORDER, y+BORDER, BORDER, 0, WINDOW_W-BORDER, BORDER, null );
		//右上
		g.drawImage(Img.WINDOW, x+w-BORDER, y, x+w, y+BORDER, WINDOW_W-BORDER, 0, WINDOW_W, BORDER, null);
		//左中
		g.drawImage(Img.WINDOW, x, y+BORDER, x+BORDER, y+h-BORDER, 0, BORDER, BORDER, WINDOW_H-BORDER, null);
		//中
		g.drawImage(Img.WINDOW, x+BORDER, y+BORDER, x+w-BORDER, y+h-BORDER, BORDER, BORDER, WINDOW_W-BORDER, WINDOW_H-BORDER, null);
		//右中
		g.drawImage(Img.WINDOW, x+w-BORDER, y+BORDER, x+w, y+h-BORDER, WINDOW_W-BORDER, BORDER, WINDOW_W, WINDOW_H-BORDER, null);
		//左下
		g.drawImage(Img.WINDOW, x, y+h-BORDER, x+BORDER, y+h, 0, WINDOW_H-BORDER, BORDER, WINDOW_H, null);
		//中下
		g.drawImage(Img.WINDOW, x+BORDER, y+h-BORDER, x+w-BORDER, y+h, BORDER, WINDOW_H-BORDER, WINDOW_W-BORDER, WINDOW_H, null);
		//右下
		g.drawImage(Img.WINDOW, x+w-BORDER, y+h-BORDER, x+w, y+h, WINDOW_W-BORDER, WINDOW_H-BORDER, WINDOW_W, WINDOW_H, null);
	}

	
	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	
	
	/**
	 * 显示数字的方法
	 * 
	 * @param x 左上角x坐标
	 * @param y 左上角y坐标
	 * @param num 要显示的数字
	 * @param bitCount 数字位数
	 * @param g 画笔对象
	 */
	protected void drawNumberLeftPad(int x,int y,int num,int maxBit,Graphics g){
		//把要打印的数字转化为字符串
		String strNum = Integer.toString(num);
		//循环绘制数字
		for (int i = 0; i < maxBit; i++) {
			//判断是否满足绘制条件
			if(maxBit-i <= strNum.length()){
				//获得数字在字符串中的下标
				int idx = i-maxBit+strNum.length();
				//把数字中的每一位取出
				int bit = strNum.charAt(idx)-'0';
				//绘制数字
				g.drawImage(Img.NUMBER, 
						this.x+x+IMG_NUMBER_W*i, this.y+y, 
						this.x+x+IMG_NUMBER_W*(i+1), this.y+y+IMG_NUMBER_H, 
						bit * IMG_NUMBER_W, 0, 
						(bit+1)* IMG_NUMBER_W, IMG_NUMBER_H, 
						null);
			}
		}
	}
	
	
	/**
	 * 值槽（包含值槽着色宽度和颜色渐变）
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g){
		//将参与重复计算的值提取出来以供使用
		int rect_x=this.x+PADDING*2;
		int rect_y=this.y+y-PADDING;
		//绘制槽值（外边有两层框，详见框架中"值槽"截图）
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.RectW, IMG_RECT_H+4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x +1, rect_y +1, this.RectW-2, IMG_RECT_H+2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x +2, rect_y +2, this.RectW-4, IMG_RECT_H);
		/**
		 * 绘制值槽
		 */
		//求出值槽宽度
		int w=(int)((percent * (this.RectW-4)));
		//求出颜色（注意后面-1为了将超过记录的分数显示成为满格，如果不-1的话值槽会显示空）
		int subIdx=(int)(percent * IMG_RECT_W) -1;
		//画值槽啦！
		g.drawImage(Img.RECT,
				rect_x +2, rect_y +2, 
				rect_x +w+2, rect_y +2+IMG_RECT_H, 
				subIdx, 0,
				subIdx+1, IMG_RECT_H, 
				null);
		Color MY_COLOR = new Color(0xF5F5F5);
		g.setColor(MY_COLOR);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x+2, rect_y+15);
		if(number!=null){
			g.drawString(number, rect_x+165, rect_y+15);
		}
	} 
	
	
	/**
	 * 显示居中绘图方法
	 */
	protected void drawImageAtCenter(Image img,Graphics g){
		int imgW=img.getWidth(null);
		int imgH=img.getHeight(null);
		g.drawImage(img, this.x+(this.w-imgW>>1), this.y+(this.h-imgH>>1), null);
	}
	
	
	/**
	 * @author J
	 * @param g 画笔
	 * 刷新游戏具体内容   抽象类抽象方法意为让子类调用具体行为
	 * */
	abstract public void paint(Graphics g);
	
	
	
}