All JARs which are tested and working fine are placed here.

Can be blindly copied and executed for use.

# all angular braces are placeholders for input <give-me-a-value-here>

# to see contents inside a JAR
$ jar -tvf <jar-file-name>

General Usage:
$ hadoop jar <jar-file-name> <full-qualified-class-name> <input-file-path> <output-file-path>


JARs Usage:
-----------

# for word-count-tool.jar
$ hadoop jar word-count-tool.jar org.indianschool.hadoop.mr.examples.WordCountTool <input-file> <output-file>
