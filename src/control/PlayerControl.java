package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class PlayerControl extends KeyAdapter {
	
	
	private GameControl gameControl;
	//构造方法
	public PlayerControl(GameControl ctrl){
		this.gameControl=ctrl;
	}
	
	/**
	 * 键盘按下会触发的事件
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		this.gameControl.actionByKeyCode(e.getKeyCode());
	}
	
	
}
		
	

	
