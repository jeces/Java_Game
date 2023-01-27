package handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Common.Message;
import client.ClientUI;
import clientPanel.MessageListFrame;
import clientPanel.ShowMessageFrame;

public class ShowMessageHandler extends MouseAdapter {
	MessageListFrame target;
	public ShowMessageHandler(MessageListFrame m) {
		target = m;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount() == 2) {
			// 클릭 수가 2면[더블클릭]
			String str = ((String) target.messageList.getSelectedValue()).trim();
			// str에 선택된 값을 저장
			System.out.println(str);
			
			int idx = target.messageList.getSelectedIndex();
			// 선택된 index값 idx에 저장 순번
			String result = str.substring(5,7);
			// 수신, 발신인지
			System.out.println(result);
			ShowMessageFrame s = new ShowMessageFrame();
			// ShowMessageFrame 생성
			
			
			System.out.println("11");
			ClientUI.net.sendCheckReadMessageRequest(idx);
			// idx를 넣어서 수행
			ClientUI.net.sendShowMessageListRequest();
			// 메세지 목록 요청
			Message m = ClientUI.net.sendShowMessageRequest(idx);
			// 읽을 메세지 불러옴
			s.setVisible(true);
			// ShowMessageFrame 띄워주고
			System.out.println("호출");
			if(result.equals("수신")) {
				// 수신이라면
				s.lbText.setText("발신자 : ");
				// 발신자라고 띄워주고
			} else {
				// 발신이라면
				s.lbText.setText("수신자 : ");
				// 수신자라고 띄워줌
			}
			
			System.out.println("22");
			s.tfId.setText(m.getName());
			s.tfMessage.setText(m.getMent());
			
		}
	}
}
