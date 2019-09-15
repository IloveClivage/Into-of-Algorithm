import java.io.File;
import java.util.*;

class Main {
	public static class Edge{
		int id;
		int from;
		int target;
		int cost;
		public Edge(int id, int from, int target, int cost) {
			this.id = id;
			this.from = from;
			this.target =target;
			this.cost = cost;
		}
	}
	
	public static Comparator<Edge> comparator = new Comparator<Edge>() {
		@Override
		public int compare(Edge e1, Edge e2) {
			if(e1.cost == e2.cost) return e1.id - e2.id;
			return e1.cost - e2.cost;
		}
	};
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int i = 1;
		int flag = -1;
		int N = 0;
		int M = 0;
		int first = 0;
		int second = 0;
		PriorityQueue<Edge> alledge = new PriorityQueue<>(comparator);
		ArrayList<LinkedList<Edge>> refer = new ArrayList<LinkedList<Edge>>();
		while (scan.hasNextInt()) {
			int num = scan.nextInt();
			if(i == 1) {
				N = num;
				for(int j=1; j<=N; j++) {
					refer.add(new LinkedList<Edge>());
				}
			}
			if(i == 2) M = num;
			if(i == 3) flag = num;
			if(i > 3) {
				if(i%3 == 1) {
					first = num;
				}
				else if(i%3 == 2) {
					second = num;
				}
				else {
					int cost = num;
					Edge outward = new Edge((i-1)/3, first, second, cost);
					Edge inward = new Edge((i-1)/3, second, first, cost);
					refer.get(first-1).add(outward);
					refer.get(second-1).add(inward);
					alledge.add(inward);
					alledge.add(outward);
				}
			}
			i++;
		}

		scan.close();

		LinkedList<Edge> output = new LinkedList<>();
		
		if(flag == 1) {
			PriorityQueue<Edge> edgeset = new PriorityQueue<>(comparator);
			HashSet<Integer> vertice = new HashSet<>();
			int next = 1;
			vertice.add(next);
			for(Edge e: refer.get(next-1)) {
				edgeset.add(e);
			}
			while(vertice.size()<N) {
				Edge edge = edgeset.poll();
			    if(!vertice.contains(edge.target)){
			    	next = edge.target;
			    	vertice.add(next);
			    	output.add(edge);
			    }
				for(Edge e: refer.get(next-1)) {
					if(!vertice.contains(e.target)) {
						edgeset.add(e);
					}
				}
			}
		}
		

		else if(flag == 0) {
			HashSet<HashSet<Integer>> allset = new HashSet<>();
			HashSet<Integer> idset = new HashSet<>();
			for(int j=1; j<=N; j++) {
				HashSet<Integer> set = new HashSet<>();
				set.add(j);
				allset.add(set);
			}
			while(idset.size()<N-1) {
				Edge edge = alledge.poll();
				if(!idset.contains(edge.id)) {
					boolean judge = true;
					for(HashSet<Integer> ele: allset) {
						if(ele.contains(edge.from) && ele.contains(edge.target)) judge = false;
					}
					if(judge) {
						idset.add(edge.id);
						output.add(edge);
						HashSet<Integer> from = new HashSet<>();
						HashSet<Integer> target = new HashSet<>();
						for(HashSet<Integer> ele: allset) {
							if(ele.contains(edge.from)) from = ele;
							if(ele.contains(edge.target)) target = ele;
						}
						from.addAll(target);
						target = null;
					}
				}
			}
			
		}

		for(Edge e: output) {
			System.out.println(e.id);
		}
		
		
	}

}
