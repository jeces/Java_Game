 package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import client.ClientUI;
import clientPanel.Music;

public class Game extends Thread {

	private Image gameInfoImage = new ImageIcon(getClass().getClassLoader().getResource("images/gameInfo.png")).getImage();
	private Image judgementLineImage = new ImageIcon(getClass().getClassLoader().getResource("images/judgementLine.png"))
			.getImage();
	private Image noteRouteSImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	private Image noteRouteDImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	private Image noteRouteFImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Image = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png"))
			.getImage();
	private Image noteRouteSpace2Image = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png"))
			.getImage();
	private Image noteRouteJImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	private Image noteRouteKImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	private Image noteRouteLImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();

	private Image noteRouteLineImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRouteLine.png"))
			.getImage();
	private Image judgeImage;
	private Image resultImage;

	private String titleName;
	private String musicTitle;
	private Music gameMusic;
	public int score = 0;
	public int vsScore = 0;
	ArrayList<Note> noteList = new ArrayList<>();
	public GameFrame g;
	public GameCounter t;
	private boolean isGame=false;
	public Game(String titleName, String musicTitle, GameFrame g) {
		this.g = g;
		this.titleName = titleName;
		this.musicTitle = musicTitle;
		gameMusic = new Music(musicTitle, false);
		t = new GameCounter(this);

	}

	@Override
	public void run() {
		dropNotes();
	}

