@SuppressWarnings("unchecked")
public class Hashtable<K, V> {
    private Pair[] table;
    private int n;//the number of key-value pairs in the table
    private int m;//the size of the table
    private double alphaHigh = 0.5;//resize if n/m exceeds this (1/2)
    private double alphaLow = 0.125;//resize if n/m goes below this (1/8)
    private boolean[] deleted;
    

    //constructor--default table size is 11
    public Hashtable() {
	n = 0;
	m = 11;
	table = new Pair[m];
	deleted = new boolean[m];
    }

    //constructor
    public Hashtable(int m) {
	n = 0;
	this.m = m;
	table = new Pair[m];
	deleted = new boolean[m];
    }

    //returns the value associated with key <key>
    //return null if key is not in table
    //do not forget that you will have to cast the result to (V)
    public V get(K key) {
	int h = getIndex(key);
	if(table[h] == null)
	    return null;
	return (V)table[h].getValue();
    }

    //puts (key, val) into the table or updates value
    //if the key is already in the table
    //resize to getNextNum(2*m) if (double)n/m exceeds alphaHigh after the insert
    public void put(K key, V val) {
    //System.out.println("put: " + key + ", " + val);
	int h = getIndex(key);
	//System.out.println("index = " + h);
	if(table[h] == null) {
	    table[h] = new Pair(key, val);
	    n++;
	} else
	    table[h].setValue(val);
	if((double)n/m > alphaHigh)
	    resize(getNextNum(2*m));
    }

    //removes the (key, value) pair associated with <key>
    //returns the deleted value or null if the element was not in the table
    //resize to getNextNum(m/2) if m/2 >= 11 AND (double)n/m < alphaLow after the delete
    public V delete(K key) {
	int h = getIndex(key);
	V ret = null;
	if(table[h] == null)
	    return ret;
	else {
	    deleted[h] = true;
	    ret = (V)table[h].getValue();
	    n--;
	}
	if(m/2 >= 11 && (double)n/m < alphaLow)
	    resize(getNextNum(m/2));
	return ret;
    }

    //return true if table is empty
    public boolean isEmpty() {
	return n == 0;
    }

    //return the number of (key,value) pairs in the table
    public int size() {
	return n;
    }

    //This method is used for testing only. Do not use this method yourself for any reason
    //other than debugging. Do not change this method.
    public Pair[] getTable() {
	return table;
    }

    //PRIVATE

    private int getIndex(K key) {
	int hash = key.hashCode() % m;
	if(hash < 0)
	    hash += m;
	while(table[hash] != null) {
	    if(((K)table[hash].getKey()).equals(key) && deleted[hash] == false)
		return hash;
	    hash = (hash + 1) % m;
	}
	return hash;
    }
    
    //gets the next multiple of 6 plus or minus 1,
    //which has a decent probability of being prime.
    //Use this method when resizing the table.
    private int getNextNum(int num) {
	if(num == 2 || num == 3)
	    return num;
	int rem = num % 6;
	switch(rem) {
	case 0: num++; break;
	case 2: num+=3; break;
	case 3: num+=2; break;
	case 4: num++; break;
	}
	return num;
    }

    private void resize(int max) {System.out.println("resizing to " + max);
	Pair[] temp = table;
	this.table = new Pair[max];
	this.m = max;
	this.n = 0;
	for(int i = 0; i < temp.length; i++) {
	    if(temp[i] != null && deleted[i] == false)
		put((K)temp[i].getKey(), (V)temp[i].getValue());
	}
	this.deleted = new boolean[max];
    }
}
      
