import java.awt.*;

public class SquarePiece extends Tetrimino 
{
	
	public SquarePiece(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{true, true},
				 			{true, true}};
		setShape(temp);
		setColor(Color.YELLOW);
	}

}
