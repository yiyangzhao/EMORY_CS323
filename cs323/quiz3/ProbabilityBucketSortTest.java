package sort;



import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import utils.AbstractEngineComparer;
import utils.DSUtils;


public class ProbabilityBucketSortTest
{
	@Test
	public void testAccuracy()
	{
		final int ITERATIONS = 10;
		final int SIZE = 100;
		
		testAccuracy(ITERATIONS, SIZE, new ProbabilityBucketSort(SIZE));
		
	}
	
	void testAccuracy(final int ITERATIONS, final int SIZE, AbstractSort<Double> engine)
	{
		final Random rand = new Random(0);
		Double[] original, sorted;
		
		for (int i=0; i<ITERATIONS; i++)
		{
			original = DSUtils.getRandomDoubleArray(rand, SIZE);
			sorted = Arrays.copyOf(original, SIZE);
			
			engine.sort(original);
			Arrays.sort(sorted);
		
			assertTrue(DSUtils.equals(original, sorted));
		}
	}
}

