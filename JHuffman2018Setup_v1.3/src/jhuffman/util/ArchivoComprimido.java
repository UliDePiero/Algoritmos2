package jhuffman.util;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import jhuffman.ds.Node;

import java.io.File;

//import jhuffman.util.UBuffFile;

public class ArchivoComprimido
{
	private File file = null;
	//private BufferedInputStream bis = null;
	//private BufferedOutputStream bos = null;
	private FileInputStream fis=null;
	private FileOutputStream fos=null;
	
	public void setFilename(String filename)
	{		
		file = new File(filename);
	}
	
	public void save(File fi, TablaH table)
	{
		try
		{
			fis = new FileInputStream(fi.getName());
			fos = new FileOutputStream(file);
			//bis = new BufferedInputStream(new FileInputStream(fi.getFilename()));
			//bos = new BufferedOutputStream(new FileOutputStream(file));
			//UBuffFile uFileOut = new UBuffFile(bos);
			BitWriter uFileOut = new BitWriter(fos);
			
			//Grabo la longitud del archivo original:
			long longitudFile = fi.length();
			uFileOut.writeLength(longitudFile);
			
			//int c = bis.read();
			int c = fis.read();
			
			while (c>=0)
			{			
				for(int i=0; i<table.getCode(c).getLength(); i++)
				{
					uFileOut.writeBit(table.getCode(c).getBitAt(i));	
				}			
				
				//c = bis.read();
				c = fis.read();
			}
			uFileOut.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				/*if(bos!=null) bos.close();
				if(bis!=null) bis.close();*/
				if(fos!=null) fos.close();
				if(fis!=null) fis.close();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
				throw new RuntimeException(e2);
			}
		}
	}
	
	public void restore(File fi, ArbolH tree)
	{
		try
		{
			fis = new FileInputStream(file);
			fos = new FileOutputStream(fi.getName());			
			//bis = new BufferedInputStream(new FileInputStream(file));
			//bos = new BufferedOutputStream(new FileOutputStream(fi.getFilename()));
			//UBuffFile uFileIn = new UBuffFile(bis);
			BitReader uFileIn = new BitReader(fis);
			
			long longitudFile = uFileIn.readLength();
			Node nodo = tree.getRoot();
			
			for (long l=0; l<longitudFile; l++)
			{
				while (nodo.getIzq()!=null || nodo.getDer()!=null)
				{
					int bit = uFileIn.readBit();
					if (bit==0)			
					{
						nodo = nodo.getIzq();						
					}
					else
					{
						nodo = nodo.getDer();
					}
				}
				//bos.write(nodo.getC());	
				fos.write(nodo.getC());
				nodo = tree.getRoot();
			}					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				//if(bos!=null) bos.close();
				if(fos!=null) fos.close();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
				throw new RuntimeException(e2);
			}
		}		
	}
}