package game;

public class Beat {

	private String noteName;
	private int time;
	public Beat(String noteName, int time) {
		this.noteName = noteName;
		this.time = time;
	}
	public String getNoteName() {
		return noteName;
	}
	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
}
