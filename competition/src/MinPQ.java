@SuppressWarnings("unchecked")
public class MinPQ<T extends Comparable<T>> {
    private T[] array;
    private int nextOpen;
    private final int CAP;

    /***
     *constructor: constructs a new 
     *MinPQ with starting capacity of 10
     ***/
    public MinPQ() {
	this.CAP = 10;
	this.array = (T[]) new Comparable[CAP];
	this.nextOpen = 0;
    }

    /**
     *constructor: constructs a new MinPQ with 
     *starting capacity of cap
     ***/
    public MinPQ(int cap) {
	this.CAP = cap;
	this.array = (T[]) new Comparable[CAP];
	this.nextOpen = 0;
    }

    /***
     *@param item to be inserted into PQ
     *if the array is full after the insert, 
     *resize the array to be twice as large
     * @throws EmptyQueueException 
     ***/
    public void insert(T item) {//System.out.println("insert " + item);
//	if(nextOpen == array.length)
//	    resize(2*array.length);
//	array[nextOpen] = item;
//	swim(nextOpen);
//	nextOpen++;
    	
    	//if full, and item > min, delete min and add item
    	if(nextOpen == array.length) {
    	    if (item.compareTo(array[0]) > 0) {
    	    	try {
					delMin();
			    	array[nextOpen] = item;
			    	swim(nextOpen);
			    	nextOpen = array.length;
				} catch (EmptyQueueException e) {
					e.printStackTrace();
				}
    	    }
    	}
    	else {
		    array[nextOpen] = item;
		    swim(nextOpen);
		    nextOpen++;
		    }
    }

    /***
     *@return and remove the min item in the PQ and re-heapify
     *throw EmptyQueueException if the PQ is empty
     ***/
    public T delMin() throws EmptyQueueException {//System.out.println("delMin");
	if(isEmpty())
	    throw new EmptyQueueException();
	T min = array[0];
	nextOpen--;
	swap(0, nextOpen);
	array[nextOpen] = null;
	sink(0);
	if(nextOpen <= this.array.length/4 && this.array.length/2 >= CAP)
	    resize(this.array.length/2);
	return min;
    }

    /***
     *@return but do not remove the min item in the PQ
     *throw EmptyQueueException if the PQ is empty
     ***/
    public T getMin() throws EmptyQueueException {
	if(isEmpty())
	    throw new EmptyQueueException();
	return array[0];
    }

    /***
     *@return the number of items currently in 
     *the PQ
     ***/
    public int size() {
	return nextOpen;
    }

    /***
     *@return true if the PQ is empty and false
     *otherwise
     ***/
    public boolean isEmpty() {
	return nextOpen == 0;
    }

    /***
     *returns the underlying array
     *This method is only used for testing.
     *Do not change it, and do not use it in your
     *own code!!!
     ***/
    public T[] getArray() {
	return this.array;
    }

    /***
     *@param i the index of the element
     *that may need to swim up the heap
     ***/
    private void swim(int i) {
	if(i == 0)
	    return;
	int p = (int)((i-1)/2);
	while(p >= 0) {
	    if(array[i].compareTo(array[p]) < 0) {
		swap(i, p);
		i = p;
		p = (int)((i-1)/2);
		swim(i);
	    }
	    else 
		return;
	}
    }

    /***
     *@param i the index of the element
     *that may need to sink down the heap
     ***/
    private void sink(int i) {
	if(array[i] == null)
	    return;
	int lc = 2*i + 1;
	int rc = 2*i + 2;
	if(lc > nextOpen - 1)
	    return;
	if(rc > nextOpen - 1) {
	    if(array[i].compareTo(array[lc]) > 0) {
		swap(i, lc);
		sink(lc);
	    }
	    return;
	}
	int min = 0;
	if(array[lc].compareTo(array[rc]) <= 0)
	    min = lc;
	else
	    min = rc;
	if(array[i].compareTo(array[min]) > 0) {
	    swap(i, min);
	    sink(min);
	}
    }    

    /***
     *@param i
     *@param j
     *swap array[i] and array[j]
     ***/
    private void swap(int i, int j) {
	if(i == j)
	    return;
	T temp = array[i];
	array[i] = array[j];
	array[j] = temp;
    }

    /***
     *resize array to have the capacity
     *@param newCap
     ***/
    private void resize(int newCap) {
	T[] newArray = (T[]) new Comparable[newCap];
	for(int i = 0; i < nextOpen; i++)
	    newArray[i] = array[i];
	this.array = newArray;
    }	
}
    
