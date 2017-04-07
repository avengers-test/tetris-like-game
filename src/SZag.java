import java.awt.Color;


public class SZag extends Tetrimino
{
	public SZag(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{false, true, true},
							{true,true, false}};
		setShape(temp);
		setColor(Color.GREEN);
	}
}
