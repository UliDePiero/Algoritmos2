package jhuffman.util;

import jhuffman.ds.Node;

public class ListaH 
{
	Node p;
	
	public Node removeFirstNode()
	{
		Node aux = p;
		//p = p.getSig();
		p = p.getDer();
		return aux;
	}

	public void addNode(Node n)
	{
		Node nuevo= new Node();
		nuevo.setC(n.getC());
		nuevo.setN(n.getN());
		nuevo.setDer(n.getDer());
		nuevo.setIzq(n.getIzq());
		//nuevo.setSig(null); 
		Node ant = null;
		Node aux = p;
		while( aux!=null && compareNode(aux, n)<=0)
		{
			ant = aux;
			//aux = aux.getSig();
			aux = aux.getDer();
		}
		if( ant==null )
		{
			p = nuevo;
		}
		else
		{
			//ant.setSig(nuevo);
			ant.setDer(nuevo);
		}
		//nuevo.setSig(aux);
		nuevo.setDer(aux);
	}
	
	public ArbolH toTree()
	{		
		Node izq= removeFirstNode();
		int i = 257;
		while(p!=null)
		{
			Node der = removeFirstNode();
			Node aux = new Node();
			aux.setDer(izq);
			aux.setIzq(der);
			aux.setN(izq.getN()+der.getN());
			aux.setC(i);
			addNode(aux);
			izq = removeFirstNode();
			i++;
		}
		ArbolH arbol = new ArbolH();
		arbol.setRoot(izq);
		return arbol;
	}
	
	public long compareNode(Node n1, Node n2)
	{
		return n1.getN()<n2.getN()?-1:
			   n1.getN()>n2.getN()?1:
			   n1.getC()<n2.getC()?0:1;
	}
}