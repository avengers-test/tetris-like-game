/*
 * Scoring (could be more advanced)
 * Help
 */
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;

public class Tetris extends JPanel implements ActionListener, KeyListener, ComponentListener
{
	private Color[][] board;
	private Tetrimino current;
	private Tetrimino next;
	private Tetrimino hold;
	private int score;
	private int level;
	private int toNext;
	private Timer tm;
	private boolean paused;
	private boolean holdPause;
	private boolean onHold;
	private boolean gameOver;
	private Tetrimino cheat;
	
	//This is the size of the actual tetris game
	public static int boardLength = Tetrimino.sideLength * 10;
	//These are the variables that control large the side of the board is
	public static int sideBuffer = (int)(Tetrimino.sideLength * 5.5); 
	// Top Right Hold Box
	public static int topBox = Tetrimino.sideLength * 2; //Defines how far form the top this box is
	public static int boxHeight = Tetrimino.sideLength * 7;//Describes the height of the boxes
	public static int bottomBox = topBox + boxHeight + Tetrimino.sideLength *2;
	
	public Tetris()
	{
		board = new Color[20][10];
		tm = new Timer(1000, this);
		tm.start();
		setNewTetrimino();
		this.setSize(boardLength + 2* sideBuffer+2, current.getSideLength() * 20 + 42);	
		this.setBackground(Color.WHITE);
		this.addKeyListener(this);
		this.addComponentListener(this);
		paused = false;
		onHold = false;
		gameOver = false;
		level = 1;
		toNext = 10 * level;
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Color backColor = new Color(0,102,204);
		g.setColor(backColor);
		g.fillRoundRect(Tetrimino.sideLength/2, topBox, sideBuffer - Tetrimino.sideLength, boxHeight, boxHeight/25, Tetrimino.sideLength/2);
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength()));
		g.drawString("Hold",(int)(current.getSideLength() * 0.75), current.getSideLength() + topBox);
		
		g.setColor(backColor);
		g.fillRoundRect(Tetrimino.sideLength/2, bottomBox, sideBuffer - Tetrimino.sideLength, boxHeight, boxHeight/25, Tetrimino.sideLength/2);
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength()));
		g.drawString("Score",(int)(current.getSideLength() * 0.75), current.getSideLength() + bottomBox);
		g.drawString(((Integer) score).toString(),(int)(current.getSideLength() * 0.75),(int)(current.getSideLength()*2.1) + bottomBox);
		g.drawString("Level",(int)(current.getSideLength() * 0.75), current.getSideLength()*11/10* 3 + bottomBox);
		g.drawString(((Integer) level).toString(),(int)(current.getSideLength() * 0.75),current.getSideLength()*11/10*4 + bottomBox);
		g.drawString("To Next",(int)(current.getSideLength() * 0.75), current.getSideLength()*11/10 * 5 + bottomBox);
		g.drawString(((Integer) toNext).toString(),(int)(current.getSideLength() * 0.75),current.getSideLength()*11/10* 6 + bottomBox);
		
		
		if(hold != null)
		{
			hold.paint(g, true);
		}
		
		g.setColor(backColor);
		g.fillRoundRect(Tetris.sideBuffer + Tetris.boardLength + Tetrimino.sideLength/2, topBox, sideBuffer - Tetrimino.sideLength, boxHeight, boxHeight/25, Tetrimino.sideLength/2);
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength()));
		g.drawString("Next",Tetris.sideBuffer + Tetris.boardLength + (int)(current.getSideLength() * 0.75), current.getSideLength() + topBox);
		if(next !=null)
		{
			next.paint(g, false);
		}
		
		if(paused)
		{
			g.setColor(Color.DARK_GRAY);
			g.fillRect((int)(sideBuffer), 0, current.getSideLength() * 10+2, current.getSideLength() * 20 + 2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength() + 12));
			g.drawString("PAUSED", (int)(current.getSideLength() *1.75) + sideBuffer, current.getSideLength()*3);
			g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength()));
			g.drawString("Press \"N\" to Restart",(int)(current.getSideLength() * 0.25) + sideBuffer, current.getSideLength()*4 + 20);
			return;
		}
		
		for(int i=0; i<20; i++)
			for(int j=0; j<10; j++)
			{
				g.setColor(Color.BLACK);
				g.fillRect((int)(sideBuffer) +j*current.getSideLength(), i*current.getSideLength(), current.getSideLength(), current.getSideLength());
				if(board[i][j] == null)
				{
					g.setColor(Color.GRAY);
					g.fillRect((int)(sideBuffer) + j*current.getSideLength()+ current.getSideLength()/32 + 1, i*current.getSideLength() + current.getSideLength()/32 + 1, current.getSideLength()-((int) current.getSideLength()/16) - 2, current.getSideLength()-((int) current.getSideLength()/16) - 2);
				}
				else
				{
					g.setColor(board[i][j]);
					g.fillRect((int)(sideBuffer) + j*current.getSideLength() + current.getSideLength()/32 + 1, i*current.getSideLength() + current.getSideLength()/32 + 1,  current.getSideLength()-((int) current.getSideLength()/16) - 2, current.getSideLength()-((int) current.getSideLength()/16) - 2);
				}
			}
		
		if(!current.check(board, Direction.None))
		{
			current.paint(g);
			tm.stop();
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength() + 12));
			g.drawString("Game Over", (int)(sideBuffer) + (int)(current.getSideLength() *1.75), current.getSideLength()*3);
			g.setFont(new Font("Times New Roman",Font.BOLD, current.getSideLength()));
			g.drawString("Press \"N\" to Restart",(int)(sideBuffer) + (int)(current.getSideLength() * 0.25), current.getSideLength()*4 + 20);
			gameOver = true;
			
			return;
		}
		else
		{
			int actualY = current.getY();
			while(current.check(board, Direction.Down))
			{
				current.setY(current.getY() + 1);
			}
			current.paintHollow(g);
			
			current.setY(actualY);
		}
		
		current.paint(g);
				
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		down(false);
		repaint();
	}
	
	public void keyTyped(KeyEvent e){	}
	
	public void keyReleased(KeyEvent e)	{	}
	
	public void keyPressed(KeyEvent e)
	{
		if(gameOver)
			return;
		
		if(!paused && (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT))
		{
			right();
		}
		else if(!paused && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT))
		{
			left();
		}
		else if(!paused && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP))
		{
			hardTap();
		}
		else if(!paused && (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN))
		{
			down(true);
		}
		else if(!paused && (e.getKeyCode() == KeyEvent.VK_SPACE))
		{
			current.turn(board);
		}
		else if(e.getKeyCode() == KeyEvent.VK_P)
		{
			pause();
		}
		else if(!onHold && e.getKeyCode() == KeyEvent.VK_H)
		{
			hold();
		}
		else if(!paused && e.getKeyCode()== KeyEvent.VK_Q)
		{
			cheat = new StraightPiece(current.getX(), 0);
		}
		repaint();
	}
	
	private void right()
	{
		if(current.check(board, Direction.Right))
		{
			//repaint(getX()-1, getY() - 1, current.getLength()*current.getSideLength(), current.getHeight()*current.getSideLength());
			current.setX(current.getX() + 1);
			//repaint(getX()-1, getY() - 1, current.getLength()*current.getSideLength(), current.getHeight()*current.getSideLength());
		}
			
	}
	
	private void left()
	{
		if(current.check(board, Direction.Left))
		{
			//repaint(getX()-1, getY() - 1, current.getLength()*current.getSideLength(), current.getHeight()*current.getSideLength());
			current.setX(current.getX() - 1);
			//repaint(getX()-1, getY() - 1, current.getLength()*current.getSideLength(), current.getHeight()*current.getSideLength());
		}
			
	}
	
	private void down(boolean isKeyType)
	{
		if(current.check(board, Direction.Down))
		{
			current.setY(current.getY() + 1);
			if(isKeyType)
				score +=1;
		}
		else
		{
			current.addTo(board);
			setNewTetrimino();
			updateBoard();
		}
	}
	
	private void hardTap()
	{
		while(current.check(board, Direction.Down))
		{
			current.setY(current.getY() + 1);
			score+=2;
		}
		current.addTo(board);
		setNewTetrimino();
		updateBoard();
	}
	
	private void hold()
	{
		int tempInt = current.getX();
		if(hold == null)
		{
			hold = current;
			setNewTetrimino();
		}
		else
		{
			Tetrimino temp = hold;
			hold = current;
			current = temp;
			current.setY(0);
			current.setX(tempInt);
		}
		if((current.getX() + current.getLength())>=10)
			current.setX((10-current.getLength()));
		onHold = true;
			
	}
	
	private void updateBoard()
	{
		int numCleared = 0;
		for(int i=0; i< board.length; i++)
			if(rowFull(i))
			{
				board[i] = new Color[10];
				numCleared++;
			}
		
		int check = 19;
		for(int i = 19; i>=0; i--)
		{
			if(!rowEmpty(i) && check != i)
			{
				board[check] = board[i];
				board[i] = new Color[10];
				check--;
			}
			else if(!rowEmpty(i))
			{
				check--;
			}
		}
		if(numCleared == 3)//bonus for triple
			numCleared = 5;
		else if(numCleared == 4)//bonus for quad
			numCleared = 8;
		
		score += 100 * level * numCleared;
		
		toNext -= numCleared;
		if(toNext<=0)
		{
			level++;
			toNext += 10*level;
			tm.setDelay((tm.getDelay() * 16)/20);
		}
		
		onHold = false;
	}
	
	private void pause()
	{
		//pauses the game
		if(paused)
			tm.start();
		else
			tm.stop();
		paused =! paused;
	}
	
	private void setNewTetrimino()
	{
		int nextX = 4;
		if(current != null)
			nextX = current.getX();
		
		if(next != null)
			current = next;
		
		switch((int)(Math.random() * 7))
		{
		case 6:
			next = new ZZag(0,0);
			break;
		case 5:
			next = new SZag(0,0);
			break;
		case 4:
			next = new RightL(0,0);
			break;
		case 3:
			next = new LeftL(0,0);
			break;
		case 2:
			next = new StraightPiece(0,0);
			break;
		case 1:
			next = new SquarePiece(0,0);
			break;
		case 0:
			next = new HalfPlus(0,0);
			break;
		}
		
		if(cheat != null)
		{
			next = cheat;
			cheat = null;
		}
		
		int temp = (int)(Math.random() * 3);
		for(int i=0; i<temp; i++)
		{
			next.turn(null);
		}
		
		if(current == null)
		{
			setNewTetrimino();
		}
		
		current.setX(nextX);
		if((nextX + current.getLength())>10)
			current.setX((10-current.getLength()));
		
	}

	private boolean rowFull(int row)
	{
		
		for(int i=0; i<board[row].length; i++)
		{
			if(board[row][i]==null)
				return false;
		}
		return true;
	}
	
	private boolean rowEmpty(int row)
	{
		for(int i=0; i<board[row].length; i++)
		{
			if(board[row][i]!=null)
				return false;
		}
		return true;
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}

	public int getScore()
	{
		return score;
	}
	
	public void componentHidden(ComponentEvent e)
	{
		if(!paused)
		{
			pause();
			holdPause = true;
		}
		else
		{
			holdPause = false;
		}
	}
	
	public void componentMoved(ComponentEvent e){ }
	
	public void componentResized(ComponentEvent e)
	{
		Tetrimino.setSideLength(min(((this.getSize().height - 42)/20),this.getSize().width/21));
		boardLength = Tetrimino.sideLength * 10;
		sideBuffer = (int)(Tetrimino.sideLength * 5.5); 
		topBox = Tetrimino.sideLength * 2; //Defines how far form the top this box is
		boxHeight = Tetrimino.sideLength * 7;//Describes the height of the boxes
		bottomBox = topBox + boxHeight + Tetrimino.sideLength *2;
		this.setSize(new Dimension(boardLength + 2* sideBuffer+2, Tetrimino.sideLength * 20 + 42));
		repaint();
	}
	
	private int min(int a, int b)
	{
		if(a>b)
			return b;
		return a;
	}
	
	public void componentShown(ComponentEvent e)
	{
		if(holdPause)
		{
			pause();
			holdPause = false;
		}
	}
	
	public static void main(String[] args)
	{
		TetrixBoard tb = new TetrixBoard();
	}
}