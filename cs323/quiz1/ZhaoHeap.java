//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
//A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - YIYANG ZHAO
//This code uses some code from a code supplied by the instructor per homework instruction
//https://github.com/emory-courses/cs323/blob/master/src/main/java/edu/emory/mathcs/cs323/queue/BinaryHeap.java

//Import stuff 
//THIS CODE REQUIRES DSUtils put in utils package
//This code passed the PriorityQueueTest

package queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.DSUtils;
public class ZhaoHeap <T extends Comparable<T>> extends AbstractPriorityQueue<T>{

	private List<T> l_keys;
	private int n_size;
	
	//Constructor
	public ZhaoHeap()
	{
		l_keys = new ArrayList<>();
		l_keys.add(null);			//Initialize root as null
		n_size = 0;
	}

	
	//add, mostly the same
	@Override
	public void add(T key) {
		// DONE_ TODO Auto-generated method stub
		l_keys.add(key);
		terswim(++n_size);
	}
	
	//terswim, equv. to swim in Binary Heap
	//Childern to a parent k is k*3-1;k*3;k*3+1
	private void terswim(int k) {
		// DONE_ TODO Auto-generated method stub
		while (k > 1 && DSUtils.compareTo(l_keys, (k+1)/3, k) < 0)
		{
			Collections.swap(l_keys, (k+1)/3, k);
			k = (k+1)/3;
		}

	}

	@Override
	public T removeMax() {
		// DONE_ TODO Auto-generated method stub
		throwNoSuchElementException();
		Collections.swap(l_keys, 1, n_size);
		T max = l_keys.remove(n_size--);
		tersink(1);
		return max;

	}

	//Basic idea is to find the max child to a parent
	//and do swapping until parent is in adequate position
	private void tersink(int k) {
		// DONE_ TODO Auto-generated method stub
		int c, cmax;
		//c is the child with smallest index to parent p and cmax finds the child with largest key
		int p = k;
		//p is the parent
		while (p*3-1 <= n_size)  {
			c = p*3-1;
			
			//finds the largest child in key
			cmax = c;
			if (c+1 <= n_size) {
				if (DSUtils.compareTo(l_keys, cmax, c+1) < 0) cmax = c+1;
			}
			if (c+2 <= n_size) {
				if (DSUtils.compareTo(l_keys, cmax, c+2) < 0) cmax = c+2;
			}
			
			//check parent with largest child in key
			if (DSUtils.compareTo(l_keys, p, cmax) >= 0) break;
			Collections.swap(l_keys, cmax, p);
			p = cmax ;
		}		
		

	
	}

	@Override
	public int size() {
		// DONE_ TODO Auto-generated method stub
		return n_size;
	}

}
