==========================================================================================================================================
Program to process XML file
==========================================================================================================================================
Program Name  :   xml.jar
Purpose       :   To process XML file using a Map Reduce 
Logic Used    :   1. Create a custom XMLInputFormat to read the XML files
                     XMLInputFormat does the following jobs.
                        1.  Specifies the Type Speficications of Input Key and Values
                        2.  Specifies the Inputs Partition ( Input Splits ) which directly impacts on the no. of Mapper Task
                        3.  Specifies the way of reading data from the Input Splits (RecordReader)
                  2. Create a XMLMapper to read data and process it
                  3. Create a XMLDriver which sets the job with above configuration
==========================================================================================================================================                  
Input Data    :   input-data.xml
                      <Data>
                      <employee>
                      <id>1</id>
                      <name>krishna</name>
                      <gender>M</gender>
                      </employee>
                      <employee>
                      <id>2</id>
                      <name>Giridar</name>
                      <gender>M</gender>
                      </employee>
                      <employee>
                      <id>3</id>
                      <name>Umesh</name>
                      <gender>M</gender>
                      </employee>
                      <employee>
                      <id>4</id>
                      <name>Nakeeran</name>
                      <gender>M</gender>
                      </employee>
                      </Data>

Output Data     :   part-m-00000
                      1,krishna,M
                      2,Giridar,M
                      3,Umesh,M
                      4,Nakeeran,M
==========================================================================================================================================                      
Pseudo Code     :   
========================
Mapper <Kin, Vin, Kout, Vout>   : <LongWritable, Text, Text, NullWritable>
Mapper Input <K, V> : <6>, < <Data> >
                      <16>, < <employee> >
                      <26>, < <id>1</id> >
                      <46>, < <name>krishna</name> >
                      <64>, < <gender>M</gender> >
                      <75>, < </employee> >
                      <85>, < <employee> >
                      <95>, < <id>2</id> >
                      <115>, < <name>Giridar</name> >
                      <133>, < <gender>M</gender> >
                      <144>, < </employee> >
                      <154>, < <employee> >
                      <164>, < <id>3</id> >
                      <182>, < <name>Umesh</name> >
                      <200>, < <gender>M</gender> >
                      <211>, < </employee> >
                      <221>, < <employee> >
                      <231>, < <id>4</id> >
                      <252>, < <name>Nakeeran</name> >
                      <270>, < <gender>M</gender> >
                      <281>, < </employee> >
                      <288>, < </Data> >

Search for the element EMPLOYEE [<employee> and </employee>]
All the text between the start and end tag of employee, had been taken as a single node.
Then get all the individual elements separately.
                      
Mapper Output <K, V>  : <1,krishna,M>, <null>
                        <2,Giridar,M>, <null>
                        <3,Umesh,M>, <null>
                        <4,Nakeeran,M>, <null>              
==========================================================================================================================================
Jar file Structure
===================
[edureka@localhost Jar_files]$ jar tvf xml.jar
    25 Sat Jul 15 17:20:56 IST 2017 META-INF/MANIFEST.MF
  4052 Sat Jul 15 17:19:12 IST 2017 XML/XmlInputFormat$XmlRecordReader.class
  1109 Sat Jul 15 17:19:12 IST 2017 XML/XmlInputFormat.class
  2236 Sat Jul 15 17:20:56 IST 2017 XML/XMLDriver.class
  3944 Sat Jul 15 17:17:12 IST 2017 XML/XMLMapper.class

