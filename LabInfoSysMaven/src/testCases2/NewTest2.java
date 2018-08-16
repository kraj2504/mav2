package testCases2;

public class NewTest2 
{
  public static void main(String[] args)
  {
	  ex obj = new ex();
	  String na = "dfsd";
	  
	  System.out.println("i = "+obj.i);
	  System.out.println("j = "+obj.j);
	  obj.meth(6,na);
  }
}

class ex
{
	int i,j;
	ex()
	{
		i= 10;
		j=5;
	}
	
	void meth(int k,String name)
	{
	  System.out.println("Result of i/j = "+(i/j)+"   Result of i%j = "+ (i%j));
	  switch(k)
	  {
		  case 1:
			  System.out.println("Hai");
			  break;
	  }
	}
}
