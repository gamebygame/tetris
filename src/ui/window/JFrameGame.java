package ui.window;

import javax.swing.JFrame;
import util.FrameUtil;
import config.FrameConfig;
import config.GameConfig;


@SuppressWarnings("serial")
public class JFrameGame extends JFrame{
	
	
	public JFrameGame(JPanelGame panelGame){
		/**
		 * 取得 xml 配置文件中 game 子元素 frame 的      标题    窗口宽度    窗口高度      配置 
		 * （已经在 GameConfig 创建了所有配置类的 get 方法，直接调用该方法取得配置好的参数值）
		 */
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//设置标题
		this.setTitle(fCfg.getTitle());
		//设置默认关闭属性（程序结束）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(fCfg.getWidth(),fCfg.getHeight());
		//不允许用户改变窗口大小
		this.setResizable(false);
		//居中
		FrameUtil.setFrameCenter(this);
		//设置默认Panel
        this.setContentPane(panelGame);
        //设置窗口可见
        this.setVisible(true);
	}
	
	
}
