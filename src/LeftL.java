import java.awt.Color;

public class LeftL extends Tetrimino
{

	public LeftL(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{true, true, true},
				 			{false, false, true}};
		setShape(temp);
		setColor(Color.BLUE);
	}
}
