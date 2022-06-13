public class Contestant implements Comparable {
    private String name;
    private int score;

    public Contestant(String name, int score) {
	this.name = name;
	this.score = score;
    }

    public String getName() {
	return this.name;
    }

    public int getScore() {
	return this.score;
    }

    public int compareTo(Object o) {
	Contestant c = (Contestant) o;
	int d = this.score - c.getScore();
	if(d == 0)
	    d = this.name.compareTo(c.getName());
	return d;
    }

    public boolean equals(Contestant c) {
	return this.name.equals(c.getName());
    }

    public String toString() {
	return name + ": " + score;
    }
}
