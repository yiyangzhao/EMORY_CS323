//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
//A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - YIYANG ZHAO
//This code uses some code from a code supplied by the instructor per homework instruction
//https://github.com/emory-courses/cs323/blob/master/src/main/java/edu/emory/mathcs/cs323/

package sort;

public class ProbabilityBucketSort extends AbstractBucketSort<Double>{

	
	public ProbabilityBucketSort(int size) {
		
	
		super(10, true);

	}
	


	
	protected int getBucketIndex(Double key) {
		
		return (int)(key*10);
		
	}
	
}
