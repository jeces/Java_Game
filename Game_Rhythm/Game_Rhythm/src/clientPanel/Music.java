package clientPanel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {
	public Player player;
	public boolean isLoop;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	InputStream is;
	String name;
	public Music(String name, boolean isLoop) {
		
		try {
			this.isLoop = isLoop;
			// file = new File(getClass().getClassLoader().getResource("music/" + name).toURI());
			//file = new File(ClassLoader.getSystemResource("music/"+name).toURI());
		//	System.out.println("?? " + file.getAbsolutePath());
		//	fis = new FileInputStream(file);
			
			is =getClass().getClassLoader().getResourceAsStream("music/" + name);
			System.out.println(is);
		//	bis = new BufferedInputStream(is);
			player = new Player(is);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("run? ?" + e.getMessage());
			e.printStackTrace();
		}
		this.name = name;
	}

	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}

	public void close() {
		isLoop = false;
		player.close();
		interrupt();
	}
	

	@Override
	public void run() {
		try {
			do {
				player.play();
			//	fis = new FileInputStream(file);
				is =getClass().getClassLoader().getResourceAsStream("music/" + name);
				bis = new BufferedInputStream(is);
				player = new Player(is);
			} while (isLoop);
		
		} catch (Exception e) {
			close();
			System.out.println("run? ?" + e.getMessage());
			e.printStackTrace();
		}
	}
}
