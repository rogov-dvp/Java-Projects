package parts;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class testing {
	public static void main(String[] args) {
		//byte: 8 bits
		
		System.out.println(5 + -1);
/*		byte a = 0b00001000; //0011 1100       value: 
							 //1100 0011
		byte b = 0b00000100; //0000 1101	   value: 13
		byte c = 0b00000011; //0111 1111 max
		System.out.println(a&b);		//12
		System.out.println(a|b|c);		//13
		System.out.println(a^b);		//1
		System.out.println(a);
		System.out.println(~a);			//-61
		System.out.println(~c);*/
		List<Integer> array = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
		
		Collections.shuffle(array);
		System.out.println(array.size());
		}	

}

