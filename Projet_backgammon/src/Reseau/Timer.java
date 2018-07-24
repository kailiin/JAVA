package Reseau;

public class Timer extends Thread{
	public HandlePartie hp; 
	
	public Timer(HandlePartie hp){
		this.hp = hp;
	}
	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(100);
				synchronized(hp){
				if(!hp.update(0.1f))
					return;
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}

}
