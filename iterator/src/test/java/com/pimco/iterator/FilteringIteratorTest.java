package com.pimco.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class FilteringIteratorTest {
		
	private IObjectTest fizzTest = new IObjectTest() {
		@Override
		public boolean test(Object o) {
			
			if (o == null) {
				return false;
			}
			
			return o.toString().startsWith("fizz");
		}
	};

	@Test
	public void testHasNextMatch() {
		List<String> testList = Arrays.asList("fizz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		Assert.assertTrue(fIterator.hasNext());
	}
	
	@Test
	public void testHasNextNoMatch() {
		List<String> testList = Arrays.asList("fuzz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		Assert.assertFalse(fIterator.hasNext());
	}
	
	@Test
	public void testHasNextEmpty() {
		List<String> testList = new ArrayList<>();
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		Assert.assertFalse(fIterator.hasNext());
	}
	
	@Test
	public void testNextMatch() {
		List<String> testList = Arrays.asList("fizz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		Assert.assertEquals("fizz", fIterator.next());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testNextNoMatch() {
		List<String> testList = Arrays.asList("buzz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		fIterator.next();
	}
	
	@Test
	public void testNextNextMatch() {
		List<String> testList = Arrays.asList("buzz", "fizzbuzz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		Assert.assertEquals("fizzbuzz", fIterator.next());
	}
	
	@Test
	public void testNextNull() {
		IObjectTest fizzBuzzTest = new IObjectTest() {
			@Override
			public boolean test(Object o) {
				return o == null;
			}
		};
		
		List<String> testList = Arrays.asList("buzz", null, "fizzbuzz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzBuzzTest);
		Assert.assertNull(fIterator.next());
	}

	@Test
	public void testHasNextAndNext() {
		List<String> testList = Arrays.asList("fizz", "buzz", "bizz", "fizz", null, "fizzbizz", "buzz");
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);

		Assert.assertTrue(fIterator.hasNext());
		Assert.assertEquals(fIterator.next(), "fizz");
		Assert.assertTrue(fIterator.hasNext());
		Assert.assertEquals(fIterator.next(), "fizz");
		Assert.assertTrue(fIterator.hasNext());
		Assert.assertEquals(fIterator.next(), "fizzbizz");
		Assert.assertFalse(fIterator.hasNext());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRemove() {
		List<String> testList = new ArrayList<String>(Arrays.asList("buzz", "fizzbuzz"));
		FilteringIterator<String> fIterator = new FilteringIterator<String>(testList.iterator(), fizzTest);
		fIterator.remove();
	}
}