==========================================================================================================================================                      
How to run:
===========
          hadoop jar <jarfilename> <driverclass_name> <input_folder path> <output path>
          hadoop jar xml.jar XML.XMLDriver /HandsOn/input/*.xml /HandsOn/output/xml-out
          
==========================================================================================================================================
Output log:
=============
[edureka@localhost Jar_files]$ hadoop jar xml.jar XML.XMLDriver /HandsOn/input/*.xml /HandsOn/output/xml-out
17/07/15 17:28:19 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/07/15 17:28:20 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
17/07/15 17:28:22 INFO input.FileInputFormat: Total input paths to process : 1
17/07/15 17:28:22 INFO mapreduce.JobSubmitter: number of splits:1
17/07/15 17:28:22 INFO Configuration.deprecation: user.name is deprecated. Instead, use mapreduce.job.user.name
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.jar is deprecated. Instead, use mapreduce.job.jar
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.output.value.class is deprecated. Instead, use mapreduce.job.output.value.class
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.mapoutput.value.class is deprecated. Instead, use mapreduce.map.output.value.class
17/07/15 17:28:22 INFO Configuration.deprecation: mapreduce.map.class is deprecated. Instead, use mapreduce.job.map.class
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.job.name is deprecated. Instead, use mapreduce.job.name
17/07/15 17:28:22 INFO Configuration.deprecation: mapreduce.inputformat.class is deprecated. Instead, use mapreduce.job.inputformat.class
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.input.dir is deprecated. Instead, use mapreduce.input.fileinputformat.inputdir
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.output.dir is deprecated. Instead, use mapreduce.output.fileoutputformat.outputdir
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.output.key.class is deprecated. Instead, use mapreduce.job.output.key.class
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.mapoutput.key.class is deprecated. Instead, use mapreduce.map.output.key.class
17/07/15 17:28:22 INFO Configuration.deprecation: mapred.working.dir is deprecated. Instead, use mapreduce.job.working.dir
17/07/15 17:28:22 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1500058132851_0001
17/07/15 17:28:23 INFO impl.YarnClientImpl: Submitted application application_1500058132851_0001 to ResourceManager at /0.0.0.0:8032
17/07/15 17:28:23 INFO mapreduce.Job: The url to track the job: http://localhost:8088/proxy/application_1500058132851_0001/
17/07/15 17:28:23 INFO mapreduce.Job: Running job: job_1500058132851_0001
17/07/15 17:28:40 INFO mapreduce.Job: Job job_1500058132851_0001 running in uber mode : false
17/07/15 17:28:40 INFO mapreduce.Job:  map 0% reduce 0%
17/07/15 17:28:49 INFO mapreduce.Job:  map 100% reduce 0%
17/07/15 17:28:49 INFO mapreduce.Job: Job job_1500058132851_0001 completed successfully
17/07/15 17:28:49 INFO mapreduce.Job: Counters: 27
	File System Counters
		FILE: Number of bytes read=0
		FILE: Number of bytes written=79871
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=424
		HDFS: Number of bytes written=47
		HDFS: Number of read operations=5
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
	Job Counters 
		Launched map tasks=1
		Data-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=7392
		Total time spent by all reduces in occupied slots (ms)=0
	Map-Reduce Framework
		Map input records=4
		Map output records=4
		Input split bytes=114
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=66
		CPU time spent (ms)=400
		Physical memory (bytes) snapshot=53977088
		Virtual memory (bytes) snapshot=360067072
		Total committed heap usage (bytes)=16252928
	File Input Format Counters 
		Bytes Read=310
	File Output Format Counters 
		Bytes Written=47
[edureka@localhost Jar_files]$ hdfs dfs -ls /HandsOn/output/xml
17/07/15 17:29:15 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
ls: `/HandsOn/output/xml': No such file or directory
[edureka@localhost Jar_files]$ hdfs dfs -ls /HandsOn/output/xml-out
17/07/15 17:29:24 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Found 2 items
-rw-r--r--   1 edureka supergroup          0 2017-07-15 17:28 /HandsOn/output/xml-out/_SUCCESS
-rw-r--r--   1 edureka supergroup         47 2017-07-15 17:28 /HandsOn/output/xml-out/part-m-00000
[edureka@localhost Jar_files]$ hdfs dfs -cat /HandsOn/output/xml-out/part-m-00000
17/07/15 17:29:50 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
1,krishna,M
2,Giridar,M
3,Umesh,M
4,Nakeeran,M
[edureka@localhost Jar_files]$
