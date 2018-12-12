package com.testL.such;


import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Field;

public class Indexer {
	private IndexWriter writer;
	/*****
	 * 		索引实例
	 * @param indexDir
	 * 		构造方法
	 * @throws Exception
	 */
	public Indexer(String indexDir) throws Exception {
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		Analyzer analyze = new StandardAnalyzer();// 分词器
		IndexWriterConfig config = new IndexWriterConfig(analyze);
		writer = new IndexWriter(dir, config);
	}
	/****
	 * 关闭索引
	 * @throws Exception
	 */
	public void close() throws Exception {
		writer.close();
	}

	/***
	 * 索引指定目录的所有文件
	 * 
	 * @param dataDir
	 * @throws Exception
	 */
	public int index(String dataDir) throws Exception {
		File[] file = new File(dataDir).listFiles();
		for (File f : file) {
			indexFile(f);
		}
		return writer.numDocs();
	}
	/**
	 * 索引指定文件
	 * @param f
	 */
	private void indexFile(File f) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("索引文件：" + f.getCanonicalPath());
		getDocument(f);
	}

	/**
	 * 获取文档中的每一个字段
	 * 
	 * @param f
	 * @return
	 */
	private Document getDocument(File f) throws Exception {
		// TODO Auto-generated method stub
		Document document = new Document();
		document.add(new TextField("contents", new FileReader(f)));
		document.add(new TextField("fileName", f.getName(), Field.Store.YES));
		document.add(new TextField("fullPath", f.getCanonicalPath(),
				Field.Store.YES));
		return document;
	}

	public static void main(String[] args) {
		String indexDir = "E:\\luence";
		String dataDir = "E:\\教程\\lucene\\yi\\资料\\data";
		Indexer indexer = null;
		int number = 0;
		long startTime = System.currentTimeMillis();// 开始时间

		try {
			indexer = new Indexer(indexDir);
			number = indexer.index(dataDir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();// 获取借宿时间
		System.out.println("索引：" + number + "个文件，共花费了" + (endTime - startTime)
				+ "毫秒");
	}
}
