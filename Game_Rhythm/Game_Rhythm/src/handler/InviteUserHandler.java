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
			// 더블클릭하면
			String str = ((String) i.userList.getSelectedValue());
			// 리스트 str로 받아서
			System.out.println(str);
			String data = str.substring(1);
			// data에 첫번째값 받아서 "["다음꺼 받고
		
			String[] result = data.split("]");
			// ] 빼주고
			// 결국 nick이 result에 저장되고
			System.out.println(result[0]);
			ClientUI.net.sendinviteRequest(result[0]);
			// 수행
			i.dispose();
		}
	}
}
