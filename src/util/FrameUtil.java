package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


/**
 * 获取本机窗口大小并使要显示的窗口居中方法
 * x y 参量是主窗口边框 frame 的左上角起始坐标
 */
public class FrameUtil {
	
	public static void setFrameCenter(JFrame jf){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		//为显示至中间位置使用本机长宽减去Frame长宽除以2得到位移距离
		int x = screen.width - jf.getWidth()>>1;
		int y = screen.height - jf.getHeight()>>1 - 32;
		jf.setLocation(x,y);
	}

}
