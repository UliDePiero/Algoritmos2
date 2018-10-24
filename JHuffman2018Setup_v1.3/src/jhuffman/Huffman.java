package jhuffman;


import java.io.File;
import java.io.FileInputStream;

import jhuffman.util.ArbolH;
import jhuffman.util.ArchivoComprimido;
import jhuffman.util.CodigoDelArchivo;
import jhuffman.util.ListaH;
import jhuffman.util.TablaH;

/*
import jhuffman.def.IFileCode;
import jhuffman.def.IFileCompressed;
import jhuffman.def.IFileInput;
import jhuffman.def.ITree;
import jhuffman.def.IList;
import jhuffman.def.ITable;
*/
public class Huffman
{	
	public static void main(String[] args)
	{
		String filename = args[0];
		if( filename.endsWith(".huf") )
		{
			descomprimir(filename);
		}
		else
		{
			comprimir(filename);
		}
	}

	public static void comprimir(String filename)
	{
		// abro el archivo
		/*IFileInput fi = new IFileInput();
		fi.setFilename(args[0]);*/
		File file = new File(filename);
		FileInputStream fis = null;
		// obtengo la tabla de ocurrencias
		/*ITable table = fi.createTable();*/
		TablaH table = new TablaH();
		
		try
		{
			// abro el archivo para leer los bits
			fis = new FileInputStream(file);
						
			int c = fis.read();
			while( c>=0 )
			{
				table.addCount(c);
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

		// obtengo la lista enlazada
		ListaH list = table.createSortedList();

		// convierto la lista en arbol
		ArbolH tree = list.toTree();
		
		// asigno los codigos en la tabla
		table.loadHuffmanCodes(tree);

		// abro el archivo de codigo
		CodigoDelArchivo codeFile = new CodigoDelArchivo();
		codeFile.setFilename(filename + ".cod");

		// grabo el archivo tomando los codigos del arbol
		codeFile.save(table);

		// abro el archivo comprimido
		ArchivoComprimido compressFile = new ArchivoComprimido();
		compressFile.setFilename(filename + ".dat");

		// grabo el archivo comprimido
		compressFile.save(file,table);
	}
	
	public static void descomprimir(String filename)
	{
		// abro el archivo de codigos
		CodigoDelArchivo codeFile = new CodigoDelArchivo();
		codeFile.setFilename(filename + ".cod");

		// leo el archivo y genero la lista
		ArbolH tree = codeFile.load();

		// abro el archivo comprimido
		ArchivoComprimido compressFile = new ArchivoComprimido();
		compressFile.setFilename(filename + ".dat");
		
		// abro el archivo
		/*IFileInput fi = new IFileInput();
		fi.setFilename(filename);*/
		File file = new File(filename);
		
		// recupera el archivo original
		compressFile.restore(file,tree);
	}
}
