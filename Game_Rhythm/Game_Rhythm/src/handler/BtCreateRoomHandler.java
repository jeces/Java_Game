package handler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;

import client.ClientUI;
import clientPanel.CreateRoomFrame;
import clientPanel.SelectPanel;

public class BtCreateRoomHandler implements ActionListener {
	ClientUI ui;
	public BtCreateRoomHandler(ClientUI c) {
		ui = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		CreateRoomFrame r = new CreateRoomFrame(ui);
		String [] titles = new String[] { "페어플레이합시다.", "매너게임","일단 들어오세요.","스피드 한게임"};
		r.tfTitle.setText(titles[(int)(Math.random()*titles.length)]);
		r.setVisible(true);

		r.setLocationRelativeTo(null);
	}
	
	
}
