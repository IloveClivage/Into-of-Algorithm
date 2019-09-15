import java.io.*;
import java.util.*;

class Main {
	public static int[][] read() {
		InputStreamReader is = new InputStreamReader(System.in);  	    
		BufferedReader br = new BufferedReader(is);
		int n = 0;
		int m = 0;
		int x = 0;
		int y = 0;
		int c = 0;
		int row=0;
		int[][] graph = null;
		String str = "";
		try {
			str = br.readLine();
			int k = 0;
			for(;str.charAt(k)!=' ';k++) {}
			n = Integer.parseInt(str.substring(0, k));
			m = Integer.parseInt(str.substring(k+1));
			graph = new int[n+1][n+1];
			graph[0][0] = n;
			graph[0][1] = m;
		} catch(Exception e) {}
		
		try {
			
			for(row=0; row<m; row++){
				str = br.readLine();
				int k1 = 0;
				int k2 = 0;
				int k3 = str.length();
				for(int k=0; k<str.length(); k++) {
					if(k1*k2!=0 && str.charAt(k)==' ')k3=k;
					else if(k1!=0 && str.charAt(k)==' ') k2=k;
					else if(str.charAt(k)==' ') k1=k;
				}
				x = Integer.parseInt(str.substring(0, k1));
				y = Integer.parseInt(str.substring(k1+1, k2));
				c = Integer.parseInt(str.substring(k2+1,k3));
				graph[x][y] = c;
			}
			br.close();
		} catch(Exception e) {}
		return graph;
	}
	
	//Class EdmondKarp
	public static class EdmondKarp{
		int[][] graph;
		int flow;
		int n;
		int m;
		public EdmondKarp(int[][] graph) {
			this.graph = graph;
			this.flow = 0;
			n = graph[0][0];
			m = graph[0][1];
		}
		
		public int modify(LinkedList<Integer> path) {
			if(path == null || path.size()==1) return 0;
			int min = 0;
			for(int i=0; i<path.size()-1; i++) {
				int s = path.get(i);
				int e = path.get(i+1);
				if(i==0) min = graph[s][e]; 
				if(min > graph[s][e]) min = graph[s][e];
			}
			for(int i=0; i<path.size()-1; i++) {
				int s = path.get(i);
				int e = path.get(i+1);
				graph[s][e] -= min;
				graph[e][s] += min;
			}
			return min;
		}
		
		public LinkedList<Integer> BFS(){
			HashMap<Integer, Integer> dict = new HashMap<>();
			LinkedList<Integer> queue = new LinkedList<>();
			LinkedList<Integer> path = new LinkedList<>();
			queue.add(1);
			dict.put(1, 0);
			while(!queue.isEmpty()) {
				int node = queue.poll();
				for(int i=1; i<=n; i++) {
					if(graph[node][i]>0 && !dict.containsKey(i)) {
						dict.put(i, node);
						queue.add(i);
						if(i == 2) {
							path.add(2);
							int last = dict.get(i);
							while(last != 0) {
								path.addFirst(last);
								last = dict.get(last);
							}
							return path;
						}
					}
				}
			}
			return null;
		}
		
		public int FordFulkerson() {
			LinkedList<Integer> path = BFS();
			while(path != null) {
				int min = modify(path);
				this.flow += min;
				path = BFS();
			}
			return this.flow;
		}
	}
	
	public static void main(String[] args) {
		int[][] graph = read();
		EdmondKarp edmondkarp = new EdmondKarp(graph);
		int flow = edmondkarp.FordFulkerson();
		System.out.println(flow);
	}
}
