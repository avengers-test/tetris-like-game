import java.awt.Color;


public class ZZag extends Tetrimino
{
	public ZZag(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{true, true, false},
							{false,true, true}};
		setShape(temp);
		setColor(Color.RED);
	}
}
