
public enum Direction
{
	Down(0,1),
	Left(-1,0),
	Right(1,0),
	None(0,0);
	
	private int dx;
	private int dy;
	
	Direction(int deltaX, int deltaY)
	{
		dx = deltaX;
		dy = deltaY;
	}
	
	public int dx()
	{
		return dx;
	}
	
	public int dy()
	{
		return dy;
	}
	
	
}
