package com.brick_breaker.java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.JPanel;

//GamePanel Class
public class Gameplay extends JPanel implements KeyListener, ActionListener
{
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 20;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private double ballxDir = -1.5;
	private double ballyDir = -3;

	
	private MapGenerator map;
	
	public Gameplay()
	{
		map = new MapGenerator(4, 5);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		//adding background
		g.setColor(Color.black);
		g.fillRect(1,  1,  692,  592);
		
		//drawing map
		map.draw((Graphics2D) g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(690, 0, 3, 592);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Score: "+score, 570, 30);
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if(totalBricks <= 0)
		{
			play = false;
			ballxDir = 0;
			ballyDir = 0;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won the Game!!",  230, 300);
			g.drawString("Your Score is : "+score, 230, 340);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("press SPACE to restart the game", 230, 380);
		}
		
		if(ballPosY > 570)
		{
			play = false;
			ballxDir = 0;
			ballyDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over!!",  230, 300);
			g.drawString("Your Score is : "+score, 230, 340);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("press SPACE to restart the game", 230, 380);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		timer.start();
		
		if(play)
		{
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
			{
				ballyDir = -ballyDir;
			}
			
			A: for(int i=0; i<map.map.length; i++)
			{
				for(int j=0; j<map.map[0].length; j++)
				{
					if(map.map[i][j] > 0)
					{
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width)
							{
								ballxDir = -ballxDir;
							}
							else
							{
								ballyDir = -ballyDir;
							}
							break A;
						}
					}
				}
			}
			
			ballPosX += ballxDir;
			ballPosY += ballyDir;
			if(ballPosX < 0)
			{
				ballxDir = -ballxDir;
			}
			if(ballPosY < 0)
			{
				ballyDir = -ballyDir;
			}
			if(ballPosX > 670)
			{
				ballxDir = -ballxDir;
			}
		
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e){	}
	public void keyReleased(KeyEvent e) {	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(!play)
			{
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballxDir = -1.5;
				ballyDir = -3;
				
				playerX = 310;
				score = 0;
				totalBricks = 20;
				map = new MapGenerator(4, 5);
				
				repaint();
				
			}
		}
	}
	
	public void moveRight()
	{
		play = true;
		playerX += 20;
	}
	
	public void moveLeft()
	{
		play = true;
		playerX -= 20;
	}
}
