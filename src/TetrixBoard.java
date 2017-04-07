import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import sun.audio.*;

public class TetrixBoard extends JFrame implements KeyListener
{
	
	private Tetris currentGame;
	private Clip clip;
	private boolean isMusicPlaying;
	private boolean isMusicTetris;
	
	public TetrixBoard()
	{
		currentGame = new Tetris();
		this.add(currentGame);
		this.setSize(currentGame.getSize());
		//this.setResizable(false);
		this.setVisible(true);
		this.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(20*22+2,20*21+42));
		
		try
		{
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("AudioOne.wav"));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
			isMusicPlaying = true;
			isMusicTetris = true;
		}
		catch(Exception e)
		{
			System.out.println("This will not work");
		}
		
	}
	
	public void keyTyped(KeyEvent e){	}
	
	public void keyReleased(KeyEvent e)	{	}
	
	public void keyPressed(KeyEvent e) 
	{
		if((currentGame.isPaused() || currentGame.isGameOver()) && e.getKeyCode() == KeyEvent.VK_N)
		{
			this.remove(currentGame);
			currentGame = new Tetris();
			this.add(currentGame);
			this.repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_M)
		{
			if(isMusicPlaying)
				clip.stop();
			else
				clip.loop(clip.LOOP_CONTINUOUSLY);
			
			isMusicPlaying = !isMusicPlaying;
		}
		if(e.getKeyCode() == KeyEvent.VK_V)
		{
			if(isMusicPlaying)
				clip.stop();
			try
			{
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("AudioTwo.wav"));
				if(!isMusicTetris)
					inputStream = AudioSystem.getAudioInputStream(new File("AudioOne.wav"));
				clip = AudioSystem.getClip();
				clip.open(inputStream);
				clip.loop(clip.LOOP_CONTINUOUSLY);
				isMusicPlaying = true;
				isMusicTetris = !isMusicTetris;
			}
			catch(Exception f){}
		}
		else
			currentGame.keyPressed(e);
		
	}

}
