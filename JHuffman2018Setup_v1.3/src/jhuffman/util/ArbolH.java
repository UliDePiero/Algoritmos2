package jhuffman.util;

import jhuffman.ds.Node;
import jhuffman.util.TreeUtil;;

public class ArbolH
{
	Node raiz;
	TreeUtil p;
	
	public void setRoot(Node root)
	{	
		raiz = root;
		p = new TreeUtil(raiz);
	}
	
	public Node getRoot()
	{
		return raiz;
	}
	
	public Node next(CodigoH cod)
	{
		StringBuffer buff = new StringBuffer();
		Node aux = p.next(buff);
		cod.fromString(buff.toString());
		return aux;
	}
}