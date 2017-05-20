package maxLengthWord;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public  class MaxLengthWordReducer
extends Reducer<Text,IntWritable,Text,IntWritable> {


String maxword;
public void setup(Context context) throws IOException, InterruptedException
{
maxword = new String();	
}

public void reduce(Text key, Iterable<IntWritable> values,
                Context context
                ) throws IOException, InterruptedException {

if (key.toString().length() > maxword.length())
{
	maxword = key.toString();
}
}

public void cleanup(Context context) throws IOException, InterruptedException
{
context.write(new Text(maxword),new IntWritable(maxword.length()))	;
}
}