	public void dropNotes() {
		Beat[] beats = null;
		if (titleName.equals("VIBE - 가을타나봐")) {
			int startTime = 1000;
			int gap = 120;
			beats = new Beat[] { new Beat("Space", startTime), new Beat("S", startTime + gap),
					new Beat("S", startTime + gap * 8), new Beat("Space", startTime + gap * 20),
					new Beat("K", startTime + gap * 30), new Beat("S", startTime + gap * 33),
					new Beat("F", startTime + gap * 35), new Beat("Space", startTime + gap * 39),
					new Beat("F", startTime + gap * 50), new Beat("J", startTime + gap * 51),
					new Beat("Space", startTime + gap * 55), new Beat("F", startTime + gap * 59),
					new Beat("J", startTime + gap * 71), new Beat("L", startTime + gap * 79),
					new Beat("J", startTime + gap * 80), new Beat("Space", startTime + gap * 85),
					new Beat("K", startTime + gap * 90), new Beat("L", startTime + gap * 94),
					new Beat("S", startTime + gap * 95), new Beat("L", startTime + gap * 102),
					new Beat("Space", startTime + gap * 103), new Beat("J", startTime + gap * 105),
					new Beat("K", startTime + gap * 110), new Beat("Space", startTime + gap * 119),
					new Beat("D", startTime + gap * 120), new Beat("F", startTime + gap * 127),
					new Beat("Space", startTime + gap * 135), new Beat("Space", startTime + gap * 155),
					new Beat("D", startTime + gap * 155), new Beat("K", startTime + gap * 156),
					new Beat("L", startTime + gap * 159), new Beat("J", startTime + gap * 160),
					new Beat("Space", startTime + gap * 167), new Beat("F", startTime + gap * 170),
					new Beat("Space", startTime + gap * 183), new Beat("L", startTime + gap * 185),
					new Beat("J", startTime + gap * 190), new Beat("J", startTime + gap * 193),
					new Beat("S", startTime + gap * 200), new Beat("S", startTime + gap * 205),
					new Beat("S", startTime + gap + gap * 208), new Beat("S", startTime + gap * 209),
					new Beat("L", startTime + gap * 212), new Beat("S", startTime + gap * 217),
					new Beat("Space", startTime + gap * 218), new Beat("Space", startTime + gap * 225),
					new Beat("Space", startTime + gap * 227), new Beat("J", startTime + gap * 235),
					new Beat("L", startTime + gap * 240), new Beat("F", startTime + gap * 242),
					new Beat("S", startTime + gap * 248), new Beat("K", startTime + gap * 252),
					new Beat("L", startTime + gap * 264), new Beat("Space", startTime + gap * 268),
					new Beat("S", startTime + gap * 270), new Beat("J", startTime + gap * 272),
					new Beat("K", startTime + gap * 275), new Beat("S", startTime + gap * 278),
					new Beat("Space", startTime + gap * 279), new Beat("F", startTime + gap * 282),
					new Beat("Space", startTime + gap * 290), new Beat("D", startTime + gap * 305),
					new Beat("D", startTime + gap * 310), new Beat("L", startTime + gap * 319),
					new Beat("J", startTime + gap * 320), new Beat("Space", startTime + gap * 325),
					new Beat("K", startTime + gap * 332), new Beat("L", startTime + gap * 334),
					new Beat("S", startTime + gap * 335), new Beat("S", startTime + gap * 338),
					new Beat("L", startTime + gap * 342), new Beat("Space", startTime + gap * 350),
					new Beat("J", startTime + gap * 355), new Beat("K", startTime + gap * 360),
					new Beat("S", startTime + gap * 363), new Beat("Space", startTime + gap * 365),
					new Beat("Space", startTime + gap * 369), new Beat("D", startTime + gap * 370),
					new Beat("Space", startTime + gap * 375), new Beat("J", startTime + gap * 376),
					new Beat("Space", startTime + gap * 377), new Beat("F", startTime + gap * 380),
					new Beat("F", startTime + gap * 383), new Beat("S", startTime + gap * 388),
					new Beat("Space", startTime + gap * 392), new Beat("Space", startTime + gap * 400),
					new Beat("J", startTime + gap * 405), new Beat("L", startTime + gap * 410),
					new Beat("S", startTime + gap * 413), new Beat("D", startTime + gap * 415),
					new Beat("L", startTime + gap * 419), new Beat("L", startTime + gap * 424),
					new Beat("S", startTime + gap * 425), new Beat("L", startTime + gap * 432),
					new Beat("Space", startTime + gap * 433), new Beat("J", startTime + gap * 435),
					new Beat("K", startTime + gap * 440), new Beat("Space", startTime + gap * 449),
					new Beat("D", startTime + gap * 450), new Beat("F", startTime + gap * 452),
					new Beat("Space", startTime + gap * 455), new Beat("K", startTime + gap * 456),
					new Beat("L", startTime + gap * 459), new Beat("J", startTime + gap * 460),
					new Beat("Space", startTime + gap * 467), new Beat("F", startTime + gap * 470),
					new Beat("Space", startTime + gap * 473), new Beat("L", startTime + gap * 475),
					new Beat("J", startTime + gap * 480), new Beat("J", startTime + gap * 493),
					new Beat("S", startTime + gap * 495), new Beat("S", startTime + gap + gap * 498),
					new Beat("S", startTime + gap * 509), new Beat("L", startTime + gap * 512),
					new Beat("S", startTime + gap * 517), new Beat("Space", startTime + gap * 518),
					new Beat("Space", startTime + gap * 525), new Beat("Space", startTime + gap * 527),
					new Beat("J", startTime + gap * 535), new Beat("L", startTime + gap * 540),
					new Beat("F", startTime + gap * 542), new Beat("S", startTime + gap * 548),
					new Beat("K", startTime + gap * 552), new Beat("L", startTime + gap * 564),
					new Beat("Space", startTime + gap * 568), new Beat("S", startTime + gap * 570),
					new Beat("J", startTime + gap * 572), new Beat("K", startTime + gap * 575),
					new Beat("S", startTime + gap * 578), new Beat("Space", startTime + gap * 579),
					new Beat("F", startTime + gap * 582), new Beat("Space", startTime + gap * 590),
					new Beat("D", startTime + gap * 595), new Beat("D", startTime + gap * 600),
					new Beat("L", startTime + gap * 609), new Beat("J", startTime + gap * 610),
					new Beat("Space", startTime + gap * 615), new Beat("K", startTime + gap * 617),
					new Beat("L", startTime + gap * 623), new Beat("Space", startTime + gap * 625),
					new Beat("K", startTime + gap * 626), new Beat("L", startTime + gap * 628),
					new Beat("Space", startTime + gap * 630), new Beat("S", startTime + gap * 640),
					new Beat("S", startTime + gap * 648), new Beat("F", startTime + gap * 652),
					new Beat("Space", startTime + gap * 660), new Beat("D", startTime + gap * 665),
					new Beat("K", startTime + gap * 670), new Beat("L", startTime + gap * 673),
					new Beat("Space", startTime + gap * 675), new Beat("L", startTime + gap * 679),
					new Beat("J", startTime + gap * 680), new Beat("K", startTime + gap * 685),
					new Beat("J", startTime + gap * 692), new Beat("Space", startTime + gap * 697),
					new Beat("F", startTime + gap * 702), new Beat("S", startTime + gap * 703),
					new Beat("F", startTime + gap * 708), new Beat("S", startTime + gap * 712),
					new Beat("Space", startTime + gap * 718), new Beat("L", startTime + gap * 725),
					new Beat("J", startTime + gap * 730), new Beat("J", startTime + gap * 733),
					new Beat("Space", startTime + gap * 735), new Beat("K", startTime + gap * 739),
					new Beat("S", startTime + gap * 740), new Beat("D", startTime + gap * 745),
					new Beat("S", startTime + gap * 746), new Beat("Space", startTime + gap * 747),
					new Beat("Space", startTime + gap * 748), new Beat("S", startTime + gap + gap * 754),
					new Beat("S", startTime + gap * 758), new Beat("L", startTime + gap * 762),
					new Beat("Space", startTime + gap * 770), new Beat("J", startTime + gap * 775),
					new Beat("K", startTime + gap * 780), new Beat("Space", startTime + gap * 783),
					new Beat("F", startTime + gap * 790), new Beat("F", startTime + gap * 793),
					new Beat("S", startTime + gap * 798), new Beat("F", startTime + gap * 808),
					new Beat("S", startTime + gap * 812), new Beat("Space", startTime + gap * 820),
					new Beat("L", startTime + gap * 825), new Beat("J", startTime + gap * 830),
					new Beat("J", startTime + gap * 833), new Beat("Space", startTime + gap * 835),
					new Beat("K", startTime + gap * 839), new Beat("S", startTime + gap * 840),
					new Beat("D", startTime + gap * 845), new Beat("S", startTime + gap * 846),
					new Beat("Space", startTime + gap * 847), new Beat("S", startTime + gap * 850),
					new Beat("D", startTime + gap * 855), new Beat("S", startTime + gap * 856),
					new Beat("Space", startTime + gap * 857), new Beat("L", startTime + gap * 860),
					new Beat("J", startTime + gap * 868), new Beat("J", startTime + gap * 892),
					new Beat("Space", startTime + gap * 900), new Beat("S", startTime + gap * 905),
					new Beat("K", startTime + gap * 908), new Beat("F", startTime + gap * 913),
					new Beat("Space", startTime + gap * 915), new Beat("F", startTime + gap * 919),
					new Beat("F", startTime + gap * 920), new Beat("L", startTime + gap * 925),
					new Beat("J", startTime + gap * 932), new Beat("Space", startTime + gap * 937),
					new Beat("D", startTime + gap * 942), new Beat("F", startTime + gap * 943),
					new Beat("Space", startTime + gap * 948), new Beat("S", startTime + gap * 952),
					new Beat("Space", startTime + gap * 958), new Beat("Space", startTime + gap * 965),
					new Beat("Space", startTime + gap * 967), new Beat("J", startTime + gap * 975),
					new Beat("L", startTime + gap * 980), new Beat("F", startTime + gap * 982),
					new Beat("S", startTime + gap * 988), new Beat("K", startTime + gap * 992),
					new Beat("L", startTime + gap * 994), new Beat("Space", startTime + gap * 998),
					new Beat("D", startTime + gap * 1005), new Beat("K", startTime + gap * 1010),
					new Beat("L", startTime + gap * 1013), new Beat("Space", startTime + gap * 1015),
					new Beat("L", startTime + gap * 1019), new Beat("J", startTime + gap * 1020),
					new Beat("K", startTime + gap * 1025), new Beat("J", startTime + gap * 1032),
					new Beat("Space", startTime + gap * 1037), new Beat("F", startTime + gap * 1042),
					new Beat("S", startTime + gap * 1043), new Beat("F", startTime + gap * 1048),
					new Beat("S", startTime + gap * 1052), new Beat("Space", startTime + gap * 1060),
					new Beat("L", startTime + gap * 1065), new Beat("J", startTime + gap * 1070),
					new Beat("J", startTime + gap * 1073), new Beat("Space", startTime + gap * 1075),
					new Beat("K", startTime + gap * 1079), new Beat("S", startTime + gap * 1080),
					new Beat("D", startTime + gap * 1085), new Beat("S", startTime + gap * 1086),
					new Beat("Space", startTime + gap * 1087), new Beat("L", startTime + gap * 1090),
					new Beat("J", startTime + gap * 1091), new Beat("J", startTime + gap * 1092),
					new Beat("Space", startTime + gap * 1110), new Beat("S", startTime + gap * 1115),
					new Beat("K", startTime + gap * 1120), new Beat("F", startTime + gap * 1123),
					new Beat("Space", startTime + gap * 1125), new Beat("F", startTime + gap * 1129),
					new Beat("F", startTime + gap * 1130), new Beat("L", startTime + gap * 1135),
					new Beat("J", startTime + gap * 1142), new Beat("Space", startTime + gap * 1147),
					new Beat("D", startTime + gap * 1152), new Beat("F", startTime + gap * 1153),
					new Beat("Space", startTime + gap * 1158), new Beat("S", startTime + gap * 1162),
					new Beat("Space", startTime + gap * 1170), new Beat("L", startTime + gap * 1175),
					new Beat("K", startTime + gap * 1180), new Beat("J", startTime + gap * 1183),
					new Beat("L", startTime + gap * 1185), new Beat("K", startTime + gap * 1189),
					new Beat("S", startTime + gap * 1190), new Beat("F", startTime + gap * 1195),
					new Beat("L", startTime + gap * 1198), new Beat("J", startTime + gap * 1200),
					new Beat("J", startTime + gap * 1203), new Beat("S", startTime + gap * 1210),
					new Beat("S", startTime + gap * 1215), new Beat("S", startTime + gap + gap * 1218),
					new Beat("S", startTime + gap * 1219), new Beat("L", startTime + gap * 1222),
					new Beat("S", startTime + gap * 1227), new Beat("Space", startTime + gap * 1228),
					new Beat("Space", startTime + gap * 1235), new Beat("Space", startTime + gap * 1237),
					new Beat("J", startTime + gap * 1245), new Beat("L", startTime + gap * 1250),
					new Beat("F", startTime + gap * 1252), new Beat("S", startTime + gap * 1258),
					new Beat("K", startTime + gap * 1262), new Beat("L", startTime + gap * 1264),
					new Beat("Space", startTime + gap * 1268), new Beat("S", startTime + gap * 1270),
					new Beat("J", startTime + gap * 1272), new Beat("K", startTime + gap * 1275),
					new Beat("S", startTime + gap * 1278), new Beat("Space", startTime + gap * 1279),
					new Beat("F", startTime + gap * 1282), new Beat("Space", startTime + gap * 1290),
					new Beat("D", startTime + gap * 1305), new Beat("D", startTime + gap * 1310),
					new Beat("L", startTime + gap * 1319), new Beat("J", startTime + gap * 1320),
					new Beat("Space", startTime + gap * 1325), new Beat("K", startTime + gap * 1332),
					new Beat("L", startTime + gap * 1334), new Beat("S", startTime + gap * 1335),
					new Beat("S", startTime + gap * 1338), new Beat("L", startTime + gap * 1342),
					new Beat("Space", startTime + gap * 1350), new Beat("J", startTime + gap * 1355),
					new Beat("K", startTime + gap * 1360), new Beat("S", startTime + gap * 1363),
					new Beat("Space", startTime + gap * 1365), new Beat("Space", startTime + gap * 1369),
					new Beat("D", startTime + gap * 1370), new Beat("Space", startTime + gap * 1375),
					new Beat("J", startTime + gap * 1376), new Beat("Space", startTime + gap * 1377),
					new Beat("F", startTime + gap * 1380), new Beat("F", startTime + gap * 1383),
					new Beat("S", startTime + gap * 1388), new Beat("Space", startTime + gap * 1392),
					new Beat("Space", startTime + gap * 1400), new Beat("Space", startTime + gap * 1402),
					new Beat("J", startTime + gap * 1405), new Beat("L", startTime + gap * 1410),
					new Beat("S", startTime + gap * 1413), new Beat("D", startTime + gap * 1415),
					new Beat("L", startTime + gap * 1419), new Beat("L", startTime + gap * 1424),
					new Beat("S", startTime + gap * 1425), new Beat("L", startTime + gap * 1432),
					new Beat("Space", startTime + gap * 1433), new Beat("J", startTime + gap * 1435),
					new Beat("K", startTime + gap * 1440), new Beat("Space", startTime + gap * 1449),
					new Beat("D", startTime + gap * 1450), new Beat("F", startTime + gap * 1452),
					new Beat("Space", startTime + gap * 1455), new Beat("K", startTime + gap * 1456),
					new Beat("L", startTime + gap * 1459), new Beat("J", startTime + gap * 1460),
					new Beat("Space", startTime + gap * 1467), new Beat("F", startTime + gap * 1470),
					new Beat("Space", startTime + gap * 1473), new Beat("L", startTime + gap * 1475),
					new Beat("J", startTime + gap * 1480), new Beat("J", startTime + gap * 1493),
					new Beat("S", startTime + gap * 1495), new Beat("S", startTime + gap + gap * 1498),
					new Beat("S", startTime + gap * 1509), new Beat("L", startTime + gap * 1512),
					new Beat("S", startTime + gap * 1517), new Beat("Space", startTime + gap * 1518),
					new Beat("Space", startTime + gap * 1525), new Beat("Space", startTime + gap * 1527),
					new Beat("J", startTime + gap * 1535), new Beat("L", startTime + gap * 1540),
					new Beat("F", startTime + gap * 1542), new Beat("S", startTime + gap * 1548),
					new Beat("K", startTime + gap * 1552), new Beat("L", startTime + gap * 1564),
					new Beat("Space", startTime + gap * 1568), new Beat("S", startTime + gap * 1570),
					new Beat("J", startTime + gap * 1572), new Beat("K", startTime + gap * 1575),
					new Beat("S", startTime + gap * 1578), new Beat("Space", startTime + gap * 1579),
					new Beat("F", startTime + gap * 1582), new Beat("Space", startTime + gap * 1590),
					new Beat("D", startTime + gap * 1595), new Beat("D", startTime + gap * 1600),

			};
		} else if (titleName.equals("버스커버스커 - 벚꽃엔딩")) {
			int startTime = 500;
			beats = new Beat[] { new Beat("S", startTime += 100), new Beat("D", startTime += 200),
					new Beat("F", startTime += 300), new Beat("J", startTime += 1100), new Beat("K", startTime += 150),
					new Beat("L", startTime += 300), new Beat("S", startTime += 1000), new Beat("D", startTime += 150),
					new Beat("F", startTime += 300), new Beat("J", startTime += 1000), new Beat("K", startTime += 150),
					new Beat("L", startTime += 200), new Beat("S", startTime += 1100), new Beat("D", startTime += 150),
					new Beat("F", startTime += 200),

					// 전주
					new Beat("S", startTime += 1200), new Beat("D", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 1000),

					new Beat("Space", startTime += 1000), new Beat("Space", startTime += 1000),

					new Beat("K", startTime += 1000), new Beat("L", startTime += 1000),
					new Beat("K", startTime += 1000), new Beat("L", startTime += 1000),
					new Beat("K", startTime += 1000), new Beat("Space", startTime += 1000),

					new Beat("S", startTime += 300), new Beat("D", startTime += 100), new Beat("F", startTime += 100),
					new Beat("J", startTime += 100), new Beat("K", startTime += 100), new Beat("L", startTime += 100),

					// 오늘은 우리 같이 걸어요 이 거리를
					new Beat("S", startTime += 800), new Beat("L", startTime += 200), new Beat("D", startTime += 250),
					new Beat("S", startTime += 500), new Beat("S", startTime += 500), new Beat("F", startTime += 500),
					new Beat("J", startTime += 500), new Beat("L", startTime += 500), new Beat("K", startTime += 500),
					new Beat("S", startTime += 1500), new Beat("L", startTime), new Beat("D", startTime += 500),
					new Beat("K", startTime), new Beat("S", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("S", startTime += 500), new Beat("J", startTime),
					new Beat("D", startTime += 500), new Beat("K", startTime),

					// 밤에 들려오는 자정 노래 어떤가요
					new Beat("Space", startTime += 500), new Beat("S", startTime += 650),
					new Beat("J", startTime += 500), new Beat("F", startTime += 500), new Beat("D", startTime += 500),

					new Beat("Space", startTime += 300),

					new Beat("S", startTime += 500), new Beat("D", startTime), new Beat("J", startTime += 500),
					new Beat("K", startTime),

					// 오 예
					new Beat("Space", startTime += 1000), new Beat("S", startTime += 800), new Beat("D", startTime),
					new Beat("F", startTime), new Beat("J", startTime += 50), new Beat("K", startTime),
					new Beat("L", startTime),

					// 몰랐던 그대와 단 둘이 손잡고
					new Beat("L", startTime += 2000), new Beat("F", startTime += 500), new Beat("S", startTime += 500),
					new Beat("K", startTime += 500), new Beat("Space", startTime += 500),
					new Beat("K", startTime += 500), new Beat("L", startTime += 500), new Beat("L", startTime += 1000),
					new Beat("F", startTime += 500), new Beat("S", startTime += 500), new Beat("K", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("K", startTime += 500),
					new Beat("L", startTime += 500),

					// 알 수 없는 이 떨림과 둘이 걸어요
					new Beat("L", startTime += 1000), new Beat("S", startTime), new Beat("F", startTime += 500),
					new Beat("S", startTime += 500), new Beat("K", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("K", startTime += 500),
					new Beat("L", startTime += 500), new Beat("L", startTime += 1000), new Beat("K", startTime),
					new Beat("F", startTime += 500), new Beat("S", startTime += 500), new Beat("K", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("S", startTime), new Beat("L", startTime),
					// 봄바람 휘날리며
					new Beat("L", startTime += 1000), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime),

					// 흩날리는 벛꽃잎이
					new Beat("L", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500),

					// 울려 퍼질 이거리를
					new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("D", startTime), new Beat("Space", startTime += 500),
					new Beat("K", startTime), new Beat("Space", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime += 500),
					new Beat("S", startTime += 500),

					// 둘이 걸어요
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("S", startTime += 500), new Beat("L", startTime), new Beat("F", startTime += 500),
					new Beat("J", startTime),

					// 봄바람 휘날리며
					new Beat("L", startTime += 1000), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime),

					// 흩날리는 벛꽃잎이
					new Beat("L", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500),

					// 울려 퍼질 이거리를
					new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("D", startTime), new Beat("Space", startTime += 500),
					new Beat("K", startTime), new Beat("Space", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime += 500),
					new Beat("S", startTime += 500),

					// 둘이 걸어요
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("S", startTime += 500), new Beat("L", startTime), new Beat("F", startTime += 500),
					new Beat("J", startTime),

					// 오 예
					new Beat("Space", startTime += 1000), new Beat("S", startTime += 500), new Beat("D", startTime),
					new Beat("F", startTime), new Beat("J", startTime += 50), new Beat("K", startTime),
					new Beat("L", startTime),

					// 전주
					new Beat("S", startTime += 1500), new Beat("D", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 1000),
					new Beat("Space", startTime += 1000), new Beat("Space", startTime += 1000),

					new Beat("K", startTime += 1000), new Beat("L", startTime += 1000),
					new Beat("K", startTime += 1000), new Beat("L", startTime += 1000),
					new Beat("K", startTime += 1000), new Beat("Space", startTime += 1000),

					new Beat("S", startTime += 300), new Beat("D", startTime += 100), new Beat("F", startTime += 100),
					new Beat("J", startTime += 100), new Beat("K", startTime += 100), new Beat("L", startTime += 100),

					// 그대여 우리 이제 손잡아요 이거리에
					new Beat("S", startTime += 800), new Beat("L", startTime += 200), new Beat("D", startTime += 250),
					new Beat("S", startTime += 500), new Beat("S", startTime += 500), new Beat("F", startTime += 500),
					new Beat("J", startTime += 500), new Beat("L", startTime += 500), new Beat("K", startTime += 500),
					new Beat("S", startTime += 1500), new Beat("L", startTime), new Beat("D", startTime += 500),
					new Beat("K", startTime), new Beat("S", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("S", startTime += 500), new Beat("J", startTime),
					new Beat("D", startTime += 500), new Beat("K", startTime),

					// 밤에 들려오는 자정 노래 어떤가요
					new Beat("Space", startTime += 500), new Beat("S", startTime += 650),
					new Beat("J", startTime += 500), new Beat("F", startTime += 500), new Beat("D", startTime += 500),

					new Beat("Space", startTime += 300),

					new Beat("S", startTime += 500), new Beat("D", startTime), new Beat("J", startTime += 500),
					new Beat("K", startTime),

					// 오 예
					new Beat("Space", startTime += 1300), new Beat("S", startTime += 800), new Beat("D", startTime),
					new Beat("F", startTime), new Beat("J", startTime += 50), new Beat("K", startTime),
					new Beat("L", startTime),

					// 사랑하는 그대와 단 둘이 손잡고
					new Beat("L", startTime += 2000), new Beat("F", startTime += 500), new Beat("S", startTime += 500),
					new Beat("K", startTime += 500), new Beat("Space", startTime += 500),
					new Beat("K", startTime += 500), new Beat("L", startTime += 500), new Beat("L", startTime += 1000),
					new Beat("F", startTime += 500), new Beat("S", startTime += 500), new Beat("K", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("K", startTime += 500),
					new Beat("L", startTime += 500),

					// 알 수 없는 이 떨림과 둘이 걸어요
					new Beat("L", startTime += 1000), new Beat("S", startTime), new Beat("F", startTime += 500),
					new Beat("S", startTime += 500), new Beat("K", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("K", startTime += 500),
					new Beat("L", startTime += 500), new Beat("L", startTime += 1000), new Beat("K", startTime),
					new Beat("F", startTime += 500), new Beat("S", startTime += 500), new Beat("K", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("S", startTime), new Beat("L", startTime),

					// 봄바람 휘날리며
					new Beat("L", startTime += 1000), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime),

					// 흩날리는 벛꽃잎이
					new Beat("L", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500),

					// 울려 퍼질 이거리를
					new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("D", startTime), new Beat("Space", startTime += 500),
					new Beat("K", startTime), new Beat("Space", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime += 500),
					new Beat("S", startTime += 500),

					// 둘이 걸어요
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("S", startTime += 500), new Beat("L", startTime), new Beat("F", startTime += 500),
					new Beat("J", startTime),

					// 봄바람 휘날리며
					new Beat("L", startTime += 1000), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime),

					// 흩날리는 벛꽃잎이
					new Beat("L", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500),

					// 울려 퍼질 이거리를
					new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("D", startTime), new Beat("Space", startTime += 500),
					new Beat("K", startTime), new Beat("Space", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime += 500),
					new Beat("S", startTime += 500),

					// 둘이 걸어요
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("S", startTime += 500), new Beat("L", startTime), new Beat("F", startTime += 500),
					new Beat("J", startTime),

					// 바람불면 울렁이는
					new Beat("S", startTime += 2000), new Beat("D", startTime), new Beat("K", startTime),
					new Beat("L", startTime += 1000), new Beat("K", startTime), new Beat("D", startTime),
					new Beat("F", startTime += 1000), new Beat("D", startTime), new Beat("J", startTime),
					new Beat("J", startTime += 1000), new Beat("K", startTime), new Beat("F", startTime),
					// 기분탓에 나도모르게
					new Beat("F", startTime += 1000), new Beat("D", startTime), new Beat("Space", startTime),
					new Beat("J", startTime += 1000), new Beat("K", startTime), new Beat("Space", startTime),
					new Beat("S", startTime += 1000), new Beat("D", startTime), new Beat("Space", startTime),
					new Beat("L", startTime += 1000), new Beat("K", startTime), new Beat("Space", startTime),
					// 바람불면 저편에서
					new Beat("S", startTime += 1000), new Beat("L", startTime), new Beat("Space", startTime),
					new Beat("D", startTime += 1000), new Beat("K", startTime), new Beat("Space", startTime),
					new Beat("S", startTime += 1000), new Beat("F", startTime), new Beat("Space", startTime),
					new Beat("J", startTime += 1000), new Beat("L", startTime), new Beat("Space", startTime),
					// 그대여 니 모습이 자꾸 겹쳐
					new Beat("S", startTime += 1000), new Beat("L", startTime += 200), new Beat("D", startTime += 250),
					new Beat("D", startTime += 500), new Beat("K", startTime), new Beat("Space", startTime),
					new Beat("S", startTime += 1000), new Beat("F", startTime), new Beat("Space", startTime),
					new Beat("J", startTime += 1000), new Beat("L", startTime), new Beat("Space", startTime),

					// 오 또 울렁이는
					new Beat("S", startTime += 2000), new Beat("D", startTime), new Beat("K", startTime),
					new Beat("L", startTime += 1000), new Beat("K", startTime), new Beat("D", startTime),
					new Beat("F", startTime += 1000), new Beat("D", startTime), new Beat("J", startTime),
					new Beat("J", startTime += 1000), new Beat("K", startTime), new Beat("F", startTime),
					// 기분 탓에 나도 모르게
					new Beat("F", startTime += 1000), new Beat("D", startTime), new Beat("Space", startTime),
					new Beat("J", startTime += 1000), new Beat("K", startTime), new Beat("Space", startTime),
					new Beat("S", startTime += 1000), new Beat("D", startTime), new Beat("Space", startTime),
					new Beat("L", startTime += 1000), new Beat("K", startTime), new Beat("Space", startTime),
					// 바람 불면 저편에서
					new Beat("S", startTime += 1000), new Beat("L", startTime), new Beat("Space", startTime),
					new Beat("D", startTime += 1000), new Beat("K", startTime), new Beat("Space", startTime),
					new Beat("S", startTime += 1000), new Beat("F", startTime), new Beat("Space", startTime),
					new Beat("J", startTime += 1000), new Beat("L", startTime), new Beat("Space", startTime),
					// 그대여 니 모습이 자꾸 겹쳐
					new Beat("S", startTime += 250), new Beat("L", startTime += 200), new Beat("D", startTime += 250),
					new Beat("D", startTime += 500), new Beat("K", startTime), new Beat("Space", startTime),
					new Beat("S", startTime += 1000), new Beat("F", startTime), new Beat("Space", startTime),
					new Beat("J", startTime += 1000), new Beat("L", startTime), new Beat("Space", startTime),

					// 사랑하는 연인들이 많군요
					new Beat("S", startTime += 2500), new Beat("D", startTime += 250), new Beat("K", startTime += 250),

					new Beat("L", startTime += 750), new Beat("J", startTime += 250), new Beat("D", startTime += 250),
					new Beat("K", startTime += 250),

					new Beat("L", startTime += 500), new Beat("J", startTime += 250), new Beat("D", startTime += 250),
					new Beat("K", startTime += 250),

					// 알 수 없는 친구들이 많아요
					new Beat("S", startTime += 750), new Beat("D", startTime += 250), new Beat("K", startTime += 250),

					new Beat("L", startTime += 750), new Beat("J", startTime += 250), new Beat("D", startTime += 250),
					new Beat("K", startTime += 250),

					// 흩날리는 벚꽃 잎이 많군요
					new Beat("S", startTime += 750), new Beat("D", startTime += 250), new Beat("K", startTime += 250),

					new Beat("L", startTime += 750), new Beat("J", startTime += 250), new Beat("D", startTime += 250),
					new Beat("K", startTime += 250),

					new Beat("L", startTime += 750), new Beat("J", startTime += 250), new Beat("D", startTime += 250),
					new Beat("K", startTime += 250),

					// 좋아요
					new Beat("Space", startTime += 1000), new Beat("S", startTime), new Beat("D", startTime),
					new Beat("K", startTime), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("D", startTime), new Beat("K", startTime),
					new Beat("L", startTime), new Beat("Space", startTime += 500), new Beat("S", startTime),
					new Beat("D", startTime), new Beat("K", startTime), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("S", startTime), new Beat("D", startTime),
					new Beat("K", startTime), new Beat("L", startTime),

					new Beat("Space", startTime += 500), new Beat("S", startTime), new Beat("D", startTime),
					new Beat("K", startTime), new Beat("L", startTime),

					// 봄바람 휘날리며
					new Beat("L", startTime += 2000), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime),

					// 흩날리는 벛꽃잎이
					new Beat("L", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500),

					// 울려 퍼질 이거리를
					new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("D", startTime), new Beat("Space", startTime += 500),
					new Beat("K", startTime), new Beat("Space", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime += 500),
					new Beat("S", startTime += 500),

					// 둘이 걸어요
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("S", startTime += 500), new Beat("L", startTime), new Beat("F", startTime += 500),
					new Beat("J", startTime),

					// 봄바람 휘날리며
					new Beat("L", startTime += 1000), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime),

					// 흩날리는 벛꽃잎이
					new Beat("L", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("Space", startTime += 500), new Beat("K", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500),

					// 울려 퍼질 이거리를
					new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("D", startTime), new Beat("Space", startTime += 500),
					new Beat("K", startTime), new Beat("Space", startTime += 500), new Beat("L", startTime),
					new Beat("Space", startTime += 500), new Beat("F", startTime), new Beat("Space", startTime += 500),
					new Beat("Space", startTime += 500), new Beat("L", startTime += 500),
					new Beat("S", startTime += 500),

					// 둘이 걸어요
					new Beat("Space", startTime += 500), new Beat("L", startTime), new Beat("Space", startTime += 500),
					new Beat("S", startTime), new Beat("J", startTime += 500), new Beat("S", startTime += 500),
					new Beat("S", startTime += 500), new Beat("L", startTime), new Beat("F", startTime += 500),
					new Beat("J", startTime),

					// 오 예
					new Beat("Space", startTime += 1300), new Beat("S", startTime += 800), new Beat("D", startTime),
					new Beat("F", startTime), new Beat("J", startTime += 50), new Beat("K", startTime),
					new Beat("L", startTime),

					new Beat("S", startTime += 500), new Beat("D", startTime += 150), new Beat("F", startTime += 200),
					new Beat("J", startTime += 1100), new Beat("K", startTime += 150), new Beat("L", startTime += 300),
					new Beat("S", startTime += 1000), new Beat("D", startTime += 150), new Beat("F", startTime += 300),
					new Beat("J", startTime += 1000), new Beat("K", startTime += 150), new Beat("L", startTime += 200),
					new Beat("S", startTime += 1100), new Beat("D", startTime += 150),
					new Beat("F", startTime += 200), };
		} else if(titleName.equals("CODE KUNST - PARACHUTE")) {
			int startTime =2200 ;
			beats = new Beat[] {
					new Beat("S", startTime += 400),
					new Beat("D", startTime += 325),
					new Beat("F", startTime += 325),
					new Beat("J", startTime += 345),
					new Beat("K", startTime += 400),
					new Beat("L", startTime += 325),
					new Beat("K", startTime += 325),
					new Beat("J", startTime += 325),
					new Beat("F", startTime += 325),
					new Beat("D", startTime += 440),
					new Beat("F", startTime += 325),
					new Beat("D", startTime += 325),
					new Beat("S", startTime += 325),
					new Beat("J", startTime += 325),
					new Beat("K", startTime += 440),
					new Beat("J", startTime += 260),
					new Beat("K", startTime += 260),
					new Beat("L", startTime += 260),
					new Beat("F", startTime += 330),
					new Beat("D", startTime += 260),
					new Beat("Space", startTime += 260), 
					new Beat("S", startTime += 400),
					new Beat("D", startTime += 325),
					new Beat("F", startTime += 325),
					new Beat("J", startTime += 345),
					new Beat("K", startTime += 400),
					new Beat("L", startTime += 325),
					new Beat("K", startTime += 325),
					new Beat("J", startTime += 325),
					new Beat("F", startTime += 325),
					new Beat("D", startTime += 440),
					new Beat("F", startTime += 325),
					new Beat("D", startTime += 325),
					new Beat("S", startTime += 325),
					new Beat("J", startTime += 325),
					new Beat("K", startTime += 440),
					new Beat("J", startTime += 260),
					new Beat("K", startTime += 260),
					new Beat("L", startTime += 260),
					new Beat("F", startTime += 330),
					new Beat("D", startTime += 260),
					new Beat("Space", startTime += 260), 
					new Beat("S", startTime += 3300), 
					new Beat("J", startTime), 
					new Beat("Space", startTime), 
					new Beat("Space", startTime += 3400), 
					new Beat("K", startTime), 
					new Beat("F", startTime), 
					new Beat("Space", startTime += 3400),
					new Beat("S", startTime), 
					new Beat("J", startTime), 
					new Beat("S", startTime += 4600),
					new Beat("Space", startTime += 280),
					new Beat("K", startTime += 280),
					new Beat("Space", startTime += 280),
					new Beat("L", startTime += 280),
					new Beat("Space", startTime += 280),
					new Beat("D", startTime += 280),
					new Beat("K", startTime += 280),
					new Beat("S", startTime += 300),
					new Beat("L", startTime += 280),
					new Beat("Space", startTime += 2200),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 1500),
					new Beat("K", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 1700),
					new Beat("D", startTime += 400),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 1000),
					new Beat("L", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("S", startTime += 600),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("L", startTime += 1000),
					new Beat("D", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("J", startTime += 600),
					new Beat("K", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("S", startTime += 1000),
					new Beat("D", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 600),
					new Beat("F", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 1700),
					new Beat("Space", startTime += 400),
					new Beat("S", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 1000),
					new Beat("D", startTime += 230),
					new Beat("J", startTime += 700),
					new Beat("K", startTime += 400),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 1000),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 700),
					new Beat("F", startTime += 400),
					new Beat("K", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 1000),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 700),
					new Beat("K", startTime += 400),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 1000),
					new Beat("D", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 500),
					new Beat("S", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 500),
					new Beat("K", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 500),
					new Beat("F", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("J", startTime += 500),
					new Beat("F", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 500),
					new Beat("D", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("K", startTime += 500),
					new Beat("D", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("K", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("K", startTime += 500),
					new Beat("D", startTime += 230),
					new Beat("J", startTime += 230),
					new Beat("F", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("D", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("S", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("L", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 230),
					new Beat("Space", startTime += 500),
					new Beat("J", startTime), 
					new Beat("Space", startTime), 
					new Beat("Space", startTime += 230),
					new Beat("K", startTime), 
					new Beat("F", startTime), 
					new Beat("Space", startTime += 500),
					new Beat("S", startTime), 
					new Beat("J", startTime), 
					new Beat("Space", startTime += 230),
					new Beat("L", startTime), 
					new Beat("S", startTime)};
			}else {
			int startTime = 1000;
			beats = new Beat[] { new Beat("Space", startTime), new Beat("Space", startTime += 1000),
					new Beat("Space", startTime += 1000), new Beat("Space", startTime += 1000),
					new Beat("Space", startTime += 1000),

					new Beat("S", startTime += 1000), new Beat("L", startTime += 1000),
					new Beat("S", startTime += 1000), new Beat("L", startTime += 1000),
					new Beat("S", startTime += 1000),

					new Beat("D", startTime += 150), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("J", startTime += 100), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("Space", startTime += 120),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("J", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("L", startTime += 350),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 150), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("L", startTime += 200),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("J", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100), new Beat("J", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("L", startTime += 350), new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("K", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("S", startTime += 200), new Beat("L", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("J", startTime += 150), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("D", startTime += 350),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("L", startTime += 200),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("J", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100), new Beat("J", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("L", startTime += 350), new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),

					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("K", startTime += 250), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("J", startTime += 100), new Beat("S", startTime += 150),
					new Beat("L", startTime += 200), new Beat("Space", startTime += 120),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("J", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300),

					new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("D", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 150), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("D", startTime += 350),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("L", startTime += 200),
					new Beat("J", startTime += 350),

					new Beat("F", startTime += 350), new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("J", startTime += 100),
					new Beat("F", startTime += 150), new Beat("K", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100), new Beat("J", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("L", startTime += 350), new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("K", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("K", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 150), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("K", startTime += 350), new Beat("L", startTime += 200),

					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("J", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100), new Beat("J", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("L", startTime += 350), new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("K", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 150), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("L", startTime += 200),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350),

					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("K", startTime += 100), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("J", startTime += 100), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("Space", startTime += 120),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("J", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("L", startTime += 350),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 150), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("K", startTime += 250), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("L", startTime += 200),

					new Beat("K", startTime += 300), new Beat("F", startTime += 350), new Beat("L", startTime += 200),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("L", startTime += 350),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350), new Beat("J", startTime += 100),
					new Beat("F", startTime += 150), new Beat("S", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 350), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 100), new Beat("L", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("K", startTime += 300), new Beat("J", startTime += 100),
					new Beat("S", startTime += 150), new Beat("L", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100), new Beat("J", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("L", startTime += 350), new Beat("D", startTime += 100), new Beat("F", startTime += 150),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("D", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("K", startTime += 300),
					new Beat("J", startTime += 100), new Beat("S", startTime += 150), new Beat("L", startTime += 200),
					new Beat("Space", startTime += 120), new Beat("Space", startTime += 120),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("Space", startTime += 120),
					new Beat("Space", startTime += 120), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("Space", startTime += 120),
					new Beat("K", startTime += 200), new Beat("Space", startTime += 120),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("F", startTime += 350), new Beat("L", startTime += 200),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("K", startTime += 300), new Beat("F", startTime += 350),
					new Beat("Space", startTime += 120), new Beat("D", startTime += 100),
					new Beat("F", startTime += 150), new Beat("L", startTime += 200), new Beat("J", startTime += 250),
					new Beat("D", startTime += 300), new Beat("F", startTime += 350),

					new Beat("S", startTime += 300), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("J", startTime += 350), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("F", startTime += 150), new Beat("L", startTime += 200),
					new Beat("J", startTime += 250), new Beat("S", startTime += 300), new Beat("F", startTime += 350),
					new Beat("D", startTime += 100), new Beat("Space", startTime += 120),
					new Beat("D", startTime += 100), new Beat("Space", startTime += 120),
					new Beat("F", startTime += 350), new Beat("L", startTime += 200), new Beat("F", startTime += 350),
					new Beat("L", startTime += 200), new Beat("J", startTime += 350), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("S", startTime += 300),

					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("D", startTime += 100),
					new Beat("Space", startTime += 120), new Beat("F", startTime += 150),
					new Beat("L", startTime += 200), new Beat("J", startTime += 250), new Beat("S", startTime += 300),
					new Beat("F", startTime += 350), new Beat("Space", startTime += 200),
					new Beat("Space", startTime += 200), new Beat("Space", startTime += 200),
					new Beat("Space", startTime += 200), new Beat("Space", startTime += 200),
					new Beat("Space", startTime += 200), new Beat("Space", startTime += 200), };
		}
		
		t.start();
		
		synchronized (t) {
			gameMusic.start();
		}
		int i = 0;
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
				dropped = true;
			}
			if(i==beats.length-1)
				isGame=true;
			if (dropped) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		while (!Thread.interrupted()) {
			if (gameMusic.player.isComplete() && g.twouser) {
				if (score > vsScore) {
					// 이김
					ClientUI.net.sendGameResultRequest(0);
					ClientUI.net.sendSingleGameResultRequest(score);
				} else if (score < vsScore) {
					// 짐
					ClientUI.net.sendGameResultRequest(1);
					ClientUI.net.sendSingleGameResultRequest(score);
				} else {
					// 무승부
					ClientUI.net.sendGameResultRequest(2);
					ClientUI.net.sendSingleGameResultRequest(score);
				}
				if(g.sp.pnMusicChoice.btReady.isEnabled()) {
					g.sp.pnMusicChoice.btReady.doClick();
				}
				g.sp.pnMusicChoice.p.selectTrack(g.sp.pnMusicChoice.p.nowSelected);
				g.sp.setLocationRelativeTo(null);
				g.sp.setVisible(true);
				g.dispose();
			} else if(gameMusic.player.isComplete() && !g.twouser){
				ClientUI.net.sendSingleGameResultRequest(score);
				g.sp.pnMusicChoice.p.selectTrack(g.sp.pnMusicChoice.p.nowSelected);
				g.sp.setLocationRelativeTo(null);
				g.sp.setVisible(true);
				g.dispose();
			}
		}
	}

	public void screenDraw(Graphics2D g) {
		

		g.drawImage(noteRouteLineImage, 206, 15, null);
		g.drawImage(noteRouteSImage, 210, 15, null);
		g.drawImage(noteRouteLineImage, 310, 15, null);
		g.drawImage(noteRouteDImage, 314, 15, null);
		g.drawImage(noteRouteLineImage, 414, 15, null);
		g.drawImage(noteRouteFImage, 418, 15, null);
		g.drawImage(noteRouteLineImage, 518, 15, null);
		g.drawImage(noteRouteSpace1Image, 522, 15, null);
		g.drawImage(noteRouteSpace2Image, 622, 15, null);
		g.drawImage(noteRouteLineImage, 722, 15, null);
		g.drawImage(noteRouteJImage, 726, 15, null);
		g.drawImage(noteRouteLineImage, 826, 15, null);
		g.drawImage(noteRouteKImage, 830, 15, null);
		g.drawImage(noteRouteLineImage, 930, 15, null);
		g.drawImage(noteRouteLImage, 934, 15, null);
		g.drawImage(noteRouteLineImage, 1034, 15, null);

		g.drawImage(judgementLineImage, 0, 650, null);

		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if(note.getY()>730) {
				judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/miss.png")).getImage();
			}
			if (!note.isProceeded()) {
				noteList.remove(i);
				i--;
			} else {
				note.screenDraw(g);
			}
			
		}

		g.drawImage(gameInfoImage, 0, 740, null);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("맑은고딕", Font.BOLD, 30));
		g.drawString(titleName, 40, 780);
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("맑은고딕", Font.PLAIN, 30));
		g.drawString("S", 250, 680);
		g.drawString("D", 354, 680);
		g.drawString("F", 458, 680);
		g.drawString("Space Bar", 552, 680);
		g.drawString("J", 766, 680);
		g.drawString("K", 870, 680);
		g.drawString("L", 984, 680);
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString(score + "", 545, 780);
		if(this.g.twouser) {
			g.drawString("상대방 : " + vsScore, 1000, 780);	
		}
		if(isGame && this.g.twouser) {
			judgeImage=new ImageIcon(getClass().getClassLoader().getResource("images/blank.png")).getImage();
			if(score> vsScore)
				resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/win.png")).getImage();
			else if(score ==vsScore)
				resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/draw.png")).getImage();
			else
				resultImage = new ImageIcon(getClass().getClassLoader().getResource("images/lose.png")).getImage();
			g.drawImage(resultImage	,300, 150, null);
		}else {
			g.drawImage(judgeImage,205, 100, null);
		}
		t.screenDraw(g);
		
	}

	public void pressS() {
		noteRouteSImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("S");
	}

	public void releaseS() {
		noteRouteSImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void pressD() {
		noteRouteDImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("D");
	}

	public void releaseD() {
		noteRouteDImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void pressF() {
		noteRouteFImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("F");
	}

	public void releaseF() {
		noteRouteFImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void pressSpace() {
		noteRouteSpace1Image = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("Space");
	}

	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void pressJ() {
		noteRouteJImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("J");
	}

	public void releaseJ() {
		noteRouteJImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void pressK() {
		noteRouteKImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("K");
	}

	public void releaseK() {
		noteRouteKImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void pressL() {
		noteRouteLImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoutePressed.png")).getImage();
		judge("L");
	}

	public void releaseL() {
		noteRouteLImage = new ImageIcon(getClass().getClassLoader().getResource("images/noteRoute.png")).getImage();
	}

	public void judge(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
		}
	}
	public void judgeEvent(String input) {
		if(input.equals("miss")) {
			judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/miss.png")).getImage();
		}else if(input.equals("early")) {
			judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/early.png")).getImage();
		}else if(input.equals("good")) {
			judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/good.png")).getImage();
		}else if(input.equals("great")) {
			judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/great.png")).getImage();
		}else if(input.equals("perfect")) {
			judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/perfect.png")).getImage();
		}else if(input.equals("late")) {
			judgeImage = new ImageIcon(getClass().getClassLoader().getResource("images/late.png")).getImage();
		}
	}

}
