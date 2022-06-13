import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Competition {
	private TreeMap<Integer, ArrayList<String>> ranks;
	private Hashtable<String, MinPQ<Integer>> competitors;
	private int m;
	
	public Competition(int m) {
		this.m = m;
		//competitors represents every person, and their integer MinPQ
		competitors = new Hashtable<String, MinPQ<Integer>>();
		//ranks represents the total score of a person or people, and is ordered such that the last keys are the top scorers
		ranks = new TreeMap<Integer, ArrayList<String>>();
		new Hashtable<Integer, ArrayList<String>>();
	}
	
	public void processScore(String name, int score) {
		//given this name and score, add them to the hashtable
		MinPQ<Integer> get_PQ = (MinPQ<Integer>) competitors.get(name);
		if (get_PQ == null) {
			//if the name isn't in the table, then create a new PQ
			MinPQ<Integer> new_PQ = new MinPQ<Integer>(m);
			new_PQ.insert(Integer.valueOf(score));
			//put into hashtable
			competitors.put(name, new_PQ);
		} else {
			Integer old_score = Integer.valueOf(totalScore(name));
			//remove this persons current ranking
			ArrayList<String> names_at_old_rank = ranks.get(old_score);
			names_at_old_rank.remove(name);
			if (names_at_old_rank.size() == 0) {
				ranks.remove(old_score);
			} else {
				ranks.put(old_score, names_at_old_rank);
			}
						
			//update, put back into hashtable
			get_PQ.insert(score);
			competitors.put(name, get_PQ);
		}
		
		//update the rankings
		//get this persons total score (newly updated)
		Integer total_score = Integer.valueOf(totalScore(name));

		//gives back an arraylist of name strings for this new total score
		ArrayList<String> names_at_rank = (ArrayList<String>) ranks.get(Integer.valueOf(totalScore(name)));
		if (names_at_rank == null) {
			names_at_rank = new ArrayList<String>();
		}
		names_at_rank.add(name);
		
		//put that person into their new position in ranks
		ranks.put(total_score, names_at_rank);
	}
	
	public String totalScore(String name) {
		MinPQ get_PQ = (MinPQ) competitors.get(name);
		if (get_PQ == null) {
			return null;
		}
		
		Integer count = 0;
		for (Comparable i : get_PQ.getArray()) {
			if (i == null) {
				break;
			}
			count = count + (Integer) i;
		}
		return count.toString();
	}
	
	public String scores(String name) {
		MinPQ get_PQ = (MinPQ<Integer>) competitors.get(name);
		if (get_PQ == null) {
			return null;
		}
		Comparable[] get_arr = get_PQ.getArray();
		ArrayList<Integer> new_arr = new ArrayList<Integer>();
		//convert the array correctly so it can be sorted
		for (int i = 0; i < get_arr.length; i++) {
			if (get_arr[i] != null) {
				Integer current_integer = (Integer) get_arr[i];
				new_arr.add(current_integer);
			} else {
				break;
			}
		}
		Object[] ret_arr = new_arr.toArray();
		Arrays.sort(ret_arr);
		return Arrays.toString(ret_arr);
	}	
	
	public String top(int m) {
		//get reverse order of treemap
		ArrayList<ArrayList<String>> builder = new ArrayList<ArrayList<String>>();
		
		//for every entry in ranks, add to builder
		for (Entry<Integer, ArrayList<String>> entry : ranks.entrySet()) {
			builder.add(entry.getValue());
		}		
		
		ArrayList<String> ret_list = new ArrayList<String>();
		ArrayList<String> current_index = new ArrayList<String>();
		int counter = 1;
		for (int i = builder.size()-1; i >= 0; i--) {
			if (counter > m) {
				break;
			}
			current_index = builder.get(i);
			for (String str : current_index) {
				ret_list.add(str);
			}
			counter++;
		}
		
		//create the return string
		String build_str = "[";
		for (int i = 0; i < ret_list.size(); i++) {
			if (i == ret_list.size()-1) {
				build_str = build_str + ret_list.get(i) + ": " + totalScore(ret_list.get(i)) + "]";
			} else {
				build_str = build_str + ret_list.get(i) + ": " + totalScore(ret_list.get(i)) + ", ";
			}
		}
		
		return build_str;
	}
	
	public String bottom(int m) {
		//get reverse order of treemap
		ArrayList<String> builder = new ArrayList<String>();
		ArrayList<String> ret_list = new ArrayList<String>();
		//for every entry in ranks, add to builder
		int counter = 1;
		for (Entry<Integer, ArrayList<String>> entry : ranks.entrySet()) {
			if (counter > m) {
				break;
			}
			builder = entry.getValue();
			for (String str : builder) {
				ret_list.add(str);
			}
			counter++;
		}
		
		//create the return string
		String build_str = "[";
		for (int i = 0; i < ret_list.size(); i++) {
			if (i == ret_list.size()-1) {
				build_str = build_str + ret_list.get(i) + ": " + totalScore(ret_list.get(i)) + "]";
			} else {
				build_str = build_str + ret_list.get(i) + ": " + totalScore(ret_list.get(i)) + ", ";
			}
		}
		
		return build_str;
	}
	
	public String rank(String name) {
		//given a name return this person's rank
		Integer total_score = Integer.valueOf(totalScore(name));
		Integer current_rank = Integer.valueOf(ranks.size());
		for (Entry<Integer, ArrayList<String>> entry : ranks.entrySet()) {
			if (total_score.equals(entry.getKey())) {
				return current_rank.toString();
			}
			current_rank = current_rank - 1;
		}
		return null;
	}
	
	public String ranked(int rank) {
		//loop through ranks until rank == current_rank, return that arraylist as a string
		Integer current_rank = Integer.valueOf(ranks.size());
		Integer comp_rank = Integer.valueOf(rank);
		ArrayList<String> ret_list = new ArrayList<String>();
		for (Entry<Integer, ArrayList<String>> entry : ranks.entrySet()) {
			if (comp_rank.equals(current_rank)) {
				ret_list = entry.getValue();
				break;
			}
			current_rank = current_rank - 1;
		}
		//create the return string
		String build_str = "[";
		for (int i = 0; i < ret_list.size(); i++) {
			if (i == ret_list.size()-1) {
				build_str = build_str + ret_list.get(i) + ": " + totalScore(ret_list.get(i)) + "]";
			} else {
				build_str = build_str + ret_list.get(i) + ": " + totalScore(ret_list.get(i)) + ", ";
			}
		}
		
		return build_str;
	}
}