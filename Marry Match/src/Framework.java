import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of applicants (employers)

	int APrefs[][]; // preference list of applicants (n*n)
	int EPrefs[][]; // preference list of employers (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int appl; // applicant's number
		int empl; // employer's number

		public MatchedPair(int Appl,int Empl)
		{
			appl=Appl;
			empl=Empl;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			APrefs=new int[n][n];
			EPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] aList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					APrefs[i][j]=Integer.parseInt(aList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] eList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					EPrefs[i][j]=Integer.parseInt(eList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).empl+" "+MatchedPairsList.get(i).appl);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		*/

		//YOUR CODE STARTS HERE
		ArrayList<Boolean> efree = new ArrayList<>();
		ArrayList<Integer> ranknow = new ArrayList<>();
		ArrayList<ArrayList<Integer>> erank = new ArrayList<>();
		ArrayList<ArrayList<Integer>> arank = new ArrayList<>();
		HashMap<Integer, Integer> pairs = new HashMap<>();
		for(int i=0; i<n; i++) {
			ArrayList<Integer> elist = new ArrayList<>();
			ArrayList<Integer> alist = new ArrayList<>();
			for(int j=0; j<n; j++) {
				elist.add(EPrefs[i][j]);
				alist.add(APrefs[i][j]);
			}
			erank.add(elist);
			arank.add(alist);
			efree.add(true);
			ranknow.add(0);
		}
		
		while(efree.contains(true)) {
			int index;
			int app;
			for(int i=0; i<n; i++) {
				if(efree.get(i)) {
					index = ranknow.get(i);
					app = erank.get(i).get(index);
					ArrayList<Integer> applist = arank.get(app);
					if(!pairs.containsKey(app)) {
						pairs.put(app, i);
						efree.set(i, false);
					}
					else {
						int currentemp = pairs.get(app);
						if(applist.indexOf(currentemp) > applist.indexOf(i)) {
							pairs.put(app, i);
							efree.set(i, false);
							efree.set(currentemp, true);
							ranknow.set(currentemp, ranknow.get(currentemp)+1);
						}
						else {
							ranknow.set(i, ranknow.get(i)+1);
						}
					}
				}
			}
		}
		
		for(int i=0; i<n; i++) {
			int emp = pairs.get(i);
			MatchedPair pair=new MatchedPair(emp, i);
			MatchedPairsList.add(pair);
		}
		
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new Framework(Args);
	}
}
