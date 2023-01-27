package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ClientUI;
import clientPanel.MusicChoicePanel;

public class BtTextInputHandler implements ActionListener{
	MusicChoicePanel target;
	public BtTextInputHandler(MusicChoicePanel musicChoicePanel) {
		target = musicChoicePanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String txt = target.ui.pnMusicChoice.txInput.getText();
		target.ui.net.sendChatRequest(txt);
		target.ui.pnMusicChoice.scrollPane.getVerticalScrollBar().setValue(target.ui.pnMusicChoice.scrollPane.getVerticalScrollBar().getMaximum());
	}
	
	
}
