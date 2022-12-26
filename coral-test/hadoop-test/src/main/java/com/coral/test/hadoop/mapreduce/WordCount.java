package com.coral.test.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huss
 * @version 1.0
 * @className WordCount
 * @description 单词数量统计
 * @date 2022/12/26 9:07
 */
public class WordCount {

    /**
     * hadoop jar hadoop-test.jar com.coral.test.hadoop.mapreduce.WordCount /test/wordcount/intput/hadoop-test-wordcount.txt /test/wordcount/intput/test1.txt /test/wordcount/output/result
     * 输入需要上传到 hdfs对应的目录中 /test/wordcount/intput/hadoop-test-wordcount.txt /test/wordcount/intput/test1.txt
     * 输出需要在hdfs上建立对应的目录
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "my word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        for (int i = 0, size = otherArgs.length; i < size - 1; i++) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text,
                IntWritable>.Context context) throws IOException, InterruptedException {
            AtomicInteger sum = new AtomicInteger();
            values.iterator().forEachRemaining(it -> sum.addAndGet(it.get()));
            this.result.set(sum.get());
            context.write(key, this.result);
        }
    }

    static class TokenizerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private Text outK = new Text();
        private final IntWritable outV = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
                throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                outK.set(itr.nextToken());
                context.write(outK, outV);
            }
        }
    }
}
