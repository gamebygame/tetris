package util;


public class GameFunction {

	/**
	 * 计算线程睡眠时间
	 */
	public static long getSleepTimeByLevel(int level){
		long sleep = (-200*level + 1000);
		sleep = sleep<100 ? 100 : sleep;
		return sleep;
	}
	
}
