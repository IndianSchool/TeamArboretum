package com.vishwa.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DistinctLine {
	// this is a mapper class that take from file as a object
	// sends text in a line and 1 as a count
	public static class DistLineMap extends
			Mapper<LongWritable, Text, Text, NullWritable> {
		// this is declaring variables for the word and its count
		// private final static IntWritable one = new IntWritable(0);
		private Text word = new Text();

		//@Override
		public void map( LongWritable k, Text v, Context C) throws IOException,
				InterruptedException {
			String str = v.toString();
//			String[] fields = str.split("\t");
//			String firstName = fields[1];
			word.set(str);
			C.write(word, NullWritable.get());
		}
	}

	public static class DistLineReducer extends
			Reducer<Text, NullWritable, Text, NullWritable> {
		//private IntWritable result = new IntWritable(0);

		public void reduce(Text k, Iterable<NullWritable> v, Context c)
				throws IOException, InterruptedException {

			c.write(k, NullWritable.get());
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception {
		Configuration cf = new Configuration();
		Job j = new Job(cf, "Dist Line");
		j.setJarByClass(DistinctWord.class);
		j.setMapperClass(DistLineMap.class); // setMapperClass(DistinctWord.class);
		j.setCombinerClass(DistLineReducer.class);
		j.setReducerClass(DistLineReducer.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(NullWritable.class);
		FileInputFormat.addInputPath(j, new Path(args[0]));
		FileOutputFormat.setOutputPath(j, new Path(args[1]));
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}
}
