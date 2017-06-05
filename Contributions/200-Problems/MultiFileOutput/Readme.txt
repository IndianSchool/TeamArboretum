==========================================================================================================================================
Program to create multiple output file using MultiFileOutput class
==========================================================================================================================================
Prgram Name   :   MultiFileOutputDriver.jar
Purpose       :   To generate multiple output files without using partitioners
Logic Used    :   Create custom key value pair using the Mapper
                  Then write the data to multiple files using the MultipleOutputs type.
                       [Driver Setting ==> LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class) ]
                       
Input format  :   customers.txt
          
                  9899821411,"Burke, Honorato U.",Alaska,Eu Incorporated
                  9899821422,"Bell, Emily R.",Arizona,Ut Eros Non Company
                  9899821379,"Hewitt, Chelsea Y.",PA,Egestas Aliquam Fringilla LLP
                  9899821387,"Baldwin, Merrill H.",VT,Rhoncus Proin Corp.

Expected Output Format  : Split data based on the city. So one city data will be residing in one output file
                          Output File1 :  Alaska  9899821411  "Burke, Honorato U."  Eu Incorporated
                          Output File2 :  Arizona 9899821422  "Bell, Emily R."  Ut Eros Non Company
                          Output File3 :  PA  9899821379  "Hewitt, Chelsea Y."   Egestas Aliquam Fringilla LLP
                          Output File4 :  VT  9899821387  "Baldwin, Merrill H." Rhoncus Proin Corp.
===========================================================================================================================================                          
Pseudocode :
================
1. Like other programs, we do have 1 Mapper, 1 Reducer and 1 driver class in this program as well.
2. Mapper Input <Key, Value> ==> <LongWritable, Text> ==> <BOF>,<9899821411,"Burke, Honorato U.",Alaska,Eu Incorporated>
                                                          <BOF>,<9899821422,"Bell, Emily R.",Arizona,Ut Eros Non Company>
                                                          <BOF>,<9899821379,"Hewitt, Chelsea Y.",PA,Egestas Aliquam Fringilla LLP>
                                                          <BOF>,<9899821387,"Baldwin, Merrill H.",VT,Rhoncus Proin Corp.>
                                                          .........
3. Mapper splits the text into separate words and create [City] as Key and [phone, name, company] as Value
   Mapper Output <Key, Value> ==> <Text, Text> ==> <Alaska>, <9899821411  "Burke, Honorato U."  Eu Incorporated>
                                                   <Arizona>,<9899821422  "Bell, Emily R."  Ut Eros Non Company>
                                                   <PA>, <9899821379  "Hewitt, Chelsea Y."  Egestas Aliquam Fringilla LLP>
                                                   <VT>, <<9899821387 "Baldwin, Merrill H." Rhoncus Proin Corp.>
4. Reducer input is same as above mapper output ==> <Key, Value> ==> <Text, Text> 
                                               ==> <Alaska>, <9899821411  "Burke, Honorato U."  Eu Incorporated>
                                                   <Arizona>,<9899821422  "Bell, Emily R."  Ut Eros Non Company>
                                                   <PA>, <9899821379  "Hewitt, Chelsea Y."  Egestas Aliquam Fringilla LLP>
                                                   <VT>, <<9899821387 "Baldwin, Merrill H." Rhoncus Proin Corp.>
   In reducer we need to set the output format as "MultipleOutputs" using the setup method.
             setup() ==> multipleOutputs  = new MultipleOutputs<Text, Text>(context);
             
   Then write the output based on the key  value [here the key value is City] to spli the data to multiplr files based on the city
             multipleOutputs.write(key, value, key.toString());             
             
   Then close the "MultipleOutputs" format in the cleanup method.
            multipleOutputs.close();
            
5. Finally specify the Driver Class settings as 
        LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
    This is mainly responsible for splitting the data to different files.      
    
===========================================================================================================================================
How to run:
=============

    hadoop jar <jar file name> < class name> <input file name> <output folder>
    hadoop jar MultiFileOutputDriver.jar MultiFileOutputDriver /HandsOn/input/customers.txt /HandsOn/output/multi-file-output
