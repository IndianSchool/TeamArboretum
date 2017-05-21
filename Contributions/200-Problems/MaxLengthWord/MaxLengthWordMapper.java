package maxLengthWord;

/* MaxLengthWord program with MapReduce (HADOOP 2)
 * https://hadoop.apache.org/docs/r2.7.2/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
 */
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxLengthWordMapper
extends Mapper<Object, Text, Text, IntWritable>{

private final static IntWritable len = new IntWritable(1);
private Text word = new Text();

public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
StringTokenizer itr = new StringTokenizer(value.toString());
while (itr.hasMoreTokens()) {
 word.set(itr.nextToken());
 len.set(word.getLength());
 context.write(word, len);
}
}
}
