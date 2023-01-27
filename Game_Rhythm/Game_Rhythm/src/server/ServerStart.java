package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStart {
	public static void 	main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(56789);
			System.out.println("서버시작");
			while(!server.isClosed()) {
				Socket socket = server.accept();
				Thread p = new PersonalServer(socket);
				p.start();
				System.out.println("서버 접속");
			}
			server.close();
		}catch(IOException e) {
			System.out.println("[server] network error "  + e.toString()); 
		}
	}
}