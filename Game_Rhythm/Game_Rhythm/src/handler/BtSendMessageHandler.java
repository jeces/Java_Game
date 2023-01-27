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
		// 더블클릭 시 id 받아와서 name에 저장
		String ment = m.tfMessage.getText();
		// 쓴 글 ment에 저장
		if(ment.equals("")) {
			// ment가 비어있으면
			JOptionPane.showMessageDialog(m, "보내실 메시지를 입력하세요.");
			// ment 입력하라고 넣고
			return;
		}
		ClientUI.net.sendMessageRequest(name, ment);
		// 보냄
		m.dispose();
		// 프레임 닫음
	}
	
}
