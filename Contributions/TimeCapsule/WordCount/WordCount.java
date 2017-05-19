package WordCount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//A simple Hadoop MapReduce program to count the number of occurence of all distinct words
//The MapReduce WordCount program using the Tool interface
//The tool interface helps in handling of generic command line options
//If you provide any option that is specific for Hadoop,
//this would be read by the system and discarded.
//So your program need not process the generic MapReduce command line options
//Your program can directly use the specific command line arguments for your program
//and ignore the ones for the MapReduce framework

public class WordCount {
	// The Map Class
	// The input to the map method would be a Object key and Text (String) value
	// Notice the class declaration is done with Object key and Text value
	// The TextInputFormat splits the data line by line.
	// The key for TextInputFormat is nothing but the line number and hence can
	// be ignored
	// The value for the TextInputFormat is a line of text from the input
	// The map method can emit data using context.write() method
	// However, to match the class declaration, it must emit Text as key and
	// IntWribale as value
	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable n = new IntWritable(1);
		private Text t = new Text();

		// The map method
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// The TextInputFormat splits the data line by line.
			// So each map method receives one line from the input
			String line = value.toString();
			// Tokenize to get the individual words
			StringTokenizer tokens = new StringTokenizer(line);
			while (tokens.hasMoreTokens()) {
				t.set(tokens.nextToken());
				context.write(t, n);
			}
		}
	}

	// The reduce class
	// The key is Text and must match the datatype of the output key of the map
	// method
	// The value is IntWritable and also must match the datatype of the output
	// value of the map method
	public static class IntSumReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		// The reduce method
		// For key, we have an Iterable over all values associated with this key
		// The values come in a sorted fasion.
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String args[]) throws Exception {
		// Get system configuration
		Configuration config = new Configuration();
		// Create a Hadoop Job
		Job j = Job.getInstance(config, "Word Count");
		// Attach the job to this Class
		// Number of reducers
		//job.setNumReduceTasks(n);
		j.setJarByClass(TokenizerMapper.class);
		// Set the Map class
		j.setMapperClass(TokenizerMapper.class);
		// Set the Combiner class
		// The combiner class reduces the mapper output locally
		// All the outputs from the mapper having the same key are reduced locally
		// This helps in reducing communication time as reducers get only one tuple per key per mapper
		// For this example, the Reduce logic is good enough as the combiner logic
		// Hence we use the same class.
		// However, this is not neccessary and you can write separate Combiner class
		j.setCombinerClass(IntSumReducer.class);
		// Set the reducer class
		j.setReducerClass(IntSumReducer.class);
		// Set the Output Key/Value from the reducer
		// Must match with what the reducer outputs
		// using context.write()
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(IntWritable.class);
		// Parameters
		FileInputFormat.addInputPath(j, new Path(args[0]));
		FileOutputFormat.setOutputPath(j, new Path(args[1]));
		//Sytem check for job completion.
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

}
