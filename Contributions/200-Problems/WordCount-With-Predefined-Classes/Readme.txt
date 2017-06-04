==========================================================================================================================================
Word Count program with predefined classes.
==========================================================================================================================================
Program   : WCpredefinedClasses.jar
Purpose   : to know use of inbuilt mapper/reducer classes
Logic Used  : Word count with inbuilt classes
Input Format :  input.txt 
                dinesh
                vishwa
                jagdheesh
                Shashi
                keshav
Output format : output.txt
                dinesh    1
                vishwa    1
                jagdheesh 1
                Shashi    1
                keshav    1
==========================================================================================================================================
How to Run :
================
    hadoop jar <jar file name>         < class name >      < input file name >             < output path >      
    hadoop jar WCpredefinedClasses.jar WCpredefinedClasses /HandsOn/input/wordcount_01.txt /HandsOn/output/wc-pc-out
==========================================================================================================================================
Output log:
==============
[edureka@localhost Jar_files]$ hadoop jar WCpredefinedClasses.jar WCpredefinedClasses /HandsOn/input/wordcount_01.txt /HandsOn/output/wc-pc-out
17/06/04 19:20:44 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/06/04 19:20:45 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
17/06/04 19:20:46 WARN mapreduce.JobSubmitter: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
17/06/04 19:20:46 INFO input.FileInputFormat: Total input paths to process : 1
17/06/04 19:20:47 INFO mapreduce.JobSubmitter: number of splits:1
17/06/04 19:20:47 INFO Configuration.deprecation: user.name is deprecated. Instead, use mapreduce.job.user.name
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.jar is deprecated. Instead, use mapreduce.job.jar
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.output.value.class is deprecated. Instead, use mapreduce.job.output.value.class
17/06/04 19:20:47 INFO Configuration.deprecation: mapreduce.map.class is deprecated. Instead, use mapreduce.job.map.class
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.job.name is deprecated. Instead, use mapreduce.job.name
17/06/04 19:20:47 INFO Configuration.deprecation: mapreduce.reduce.class is deprecated. Instead, use mapreduce.job.reduce.class
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.input.dir is deprecated. Instead, use mapreduce.input.fileinputformat.inputdir
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.output.dir is deprecated. Instead, use mapreduce.output.fileoutputformat.outputdir
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.output.key.class is deprecated. Instead, use mapreduce.job.output.key.class
17/06/04 19:20:47 INFO Configuration.deprecation: mapred.working.dir is deprecated. Instead, use mapreduce.job.working.dir
17/06/04 19:20:47 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1496499682766_0012
17/06/04 19:20:48 INFO impl.YarnClientImpl: Submitted application application_1496499682766_0012 to ResourceManager at /0.0.0.0:8032
17/06/04 19:20:48 INFO mapreduce.Job: The url to track the job: http://localhost:8088/proxy/application_1496499682766_0012/
17/06/04 19:20:48 INFO mapreduce.Job: Running job: job_1496499682766_0012
17/06/04 19:20:58 INFO mapreduce.Job: Job job_1496499682766_0012 running in uber mode : false
17/06/04 19:20:58 INFO mapreduce.Job:  map 0% reduce 0%
17/06/04 19:21:07 INFO mapreduce.Job:  map 100% reduce 0%
17/06/04 19:21:16 INFO mapreduce.Job:  map 100% reduce 100%
17/06/04 19:21:17 INFO mapreduce.Job: Job job_1496499682766_0012 completed successfully
17/06/04 19:21:18 INFO mapreduce.Job: Counters: 43
	File System Counters
		FILE: Number of bytes read=154
		FILE: Number of bytes written=158887
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=199
		HDFS: Number of bytes written=85
		HDFS: Number of read operations=6
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
	Job Counters 
		Launched map tasks=1
		Launched reduce tasks=1
		Data-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=6737
		Total time spent by all reduces in occupied slots (ms)=6430
	Map-Reduce Framework
		Map input records=4
		Map output records=11
		Map output bytes=126
		Map output materialized bytes=154
		Input split bytes=117
		Combine input records=0
		Combine output records=0
		Reduce input groups=9
		Reduce shuffle bytes=154
		Reduce input records=11
		Reduce output records=9
		Spilled Records=22
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=148
		CPU time spent (ms)=1290
		Physical memory (bytes) snapshot=212566016
		Virtual memory (bytes) snapshot=720814080
		Total committed heap usage (bytes)=137433088
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters 
		Bytes Read=82
	File Output Format Counters 
		Bytes Written=85
[edureka@localhost Jar_files]$ hdfs dfs -cat /HandsOn/output/wc-pc-out/part-r-00000
17/06/04 19:22:24 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
arjun	2
dinesh	1
dinesh,	1
eshwar,	1
raja,	1
ramesh	1
sathish,	2
seenu,	1
vasanth,	1
