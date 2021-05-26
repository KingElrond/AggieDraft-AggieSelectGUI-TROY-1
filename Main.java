import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.swing.Box;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

public class Main extends JPanel implements ActionListener{ // Add panel on side that shows each creature stats

	static JFrame frame,tableFrame,selectFrame,AggieFrame;
	static final int width = 410, height = 410;
	private BufferedImage icon;
  private JLabel agIMG;
  static ImageIcon ag1Icon = new ImageIcon("rayquaza.png");
  static ImageIcon ag2Icon = new ImageIcon("pika.png");
/*
  private ImageIcon SportBlack = new ImageIcon("SportBlack.jpeg");
  private ImageIcon SportBlue = new ImageIcon("SportBlue.jpeg");
  private ImageIcon SportWhite = new ImageIcon("SportWhite.jpeg"); 
  */
	static Aggie p1,p2;
  static Aggie[] AggieLists;
	static JToggleButton toggle, p1sel,p2sel;
  static JButton nextAG,prevAG,agselStart;
	private JTable table = new JTable();
	private JScrollPane container;
	static DefaultTableModel model = new DefaultTableModel();
	private Graphics2D ga;
	static Timer timer;
	static JTextField display = new JTextField();
  static String agselAB1,agselAB2,agselAB3,agselAB4;
  static JTextField agSelName,agselStats;
  static String agselAB1t,agselAB2t,agselAB3t,agselAB4t;
	static String str;
	static int end;
	private static Scanner kb = new Scanner(System.in);
	
	public Main(Aggie player1, Aggie player2) {
		p1 = player1;
		p2 = player2;
		
		setSize(width,height);  // creating panel to place on frame
		setLayout(null);
		setBackground(new Color(145,255,255));
		
		add(new BarPanel(20,40,p1)); // adds health bars
		add(new BarPanel(210,190,p2));
		
		try { // gets toggle icon
			icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("toggle.png"));
		}
		catch(Exception e) {
			System.out.println("Failed to load file");
		}
		


		
		toggle = new JToggleButton(); // adds toggle button
		toggle.setBounds(width-32,-5,icon.getWidth()-5,icon.getHeight()-5);
		toggle.setOpaque(false);
		toggle.setFocusable(false);
		toggle.setContentAreaFilled(false);
		toggle.addActionListener(this);
		toggle.setBorder(null);
		add(toggle);
		
		setTable(); // stats table
		
		tableFrame = new JFrame();// stats window
		tableFrame.setBounds(frame.getX()+width,frame.getY(),(width/3)*2,height/2);
		tableFrame.setVisible(false);
		tableFrame.setBackground(Color.BLACK);
		tableFrame.add(container);
		tableFrame.setDefaultCloseOperation(3);

		display.setFont(new Font("courier",Font.PLAIN,12));
		display.setBackground(Color.black);
		display.setEditable(false);
		display.setForeground(Color.WHITE);
		display.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		display.setBounds(15,285,380,25);
		add(display);
		
		
		timer = new Timer(100,this);
		
	    p1.setAbilityPanel(new AttackPanel(p1,p2)); 
	    p2.setAbilityPanel(new AttackPanel(p2,p1)); 
		
	    add(p1.getAbilityPanel());
	    add(p2.getAbilityPanel());
	    
