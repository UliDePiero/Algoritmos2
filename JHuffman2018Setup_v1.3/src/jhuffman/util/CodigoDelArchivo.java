package jhuffman.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import jhuffman.util.BitReader;
import jhuffman.util.BitWriter;

public class CodigoDelArchivo
{
	private File file = null;
	private FileOutputStream fos = null;
	private FileInputStream fis = null;
	
	public void setFilename(String f)
	{		
		file = new File(f);
	}
	
	public void save(TablaH table)
	{
		try
		{
			fos = new FileOutputStream(file);
			//UBuffFile uFile = new UBuffFile(bos);
			BitWriter uFile = new BitWriter(fos);
			
			for(int i=0; i<256; i++)
			{
				if(table.getCount(i)>0)
				{
					fos.write(i); 		//Grabo el caracter.	
					int nCod = table.getCode(i).getLength(); 							
					fos.write(nCod); 	// Grabo la longitud del codigo.
					uFile.writeLength(table.getCount(i));		//Grabo las ocurrencias del caracter.
					for(int j=0; j<nCod; j++)
					{
						uFile.writeBit(table.getCode(i).getBitAt(j)); 	//Grabo el codigo bit a bit.
					}	
					uFile.flush(); //Completo el byte con ceros.
				}
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
				if(fos!=null) fos.close();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
				throw new RuntimeException(e2);
			}
		}
	}

	public ArbolH load()
	{
		ArbolH tree = new ArbolH();
		TablaH table = new TablaH();
		try
		{
			fis = new FileInputStream(file);
			BitReader uFile = new BitReader(fis);
						
			//Cargo la tabla:	
			int c, nCod, bit; 
			c = fis.read(); 		//Leo que caracter es.
			
			while (c>=0)
			{			
				nCod = fis.read(); 	//Leo la longitud del codigo.
				table.getCode(c).len = nCod;
				long ocurrencias = uFile.readLength();	//Leo ocurrencias.
				table.setCount(c,ocurrencias);
										
				for(int j=0; j<nCod; j++)
				{						
					//Leo el codigo bit a bit.
					bit = uFile.readBit();			
					table.getCode(c).arr[j] = bit;
				}	
				if (nCod%8 != 0)
				{
					for(int j=nCod;j<(1+nCod/8)*8;j++) //Completo la lectura del byte antes de leer el otro caracter.
					{
						bit = uFile.readBit();
					}
				}
				c = fis.read();								
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
				if(fis!=null) fis.close();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
				throw new RuntimeException(e2);
			}
		}
		
		//Creo lista:
		ListaH list = new ListaH();
		list = table.createSortedList();
		
		//Armo arbol:
		tree = list.toTree();
		
		
		return tree;
	}
}