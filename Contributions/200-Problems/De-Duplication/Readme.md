=================================================================================================================================
Program to remove duplicates
=================================================================================================================================
Program : removeDuplicates.jar
Purpose : Many of the times we would end up with cleaning the input files for the following reasons
          1. Input might have duplicates in it.
          2. Input may be invalid.
          There are even many more scenarios to be looked in while processing the input files to get the desired results.
          
          So we will talk about the first case which is having duplicates in the input.
          
          Why do we remove duplicates from the input?
          Answer  : Suppose you have a factor to be applied on the some sales done on a particular region as below
                    region1     0.5
                    region1     0.5
                    region2     0.25
                    
                    Sales :   region1   100
                              region2   200
                              
                    So after applying then the data would be as 
                              region1   50
                              region1   50
                              region2   50
                     
                    This clearly states that the data before and applying factor is same. 
                    This might even go worse as sometimes it would it double/trible making worse than ever.
=================================================================================================================================
Logic Used  :   Mapper splits the data into key, value pairs 
                But the reducer takes the key as constant and iterate for no of values available for the same key.
=================================================================================================================================
Pseudocode  :   Input :-  duplicates.csv
                
      ubu_hadoop@dinesh-VirtualBox:/usr/local/hadoop/hadoop-2.7.3/bin$ ./hdfs dfs -cat /usr/dinesh/input/duplicates.csv
                    hi, how are you?
                    hi, how are you?
                    where were you these days
                    where were you these days

          Mapper Input : <LongWritable, Text>
                    <1>, <hi, how are you?>
                    <17>, <hi, how are you?>
                    <33>, <where were you these days>
                    <60>, <where were you these days>
          
          Mapper Output : <Text, NullWritable>
                    <hi, how are you?>, <Null>
                    <hi, how are you?>, <Null>
                    <where were you these days>, <Null>
                    <where were you these days>, <Null>
                    
          Reducer Input :  <Text key, Iterator<NullWritable> value>
                    <hi, how are you?>, <Null>
                    <hi, how are you?>, <Null>
                    <where were you these days>, <Null>
                    <where were you these days>, <Null>
           
                    So here we have only two keys, and we are just sending the keys out from reducer.
                    Note that the Iterator is in place of Value. 
                    This indicates that the reduce method will be invoked once for each single key 
                    and it will iterate for available no of corresponding values.
                    
         So the final output will be as follows without any duplicates in it.

                    hi, how are you?
                    where were you these days
=================================================================================================================================
How to Run :
==========
          hadoop jar <jar_file_name.jar> <class_name> <input_path> <output_path>
          ubu_hadoop@dinesh-VirtualBox:/usr/local/hadoop/hadoop-2.7.3/bin$ ./hadoop jar /home/ubu_hadoop/Desktop/Hadoop/Jar/removeDuplicates.jar removeDuplicates.removeDuplicateDriver /usr/dinesh/input /usr/dinesh/output/remove-duplicates
