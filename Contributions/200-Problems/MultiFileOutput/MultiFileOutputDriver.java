import multiFileOutputpkg.MultiOutputReducer;
import multiFileOutputpkg.MultipleOutputMapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
 
 
public class MultiFileOutputDriver extends Configured implements Tool
{
    public static void main( String[] args ) throws Exception
    {
       int exitCode = ToolRunner.run(new Configuration(),new MultiFileOutputDriver(),args);
       System.exit(exitCode);
    }
 
    @Override
    public int run(String[] args) throws Exception 
    {
    	if(args.length != 2) 
    	{
    		System.out.println("Two Params are required to extecute App <input-path> <output-path>");
    	}
   
    	Job job = new Job(getConf());
    	job.setJobName("MultipleOutputFormat example");
    	job.setJarByClass(MultiFileOutputDriver.class);
    	LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
    	FileInputFormat.setInputPaths(job, new Path(args[0]));
    	FileOutputFormat.setOutputPath(job, new Path(args[1]));
   
    	job.setMapperClass(MultipleOutputMapper.class);
    	job.setMapOutputKeyClass(Text.class);
    	job.setReducerClass(MultiOutputReducer.class);
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(Text.class);
    	job.setNumReduceTasks(1);
   
    	boolean success = job.waitForCompletion(true);
    	return success ? 0 : 1;
    }
}