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
		// 방제목 title에 저장
		String pw = "";
		// pw 없음
		if(target.rbOneUser.isSelected()) {
			// 1인용 선택
			target.ui.net.sendCreateRoomRequest(title, pw, false);
			// 타이틀과 pw와 false를 보내준다.
			target.setVisible(false);
			// 방만들기 선택화면 지워주고
		} else {
			// 2인용 선택
			if(target.tfPw.isEditable() ) {
				// 비밀번호방이면 들어온다.
				if(String.valueOf(target.tfPw.getPassword()).equals("")) {
					// 비밀번호가 입력되어있지 않으면 들어온다.
					JOptionPane.showMessageDialog(target, "비밀번호를 입력해주세요.");
					// JOption 띄우고
					return;
					// 메소드 처음으로
				} else {
					// 비밀번호 입력했으면
					pw = String.valueOf(target.tfPw.getPassword()).trim();
					// pw에 비밀번호 저장 String 값으로
				}
			}
			target.ui.net.sendCreateRoomRequest(title, pw, true);
			// 타이틀과 pw와 true를 보내준다.
			target.setVisible(false);
			// 방만들기 선택화면 지워주고
		}
	}
	
	
}
