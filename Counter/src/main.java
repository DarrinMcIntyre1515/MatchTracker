import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class main {

	public static void main(String[] args) {
		// COUNT STUFF!
		try {
			int input = Integer.parseInt(args[0]);
			run(input);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private static void run (int input) {
		double[] stats = new double[6];
		String fileName = "League_Match_History";
		String filePath = "./" + fileName;
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		    	if(!(line.contains("5v5 Norm") || line.contains("---") || line.contains("Total"))) {
		    		sb.append(line);
			        sb.append(System.lineSeparator());
		    	}
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    
		    everything = everything.replaceAll("\\s+", "");
		    
		    //optimization --> couldn't we just split on "|" instead of " "? This seems pointless.. check this.
		    everything = everything.replace("|", " ");
		    String[] nums = everything.split(" ");

		    for(int x = 0; x < nums.length; x++) {
		    	stats[x] = Double.parseDouble(nums[x]);
		    }
		    
		} catch (IOException e) {
			System.console().printf("File does not exist, creating file '" + fileName + "'.");
		}
		
		
		write(input, stats, filePath);
		/*
		if(input == 0) {
			write(1, stats, filename);
		} else if (input == 1) {
			write(-1, stats, filename);
		} else {
			System.out.println("Invalid input.");
		}
		*/
	}
	
	private static void write (int data, double[] stats, String filename) {
		try (FileWriter fstream = new FileWriter(filename, false)){
			try(BufferedWriter out = new BufferedWriter(fstream)){
				out.write(" 5v5 Norm | 3v3 Bots | ARAM | Rotating | Honor Received | All Team Honored Someone");
				out.newLine();
				out.write("-----------------------------------------------------------------------------------");
				out.newLine();
				
				// Increment the one data point.
				stats[data] += 1;
				
				out.write(stats[0] + " | " + stats[1] + " | " + stats[2] + " | " + stats[3] + " | " + stats[4] + " | " + stats[5]);
				out.newLine();
				out.newLine();

				DecimalFormat dec = new DecimalFormat("#0.00");
				double totalPlayed = stats[0]+stats[1]+stats[2]+stats[3];
				
				out.write("Total Played: " + dec.format(totalPlayed));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
