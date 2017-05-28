==========================================================================================================================================
Secondary Sorting Technique
==========================================================================================================================================
Program Name  : Sort.jar
Purpose       : To do sorting as per the requirement
Logic Used    : By default, Mapreduce sorts the data based on keys only.
                This functionality would not work in all scenarios.
                To achieve sorting we would need to create a custom key value pair.
                
                1. Custom Key value pair can be created using a WritableComparable.
                      readFields() -- Deserialization
                      write()      -- serialization
                      compareTo    -- to compare the current and previous key - value pair and to decide on sorting
                2. Then create a mapper and generate the custom key-value pair
                3. Create a WritableComparator to do sorting 
                4. Create a WritableComparator to do grouping
                5. Create a partitioner to route the data to relevant reducers
                6. Create a reducer to just put the data out
                7. Create a driver class to run the job.
Sample Input    : 
<emp_src.txt>       EmpID DOB         FName     LName     Gender  Hire date     DeptID
                    10003 1959-12-03  Parto     Bamford   M       1986-08-28    d004
                    10004 1954-05-01  Chirstian Koblick   M       1986-12-01    d004
                    10005 1955-01-21  Kyoichi   Maliniak  M       1989-09-12    d003

Expected output :   
                      Sort order: [DeptID asc, {LName,FName,EmpID} desc]

                      DeptID  LName       FName       EmpID
                      d001    Zykh    Sudhanshu       205927
                      d001    Zykh    Nidapan 452738
==========================================================================================================================================
Pseudo Code :
================
1. Mapper Input ==> <BOF>, <10003 1959-12-03  Parto     Bamford   M       1986-08-28    d004>
                    <BOF>, <10004 1954-05-01  Chirstian Koblick   M       1986-12-01    d004>
                    <BOF>, <10005 1955-01-21  Kyoichi   Maliniak  M       1989-09-12    d003>
2. Mapper Output ==> <d004>, <1986-08-28  Parto     Bamford 10003>
                     <d004>, <1986-12-01  Chirstian Koblick 10004>
                     <d003>, <1989-09-12  Kyoichi   Maliniak  10005>
3. SortComparator ==> <d003>, <1989-09-12  Kyoichi   Maliniak  10005>
                      <d004>, <1986-08-28  Parto     Bamford 10003>
                      <d004>, <1986-12-01  Chirstian Koblick 10004>
4. Grouping Comparatpr ==>  <d003>, <1989-09-12  Kyoichi   Maliniak  10005>
                            <d004>, <1986-12-01  Chirstian Koblick 10004>
                            <d004>, <1986-08-28  Parto     Bamford 10003>
5. Partitioner   ==> splits the mapper output data into the different reducers based on the number of reducers
6. Reducer input ==> <d003>, <1989-09-12  Kyoichi   Maliniak  10005>
                     <d004>, <1986-12-01  Chirstian Koblick 10004>
                     <d004>, <1986-08-28  Parto     Bamford 10003>
7. Reducer Output ==> <d003 1989-09-12  Kyoichi   Maliniak  10005>, <null>
                      <d004 1986-12-01  Chirstian Koblick 10004>, <null>
                      <d004 1986-08-28  Parto     Bamford 10003>, <null>                   
==========================================================================================================================================
Output Log:
================
[edureka@localhost Jar_files]$ hadoop jar sort.jar SecondarySortBasicDriver /HandsOn/input/emp_src.txt /HandsOn/output/emp-sort-out
17/05/28 23:31:33 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/05/28 23:31:35 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
17/05/28 23:31:36 INFO input.FileInputFormat: Total input paths to process : 1
17/05/28 23:31:36 INFO mapreduce.JobSubmitter: number of splits:1
17/05/28 23:31:36 INFO Configuration.deprecation: user.name is deprecated. Instead, use mapreduce.job.user.name
17/05/28 23:31:36 INFO Configuration.deprecation: mapred.jar is deprecated. Instead, use mapreduce.job.jar
17/05/28 23:31:36 INFO Configuration.deprecation: mapreduce.partitioner.class is deprecated. Instead, use mapreduce.job.partitioner.class
17/05/28 23:31:36 INFO Configuration.deprecation: mapred.output.value.class is deprecated. Instead, use mapreduce.job.output.value.class
17/05/28 23:31:36 INFO Configuration.deprecation: mapred.output.key.comparator.class is deprecated. Instead, use mapreduce.job.output.key.comparator.class
17/05/28 23:31:36 INFO Configuration.deprecation: mapred.mapoutput.value.class is deprecated. Instead, use mapreduce.map.output.value.class
17/05/28 23:31:37 INFO Configuration.deprecation: mapreduce.map.class is deprecated. Instead, use mapreduce.job.map.class
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.job.name is deprecated. Instead, use mapreduce.job.name
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.output.value.groupfn.class is deprecated. Instead, use mapreduce.job.output.group.comparator.class
17/05/28 23:31:37 INFO Configuration.deprecation: mapreduce.reduce.class is deprecated. Instead, use mapreduce.job.reduce.class
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.input.dir is deprecated. Instead, use mapreduce.input.fileinputformat.inputdir
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.output.dir is deprecated. Instead, use mapreduce.output.fileoutputformat.outputdir
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.output.key.class is deprecated. Instead, use mapreduce.job.output.key.class
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.mapoutput.key.class is deprecated. Instead, use mapreduce.map.output.key.class
17/05/28 23:31:37 INFO Configuration.deprecation: mapred.working.dir is deprecated. Instead, use mapreduce.job.working.dir
17/05/28 23:31:37 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1495881173468_0015
17/05/28 23:31:37 INFO impl.YarnClientImpl: Submitted application application_1495881173468_0015 to ResourceManager at /0.0.0.0:8032
17/05/28 23:31:38 INFO mapreduce.Job: The url to track the job: http://localhost:8088/proxy/application_1495881173468_0015/
17/05/28 23:31:38 INFO mapreduce.Job: Running job: job_1495881173468_0015
17/05/28 23:31:50 INFO mapreduce.Job: Job job_1495881173468_0015 running in uber mode : false
17/05/28 23:31:50 INFO mapreduce.Job:  map 0% reduce 0%
17/05/28 23:32:00 INFO mapreduce.Job:  map 100% reduce 0%
17/05/28 23:32:10 INFO mapreduce.Job:  map 100% reduce 100%
17/05/28 23:32:10 INFO mapreduce.Job: Job job_1495881173468_0015 completed successfully
17/05/28 23:32:11 INFO mapreduce.Job: Counters: 43
	File System Counters
		FILE: Number of bytes read=145
		FILE: Number of bytes written=161205
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=312
		HDFS: Number of bytes written=115
		HDFS: Number of read operations=6
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
	Job Counters 
		Launched map tasks=1
		Launched reduce tasks=1
		Data-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=8507
		Total time spent by all reduces in occupied slots (ms)=7677
	Map-Reduce Framework
		Map input records=4
		Map output records=3
		Map output bytes=133
		Map output materialized bytes=145
		Input split bytes=112
		Combine input records=0
		Combine output records=0
		Reduce input groups=2
		Reduce shuffle bytes=145
		Reduce input records=3
		Reduce output records=3
		Spilled Records=6
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=209
		CPU time spent (ms)=1270
		Physical memory (bytes) snapshot=212172800
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
		Bytes Read=200
	File Output Format Counters 
		Bytes Written=115
                     
                     
