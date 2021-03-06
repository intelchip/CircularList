package circularlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularListArrayBased<E> implements CircularList<E> {
	/**
	 * @property {E[]} circularList			- Our array container of objects
	 */
	private E[] circularList;
	/**
	 * @property {int} circularListSize 	- Keeps the tally of how many items are in the list
	 */
	private int circularListSize;

	/**
	 * Default Constructor
	 */
	@SuppressWarnings("unchecked")
	public CircularListArrayBased() {
		this.circularList = (E[]) new Object[1];
		this.circularListSize = 0;
	}

	/**
	 * Determines whether a list is empty. 
	 * @return {boolean} returns true if the list is empty, otherwise false
	 */
	public boolean isEmpty() {
		return this.circularListSize == 0;
	}

	/**
	 * Determines the length of a list.	 
	 * @return {int} the number of elements in the list without wrapping
	 */
	public int size() {
		return this.circularListSize;
	}

	/**
	 * Removes all elements from the list.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		this.circularListSize = 0;
		this.circularList = (E[]) new Object[1];
	}


	/**
	 * Adds a new item to the end of the list. 
	 * @param {E} item the new item to add
	 * @return {boolean} returns true if the list was modified
	 */
	@SuppressWarnings("unchecked")
	public boolean add(E item) {

		if (item == null)
			return false;

		E[] newCircularList = (E[]) new Object[this.size() + 1];

		for (int i = 0; i < this.size(); i++) {
			newCircularList[i] = this.get(i);
		}

		// add new item
		this.circularList = newCircularList;
		this.add(this.circularListSize++, item);

		return true;
	}

	/**
	 * Adds a new item to the list at position index. Other items are shifted,
	 * not overwritten. 
	 * @param {int} index where to add the new item
	 * @param  {E} item the new item to add
	 * @throws IndexOutOfBoundsException if index is negative
	 */
	public void add(int index, E item) throws IndexOutOfBoundsException {

		if (index < 0)
			throw new IndexOutOfBoundsException("The index provided is a negative value!");
		
		this.circularList[index] = item;
	}


	/**
	 * Remove and return the item at the given index. 
	 * @param {int} index the position of the item to remove
	 * @return {E} the item that was removed
	 * @throws IndexOutOfBoundsException if index is negative
	 */
	public E remove(int index) throws IndexOutOfBoundsException {

		// Throw an exception if the index is negative
		if (index < 0)
			throw new IndexOutOfBoundsException("The index provided is a negative value!");
		
		// item to remove
		E itemToRemove = this.get(index);

		// create a tempList
		CircularList<E> tempList = new CircularListArrayBased<E>();

		// populate the tempList with all the items excluding the item we'll delete
		for (E item : this) {
			if (item != itemToRemove)
				tempList.add(item);
		}
		
		// empty the current list
		this.clear();
		
		// populate this list with the tempList's data
		for (E item : tempList)
			this.add(item);

		return itemToRemove;
	}

	/**
	 * Retrieve the item at the given index without altering the list. 
	 * @param {int} index the position of the item to retrieve
	 * @return {E} the item at position index 
	 * @throws IndexOutOfBoundsException if index is negative or list is empty
	 */
	public E get(int index) throws IndexOutOfBoundsException {

		// Throw an exception if the index is a negative value or the list is empty
		if ( index < 0 || this.isEmpty())
			throw new IndexOutOfBoundsException("The index provided is a negative value or the List is empty!");

		return this.circularList[index];
	}

	/**
	 * Generate an iterator for the list. The iterator should visit the items in
	 * a circular pattern. As long as the list is not empty, it should never
	 * stop.
	 */
	public Iterator<E> iterator() {

		final CircularListArrayBased<E> circularList = this;

		Iterator<E> itr = new Iterator<E>() {
			
			// the next array index to return
			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < circularList.size();
			}

			@Override
			public E next() {
				if (!hasNext())
					throw new NoSuchElementException();

				return circularList.get(index++);
			}

			@Override
			public void remove() {
				circularList.remove(index);
			}

		};

		return itr;
	}
}
