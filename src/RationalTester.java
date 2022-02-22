public class RationalTester
{
         	
   public static void main(String[] args) throws Exception
   {
//      Rational n1 = new Rational(1, 3);
//      Rational n2 = new Rational(1, 4);
//      Rational n2b = new Rational(2, 8);
//      System.out.println(n1+" equals? " +  n2+ "  : "+n1.equals(n2));
//      System.out.println(n2+" equals? " +  n2b+ "  : "+n2.equals(n2b));
//
//   
//      Rational n3 = n1.add(n2);
//      System.out.println(n1+" + "+ n2 + " = " + n3); 
//        
//      Rational n4 = new Rational(3, 5);
//      Rational n5 = n4.reciprocal();  
//      System.out.println("reciprocal of: " + n4+" is: "+ n5 );  
   	  
   	  //Create your own tests to validate your Rational class 	
      
      Rational r1 = new Rational(1, 3);
      Rational r2 = new Rational(1, 4);
      Rational r3 = new Rational(2);
      Rational r4 = new Rational(1, 3);
      Rational r5 = new Rational(3, 9);
      Rational r6 = new Rational(-4, -7);
      Rational r7 = new Rational(3, 5);
      
      
      System.out.println("r1: " + r1);
      System.out.println("r2: " + r2);
      System.out.println("r3: " + r3);
      System.out.println("r4: " + r4);
      System.out.println("r5: " + r5);
      System.out.println("r6: " + r6);
      System.out.println("r7: " + r7);
      System.out.println();
      
      System.out.println("Test Equals (same values) r1.equals(r4): " + r1.equals(r4));
      System.out.println("Test Equals (equivalent values) r1.equals(r5): " + r1.equals(r4));
      System.out.println("Test Equals (not equal) r1.equals(r2): " + r1.equals(r2));
      System.out.println();
      
      System.out.println("Test Reciprocal: " + r7 + " --> " + r7.reciprocal());
      System.out.println();
      
      //System.out.println("1/2 --> " + r1);
      printResult("add", r1, r2, new Rational(7, 12));
      printResult("subtract", r1, r2, new Rational(-1, 12));
      printResult("multiply", r1, r2, new Rational(1, 12));
      printResult("divide", r1, r2, new Rational(4, 3));
      
      
      // Part 2 - Develop your own tests for types of Rational numbers to validate the class
      
      
   }
   
   public static void printResult(String operation, Rational r1, Rational r2, Rational expected) {

	   Rational result = null;
	   String sign = "";
	  
	   if (operation.equals("add")) {
		   result = r1.add(r2);
		   sign = "+";
	   } 
	   // Complete for other cases...
	   else if (operation.equals("subtract")) {
		   result = r1.subtract(r2);
		   sign = "-";
	   } else if (operation.equals("multiply")) {
		   result = r1.multiply(r2);
		   sign = "*";
	   } else if (operation.equals("divide")) {
		   result = r1.divide(r2);
		   sign = "/";
	   }
	   
	   
	   System.out.print(operation + "\t" + r1 + " " + sign + " " + r2 + " --> " + result);
	   System.out.print("\tExpected: " + expected);
	   System.out.println("\t" + result.equals(expected));
   }
}
