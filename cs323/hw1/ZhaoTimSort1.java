//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
//A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - YIYANG ZHAO
//This code uses some code from a code supplied by the instructor 
//https://github.com/emory-courses/cs323/blob/master/src/main/java/edu/emory/mathcs/cs323/
//This program use some pieces of code from MergeSort; InsertionSort, ShellSort in Choi's GitHub

package sort;

import java.util.Arrays;



public class ZhaoTimSort1<T extends Comparable<T>> extends MergeSort<T>
{
	private T[] l_temp;	// n-extra spaces
	
	@Override
	public void sort(T[] array, int beginIndex, int endIndex)
	{
		if (beginIndex + 1 >= endIndex) return;
		
		if (endIndex + 1 - beginIndex > 64) {
			int middleIndex = beginIndex + (endIndex - beginIndex) / 2;

			//Sort left partition
			sort (array, beginIndex, middleIndex);
			//Sort Right partition
			sort (array, middleIndex, endIndex);
			//Merge partitions
			merge(array, beginIndex, middleIndex, endIndex);
		} else
		{
			InsSort(array, beginIndex, endIndex);
		}
	}
	
	private void InsSort(T[] array, int beginIndex, int endIndex) {
		int h =1;
		int beginH = beginIndex + h;
		for (int i=beginH; i<endIndex; i++)
			for (int j=i; j>=beginH && compareTo(array, j, j-h) < 0; j-=h)
				swap(array, j, j-h);
		
	}

	/**
	 * @param beginIndex the beginning index of the 1st half (inclusive).
	 * @param middleIndex the ending index of the 1st half (exclusive).
	 * @param endIndex the ending index of the 2nd half (exclusive).
	 */
	private void merge(T[] array, int beginIndex, int middleIndex, int endIndex)
	{
		int fst = beginIndex, snd = middleIndex;
		copy(array);
		
		for (int k=beginIndex; k<endIndex; k++)
		{
			if (fst >= middleIndex)						// no key left in the 1st half
				assign(array, k, l_temp[snd++]);
			else if (snd >= endIndex)					// no key left in the 2nd half
				assign(array, k, l_temp[fst++]);
			else if (compareTo(l_temp, fst, snd) < 0)	// 1st key < 2nd key
				assign(array, k, l_temp[fst++]);
			else
				assign(array, k, l_temp[snd++]);
		}
	}
	
	private void copy(T[] array)
	{
		final int N = array.length;
		n_assignments += N;
		
		if (l_temp == null || l_temp.length < N)
			l_temp = Arrays.copyOf(array, N);
		else
			System.arraycopy(array, 0, l_temp, 0, N);
	}
}
