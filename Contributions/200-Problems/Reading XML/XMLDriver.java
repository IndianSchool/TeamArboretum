package XML;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
 
public class XMLDriver {
 
    /** Krishna - for processing XML file using Hadoop MapReduce
     * @param args
     */
    public static void main(String[] args) {
        try {
 
            Configuration conf = new Configuration();
            // conf.setInt(FixedLengthInputFormat.FIXED_RECORD_LENGTH, 2048);
 
            // OR alternatively you can set it this way, the name of the
            // property is
            // "mapreduce.input.fixedlengthinputformat.record.length"
            // conf.setInt("mapreduce.input.fixedlengthinputformat.record.length",
            // 2048);
            String[] arg = new GenericOptionsParser(conf, args).getRemainingArgs();
 
            conf.set("START_TAG_KEY", "<employee>");
            conf.set("END_TAG_KEY", "</employee>");
 
            Job job = new Job(conf, "XML Processing Processing");
            job.setJarByClass(XMLDriver.class);
            job.setMapperClass(XMLMapper.class);
 
            job.setNumReduceTasks(0);
 
            job.setInputFormatClass(XmlInputFormat.class);
            // job.setOutputValueClass(TextOutputFormat.class);
 
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(LongWritable.class);
 
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);
 
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
 
            job.waitForCompletion(true);
 
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
        // job.setReducerClass(ClickReducer.class);
 
    }
 
}
