package searchStringpkg;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SearchStringMapper extends Mapper<Object, Text, Text, IntWritable>{

private Text word = new Text();

public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
 
	String str = value.toString();
	
	Configuration conf = context.getConfiguration();
	String searchString = conf.get("searchString");
	String caseCheck	= conf.get("caseCheck");
	
	Pattern p;
	if (caseCheck != null && ( caseCheck.toLowerCase() == "-i" || caseCheck.toUpperCase() == "-I" )){
		p = Pattern.compile("\\b"+searchString+"\\b", Pattern.CASE_INSENSITIVE);
	} else {
		p = Pattern.compile("\\b"+searchString+"\\b");
	}
		    	
	Matcher m = p.matcher(str);
	
	if(str != null && m.find() ) {
		word.set(value);
		context.write(word, null);
	}
}
}
