package id.ac.its.project;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToeFrame implements ActionListener{

	Random random = new Random();
	JFrame frame = new JFrame("O: 0; X: 0");
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] buttons;
	int buttonLengthSqrt;
	boolean player1_turn;
	int turn = 0;
	int scoreX = 0;
	int scoreO = 0;

	public TicTacToeFrame(int buttonLengthSqrt){
		
		this.buttonLengthSqrt = buttonLengthSqrt;
		buttons = new JButton[buttonLengthSqrt * buttonLengthSqrt];
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free",Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Tic-Tac-Toe");
		textfield.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,600,100);
		
		button_panel.setLayout(new GridLayout(buttonLengthSqrt,buttonLengthSqrt));
		button_panel.setBackground(new Color(150,150,150));
		
		for(int i=0;i<buttons.length;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			if(buttonLengthSqrt == 3) 
				buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
			else if(buttonLengthSqrt == 5)
				buttons[i].setFont(new Font("MV Boli",Font.BOLD,100));
			else 
				buttons[i].setFont(new Font("MV Boli",Font.BOLD,60));
			
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
		
		title_panel.add(textfield);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(button_panel);
		
		firstTurn();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i=0;i<buttons.length;i++) {
			if(e.getSource()==buttons[i]) {
				turn++;
				if(player1_turn) {
					if(buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(255,0,0));
						buttons[i].setText("X");
						player1_turn=false;
						textfield.setText("O turn");
						check();
					}
				}
				else {
					if(buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(0,0,255));
						buttons[i].setText("O");
						player1_turn=true;
						textfield.setText("X turn");
						check();
					}
				}
			}			
		}
	}
	
	public void firstTurn() {
		
		try {
			setButtonState(false);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(random.nextInt(2)==0) {
			player1_turn=true;
			textfield.setText("X turn");
		}
		else {
			player1_turn=false;
			textfield.setText("O turn");
		}
		setButtonState(true);
	}
	
	public void check() {
		if(turn == buttons.length) {
			draw();
		}
		else {
			boolean xWin = true;
			boolean oWin = true;
			//Cek baris
			for(int i = 0; i < buttonLengthSqrt; i++) {
				int temp = i*buttonLengthSqrt;
				xWin = true;
				oWin = true;
				for(int j = 0; j < buttonLengthSqrt; j++) {
					if(buttons[temp+j].getText()!="X") {
						xWin = false;
					}
					if(buttons[temp+j].getText()!="O") {
						oWin = false;
					}
				}
				if(xWin) {
					xWins(temp, 'h');
					return;
				}
				else if(oWin) {
					oWins(temp, 'h');
					return;
				}
			}
		
			//Cek kolom
			for(int i = 0; i < buttonLengthSqrt; i++) {
				int temp = i;
				xWin = true;
				oWin = true;
				for(int j = 0; j < buttons.length; j += buttonLengthSqrt) {
					if(buttons[temp+j].getText()!="X") {
						xWin = false;
					}
					if(buttons[temp+j].getText()!="O") {
						oWin = false;
					}
				}
				if(xWin) {
					xWins(temp, 'v');
					return;
				}
				else if(oWin) {
					oWins(temp, 'v');
					return;
				}
			}
		
			//Cek diagonal
			xWin = true;
			oWin = true;
			for(int i = 0; i < buttons.length; i += (buttonLengthSqrt+1)) {
				if(buttons[i].getText()!="X") {
					xWin = false;
				}
				if(buttons[i].getText()!="O") {
					oWin = false;
				}
			}
			if(xWin) {
				xWins(0, 'd');
				return;
			}
			else if(oWin) {
				oWins(0, 'd');
				return;
			}
		
			xWin = true;
			oWin = true;
			for(int i = buttons.length - buttonLengthSqrt; i > 0; i -= (buttonLengthSqrt-1)) {
				if(buttons[i].getText()!="X") {
					xWin = false;
				}
				if(buttons[i].getText()!="O") {
					oWin = false;
				}
			}
			if(xWin) {
				xWins(0, 'u');
				return;
			}
			else if(oWin) {
				oWins(0, 'u');
				return;
			}
		}
	}
	
	/*
	kode : 
	'h' = horizontal
	'v' = vertical
	'd' = diagonal down
	'u' = diagonal up
	 */
	public void xWins(int i, char kode) {
		setButtonWinColor(i, kode, Color.GREEN);
		
		setButtonState(false);
		textfield.setText("X wins");
		scoreX++;
		endScreen();
	}
	public void oWins(int i, char kode) {
		setButtonWinColor(i, kode, Color.GREEN);
		
		setButtonState(false);
		textfield.setText("O wins");
		scoreO++;
		endScreen();
	}
	public void draw() {
		setButtonState(false);
		textfield.setText("Draw");
		endScreen();
	}
	
	private void setButtonState(boolean state) {
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setEnabled(state);
		}
	}
	
	private void setButtonWinColor(int i, char kode, Color color) {
		if(kode == 'h') {
			for(int j = 0; j < buttonLengthSqrt; j++) {
				buttons[i+j].setBackground(color);
			}
		}
		else if(kode == 'v') {
			for(int j = 0; j < buttons.length; j += buttonLengthSqrt) {
				buttons[i+j].setBackground(color);
			}
		}
		else if(kode == 'd') {
			for(int j = 0; j < buttons.length; j += (buttonLengthSqrt+1)) {
				buttons[j].setBackground(color);
			}
		}
		else if(kode == 'u') {
			for(int j = buttons.length - buttonLengthSqrt; j > 0; j -= (buttonLengthSqrt-1)) {
				buttons[j].setBackground(color);
			}
		}
	}
	
	private void endScreen() {
		frame.setTitle("O: " + scoreO + "; X: " + scoreX);
		String[] options = {"Play Again", "Main Menu"};
		
		FileManager fileManager = new FileManager();
		fileManager.saveHighScore(scoreO > scoreX? scoreO : scoreX);
		int highscore = fileManager.loadHighScore();
		
		int choice = JOptionPane.showOptionDialog(null, "Highscore: " + highscore + 
				"\nDo you want to play again?", "Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(choice == 0) {
        	resetGame();
        }
        else if(choice == 1) {
        	frame.dispose();
        }
	}
	
	private void resetGame() {
		turn = 0;
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setText("");
			buttons[i].setBackground(new JButton().getBackground());
		}
		firstTurn();
	}
}