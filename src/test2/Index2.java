package test2;

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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Index2 {

	private static Directory directory = null;

	static {

		String pathname = new StringBuilder("c:").append(File.separatorChar)
				.append("test").append(File.separatorChar).append("lucene2")
				.toString();

		try {
			directory = FSDirectory.open(new File(pathname));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_44);
		IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(
				Version.LUCENE_44, analyzer));
		//
		writer.deleteAll();

		writer.addDocument(buildDoc(1, "corleone",
				"jiangsu province nantong city qidong"));
		writer.addDocument(buildDoc(2, "jose",
				"jiangsu province nantong city haimen"));
		writer.addDocument(buildDoc(3, "liuwenliang",
				"jiangxi province jiujiang city xiushui"));
		
		Document doc = buildDoc(4, "xiaoran", "hunan province yongzhou city");
		doc.removeField("id");
		writer.addDocument(doc);
		
		writer.addDocument(buildDoc(5, "luohui",
				"jiangxi province jiujiang city xiushui"));
		writer.addDocument(buildDoc(6, "louhui",
				"jiangxi province jiujiang city xiushui"));

		// writer.forceMerge(1);

		writer.close();

	}

	private static Document buildDoc(int id, String name, String address)
			throws IOException {

		Document doc = new Document();

		doc.add(new IntField("id", id, Field.Store.YES));
		doc.add(new StringField("name", name, Field.Store.YES));
		doc.add(new TextField("address", address, Field.Store.NO));

		return doc;
	}

}
