package client;

import javax.swing.JOptionPane;

public class ClientStart {
	public static void main(String[] args) {
		String ip = JOptionPane.showInputDialog("서버 ip 입력하세요.");
		ClientUI b = new ClientUI(ip);
		b.setLocationRelativeTo(null);
		b.setVisible(true);
	}
}