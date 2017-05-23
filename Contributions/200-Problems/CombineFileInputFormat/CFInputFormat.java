package CombineFileInputFormat;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;


/* Creating a CombineFileInputFormat class to combine smaller files into a single split
 * Smaller files ( size lesser than block size in HDFS. Ex: 64 MB)
 * Each files will be considered as a separate input by default and one map task will be assigned to each input split
 * This will cause large number of map task to run when you have large number of smaller files.
 * Also Smaller files leads to Namenode memory problem, as each files metadata details requires 150 bytes to store 
 */
public class CFInputFormat extends CombineFileInputFormat<FileLineWritable, Text> {
  public CFInputFormat(){
    super();
    /*
     * Setting the input split size to 64 MB to combine smaller files into one single split
     */
    setMaxSplitSize(67108864); // 64 MB, default block size on hadoop
  }
  // RecordReader is responsible for reading data from input split
  public RecordReader<FileLineWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException{
    return new CombineFileRecordReader<FileLineWritable, Text>((CombineFileSplit)split, context, CFRecordReader.class);
  }
  @Override
  protected boolean isSplitable(JobContext context, Path file){
    return false;
  }
}
