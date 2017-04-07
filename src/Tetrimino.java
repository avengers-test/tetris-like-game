import java.awt.*;

public abstract class Tetrimino 
{
	private int x, y;
	public static int sideLength = 40;
	private int length, height;
	private boolean[][] shape;
	private Color color = Color.BLACK;
		
	public Tetrimino(int m, int n)
	{
		x = m;
		y = n;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int m)
	{
		x=m;
	}
	
	public void setY(int m)
	{
		y=m;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setColor(Color uColor)
	{
		color = uColor;
	}
	
	public int getSideLength()
	{
		return sideLength;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setShape(boolean[][] uShape)
	{
		shape = new boolean[uShape.length][uShape[0].length];
		for(int i=0; i< shape.length; i++)
		{
			for(int j=0; j<shape[i].length; j++)
			{
				shape[i][j] = uShape[i][j];
			}
		}
		
		height = shape.length;
		length = shape[0].length;
	}
	
	public boolean[][] getShape()
	{
		return shape;
	}
	
	public boolean check(Color[][] check, Direction d)
	{
		if(y + height + d.dy() > 20 || y + d.dy() < 0 || x + length + d.dx() > 10 || x + d.dx() < 0)
			return false;
		
		for(int i=0; i<length; i++)
			for(int j=0; j<height; j++)
			{
				if(shape[j][i] && check[y + d.dy() + j][x + d.dx() + i] != null)
					return false;
			}
		
		return true;
	}

	public void paint(Graphics g)
	{
		for(int i=0; i<height; i++)
			for(int j=0; j<length; j++)
			{
				if(shape[i][j])
				{		
					g.setColor(Color.BLACK);
					g.fillRect(Tetris.sideBuffer +(x+j)*sideLength, (y+i)*sideLength, sideLength, sideLength);
					g.setColor(color);
					g.fillRect(Tetris.sideBuffer +(x+j)*sideLength + sideLength/32 + 1 , (y+i)*sideLength + sideLength/32 + 1, (sideLength * 15)/16 - 2, (sideLength * 15)/16 - 2);	
				}
			}
	}
	
	public void paintHollow(Graphics g)
	{
		for(int i=0; i<height; i++)
			for(int j=0; j<length; j++)
			{
				if(shape[i][j])
				{		
					g.setColor(new Color(255, 215, 0));
					g.fillRect(Tetris.sideBuffer -(sideLength/32 + 1) +(x+j)*sideLength, (y+i)*sideLength - (sideLength/32 + 1), sideLength + 2 * (sideLength/32 + 1), sideLength + 2* (sideLength/32 + 1));
					g.setColor(Color.GRAY);
					g.fillRect(Tetris.sideBuffer +(x+j)*sideLength + sideLength/32 + 1 , (y+i)*sideLength + sideLength/32 + 1, (sideLength * 15)/16 - 2, (sideLength * 15)/16 - 2);	
				}
			}
	}
	
	public void paint(Graphics g, boolean hold)
	{
		if(!hold)
		{
			for(int i=0; i<height; i++)
				for(int j=0; j<length; j++)
				{
					if(shape[i][j])
					{		
						g.setColor(Color.BLACK);
						g.fillRect(Tetris.sideBuffer + Tetris.boardLength + Tetrimino.sideLength*3/4 +j*sideLength, Tetris.topBox + Tetrimino.sideLength * 2+i*sideLength, sideLength, sideLength);
						g.setColor(color);
						g.fillRect(Tetris.sideBuffer + Tetris.boardLength + Tetrimino.sideLength*3/4 + sideLength/32 + 1 +j*sideLength, Tetris.topBox + Tetrimino.sideLength * 2 + sideLength/32 + 1+i*sideLength, (sideLength * 15)/16 - 2, (sideLength * 15)/16 - 2);	
					}
				}
		}
		else
		{
			for(int i=0; i<height; i++)
				for(int j=0; j<length; j++)
				{
					if(shape[i][j])
					{		
						g.setColor(Color.BLACK);
						g.fillRect(3*sideLength/4 +j*sideLength, Tetris.topBox + Tetrimino.sideLength * 2+i*sideLength, sideLength, sideLength);
						g.setColor(color);
						g.fillRect(3*sideLength/4 + sideLength/32 + 1 +j*sideLength, Tetris.topBox + Tetrimino.sideLength * 2 + sideLength/32 + 1+i*sideLength, (sideLength * 15)/16 - 2, (sideLength * 15)/16 - 2);	
					}
				}
		}
	}
	
	
	public void addTo(Color[][] board)
	{
		for(int i=0; i<height; i++)
			for(int j=0; j<length; j++)
			{
				if(shape[i][j])
				{
					board[y+i][x+j] = color;
				}
			}
	}
	
	public void turn(Color[][] check)
	{
		boolean[][] temp = new boolean[shape[0].length][shape.length];
		if((x/sideLength+ height)>10)
			x = (10 - height) * sideLength;
		if(y/40 + length > 20)
			y= (20 - length) * sideLength;
		for(int i=0; i< shape.length; i++)
		{
			for(int j=0; j< shape[i].length; j++)
			{
				temp[j][shape.length - 1 - i] = shape[i][j];
			}
		}
		
		boolean bad = true;
		if(!checkTurn(check, temp))
		{
			int originalY = y;
			int originalX = x;
			for(int i=0; bad && i<length; i++)
			{
				if(y>0)
				{
					y --;
					if(checkTurn(check, temp))
					{
						bad = false;
					}
				}
			}
			if(bad)
				y = originalY;
			
			for(int i=0; bad && i< height; i++)
			{
				if(bad && x+ height <9)
				{
					x++;
					if(checkTurn(check, temp))
					{
						bad = false;	
					}
				}
			}
			if(bad)
				x = originalX;
			
			for(int i=0; bad && i< height; i++)
			{
				if(bad && x>0)
				{
					x--;
					if(checkTurn(check, temp))
						bad = false;
				}	
			}
			if(bad)
				x = originalX;
			
		}
		else
			bad = false;
		
		if(!bad)
		{
			shape = temp;
			height = shape.length;
			length = shape[0].length;
		}		
	}
	
	public boolean checkTurn(Color[][] check, boolean[][] temp)
	{
		if(check == null)
			return true;
		for(int i=0; i<height ; i++)
			for(int j=0; j<length ; j++)
			{
				if((i+x)>=10 || (j+y)>=20 ||(temp[j][i] && check[y + j][x + i] != null))
				{
					return false;
				}
			}
		return true;
	}
	
	public static void setSideLength(double n)
	{
		if(n>40)
			sideLength = 40;
		else if(n<20)
			sideLength = 20;
		else
			sideLength = (int) n;
	}
}