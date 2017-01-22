package com.pimco.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteringIterator<T> implements Iterator<T> {
	
	private final Iterator<T> iterator;
	
	private final IObjectTest objectTest;
	
	private boolean hasNext;
	
	private T next;
	
	public FilteringIterator(Iterator<T> iterator, IObjectTest objectTest) {
		this.iterator = iterator;
		this.objectTest = objectTest;
		bufferNext();
	}
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public T next() {
		if(!hasNext) {
			throw new NoSuchElementException();
		}
		
		T current = next;
		bufferNext();
		
		return current;
	}
	
	private void bufferNext() {		
		while(iterator.hasNext()) {
			T o = iterator.next();
			
			if(objectTest.test(o)) {
				hasNext = true;
				next = o;
				return;
			}
		}
		
		hasNext = false;
	}
}
