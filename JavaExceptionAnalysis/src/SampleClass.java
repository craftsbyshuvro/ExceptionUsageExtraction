import java.awt.GraphicsConfiguration;
import java.awt.Polygon;
import java.util.Random;

import javax.swing.JFrame;

public class SampleClass {

   public static void main(String args[]) throws Exception{
  	 Parser objParser = new Parser();

	      try {
	         int a[] = new int[2];
	         System.out.println("Access element three :" + a[1]);
	      } 
	      catch (ArrayIndexOutOfBoundsException sw) 
	      {
	    	 
	    	 String name;
	         System.out.println("Exception thrown  :");
	         throw new Exception("ads");
	      } 
	      catch (Exception e) 
	      {
	         System.out.println("Exception thrown  :"+ e);
	         throw new ArithmeticException("not valid");  
		  }
	      catch(Error ex) {
	    	  
	      }
	      finally{
	    	  if(1==1) {
					System.out.println("Condition is true.");
	    	  }
				System.out.println("This is Finally Block.");
	      }
	      System.out.println("Out of the block");
	   }
}