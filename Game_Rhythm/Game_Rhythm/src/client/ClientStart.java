package client;

import javax.swing.JOptionPane;

public class ClientStart {
	public static void main(String[] args) {
		String ip = JOptionPane.showInputDialog("���� ip �Է��ϼ���.");
		ClientUI b = new ClientUI(ip);
		b.setLocationRelativeTo(null);
		b.setVisible(true);
	}
}