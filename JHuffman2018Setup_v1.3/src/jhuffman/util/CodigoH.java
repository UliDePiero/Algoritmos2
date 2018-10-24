package jhuffman.util;

public class CodigoH
{
	int arr[] = new int[128];
	int len = 0;
	
	public int getBitAt(int i)
	{
		return arr[i];
	}

	public int getLength()
	{
		return len;
	}
	
	public void fromString(String sCod)
	{	
		for (int i=0;i<sCod.length();i++)
		{
			if (sCod.charAt(i) == 48)
			{
				arr[i] = 0;
			}
			else
			{
				arr[i] = 1;
			}
		}
		len = sCod.length();
	}
}