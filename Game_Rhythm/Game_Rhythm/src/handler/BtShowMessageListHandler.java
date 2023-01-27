package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.MessageListFrame;

public class BtShowMessageListHandler implements ActionListener {
	ClientUI target;
	public BtShowMessageListHandler(ClientUI c) {
		target = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		target.m.setLocationRelativeTo(null);
		// 가운데 설정
		target.m.setVisible(true);
		// 메세지 리스트 띄움
		ClientUI.net.sendShowMessageListRequest();
		// sendShowMessageListRequest 수행, 버튼 누를 때마다 메세지 리스트 갱신
	}
	
	
}
