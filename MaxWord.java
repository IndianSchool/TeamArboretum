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

public class MaxWord {
     public static class MaxWordMapper 
             extends Mapper<LongWritable, Text, Text, IntWritable>{
				 
             protected void map(LongWritable key, Text value,Context context)
             throws IOException, InterruptedException {
             String txt = value.toString();
             for(String line:txt.split(” “)){
             if(line.length()>0){
             context.write(new Text(line), new IntWritable(line.length()));
                     }
                   }
                }
    }

      public static class MaxWordReducer 
	          extends Reducer<Text, IntWritable, Text, IntWritable>{
                         String maxWord;
             protected void setup(Context context) throws java.io.IOException, InterruptedException {
             maxWord = new String();
    }
             protected void reduce(Text key, Iterable<IntWritable> value, Context context) throws java.io.IOException,InterruptedException {
             if (key.toString().length() > maxWord.length()) {
             maxWord = key.toString();
       }
       }
             protected void cleanup(Context context) throws IOException, InterruptedException {
             context.write(new Text(maxWord), new IntWritable(maxWord.length()));
     }
     }

public static void main(String[] args) throws Exception {
Configuration conf = new Configuration();
Job job = Job.getInstance(conf, "max Word");
job.setJarByClass(maxWord.class);
job.setMapperClass(TokenizerMapper.class);
job.setCombinerClass(IntSumReducer.class);
job.setReducerClass(IntSumReducer.class);
job.setOutputKeyClass(Text.class);
job.setOutputValueClass(IntWritable.class);
FileInputFormat.addInputPath(job, new Path(args[0]));
FileOutputFormat.setOutputPath(job, new Path(args[1]));
System.exit(job.waitForCompletion(true) ? 0 : 1);
     }
   }
 }