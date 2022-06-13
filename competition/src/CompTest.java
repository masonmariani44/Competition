import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class CompTest {
    private static String dirName = "testfiles/";//the folder where the test files are located
    private static Competition comp;
    private static double[] scoreInc = new double[]{0.5, 0.3, 0.05, 0.5};
    private static int[] numScores = new int[]{3, 5, 5, 10};//the M-values for each of the tests
    private static int[] numTBR = new int[]{3, 5, 10};//values used for testing top, bottom, and ranked
    private static String[] methods = new String[]{"totalScore", "scores", "rank", "top", "bottom", "ranked"};
    private static HashSet<String> names;
    public static void main(String[] args) {
	int score = 0;
	for(int i = 1; i <= 4; i++) {
	    score += runTest(i);
	}
	System.out.println("Total expected score: " + Math.min(score, 60));
    }

    private static int runTest(int testNum) {
	int score = 0;
	if(testNum == 4)
	    score += runTestFour();
	else
	    score += run(testNum);
	return score+1;
    }

    //test 4 takes in input for three contestants and tests the values for one of them
    //after each set of 10 scores
    private static int runTestFour() {
	System.out.println("*** Beginning Test 4 ***");
	double score = 0.0;
	comp = new Competition(numScores[3]);
	String input = dirName + "input4.txt";
	String output = dirName + "output4.txt";
	String name = "Anna Adams";
	BufferedReader br_in;
	BufferedReader br_out;
	try {
	    int count = 1;
	    br_in = new BufferedReader(new FileReader(input));
	    br_out = new BufferedReader(new FileReader(output));
	    String line = br_in.readLine();
	    while(line != null) {
		processInput(line);
		if(count%10 == 0) {
		    String exp = br_out.readLine();
		    String act = "Total Score: " + comp.totalScore(name);
		    if(exp.equals(act))
			score+=scoreInc[3];
		    else
			printMsg(exp, act);
		    exp = br_out.readLine();
		    act = "Scores: " + comp.scores(name);
		    if(exp.equals(act))
			score+=scoreInc[3];
		    else
			printMsg(exp, act);
		    exp = br_out.readLine();
		    act = "Rank: " + comp.rank(name);
		    if(exp.equals(act))
			score+=scoreInc[3];
		    else
			printMsg(exp, act);
		}
		count++;		       
		line = br_in.readLine();
	    }
	    br_in.close();
	    br_out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	int s = (int)(score+1);
	System.out.println("Expected score for test " + 4 + ": " + s);
	return s;
    }

    private static void printMsg(String exp, String act) {
	System.out.println("The Strings do not match.");
	System.out.println("Expected: " + exp);
	System.out.println("Actual: " + act);
    }

    //code for running tests 1-3
    private static int run(int testNum) {
	System.out.println("*** Beginning Test " + testNum + " ***");
	double score = 0.0;
	comp = new Competition(numScores[testNum-1]);
	names = new HashSet<String>();
	getInput(testNum);
	for(int i = 0; i < methods.length; i++) {
	    score += test(i, testNum);
	}
	int s = (int)(score + 1);
	System.out.println("Expected score for test " + testNum + ": " + s);
	return s;
    }

    //for tests 1-3, we read in the scores
    //then we test each method separately
    private static double test(int i, int testNum) {
	double score = 0.0;
	System.out.println("Testing " + methods[i] + "...");
	HashMap<String, String> map = getOutput(i, testNum);
	if(i == 0) {
	    for(String n : names) {
		String exp = map.get(n);
		String act = comp.totalScore(n);
		if(act.equals(exp))
		    score+=scoreInc[testNum-1];
		else {
		    System.out.println("The values for " + n + " don't match.");
		    System.out.println("Expected: " + exp);
		    System.out.println("Actual: " + act);
		}
	    }
	} else if(i == 1) {
	    for(String n : names) {
		String exp = map.get(n);
		String act = comp.scores(n);
		if(act.equals(exp))
		    score+=scoreInc[testNum-1];
		else {
		    System.out.println("The values for " + n + " don't match.");
		    System.out.println("Expected: " + exp);
		    System.out.println("Actual: " + act);
		}
	    }
	} else if(i == 2) {
	    for(String n : names) {
		String exp = map.get(n);
		String act = comp.rank(n);
		if(act.equals(exp))
		    score+=scoreInc[testNum-1];
		else {
		    System.out.println("The values for " + n + " don't match.");
		    System.out.println("Expected: " + exp);
		    System.out.println("Actual: " + act);
		}
	    }
	} else if(i == 3) {
	    for(int j = 1; j <= numTBR[testNum-1]; j++) {
		String exp = map.get(String.valueOf(j));
		String act = comp.top(j);
		if(act.equals(exp))
		    score+=scoreInc[testNum-1];
		else {
		    System.out.println("The values for " + j + " don't match.");
		    System.out.println("Expected: " + exp);
		    System.out.println("Actual: " + act);
		}
	    }
	} else if(i == 4) {
	    for(int j = 1; j <= numTBR[testNum-1]; j++) {
		String exp = map.get(String.valueOf(j));
		String act = comp.bottom(j);
		if(act.equals(exp))
		    score+=scoreInc[testNum-1];
		else {
		    System.out.println("The values for " + j + " don't match.");
		    System.out.println("Expected: " + exp);
		    System.out.println("Actual: " + act);
		}
	    }
	} else if(i == 5) {
	    for(int j = 1; j <= numTBR[testNum-1]; j++) {
		String exp = map.get(String.valueOf(j));
		String act = comp.ranked(j);
		if(act.equals(exp))
		    score+=scoreInc[testNum-1];
		else {
		    System.out.println("The values for " + j + " don't match.");
		    System.out.println("Expected: " + exp);
		    System.out.println("Actual: " + act);
		}
	    }
	}
	return score;
    }

    //code for reading and processing expected output files
    private static HashMap<String, String> getOutput(int i, int testNum) {
	String fn = dirName + methods[i] + "_output" + testNum + ".txt";
	HashMap<String, String> map = new HashMap<String, String>();
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(fn));
	    String line = br.readLine();
	    while(line != null) {
		processOutput(line, i, map);
		line = br.readLine();
	    }
	    br.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return map;
    }

    //code for processing a single line in an output file
    private static void processOutput(String line, int i, HashMap<String, String> map) {
	String[] split = null;
	if(i == 0 || i == 1 || i == 2) {
	    split = line.split(": ");
	    if(split.length < 2)
		return;
	    map.put(split[0], split[1]);
	} else if(i == 3 || i == 4 || i == 5) {
	    split = line.split(":: ");
	    if(split.length < 2)
		return;
	    String[] splitAgain = split[0].split(" ");
	    map.put(splitAgain[1], split[1]);
	}
    }

    //code for processing input files
    private static void getInput(int testNum) {
	String fn = dirName + "input" + testNum + ".txt";
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(fn));
	    String line = br.readLine();
	    while(line != null) {
		processInput(line);
		line = br.readLine();
	    }
	    br.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
	

    //code for processing a single line from an input file
    private static void processInput(String line) {
	String[] split = line.split(",");
	comp.processScore(split[0], Integer.parseInt(split[1]));
	names.add(split[0]);
    }

    
}
