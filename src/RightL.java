
import java.awt.Color;

public class RightL extends Tetrimino
{

	public RightL(int m, int n)
	{
		super(m,n);
		boolean[][] temp = {{false, false, true},
							{true,true, true}};
		setShape(temp);
		setColor(Color.ORANGE);
	}
}
