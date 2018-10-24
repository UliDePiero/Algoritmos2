package jhuffman.util;

import java.io.FileOutputStream;
//import java.io.RandomAccessFile;

public class BitWriter
{
	//private RandomAccessFile raf = null;
	
	private FileOutputStream fos=null;

	private String sBuffer="";
	private int b;
	
	public BitWriter(String filename)
	{
		// programar aqui		
	}
	
	public BitWriter(FileOutputStream fos)
	{
		this.fos=fos;
	}
	
	public void writeBit(int bit)
	{
		try
		{
			// concateno el bit
			sBuffer+=(b==1)?'1':'0';
			
			if( sBuffer.length()==8 )
			{
				int x = Integer.parseInt(sBuffer, 2);
				fos.write(x);
				sBuffer="";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}	
	}
	
	public void flush()
	{
		try
		{
			if( fos==null )
			{
				return;
			}
			

			// completo con ceros
			if( sBuffer.length()>0 )
			{
				sBuffer=rpad(sBuffer, 8, '0');
			}
			
			// grabo los bits que no completaron 1 byte
			if( sBuffer.length()>0 )
			{
				int x = Integer.parseInt(sBuffer, 2);
				fos.write(x);
			}
			
			sBuffer="";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
	
	public void writeLength(long n)
	{
		String b=Long.toBinaryString(n);
		b=lpad(b, 8*8, '0');
		for(int i=0; i<b.length(); i++)
		{
			writeBit(b.charAt(i)-'0');
		}
	}
	
	public static String replicate(int n, char c)
	{
		String x="";
		for(int i=0; i<n; i++)
		{
			x+=c;
		}
		
		return x;
	}
	
	public static String lpad(String s, int n, char c)
	{
		String ret=replicate(n-s.length(),c)+s;
		return ret.substring(0,n);
	}
	
	public static String rpad(String s, int n, char c)
	{
		String ret=s+replicate(n-s.length(),c);
		return ret.substring(0,n);
	}
	
	public void close()
	{
		// programar aqui		
	}
}
