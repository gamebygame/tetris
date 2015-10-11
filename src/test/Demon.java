package test;

public class Demon {
	public static void main(String[] args){
		犯人 A=new 抢劫犯("萨达姆");
		犯人 B=new 诈骗犯("小布什");
		//强制声明
		抢劫犯 C=(抢劫犯)A;
		C.dosomething();
		//强制类型转换
		int a=(int)2.25;
	}

}


class 犯人{
	protected String name;	
	public 犯人(String name){
		this.name=name;	
	}	
	public void dosomething(){
		System.out.println("我叫" +this.name+ "我是一个犯人" );
	}
}

class 抢劫犯 extends 犯人{
	public 抢劫犯(String name){
		super(name);
		}
		public void dosomething(){
			System.out.println("我叫" +this.name+ "我是一个抢劫犯");
		}	
}

class 诈骗犯 extends 犯人{
	public 诈骗犯(String name){
		super(name);
	}
	public void dosomething(){
		System.out.println("我叫" +this.name+ "我是一个诈骗犯");
	}
}