===========================================================================================================================================
Output Log:
=================
[edureka@localhost Jar_files]$ hadoop jar MultiFileOutputDriver.jar MultiFileOutputDriver /HandsOn/input/customers.txt /HandsOn/output/multi-file-output
17/06/05 10:06:29 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/06/05 10:06:33 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
17/06/05 10:06:38 INFO input.FileInputFormat: Total input paths to process : 1
17/06/05 10:06:39 INFO mapreduce.JobSubmitter: number of splits:1
17/06/05 10:06:39 INFO Configuration.deprecation: user.name is deprecated. Instead, use mapreduce.job.user.name
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.jar is deprecated. Instead, use mapreduce.job.jar
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.lazy.output.format is deprecated. Instead, use mapreduce.output.lazyoutputformat.outputformat
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.output.value.class is deprecated. Instead, use mapreduce.job.output.value.class
17/06/05 10:06:39 INFO Configuration.deprecation: mapreduce.map.class is deprecated. Instead, use mapreduce.job.map.class
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.job.name is deprecated. Instead, use mapreduce.job.name
17/06/05 10:06:39 INFO Configuration.deprecation: mapreduce.reduce.class is deprecated. Instead, use mapreduce.job.reduce.class
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.input.dir is deprecated. Instead, use mapreduce.input.fileinputformat.inputdir
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.output.dir is deprecated. Instead, use mapreduce.output.fileoutputformat.outputdir
17/06/05 10:06:39 INFO Configuration.deprecation: mapreduce.outputformat.class is deprecated. Instead, use mapreduce.job.outputformat.class
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.output.key.class is deprecated. Instead, use mapreduce.job.output.key.class
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.mapoutput.key.class is deprecated. Instead, use mapreduce.map.output.key.class
17/06/05 10:06:39 INFO Configuration.deprecation: mapred.working.dir is deprecated. Instead, use mapreduce.job.working.dir
17/06/05 10:06:41 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1496636535634_0001
17/06/05 10:06:44 INFO impl.YarnClientImpl: Submitted application application_1496636535634_0001 to ResourceManager at /0.0.0.0:8032
17/06/05 10:06:45 INFO mapreduce.Job: The url to track the job: http://localhost:8088/proxy/application_1496636535634_0001/
17/06/05 10:06:45 INFO mapreduce.Job: Running job: job_1496636535634_0001
17/06/05 10:07:31 INFO mapreduce.Job: Job job_1496636535634_0001 running in uber mode : false
17/06/05 10:07:31 INFO mapreduce.Job:  map 0% reduce 0%
17/06/05 10:08:09 INFO mapreduce.Job:  map 100% reduce 0%
17/06/05 10:08:56 INFO mapreduce.Job:  map 100% reduce 100%
17/06/05 10:09:06 INFO mapreduce.Job: Job job_1496636535634_0001 completed successfully
17/06/05 10:09:06 INFO mapreduce.Job: Counters: 43
	File System Counters
		FILE: Number of bytes read=2224
		FILE: Number of bytes written=164587
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=2258
		HDFS: Number of bytes written=2144
		HDFS: Number of read operations=6
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=27
	Job Counters 
		Launched map tasks=1
		Launched reduce tasks=1
		Data-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=35704
		Total time spent by all reduces in occupied slots (ms)=49735
	Map-Reduce Framework
		Map input records=37
		Map output records=37
		Map output bytes=2144
		Map output materialized bytes=2224
		Input split bytes=114
		Combine input records=0
		Combine output records=0
		Reduce input groups=26
		Reduce shuffle bytes=2224
		Reduce input records=37
		Reduce output records=0
		Spilled Records=74
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=1293
		CPU time spent (ms)=10980
		Physical memory (bytes) snapshot=213020672
		Virtual memory (bytes) snapshot=728113152
		Total committed heap usage (bytes)=137433088
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters 
		Bytes Read=2144
	File Output Format Counters 
		Bytes Written=0