	    GameManager gm = new GameManager(p1,p2,this);
	    str = GameManager.turn.getName() + "'s turn";

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ga = (Graphics2D)g;
		ga.setStroke(new BasicStroke(1));
		ga.setColor(new Color(9,186,8));
		ga.fillOval(20, 240, 150, 40);
		ga.fillOval(220, 110, 150, 40);
		ga.setColor(Color.green);
		ga.fillOval(20, 240, 145, 35);
		ga.fillOval(220, 110, 145, 35);
		p1.render(ga);
		p2.render(ga);
		ga.drawImage(icon, width - 25, 2, icon.getWidth()/2, icon.getHeight()/2, null);
	}
  public Main(String aggieselectString)
  {
  JPanel agSbuttons = new JPanel();
  agSbuttons.setLayout(new GridLayout(3, 1, 10, 10));
  JPanel agS2buttons = new JPanel();
  agS2buttons.setLayout(new GridLayout(3, 1, 10, 10));
  JPanel agS3buttons = new JPanel();
  agS3buttons.setLayout(new GridLayout(3, 1, 10, 10));
  nextAG = new JButton("Next");
  prevAG = new JButton("Prev");
  agselStart = new JButton("Start");
  nextAG.addActionListener(this);
  prevAG.addActionListener(this);
  agselStart.addActionListener(this);
agS2buttons.add(nextAG);
agS3buttons.add(agselStart);
agSbuttons.add(prevAG);

JPanel aggieImageSelection = new JPanel();
    agIMG = new JLabel();
    agIMG.setIcon(ag1Icon);
    agIMG.setOpaque(true);
    aggieImageSelection.add(agIMG);

JPanel agimgseltext = new JPanel();
agimseltext.setLayout(new GridLayout(2,3,10,10));
  agSelName = new JTextField("Medinasaur");
  agSelName.setEditable(false);
  agimgseltext.add(agSelName);

JPanel agselLeft = new JPanel();
JPanel agselright = new JPanel();
agselAB1 = AggieLists[0].getAbilities()[0].getName();
agselAB2 = AggieLists[0].getAbilities()[1].getName();
agselAB3 = AggieLists[0].getAbilities()[2].getName();
agselAB4 = AggieLists[0].getAbilities()[3].getName();
agselAB1t = AggieLists[0].getAbilities()[0].getDesc();
agselAB2t = AggieLists[0].getAbilities()[1].getDesc();
agselAB3t = AggieLists[0].getAbilities()[2].getDesc();
agselAB4t = AggieLists[0].getAbilities()[3].getDesc();
JTextArea larea = new JTextArea();
larea.setText(agselAB1 + "\n" + agselAB1t + "\n\n" + agselAB2 + "\n" + agselAB2t);
JTextArea Rarea = new JTextArea();
Rarea.setText(agselAB3 + "\n" + agselAB3t + "\n\n" + agselAB4 + "\n" + agselAB4t);
agselLeft.add(larea);
agselright.add(Rarea);

Box hbox = Box.createHorizontalBox();
    hbox.add(Box.createHorizontalStrut(10));
    hbox.add(agSbuttons);
    hbox.add(Box.createHorizontalStrut(10));
    hbox.add(agS3buttons);
    hbox.add(Box.createHorizontalStrut(10));
    hbox.add(agS2buttons);
    hbox.add(Box.createHorizontalStrut(10));
Box vbox = Box.createVerticalBox();
    vbox.add(Box.createVerticalStrut(5));
    vbox.add(agimgseltext);
    vbox.add(Box.createVerticalStrut(5));
    vbox.add(aggieImageSelection);
Box h1box = Box.createHorizontalBox();
h1box.add(Box.createHorizontalStrut(10));
h1box.add(agselLeft);
h1box.add(Box.createHorizontalStrut(10));
Box h2box = Box.createHorizontalBox();
h2box.add(Box.createHorizontalStrut(10));
h2box.add(agselright);
h2box.add(Box.createHorizontalStrut(10));


//I DID IT
Container c = new Container();
    c.setLayout(new BorderLayout(10,10));
    c.add(hbox, BorderLayout.NORTH);
    c.add(h1box, BorderLayout.WEST);
    c.add(vbox, BorderLayout.CENTER);
    c.add(h2box, BorderLayout.EAST);

    selectFrame = new JFrame();
		selectFrame.setSize(width,height);
		selectFrame.setLocationRelativeTo(null);
    selectFrame.setBackground(Color.BLACK);
    selectFrame.add(c);
		selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    selectFrame.setResizable(true);
    selectFrame.setVisible(true);




  }
	
	public static void main(String[] args){
		try {
			for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()) {
				if(info.getName().equalsIgnoreCase("cde/motif"))
					UIManager.setLookAndFeel(info.getClassName());
			}
		}
		catch(Exception e) {}
		Aggie a1 = new Medinasaur(new Sprite("rayquaza.png"),100,"Medinasuar",50,50,50,125,ag1Icon);
		Aggie a2 = new Penisaur(new Sprite("pika.png"),100,"Penisaur",75,80,50,70,ag2Icon);
    // params(Sprite, dimX, dimY, size, name, damage, speed, defense, health)
		Aggie[] aggieList = {a1,a2};
    AggieLists = aggieList;
		// params(Sprite, dimX, dimY, size, name, damage, speed, defense, health)
		System.out.println("Select the first Aggie\n");
		for(int i = 0; i < aggieList.length;i++) {
			System.out.println(i+1+ ") " + aggieList[i].getName());
		}
		Aggie player1 = aggieList[kb.nextInt()-1];
		
		System.out.println("Select the second Aggie");
		for(int i = 0; i < aggieList.length;i++) {
			System.out.println(i+1 + ") " + aggieList[i].getName());
		}
		Aggie player2 = aggieList[kb.nextInt()-1];
		
		frame = new JFrame();
		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

    new Main("aggieString");
		
		player1.setXpos(250);
		player1.setYpos(20);
		
		player2.setXpos(60);
		player2.setYpos(160);
		
		frame.add(new Main(player1, player2));
		frame.setVisible(true);
		
	}
	
	public static void setDisplay(String text) {
		str = text;
		end = 0;
		timer.start();
	}
	
	public void setTable() {
		Object [] header = {"Stat",p1.getName(),p2.getName()};
		model.setColumnIdentifiers(header);
		model.setRowCount(5);
		model.setValueAt("Atk", 0, 0);
		model.setValueAt("Health", 1, 0);
		model.setValueAt("Drip", 2, 0);
		model.setValueAt("Spd", 3, 0);
		model.setValueAt("Def", 4, 0);
		
		updateTable();
		
		table = new JTable(model);
		table.setPreferredSize(new Dimension((width/3)*2,height/3));
		table.setShowGrid(true);
		table.setGridColor(Color.BLACK);
		table.setBackground(Color.WHITE);
		
		container = new JScrollPane(table);
		container.setPreferredSize(table.getPreferredSize());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(end <= str.length()) {
			display.setText(str.substring(0, end));
			end++;
		}
		else {
			try {
				synchronized(timer)
				{
				    timer.wait(200);
				}
			} catch (InterruptedException e1) {}
			timer.stop();
		}
    if(nextAG.isSelected())
    {

    }

	}
	
	public static void updateTable() {
		model.setValueAt(p1.getMaxDamage(), 0, 1);
		model.setValueAt(p1.getHealth(), 1, 1);
		model.setValueAt(p1.getDrip(), 2, 1);
		model.setValueAt(p1.getSpeed(), 3, 1);
		model.setValueAt(p1.getDefense(), 4, 1);
		
		model.setValueAt(p2.getMaxDamage(), 0, 2);
		model.setValueAt(p2.getHealth(), 1, 2);
		model.setValueAt(p2.getDrip(), 2, 2);
		model.setValueAt(p2.getSpeed(), 3, 2);
		model.setValueAt(p2.getDefense(), 4, 2);
	}
}