=================================================================================================================================
Output Log  :
==============
ubu_hadoop@dinesh-VirtualBox:/usr/local/hadoop/hadoop-2.7.3/bin$ ./hadoop jar /home/ubu_hadoop/Desktop/Hadoop/Jar/removeDuplicates.jar removeDuplicates.removeDuplicateDriver /usr/dinesh/input /usr/dinesh/output/remove-duplicates
17/07/30 15:03:31 INFO Configuration.deprecation: session.id is deprecated. Instead, use dfs.metrics.session-id
17/07/30 15:03:31 INFO jvm.JvmMetrics: Initializing JVM Metrics with processName=JobTracker, sessionId=
17/07/30 15:03:32 INFO input.FileInputFormat: Total input paths to process : 1
17/07/30 15:03:32 INFO mapreduce.JobSubmitter: number of splits:1
17/07/30 15:03:33 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_local1484896192_0001
17/07/30 15:03:33 INFO mapreduce.Job: The url to track the job: http://localhost:8080/
17/07/30 15:03:33 INFO mapreduce.Job: Running job: job_local1484896192_0001
17/07/30 15:03:33 INFO mapred.LocalJobRunner: OutputCommitter set in config null
17/07/30 15:03:33 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 1
17/07/30 15:03:33 INFO mapred.LocalJobRunner: OutputCommitter is org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
17/07/30 15:03:34 INFO mapred.LocalJobRunner: Waiting for map tasks
17/07/30 15:03:34 INFO mapred.LocalJobRunner: Starting task: attempt_local1484896192_0001_m_000000_0
17/07/30 15:03:34 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 1
17/07/30 15:03:34 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
17/07/30 15:03:34 INFO mapred.MapTask: Processing split: hdfs://localhost:9000/usr/dinesh/input/duplicates.csv:0+86
17/07/30 15:03:34 INFO mapreduce.Job: Job job_local1484896192_0001 running in uber mode : false
17/07/30 15:03:34 INFO mapreduce.Job:  map 0% reduce 0%
17/07/30 15:03:36 INFO mapred.MapTask: (EQUATOR) 0 kvi 26214396(104857584)
17/07/30 15:03:36 INFO mapred.MapTask: mapreduce.task.io.sort.mb: 100
17/07/30 15:03:36 INFO mapred.MapTask: soft limit at 83886080
17/07/30 15:03:36 INFO mapred.MapTask: bufstart = 0; bufvoid = 104857600
17/07/30 15:03:36 INFO mapred.MapTask: kvstart = 26214396; length = 6553600
17/07/30 15:03:37 INFO mapred.MapTask: Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
17/07/30 15:03:42 INFO mapred.LocalJobRunner: 
17/07/30 15:03:42 INFO mapred.MapTask: Starting flush of map output
17/07/30 15:03:42 INFO mapred.MapTask: Spilling map output
17/07/30 15:03:42 INFO mapred.MapTask: bufstart = 0; bufend = 86; bufvoid = 104857600
17/07/30 15:03:42 INFO mapred.MapTask: kvstart = 26214396(104857584); kvend = 26214384(104857536); length = 13/6553600
17/07/30 15:03:42 INFO mapred.MapTask: Finished spill 0
17/07/30 15:03:42 INFO mapred.Task: Task:attempt_local1484896192_0001_m_000000_0 is done. And is in the process of committing
17/07/30 15:03:43 INFO mapred.LocalJobRunner: map
17/07/30 15:03:43 INFO mapred.Task: Task 'attempt_local1484896192_0001_m_000000_0' done.
17/07/30 15:03:43 INFO mapred.LocalJobRunner: Finishing task: attempt_local1484896192_0001_m_000000_0
17/07/30 15:03:43 INFO mapred.LocalJobRunner: map task executor complete.
17/07/30 15:03:43 INFO mapred.LocalJobRunner: Waiting for reduce tasks
17/07/30 15:03:43 INFO mapred.LocalJobRunner: Starting task: attempt_local1484896192_0001_r_000000_0
17/07/30 15:03:43 INFO output.FileOutputCommitter: File Output Committer Algorithm version is 1
17/07/30 15:03:43 INFO mapred.Task:  Using ResourceCalculatorProcessTree : [ ]
17/07/30 15:03:43 INFO mapred.ReduceTask: Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@1f1015dd
17/07/30 15:03:43 INFO reduce.MergeManagerImpl: MergerManager: memoryLimit=363285696, maxSingleShuffleLimit=90821424, mergeThreshold=239768576, ioSortFactor=10, memToMemMergeOutputsThreshold=10
17/07/30 15:03:43 INFO reduce.EventFetcher: attempt_local1484896192_0001_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
17/07/30 15:03:43 INFO mapreduce.Job:  map 100% reduce 0%
17/07/30 15:03:44 INFO reduce.LocalFetcher: localfetcher#1 about to shuffle output of map attempt_local1484896192_0001_m_000000_0 decomp: 96 len: 100 to MEMORY
17/07/30 15:03:44 INFO reduce.InMemoryMapOutput: Read 96 bytes from map-output for attempt_local1484896192_0001_m_000000_0
17/07/30 15:03:44 INFO reduce.MergeManagerImpl: closeInMemoryFile -> map-output of size: 96, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->96
17/07/30 15:03:44 INFO reduce.EventFetcher: EventFetcher is interrupted.. Returning
17/07/30 15:03:44 INFO mapred.LocalJobRunner: 1 / 1 copied.
17/07/30 15:03:44 INFO reduce.MergeManagerImpl: finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
17/07/30 15:03:44 INFO mapred.Merger: Merging 1 sorted segments
17/07/30 15:03:44 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 77 bytes
17/07/30 15:03:44 INFO reduce.MergeManagerImpl: Merged 1 segments, 96 bytes to disk to satisfy reduce memory limit
17/07/30 15:03:44 INFO reduce.MergeManagerImpl: Merging 1 files, 100 bytes from disk
17/07/30 15:03:44 INFO reduce.MergeManagerImpl: Merging 0 segments, 0 bytes from memory into reduce
17/07/30 15:03:44 INFO mapred.Merger: Merging 1 sorted segments
17/07/30 15:03:44 INFO mapred.Merger: Down to the last merge-pass, with 1 segments left of total size: 77 bytes
17/07/30 15:03:44 INFO mapred.LocalJobRunner: 1 / 1 copied.
17/07/30 15:03:44 INFO Configuration.deprecation: mapred.skip.on is deprecated. Instead, use mapreduce.job.skiprecords
17/07/30 15:03:45 INFO mapred.Task: Task:attempt_local1484896192_0001_r_000000_0 is done. And is in the process of committing
17/07/30 15:03:45 INFO mapred.LocalJobRunner: 1 / 1 copied.
17/07/30 15:03:45 INFO mapred.Task: Task attempt_local1484896192_0001_r_000000_0 is allowed to commit now
17/07/30 15:03:45 INFO output.FileOutputCommitter: Saved output of task 'attempt_local1484896192_0001_r_000000_0' to hdfs://localhost:9000/usr/dinesh/output/remove-duplicates/_temporary/0/task_local1484896192_0001_r_000000
17/07/30 15:03:45 INFO mapred.LocalJobRunner: reduce > reduce
17/07/30 15:03:45 INFO mapred.Task: Task 'attempt_local1484896192_0001_r_000000_0' done.
17/07/30 15:03:45 INFO mapred.LocalJobRunner: Finishing task: attempt_local1484896192_0001_r_000000_0
17/07/30 15:03:45 INFO mapred.LocalJobRunner: reduce task executor complete.
17/07/30 15:03:45 INFO mapreduce.Job:  map 100% reduce 100%
17/07/30 15:03:46 INFO mapreduce.Job: Job job_local1484896192_0001 completed successfully
17/07/30 15:03:47 INFO mapreduce.Job: Counters: 35
	File System Counters
		FILE: Number of bytes read=6678
		FILE: Number of bytes written=589218
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=172
		HDFS: Number of bytes written=43
		HDFS: Number of read operations=13
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=4
	Map-Reduce Framework
		Map input records=4
		Map output records=4
		Map output bytes=86
		Map output materialized bytes=100
		Input split bytes=118
		Combine input records=0
		Combine output records=0
		Reduce input groups=2
		Reduce shuffle bytes=100
		Reduce input records=4
		Reduce output records=2
		Spilled Records=8
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=54
		Total committed heap usage (bytes)=274866176
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters 
		Bytes Read=86
	File Output Format Counters 
		Bytes Written=43
ubu_hadoop@dinesh-VirtualBox:/usr/local/hadoop/hadoop-2.7.3/bin$ ./hdfs dfs -ls /usr/dinesh/output
Found 1 items
drwxr-xr-x   - ubu_hadoop supergroup          0 2017-07-30 15:03 /usr/dinesh/output/remove-duplicates
ubu_hadoop@dinesh-VirtualBox:/usr/local/hadoop/hadoop-2.7.3/bin$ ./hdfs dfs -ls /usr/dinesh/output/remove-duplicates
Found 2 items
-rw-r--r--   1 ubu_hadoop supergroup          0 2017-07-30 15:03 /usr/dinesh/output/remove-duplicates/_SUCCESS
-rw-r--r--   1 ubu_hadoop supergroup         43 2017-07-30 15:03 /usr/dinesh/output/remove-duplicates/part-r-00000
ubu_hadoop@dinesh-VirtualBox:/usr/local/hadoop/hadoop-2.7.3/bin$ ./hdfs dfs -cat /usr/dinesh/output/remove-duplicates/part-r-00000
hi, how are you?
where were you these days
