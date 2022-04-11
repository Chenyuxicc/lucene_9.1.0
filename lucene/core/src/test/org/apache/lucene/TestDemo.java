/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.tests.util.LuceneTestCase;
import org.apache.lucene.util.IOUtils;

/**
 * A very simple demo used in the API documentation (src/java/overview.html).
 *
 * <p>Please try to keep src/java/overview.html up-to-date when making changes to this class.
 */
public class TestDemo extends LuceneTestCase {

  public void testDemo() throws IOException {
    String longTerm =
        "longtermlongtermlongtermlongtermlongtermlongtermlongtermlong"
            + "termlongtermlongtermlongtermlongtermlongtermlongtermlongterm"
            + "longtermlongtermlongterm";
    String text = "This is the text to be indexed. " + longTerm;

    Path indexPath = Files.createTempDirectory("tempIndex");
    try (Directory dir = FSDirectory.open(indexPath)) {
      Analyzer analyzer = new StandardAnalyzer();
      try (IndexWriter iw = new IndexWriter(dir, new IndexWriterConfig(analyzer))) {
        //创建一个Document
        Document doc = new Document();
        //类型是TextField field.store设置为yes 表示存储field源数据
        doc.add(newTextField("fieldname", text, Field.Store.YES));
        iw.addDocument(doc);
      }

      // Now search the index.
      try (IndexReader reader = DirectoryReader.open(dir)) {
        //构造searcher
        IndexSearcher searcher = newSearcher(reader);
        //判断两个long是否相等
        //searcher.count 计算有多少个doc匹配query
        assertEquals(1, searcher.count(new TermQuery(new Term("fieldname", longTerm))));

        //构造query 这是一个term query 查询fieldname里是否包含有text
        Query query = new TermQuery(new Term("fieldname", "text"));
        TopDocs hits = searcher.search(query, 1);

        Gson gson = new Gson();
        String s = gson.toJson(hits);
        System.out.println("hits:" + s);
        assertEquals(1, hits.totalHits.value);

        // Iterate through the results.
        for (int i = 0; i < hits.scoreDocs.length; i++) {
          Document hitDoc = searcher.doc(hits.scoreDocs[i].doc);
          assertEquals(text, hitDoc.get("fieldname"));
        }

        // Test simple phrase query.
        PhraseQuery phraseQuery = new PhraseQuery("fieldname", "to", "be");
        assertEquals(1, searcher.count(phraseQuery));
      }
    }
    //删除文件/目录
    IOUtils.rm(indexPath);
  }
}
