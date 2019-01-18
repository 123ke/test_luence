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
	 * 		����ʵ��
	 * @param indexDir
	 * 		���췽��
	 * @throws Exception
	 */
	public Indexer(String indexDir) throws Exception {
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		Analyzer analyze = new StandardAnalyzer();// �ִ���
		IndexWriterConfig config = new IndexWriterConfig(analyze);
		writer = new IndexWriter(dir, config);
	}
	/****
	 * �ر�����
	 * @throws Exception
	 */
	public void close() throws Exception {
		writer.close();
	}

	/***
	 * ����ָ��Ŀ¼�������ļ�
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
	 * ����ָ���ļ�
	 * @param f
	 */
	private void indexFile(File f) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("�����ļ���" + f.getCanonicalPath());
		getDocument(f);
	}

	/**
	 * ��ȡ�ĵ��е�ÿһ���ֶ�
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
		String dataDir = "E:\\�̳�\\lucene\\yi\\����\\data";
		Indexer indexer = null;
		int number = 0;
		long startTime = System.currentTimeMillis();// ��ʼʱ��

		try {
			indexer = new Indexer(indexDir);
			number = indexer.index(dataDir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();// ��ȡ����ʱ��
		System.out.println("������" + number + "���ļ�����������" + (endTime - startTime)
				+ "����");
	}
}
