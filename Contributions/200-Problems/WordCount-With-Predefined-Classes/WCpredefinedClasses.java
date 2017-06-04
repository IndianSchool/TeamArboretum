//Example Word Count Program with the help of predefined mapper and reducer classes.
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.map.TokenCounterMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;

public class WCpredefinedClasses
{

public static void main (String [] args) throws Exception
{

  //Basic Sanity check to verify the arguments 
  if (args.length != 2) 
  {
      System.err.println("Usage: wordcount <input path> <output path>");
      System.exit(2);
  }

  Path inputPath = new Path(args[0]);
  Path outputPath = new Path(args[1]);

  Configuration conf = new Configuration();

  // Job Object configuration
  Job job = new Job();
  job.setJobName("WordCount2");
  job.setJarByClass(WCpredefinedClasses.class);
  
   
  //Set Mapper class as hadoop provided TokenCounterMapper class.
  job.setMapperClass(TokenCounterMapper.class);
  //Set Reducer class as hadoop provided IntSumReducer class.
  job.setReducerClass(IntSumReducer.class);

  //Set Output Key and value data types
  job.setOutputKeyClass(Text.class);
  job.setOutputValueClass(IntWritable.class);

  //Set Input and Output formats and paths
  FileInputFormat.addInputPath(job, inputPath);
  FileOutputFormat.setOutputPath(job, outputPath);

  System.exit(job.waitForCompletion(true) ? 0:1);
}
}
