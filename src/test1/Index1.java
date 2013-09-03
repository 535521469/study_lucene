package test1;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class Index1 {

	private static Directory directory = null;

	static {
		String filePath = new StringBuilder("c:").append(File.separatorChar)
				.append("test").append(File.separatorChar).append("lucene1")
				.toString();
		try {
			directory = new SimpleFSDirectory(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_44);

		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				analyzer);

		try (IndexWriter writer = new IndexWriter(directory, iwc);) {
			writer.addDocument(createDoc(1, "corleone",
					"jiangsu province nantong city"));
			writer.addDocument(createDoc(3, "liuwenliang",
					"jiangxi province jiujiang city"));
			writer.addDocument(createDoc(2, "jose",
					"jiangsu province haimen city"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Document createDoc(int id, String name, String address) {
		Document doc = new Document();
		Field fname = new StringField("name", name, Field.Store.YES);
		Field fid = new IntField("id", id, Field.Store.YES);
		Field faddr = new TextField("address", address, Field.Store.NO);
		doc.add(fid);
		doc.add(fname);
		doc.add(faddr);
		return doc;
	}

}