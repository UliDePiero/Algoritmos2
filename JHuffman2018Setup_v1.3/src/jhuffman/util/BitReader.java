package jhuffman.util;

import java.io.FileInputStream;

//import java.io.RandomAccessFile;

public class BitReader
{
	//private RandomAccessFile raf = null;
	private FileInputStream fis=null;
	private String sBuffer="";
	private int bitNo = 0;
	
	public BitReader(String filename)
	{
		// programar aqui		
	}
	
	public BitReader(FileInputStream fis)
	{
		this.fis=fis;
	}
	
	public int readBit()
	{
		try
		{
			if( sBuffer.length()==0 || bitNo==8 )
			{
				int b=fis.read();
				
				if( b>=0 )
				{
					sBuffer = Integer.toBinaryString(b);
					sBuffer=BitWriter.lpad(sBuffer, 8, '0');
					bitNo=0;
				}
				else
				{
					return -1;
				}
			}
			
			return sBuffer.charAt(bitNo++)-'0';
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		//return 0;
	}
	
	public long readLength()
	{
		String bin="";
		int sizeLong=8*8;
		for(int i=0; i<sizeLong; i++)
		{
			bin+=readBit();
		}
		
		return Long.parseLong(bin, 2);
	}
	
	public boolean eof()
	{
		// programar aqui
		return false;
	}
		
	public void close()
	{
		// programar aqui		
	}
}
