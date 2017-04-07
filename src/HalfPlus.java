import java.awt.Color;


public class HalfPlus extends Tetrimino
{

	public HalfPlus(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{false, true, false},
							{true,true, true}};
		setShape(temp);
		setColor(Color.MAGENTA);
	}
}