[edureka@localhost Jar_files]$ hdfs dfs -ls /HandsOn/output/multi-file-output
17/06/05 10:10:31 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Found 27 items
-rw-r--r--   1 edureka supergroup        129 2017-06-05 10:08 /HandsOn/output/multi-file-output/AL-r-00000
-rw-r--r--   1 edureka supergroup         61 2017-06-05 10:08 /HandsOn/output/multi-file-output/Alabama-r-00000
-rw-r--r--   1 edureka supergroup        124 2017-06-05 10:08 /HandsOn/output/multi-file-output/Alaska-r-00000
-rw-r--r--   1 edureka supergroup        114 2017-06-05 10:08 /HandsOn/output/multi-file-output/Arizona-r-00000
-rw-r--r--   1 edureka supergroup         45 2017-06-05 10:08 /HandsOn/output/multi-file-output/CO-r-00000
-rw-r--r--   1 edureka supergroup         50 2017-06-05 10:08 /HandsOn/output/multi-file-output/DE-r-00000
-rw-r--r--   1 edureka supergroup         53 2017-06-05 10:08 /HandsOn/output/multi-file-output/Delaware-r-00000
-rw-r--r--   1 edureka supergroup         62 2017-06-05 10:08 /HandsOn/output/multi-file-output/Florida-r-00000
-rw-r--r--   1 edureka supergroup         51 2017-06-05 10:08 /HandsOn/output/multi-file-output/GA-r-00000
-rw-r--r--   1 edureka supergroup         54 2017-06-05 10:08 /HandsOn/output/multi-file-output/Hawaii-r-00000
-rw-r--r--   1 edureka supergroup         55 2017-06-05 10:08 /HandsOn/output/multi-file-output/Illinois-r-00000
-rw-r--r--   1 edureka supergroup         65 2017-06-05 10:08 /HandsOn/output/multi-file-output/Indiana-r-00000
-rw-r--r--   1 edureka supergroup        187 2017-06-05 10:08 /HandsOn/output/multi-file-output/Kentucky-r-00000
-rw-r--r--   1 edureka supergroup         60 2017-06-05 10:08 /HandsOn/output/multi-file-output/Louisiana-r-00000
-rw-r--r--   1 edureka supergroup         55 2017-06-05 10:08 /HandsOn/output/multi-file-output/MN-r-00000
-rw-r--r--   1 edureka supergroup         52 2017-06-05 10:08 /HandsOn/output/multi-file-output/Maine-r-00000
-rw-r--r--   1 edureka supergroup        107 2017-06-05 10:09 /HandsOn/output/multi-file-output/NE-r-00000
-rw-r--r--   1 edureka supergroup        151 2017-06-05 10:08 /HandsOn/output/multi-file-output/OH-r-00000
-rw-r--r--   1 edureka supergroup         44 2017-06-05 10:08 /HandsOn/output/multi-file-output/OR-r-00000
-rw-r--r--   1 edureka supergroup         67 2017-06-05 10:09 /HandsOn/output/multi-file-output/Ohio-r-00000
-rw-r--r--   1 edureka supergroup        115 2017-06-05 10:08 /HandsOn/output/multi-file-output/Oklahoma-r-00000
-rw-r--r--   1 edureka supergroup         65 2017-06-05 10:09 /HandsOn/output/multi-file-output/PA-r-00000
-rw-r--r--   1 edureka supergroup        201 2017-06-05 10:08 /HandsOn/output/multi-file-output/Pennsylvania-r-00000
-rw-r--r--   1 edureka supergroup         51 2017-06-05 10:08 /HandsOn/output/multi-file-output/VA-r-00000
-rw-r--r--   1 edureka supergroup         56 2017-06-05 10:08 /HandsOn/output/multi-file-output/VT-r-00000
-rw-r--r--   1 edureka supergroup         70 2017-06-05 10:08 /HandsOn/output/multi-file-output/Wyoming-r-00000
-rw-r--r--   1 edureka supergroup          0 2017-06-05 10:09 /HandsOn/output/multi-file-output/_SUCCESS

[edureka@localhost Jar_files]$ hdfs dfs -cat /HandsOn/output/multi-file-output/AL-r-00000
17/06/05 10:11:46 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
AL	9899821460	"Mckenzie, Katell R."	Vel Lectus Cum Foundation
AL	9899821379	"Morales, Maryam U."	Nullam Feugiat Placerat Company

[edureka@localhost Jar_files]$ hdfs dfs -cat /HandsOn/output/multi-file-output/Pennsylvania-r-00000
17/06/05 10:12:21 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Pennsylvania	9899821436	"Cantu, Mannix S."	Integer Tincidunt Aliquam Limited 
Pennsylvania	9899821410	"Mason, Wayne A."	Dolor Limited
Pennsylvania	9899821385	"Wood, Lamar W."	Vitae Aliquet Nec Limited
[edureka@localhost Jar_files]$ 
