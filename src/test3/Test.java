package test3;

import org.apache.lucene.util.BytesRef;

public class Test {

	public static void main(String[] args) {
		BytesRef br = new BytesRef(new byte[]{'a','v','c'},0,1);
		
		System.out.println(br.utf8ToString());
		
	}

}
