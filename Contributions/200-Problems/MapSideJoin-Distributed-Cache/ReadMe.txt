==========================================================================================================================================
MapSideJoin uisng Distributed Cache 
==========================================================================================================================================
Program Name  : MapSideJoin.jar
Purpose       : Joining two datasets using Mapper only
Logic Used    : 1. Load the lookup file into the HDFS environment using the distributed cache
                2. Load all the lookup data into a list/array
                3. In mapper, splits the input file and into columns to match with the lookup file
                4. In the same way, split the lookup input ( list/array data) into separate fields for comparison.
                5. If the fields matches, then build the ouput in the mapper itself.
                6. No reducers were used. Explicitly we should set the reducer count to zero, else a default reducer ( Identity Reducer) 
                   will be invoked.
Lookup file Format  : department.txt < lookup file > -- Should be smaller dataset always
                [DeptNo DeptName] - Tab separated
                d001  Marketing
                d002	Finance
                d003	Human Resources
                d004	Production
                d005	Development
                d006	Quality Management
                d007	Sales
                d008	Research
                d009	Customer Service     

Input Format : employees.txt < input file > -- larger dataset
              [Emp_no DOB FName LName HireDate  DeptNo] - Tab separated
              10001  1953-09-02	Georgi	Facello	M	1986-06-26	d005
              10002	1964-06-02	Bezalel	Simmel	F	1985-11-21	d007
              10003	1959-12-03	Parto	Bamford	M	1986-08-28	d004
              10004	1954-05-01	Chirstian	Koblick	M	1986-12-01	d004
              10005	1955-01-21	Kyoichi	Maliniak	M	1989-09-12	d003
              10006	1953-04-20	Anneke	Preusig	F	1989-06-02	d005
              10009	1952-04-19	Sumant	Peac	F	1985-02-18	d006

Expected Output:   part-m-00000
          [Emp_no DOB FName LName HireDate  DeptNo DeptName]
          10002	1953-09-02	1953-09-02	Georgi	Facello	M	1986-06-26	d005	Development
          10002	1964-06-02	1964-06-02	Bezalel	Simmel	F	1985-11-21	d007	Sales
          10003	1959-12-03	1959-12-03	Parto	Bamford	M	1986-08-28	d004	Production
          10004	1954-05-01	1954-05-01	Chirstian	Koblick	M	1986-12-01	d004	Production
          10005	1955-01-21	1955-01-21	Kyoichi	Maliniak	M	1989-09-12	d003	Human Resources
          10006	1953-04-20	1953-04-20	Anneke	Preusig	F	1989-06-02	d005	Development
          10009	1952-04-19	1952-04-19	Sumant	Peac	F	1985-02-18	d006	Quality Management
==========================================================================================================================================
How to Run
=================

        hadoop jar <jar file name> <class name>      <lookup file>  <input file> <output folder>
        hadoop jar MapSideJoin.jar MapSideJoinDriver /HandsOn/input/department.txt /HandsOn/input/employees.txt /HandsOn/output/mapsidejoin-out
==========================================================================================================================================
Pseudo Code :
=============
Mapper Input : <Key, Value> ==> <BOF>, <Line>
               <0>,  <10002	1953-09-02	Georgi	Facello	M	1986-06-26	d005>
               <50>, <10002	1964-06-02	Bezalel	Simmel	F	1985-11-21	d007>
               <100>,<10003	1959-12-03	Parto	Bamford	M	1986-08-28	d004>
               <149>,<10004	1954-05-01	Chirstian	Koblick	M	1986-12-01	d004>
               ...........
               
Distribiuted Cache : Setup Method creates an ArrayList, and populate it with both deptId and deptname as a single line.
                    ArrayList[0] => [d001	Marketing]
                    ArrayList[1] => [d002	Finance]
                    ArrayList[2] =>	[d003	Human Resources]
                    .....
                    
Mapper Action :  Splits Key Value pair into string of array[] as follows.
                 <10002	1953-09-02	Georgi	Facello	M	1986-06-26	d005> 
                 Emp[0] = 10002
                 Emp[1] = 1953-09-02
                 Emp[2] = Georgi
                 Emp[3] = Facello
                 Emp[4] = M
                 Emp[5] = 1986-06-26
                 Emp[6] = d005
                 
                 Now split all records of Lookup array data into string of array as follows.
                 Dept[0] = d001
                 Dept[1] = Marketing

                 Then Compare both Emp[6] with Dept[0], if matches then concatenate the Employee data along the Dept[1].
                 Actually this is a record by record match. If a match is found the Department name will be displayed. 
                 If not "NOT-FOUND" will be displayed in place of dept name.
