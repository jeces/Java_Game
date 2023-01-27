package game;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import client.ClientUI;

public class Note extends Thread{
   private Image noteBasicImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteBasic.png")).getImage();
   private int x;
   int y= -150;
   private String noteType;
   
   private boolean proceeded =true;
   
   public boolean isProceeded() {
      return proceeded;
   }
   public void close() {
      proceeded=false;
   }
   public String getNoteType() {
      return noteType;
   }
   public Note(String noteType) {
      this.noteType = noteType;
      if(noteType.equals("S")) {
         x=210;
      }else if(noteType.equals("D")) {
         x=314;
      }else if(noteType.equals("F")) {
         x=418;
      }else if(noteType.equals("Space")) {
         x=522;
      }else if(noteType.equals("J")) {
         x=726;
      }else if(noteType.equals("K")) {
         x=830;
      }else if(noteType.equals("L")) {
         x=934;
      }
   
      
   }
   public void screenDraw(Graphics2D g) {
      if(!noteType.equals("Space")) {
         g.drawImage(noteBasicImage, x, y, null);
      }else {
         g.drawImage(noteBasicImage, x, y, null);
         g.drawImage(noteBasicImage, x+100, y, null);
      }
   }
   @Override
   public void run() {
      try {
         while(true) {
            drop();
            if(proceeded) {
               Thread.sleep(ClientUI.NOTE_SLEEP);
            }else {
               interrupt();
               break;
            }
         }
      }catch(Exception e) {
         System.out.println(e.getMessage());
      }
   }
   
   public void drop() {
      y  += ClientUI.NOTE_SPEED;
      if(y>730) {
         System.out.println("miss");
         close();
      }
   }
   public String judge() {
      if(y>710) {
         System.out.println("late");
         GameFrame.game.score += 100;
         ClientUI.net.sendScoreRequest(GameFrame.game.score);
         close();
         return "late";
      }else if(y>650) {
         System.out.println("perfect");
         GameFrame.game.score += 1000;
         ClientUI.net.sendScoreRequest(GameFrame.game.score);
         close();
         return "perfect";
      }else if(y>630) {
         System.out.println("great");
         GameFrame.game.score += 500;
         ClientUI.net.sendScoreRequest(GameFrame.game.score);
         close();
         return "great";
      }else if(y>600) {
         System.out.println("good");
         GameFrame.game.score += 300;
         ClientUI.net.sendScoreRequest(GameFrame.game.score);
         close();
         return "good";
      }else if(y>400){
         GameFrame.game.score += 50;
         System.out.println("early");
         ClientUI.net.sendScoreRequest(GameFrame.game.score);
         close();
         return "early";
      }else {
         System.out.println("none");
         return "none";
      }
   }
   public int getY() {
      return y;
   }
   
}