/*
 * Find a Replace a string in a file.
 * This program accepts parameters from the users in addition to the input and output file path
 * This program does not have reducer, mapper itself takes care of all the stuffs.
 * Also notice that the parameters had been passed from driver to mapper class
 * Also check the in final output file, the value is passed as null, so that it does not prints value in the final file  
 * 
 * Please enter the following parameters while invoking this code
 * 
 * hadoop jar FindReplace.jar FindReplace <input-location> <output-location> <search string> <replacement>
 * 
 */

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FindReplaceParameterized {

  public static class FindReplaceMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	
    	// get parameters from the driver class to mapper class
    	Configuration conf = context.getConfiguration();
    	String findMatch = conf.get("SearchString");
    	String rplString = conf.get("ReplaceString");
    	word = value;
    	String str = value.toString();
    	
    	if(str  != null && str.contains(findMatch) ) {
    		word.set(str.replaceAll("\\b"+findMatch+"\\b",rplString));
    	}
		context.write(word, null);
    }
  }

   public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    
    // notice the parameter setting should be done immediately after the Configuration instantiation.
    conf.set("SearchString", new String(args[2]));
    conf.set("ReplaceString", new String(args[3]));
    
    Job job = Job.getInstance(conf, "Find Replace");
    job.setJarByClass(FindReplaceParameterized.class);
    job.setMapperClass(FindReplaceMapper.class);
    job.setNumReduceTasks(0);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
