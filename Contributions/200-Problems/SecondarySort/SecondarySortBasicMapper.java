package secondarySortpkg;

/***************************************************************
*Mapper: SecondarySortBasicMapper
***************************************************************/


import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SecondarySortBasicMapper extends
  	Mapper<LongWritable, Text, CompositeKeyWritable, NullWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		
		if ((value.toString().length() > 0 ) &&  !(value.toString().contains("EmpID"))){
			String arrEmpAttributes[] = value.toString().split("\\t");

			context.write(
					new CompositeKeyWritable(
							arrEmpAttributes[5].toString(),
							(arrEmpAttributes[4].toString() + "\t"
									+ arrEmpAttributes[2].toString() + "\t" + arrEmpAttributes[0]
									.toString())), NullWritable.get());
		}
	}
}
