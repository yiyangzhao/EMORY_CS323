//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
//A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - YIYANG ZHAO
//This code uses some code from a code supplied by the instructor per homework instruction
//https://github.com/emory-courses/cs323/blob/master/src/main/java/edu/emory/mathcs/cs323/


//THIS CODE REQUIRES DSUtils put in utils package
//This code passed the SortTest


package sort;


public class ShellSortHibbard<T extends Comparable<T>> extends AbstractSort<T>
{
	@Override
	public void sort(T[] array, int beginIndex, int endIndex)
	{
		int h = getMaxH(endIndex - beginIndex);
		
		while (h >= 1)
		{
			//Insetion sort with the gap of h
			sort(array, beginIndex, endIndex, h);
			h = getNextH(h);
		}
	}
	
	protected void sort(T[] array, int beginIndex, int endIndex, final int h)
	{
		int beginH = beginIndex + h;
		
		for (int i=beginH; i<endIndex; i++)
			for (int j=i; j>=beginH && compareTo(array, j, j-h) < 0; j-=h)
				swap(array, j, j-h);
	}
	
	//Only the sequence changes. Instead of *3. it goes *2
	public int getMaxH(int n)
	{
		final int upper = n / 2;	//Esitmate the upper bound of the sequence
		int h = 1, t;
		
		while (true)
		{
			t = 2*h + 1;			//Find the next number in the sequence
			if (t > upper) break;
			h = t;
		}
		
		return h;
	}
	
	public int getNextH(int h)
	{
		return h / 2;
	}
}

