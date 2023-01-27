package handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import client.ClientUI;
import clientPanel.MessageSendFrame;

public class BtListSendMessageHandler extends MouseAdapter {
	ClientUI ui;
	public BtListSendMessageHandler(ClientUI c){
		ui = c;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			String str = (String) ui.pnRoom.userList.getSelectedValue();
			String[] data = str.split(" ");
			if(data[0].equals(ui.pnRoom.pnInfo.lbId.getText())) {
				JOptionPane.showMessageDialog(ui, "자기 자신에게는 보낼 수 없습니다.");
				return;
			}
			MessageSendFrame m = new MessageSendFrame();
			m.tfId.setText(data[0].substring(1, data[0].length()-1));
			m.setVisible(true);
		}
	}
}
