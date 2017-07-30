package removeDuplicates;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class removeDuplicateDriver extends Configured implements Tool{
	@SuppressWarnings("unused")
	public int run(String[] args) throws Exception {
		
		
		@SuppressWarnings("deprecation")
		Job job = new Job(getConf());
		Configuration conf = job.getConfiguration();
		
		job.setJobName("Example to Remove Duplicates");
		job.setJarByClass(removeDuplicateDriver.class);
				
		job.setMapperClass(removeDuplicatesMapper.class);
		job.setReducerClass(removeDuplicatesReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.submit();
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		
		System.exit(ToolRunner.run(new Configuration(), new removeDuplicateDriver(), args ));
	}
}
