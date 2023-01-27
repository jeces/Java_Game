package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.ClientUI;
import clientPanel.MessageSendFrame;

public class BtSendMessageHandler implements ActionListener{
	MessageSendFrame m;
	public BtSendMessageHandler(MessageSendFrame m) {
		// TODO Auto-generated constructor stub
		this.m = m;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name = m.tfId.getText().trim();
		// ����Ŭ�� �� id �޾ƿͼ� name�� ����
		String ment = m.tfMessage.getText();
		// �� �� ment�� ����
		if(ment.equals("")) {
			// ment�� ���������
			JOptionPane.showMessageDialog(m, "������ �޽����� �Է��ϼ���.");
			// ment �Է��϶�� �ְ�
			return;
		}
		ClientUI.net.sendMessageRequest(name, ment);
		// ����
		m.dispose();
		// ������ ����
	}
	
}
