package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.ClientUI;
import clientPanel.CreateRoomFrame;

public class BtCreateRoomFinishHandler implements ActionListener {
	CreateRoomFrame target;
	public BtCreateRoomFinishHandler(CreateRoomFrame c) {
		target = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String title = target.tfTitle.getText().trim();
		// ������ title�� ����
		String pw = "";
		// pw ����
		if(target.rbOneUser.isSelected()) {
			// 1�ο� ����
			target.ui.net.sendCreateRoomRequest(title, pw, false);
			// Ÿ��Ʋ�� pw�� false�� �����ش�.
			target.setVisible(false);
			// �游��� ����ȭ�� �����ְ�
		} else {
			// 2�ο� ����
			if(target.tfPw.isEditable() ) {
				// ��й�ȣ���̸� ���´�.
				if(String.valueOf(target.tfPw.getPassword()).equals("")) {
					// ��й�ȣ�� �ԷµǾ����� ������ ���´�.
					JOptionPane.showMessageDialog(target, "��й�ȣ�� �Է����ּ���.");
					// JOption ����
					return;
					// �޼ҵ� ó������
				} else {
					// ��й�ȣ �Է�������
					pw = String.valueOf(target.tfPw.getPassword()).trim();
					// pw�� ��й�ȣ ���� String ������
				}
			}
			target.ui.net.sendCreateRoomRequest(title, pw, true);
			// Ÿ��Ʋ�� pw�� true�� �����ش�.
			target.setVisible(false);
			// �游��� ����ȭ�� �����ְ�
		}
	}
	
	
}
