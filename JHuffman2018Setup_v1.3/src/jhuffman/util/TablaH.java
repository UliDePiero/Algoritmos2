package jhuffman.util;

import jhuffman.ds.Node;

public class TablaH
{
	public class campos
	{
		long n = 0;
		CodigoH cod = new CodigoH();
	}
	
	public class Table
	{
		private campos arr[] = new campos[256];
	
		public Table()
		{
			for (int i=0;i<256;i++)
			{
				arr[i] = new campos();
			}
		}
	}
	
	Table tabla = new Table();
	
	public void addCount(int c)
	{
		tabla.arr[c].n++;
	}
	
	public long getCount(int c)
	{
		return tabla.arr[c].n;
	}
	
	public void setCount(int c, long o) //Agregado para resolver tema de ocurrencias.
	{
		tabla.arr[c].n = o;
	}
	
	public ListaH createSortedList()
	{
		ListaH lista = new ListaH();
		Node nodo = new Node();
		for (int i=0;i<256;i++)
		{
			if (getCount(i)>0)
			{
				nodo.setC(i);
				nodo.setN(getCount(i));
				lista.addNode(nodo);
			}
		}
		return lista;
	}
	
	public void loadHuffmanCodes(ArbolH tree)
	{
		CodigoH codHuffman = new CodigoH();
		Node hoja = tree.next(codHuffman);
		while (hoja != null)
		{
			int c = hoja.getC();
			//tabla.arr[c].cod = codHuffman;
			for(int i=0;i<codHuffman.len;i++)
			{
				tabla.arr[c].cod.arr[i] = codHuffman.getBitAt(i);
			}
			tabla.arr[c].cod.len = codHuffman.getLength();
			hoja = tree.next(codHuffman);
		}
	}
	
	public CodigoH getCode(int c)
	{
		return tabla.arr[c].cod;
	}
}