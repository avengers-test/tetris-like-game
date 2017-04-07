import java.awt.*;

public class StraightPiece extends Tetrimino
{

	public StraightPiece(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{true, true,true, true}};
		setShape(temp);
		setColor(Color.CYAN);
	}
}
