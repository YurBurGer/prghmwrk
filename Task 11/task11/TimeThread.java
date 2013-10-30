package task11;

public class TimeThread extends Thread{
	private volatile boolean isWork=true, isStop=true;
	private final int wt=25*60*1000,rt=5*60*1000;
	private long db,de;
	private volatile long sht;
	
	public boolean isWork() {
		return isWork;
	}

	public void setWork(boolean isWork) {
		this.isWork = isWork;
	}

	public long getSht() {
		return sht/1000;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public TimeThread() {
		super();
		this.setDaemon(true);
	}

	public void run(){
		while(true){
			db=System.currentTimeMillis();
			while(!isStop){
				de=System.currentTimeMillis();
				if(isWork){
					sht=wt-(de-db);
					if(sht<=0){
						isWork=false;
						db=System.currentTimeMillis();
					}
				}
				else{
					sht=rt-(de-db);
					if(sht<=0){
						isWork=true;
						db=System.currentTimeMillis();
					}
				}
			}
			sht=0;
			while(isStop);
		}
	}
}
