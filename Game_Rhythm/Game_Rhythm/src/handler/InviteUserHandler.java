package handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.ClientUI;
import clientPanel.InviteUsetListFrame;
import clientPanel.ShowMessageFrame;

public class InviteUserHandler extends MouseAdapter{
	InviteUsetListFrame i;
	public InviteUserHandler(InviteUsetListFrame i) {
		// TODO Auto-generated constructor stub
		this.i = i;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			// ����Ŭ���ϸ�
			String str = ((String) i.userList.getSelectedValue());
			// ����Ʈ str�� �޾Ƽ�
			System.out.println(str);
			String data = str.substring(1);
			// data�� ù��°�� �޾Ƽ� "["������ �ް�
		
			String[] result = data.split("]");
			// ] ���ְ�
			// �ᱹ nick�� result�� ����ǰ�
			System.out.println(result[0]);
			ClientUI.net.sendinviteRequest(result[0]);
			// ����
			i.dispose();
		}
	}
}
