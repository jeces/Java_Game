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
			// Ŭ�� ���� 2��[����Ŭ��]
			String str = ((String) target.messageList.getSelectedValue()).trim();
			// str�� ���õ� ���� ����
			System.out.println(str);
			
			int idx = target.messageList.getSelectedIndex();
			// ���õ� index�� idx�� ���� ����
			String result = str.substring(5,7);
			// ����, �߽�����
			System.out.println(result);
			ShowMessageFrame s = new ShowMessageFrame();
			// ShowMessageFrame ����
			
			
			System.out.println("11");
			ClientUI.net.sendCheckReadMessageRequest(idx);
			// idx�� �־ ����
			ClientUI.net.sendShowMessageListRequest();
			// �޼��� ��� ��û
			Message m = ClientUI.net.sendShowMessageRequest(idx);
			// ���� �޼��� �ҷ���
			s.setVisible(true);
			// ShowMessageFrame ����ְ�
			System.out.println("ȣ��");
			if(result.equals("����")) {
				// �����̶��
				s.lbText.setText("�߽��� : ");
				// �߽��ڶ�� ����ְ�
			} else {
				// �߽��̶��
				s.lbText.setText("������ : ");
				// �����ڶ�� �����
			}
			
			System.out.println("22");
			s.tfId.setText(m.getName());
			s.tfMessage.setText(m.getMent());
			
		}
	}
}