=======================================================================================================================================
Output Log:
=================
[edureka@localhost Jar_files]$ hadoop jar MapSideJoin.jar MapSideJoinDriver /HandsOn/input/department.txt /HandsOn/input/employees.txt /HandsOn/output/mapsidejoin-out
17/06/04 18:13:08 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/06/04 18:13:09 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
17/06/04 18:13:10 INFO input.FileInputFormat: Total input paths to process : 1
17/06/04 18:13:10 INFO mapreduce.JobSubmitter: number of splits:1
17/06/04 18:13:10 INFO Configuration.deprecation: user.name is deprecated. Instead, use mapreduce.job.user.name
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.jar is deprecated. Instead, use mapreduce.job.jar
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.cache.files.filesizes is deprecated. Instead, use mapreduce.job.cache.files.filesizes
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.cache.files is deprecated. Instead, use mapreduce.job.cache.files
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
17/06/04 18:13:10 INFO Configuration.deprecation: mapreduce.map.class is deprecated. Instead, use mapreduce.job.map.class
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.job.name is deprecated. Instead, use mapreduce.job.name
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.input.dir is deprecated. Instead, use mapreduce.input.fileinputformat.inputdir
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.output.dir is deprecated. Instead, use mapreduce.output.fileoutputformat.outputdir
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.cache.files.timestamps is deprecated. Instead, use mapreduce.job.cache.files.timestamps
17/06/04 18:13:10 INFO Configuration.deprecation: mapred.working.dir is deprecated. Instead, use mapreduce.job.working.dir
17/06/04 18:13:11 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1496499682766_0011
17/06/04 18:13:11 INFO impl.YarnClientImpl: Submitted application application_1496499682766_0011 to ResourceManager at /0.0.0.0:8032
17/06/04 18:13:11 INFO mapreduce.Job: The url to track the job: http://localhost:8088/proxy/application_1496499682766_0011/
17/06/04 18:13:11 INFO mapreduce.Job: Running job: job_1496499682766_0011
17/06/04 18:13:21 INFO mapreduce.Job: Job job_1496499682766_0011 running in uber mode : false
17/06/04 18:13:21 INFO mapreduce.Job:  map 0% reduce 0%
17/06/04 18:13:29 INFO mapreduce.Job:  map 100% reduce 0%
17/06/04 18:13:29 INFO mapreduce.Job: Job job_1496499682766_0011 completed successfully
17/06/04 18:13:29 INFO mapreduce.Job: Counters: 29
	File System Counters
		FILE: Number of bytes read=0
		FILE: Number of bytes written=79925
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=465
		HDFS: Number of bytes written=515
		HDFS: Number of read operations=5
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
	Job Counters 
		Launched map tasks=1
		Data-local map tasks=1
		Total time spent by all maps in occupied slots (ms)=5660
		Total time spent by all reduces in occupied slots (ms)=0
	Map-Reduce Framework
		Map input records=7
		Map output records=7
		Input split bytes=114
		Spilled Records=0
		Failed Shuffles=0
		Merged Map outputs=0
		GC time elapsed (ms)=52
		CPU time spent (ms)=410
		Physical memory (bytes) snapshot=54505472
		Virtual memory (bytes) snapshot=360067072
		Total committed heap usage (bytes)=16252928
	mapSideJoin.MapSideJoinMapper$MYCOUNTER
		FILE_EXISTS=1
		RECORD_COUNT=7
	File Input Format Counters 
		Bytes Read=351
	File Output Format Counters 
		Bytes Written=515
    
[edureka@localhost Jar_files]$ hdfs dfs -cat /HandsOn/output/mapsidejoin-out/part-m-00000
17/06/04 18:15:40 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
10002	1953-09-02	1953-09-02	Georgi	Facello	M	1986-06-26	d005	Development
10002	1964-06-02	1964-06-02	Bezalel	Simmel	F	1985-11-21	d007	Sales
10003	1959-12-03	1959-12-03	Parto	Bamford	M	1986-08-28	d004	Production
10004	1954-05-01	1954-05-01	Chirstian	Koblick	M	1986-12-01	d004	Production
10005	1955-01-21	1955-01-21	Kyoichi	Maliniak	M	1989-09-12	d003	Human Resources
10006	1953-04-20	1953-04-20	Anneke	Preusig	F	1989-06-02	d005	Development
10009	1952-04-19	1952-04-19	Sumant	Peac	F	1985-02-18	d006	Quality Management
[edureka@localhost Jar_files]$ 


                    
