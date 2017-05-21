import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import searchStringpkg.SearchStringMapper;

public class SearchStringDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		if (args.length < 4) {
			conf.set("searchString",args[2]);
			conf.set("caseCheck","-c");
		}
		
		if (args.length == 4) {
			conf.set("searchString",args[2]);
			conf.set("caseCheck",args[3]);
		}
	    Job job = Job.getInstance(conf, "word search");
	    job.setJarByClass(SearchStringDriver.class);
	    job.setMapperClass(SearchStringMapper.class);
	    job.setNumReduceTasks(0);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  }
}
