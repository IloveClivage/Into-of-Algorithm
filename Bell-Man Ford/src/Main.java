import java.io.*;
import java.util.*;

public class Main {
	// read function
	public static class SP{
		LinkedList<Integer> path;
		double dist;
		public SP(LinkedList<Integer> path, double dist) {
			this.path = path;
			this.dist = dist;
		}
	}
	
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
	
	//Edmond Karp
	public static class EdmondKarp{
		int [][] graph;
		SP [][] dp;
		int m;
		int n;
		double infi = Double.POSITIVE_INFINITY;
		public EdmondKarp(int[][] graph) { 
			this.graph = graph;
			this.m = graph[0][1];
			this.n = graph[0][0];
			this.dp = new SP[n+1][m+1]; 
		}
		
		public SP ShortestDistance(int i, int j) {
			if(dp[i][j] != null) return dp[i][j];
			if(j==0 && i!=2) {
				dp[i][j] = new SP(null, infi);
				return dp[i][j];
			}
			if(j==0 && i==2) {
				LinkedList<Integer> path = new LinkedList<>();
				path.add(2);
				dp[i][j] = new SP(path, 0);
				return dp[i][j];
			}
			
			double min = ShortestDistance(i,j-1).dist;
			int node = 0;
			for(int k=1; k<=n; k++) {
				if(graph[i][k]!=0) {
					SP current = ShortestDistance(k,j-1);
					if(current.dist+graph[i][k]<min) {
						min = current.dist+graph[i][k];
						node = k;
					}
				}
			}
			
			if(node!= 0) {
				LinkedList<Integer> path = new LinkedList<>();
				path.addAll(ShortestDistance(node,j-1).path);
				path.addFirst(i);
				dp[i][j] = new SP(path, min);
				return dp[i][j];
			}
			else {
				dp[i][j] = ShortestDistance(i,j-1);
				return dp[i][j];
			}
		}
		
		public void implementation() {
			for(int j=0; j<=m; j++) {
				for(int i=1; i<=n; i++) {
					dp[i][j] = ShortestDistance(i,j);
				}
			}
		}
		
		public SP distance(int s) {
			return dp[s][m];
		}
	}
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
		int graph[][] = read();
		EdmondKarp imp = new EdmondKarp(graph);
		imp.implementation();
		System.out.println("distance:"+imp.distance(1).dist);
		Iterator<Integer> iterator = imp.distance(1).path.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

}
