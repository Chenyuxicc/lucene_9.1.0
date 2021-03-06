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
package org.apache.lucene.analysis.lt;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.tests.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.tests.analysis.MockTokenizer;
import org.tartarus.snowball.ext.LithuanianStemmer;

/**
 * Basic tests for {@link LithuanianStemmer}. We test some n/adj templates from wikipedia and some
 * high frequency terms from mixed corpora.
 */
public class TestLithuanianStemming extends BaseTokenStreamTestCase {
  private Analyzer a;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    a =
        new Analyzer() {
          @Override
          protected TokenStreamComponents createComponents(String fieldName) {
            Tokenizer tokenizer = new MockTokenizer(MockTokenizer.WHITESPACE, false);
            return new TokenStreamComponents(
                tokenizer, new SnowballFilter(tokenizer, new LithuanianStemmer()));
          }
        };
  }

  @Override
  public void tearDown() throws Exception {
    a.close();
    super.tearDown();
  }

  public void testNounsI() throws IOException {
    // n. decl. I (-as)
    checkOneTerm(a, "vaikas", "vaik"); // nom. sing.
    checkOneTerm(a, "vaikai", "vaik"); // nom. pl.
    checkOneTerm(a, "vaiko", "vaik"); // gen. sg.
    checkOneTerm(a, "vaik??", "vaik"); // gen. pl.
    checkOneTerm(a, "vaikui", "vaik"); // dat. sg.
    checkOneTerm(a, "vaikams", "vaik"); // dat. pl.
    checkOneTerm(a, "vaik??", "vaik"); // acc. sg.
    checkOneTerm(a, "vaikus", "vaik"); // acc. pl.
    checkOneTerm(a, "vaiku", "vaik"); // ins. sg.
    checkOneTerm(a, "vaikais", "vaik"); // ins. pl.
    checkOneTerm(a, "vaike", "vaik"); // loc. sg.
    checkOneTerm(a, "vaikuose", "vaik"); // loc. pl.
    checkOneTerm(a, "vaike", "vaik"); // voc. sg.
    checkOneTerm(a, "vaikai", "vaik"); // voc. pl.

    // n. decl. I (-is)
    checkOneTerm(a, "brolis", "brol"); // nom. sing.
    checkOneTerm(a, "broliai", "brol"); // nom. pl.
    checkOneTerm(a, "brolio", "brol"); // gen. sg.
    checkOneTerm(a, "broli??", "brol"); // gen. pl.
    checkOneTerm(a, "broliui", "brol"); // dat. sg.
    checkOneTerm(a, "broliams", "brol"); // dat. pl.
    checkOneTerm(a, "brol??", "brol"); // acc. sg.
    checkOneTerm(a, "brolius", "brol"); // acc. pl.
    checkOneTerm(a, "broliu", "brol"); // ins. sg.
    checkOneTerm(a, "broliais", "brol"); // ins. pl.
    checkOneTerm(a, "brolyje", "brol"); // loc. sg.
    checkOneTerm(a, "broliuose", "brol"); // loc. pl.
    checkOneTerm(a, "broli", "brol"); // voc. sg.
    checkOneTerm(a, "broliai", "brol"); // voc. pl.

    // n. decl. I (-ys)
    // note: some forms don't conflate
    checkOneTerm(a, "arklys", "arkl"); // nom. sing.
    checkOneTerm(a, "arkliai", "arkliai"); // nom. pl.
    checkOneTerm(a, "arklio", "arkl"); // gen. sg.
    checkOneTerm(a, "arkli??", "arkl"); // gen. pl.
    checkOneTerm(a, "arkliui", "arkliui"); // dat. sg.
    checkOneTerm(a, "arkliams", "arkliam"); // dat. pl.
    checkOneTerm(a, "arkl??", "arkl"); // acc. sg.
    checkOneTerm(a, "arklius", "arklius"); // acc. pl.
    checkOneTerm(a, "arkliu", "arkl"); // ins. sg.
    checkOneTerm(a, "arkliais", "arkliais"); // ins. pl.
    checkOneTerm(a, "arklyje", "arklyj"); // loc. sg.
    checkOneTerm(a, "arkliuose", "arkliuos"); // loc. pl.
    checkOneTerm(a, "arkly", "arkl"); // voc. sg.
    checkOneTerm(a, "arkliai", "arkliai"); // voc. pl.
  }

  public void testNounsII() throws IOException {
    // n. decl II (-a)
    checkOneTerm(a, "motina", "motin"); // nom. sing.
    checkOneTerm(a, "motinos", "motin"); // nom. pl.
    checkOneTerm(a, "motinos", "motin"); // gen. sg.
    checkOneTerm(a, "motin??", "motin"); // gen. pl.
    checkOneTerm(a, "motinai", "motin"); // dat. sg.
    checkOneTerm(a, "motinoms", "motin"); // dat. pl.
    checkOneTerm(a, "motin??", "motin"); // acc. sg.
    checkOneTerm(a, "motinas", "motin"); // acc. pl.
    checkOneTerm(a, "motina", "motin"); // ins. sg.
    checkOneTerm(a, "motinomis", "motin"); // ins. pl.
    checkOneTerm(a, "motinoje", "motin"); // loc. sg.
    checkOneTerm(a, "motinose", "motin"); // loc. pl.
    checkOneTerm(a, "motina", "motin"); // voc. sg.
    checkOneTerm(a, "motinos", "motin"); // voc. pl.

    // n. decl II (-??)
    checkOneTerm(a, "kat??", "kat"); // nom. sing.
    checkOneTerm(a, "kat??s", "kat"); // nom. pl.
    checkOneTerm(a, "kat??s", "kat"); // gen. sg.
    checkOneTerm(a, "ka??i??", "kat"); // gen. pl.
    checkOneTerm(a, "katei", "kat"); // dat. sg.
    checkOneTerm(a, "kat??ms", "kat"); // dat. pl.
    checkOneTerm(a, "kat??", "kat"); // acc. sg.
    checkOneTerm(a, "kates", "kat"); // acc. pl.
    checkOneTerm(a, "kate", "kat"); // ins. sg.
    checkOneTerm(a, "kat??mis", "kat"); // ins. pl.
    checkOneTerm(a, "kat??je", "kat"); // loc. sg.
    checkOneTerm(a, "kat??se", "kat"); // loc. pl.
    checkOneTerm(a, "kate", "kat"); // voc. sg.
    checkOneTerm(a, "kat??s", "kat"); // voc. pl.

    // n. decl II (-ti)
    checkOneTerm(a, "pati", "pat"); // nom. sing.
    checkOneTerm(a, "pa??ios", "pat"); // nom. pl.
    checkOneTerm(a, "pa??ios", "pat"); // gen. sg.
    checkOneTerm(a, "pa??i??", "pat"); // gen. pl.
    checkOneTerm(a, "pa??iai", "pat"); // dat. sg.
    checkOneTerm(a, "pa??ioms", "pat"); // dat. pl.
    checkOneTerm(a, "pa??i??", "pat"); // acc. sg.
    checkOneTerm(a, "pa??ias", "pat"); // acc. pl.
    checkOneTerm(a, "pa??ia", "pat"); // ins. sg.
    checkOneTerm(a, "pa??iomis", "pat"); // ins. pl.
    checkOneTerm(a, "pa??ioje", "pat"); // loc. sg.
    checkOneTerm(a, "pa??iose", "pat"); // loc. pl.
    checkOneTerm(a, "pati", "pat"); // voc. sg.
    checkOneTerm(a, "pa??ios", "pat"); // voc. pl.
  }

  public void testNounsIII() throws IOException {
    // n. decl III-m
    checkOneTerm(a, "vagis", "vag"); // nom. sing.
    checkOneTerm(a, "vagys", "vag"); // nom. pl.
    checkOneTerm(a, "vagies", "vag"); // gen. sg.
    checkOneTerm(a, "vagi??", "vag"); // gen. pl.
    checkOneTerm(a, "vagiui", "vag"); // dat. sg.
    checkOneTerm(a, "vagims", "vag"); // dat. pl.
    checkOneTerm(a, "vag??", "vag"); // acc. sg.
    checkOneTerm(a, "vagis", "vag"); // acc. pl.
    checkOneTerm(a, "vagimi", "vag"); // ins. sg.
    checkOneTerm(a, "vagimis", "vag"); // ins. pl.
    checkOneTerm(a, "vagyje", "vag"); // loc. sg.
    checkOneTerm(a, "vagyse", "vag"); // loc. pl.
    checkOneTerm(a, "vagie", "vag"); // voc. sg.
    checkOneTerm(a, "vagys", "vag"); // voc. pl.

    // n. decl III-f
    checkOneTerm(a, "akis", "ak"); // nom. sing.
    checkOneTerm(a, "akys", "ak"); // nom. pl.
    checkOneTerm(a, "akies", "ak"); // gen. sg.
    checkOneTerm(a, "aki??", "ak"); // gen. pl.
    checkOneTerm(a, "akiai", "ak"); // dat. sg.
    checkOneTerm(a, "akims", "ak"); // dat. pl.
    checkOneTerm(a, "ak??", "ak"); // acc. sg.
    checkOneTerm(a, "akis", "ak"); // acc. pl.
    checkOneTerm(a, "akimi", "ak"); // ins. sg.
    checkOneTerm(a, "akimis", "ak"); // ins. pl.
    checkOneTerm(a, "akyje", "ak"); // loc. sg.
    checkOneTerm(a, "akyse", "ak"); // loc. pl.
    checkOneTerm(a, "akie", "ak"); // voc. sg.
    checkOneTerm(a, "akys", "ak"); // voc. pl.
  }

  public void testNounsIV() throws IOException {
    // n. decl IV (-us)
    checkOneTerm(a, "s??nus", "s??n"); // nom. sing.
    checkOneTerm(a, "s??n??s", "s??n"); // nom. pl.
    checkOneTerm(a, "s??naus", "s??n"); // gen. sg.
    checkOneTerm(a, "s??n??", "s??n"); // gen. pl.
    checkOneTerm(a, "s??nui", "s??n"); // dat. sg.
    checkOneTerm(a, "s??nums", "s??n"); // dat. pl.
    checkOneTerm(a, "s??n??", "s??n"); // acc. sg.
    checkOneTerm(a, "s??nus", "s??n"); // acc. pl.
    checkOneTerm(a, "s??numi", "s??n"); // ins. sg.
    checkOneTerm(a, "s??numis", "s??n"); // ins. pl.
    checkOneTerm(a, "s??nuje", "s??n"); // loc. sg.
    checkOneTerm(a, "s??nuose", "s??n"); // loc. pl.
    checkOneTerm(a, "s??nau", "s??n"); // voc. sg.
    checkOneTerm(a, "s??n??s", "s??n"); // voc. pl.

    // n. decl IV (-ius)
    checkOneTerm(a, "profesorius", "profesor"); // nom. sing.
    checkOneTerm(a, "profesoriai", "profesor"); // nom. pl.
    checkOneTerm(a, "profesoriaus", "profesor"); // gen. sg.
    checkOneTerm(a, "profesori??", "profesor"); // gen. pl.
    checkOneTerm(a, "profesoriui", "profesor"); // dat. sg.
    checkOneTerm(a, "profesoriams", "profesor"); // dat. pl.
    checkOneTerm(a, "profesori??", "profesor"); // acc. sg.
    checkOneTerm(a, "profesorius", "profesor"); // acc. pl.
    checkOneTerm(a, "profesoriumi", "profesor"); // ins. sg.
    checkOneTerm(a, "profesoriais", "profesor"); // ins. pl.
    checkOneTerm(a, "profesoriuje", "profesor"); // loc. sg.
    checkOneTerm(a, "profesoriuose", "profesor"); // loc. pl.
    checkOneTerm(a, "profesoriau", "profesor"); // voc. sg.
    checkOneTerm(a, "profesoriai", "profesor"); // voc. pl.
  }

  public void testNounsV() throws IOException {
    // n. decl V
    // note: gen.pl. doesn't conflate
    checkOneTerm(a, "vanduo", "vand"); // nom. sing.
    checkOneTerm(a, "vandenys", "vand"); // nom. pl.
    checkOneTerm(a, "vandens", "vand"); // gen. sg.
    checkOneTerm(a, "vanden??", "vanden"); // gen. pl.
    checkOneTerm(a, "vandeniui", "vand"); // dat. sg.
    checkOneTerm(a, "vandenims", "vand"); // dat. pl.
    checkOneTerm(a, "vanden??", "vand"); // acc. sg.
    checkOneTerm(a, "vandenis", "vand"); // acc. pl.
    checkOneTerm(a, "vandeniu", "vand"); // ins. sg.
    checkOneTerm(a, "vandenimis", "vand"); // ins. pl.
    checkOneTerm(a, "vandenyje", "vand"); // loc. sg.
    checkOneTerm(a, "vandenyse", "vand"); // loc. pl.
    checkOneTerm(a, "vandenie", "vand"); // voc. sg.
    checkOneTerm(a, "vandenys", "vand"); // voc. pl.
  }

  public void testAdjI() throws IOException {
    // adj. decl I
    checkOneTerm(a, "geras", "ger"); // m. nom. sing.
    checkOneTerm(a, "geri", "ger"); // m. nom. pl.
    checkOneTerm(a, "gero", "ger"); // m. gen. sg.
    checkOneTerm(a, "ger??", "ger"); // m. gen. pl.
    checkOneTerm(a, "geram", "ger"); // m. dat. sg.
    checkOneTerm(a, "geriems", "ger"); // m. dat. pl.
    checkOneTerm(a, "ger??", "ger"); // m. acc. sg.
    checkOneTerm(a, "gerus", "ger"); // m. acc. pl.
    checkOneTerm(a, "geru", "ger"); // m. ins. sg.
    checkOneTerm(a, "gerais", "ger"); // m. ins. pl.
    checkOneTerm(a, "gerame", "ger"); // m. loc. sg.
    checkOneTerm(a, "geruose", "ger"); // m. loc. pl.

    checkOneTerm(a, "gera", "ger"); // f. nom. sing.
    checkOneTerm(a, "geros", "ger"); // f. nom. pl.
    checkOneTerm(a, "geros", "ger"); // f. gen. sg.
    checkOneTerm(a, "ger??", "ger"); // f. gen. pl.
    checkOneTerm(a, "gerai", "ger"); // f. dat. sg.
    checkOneTerm(a, "geroms", "ger"); // f. dat. pl.
    checkOneTerm(a, "ger??", "ger"); // f. acc. sg.
    checkOneTerm(a, "geras", "ger"); // f. acc. pl.
    checkOneTerm(a, "gera", "ger"); // f. ins. sg.
    checkOneTerm(a, "geromis", "ger"); // f. ins. pl.
    checkOneTerm(a, "geroje", "ger"); // f. loc. sg.
    checkOneTerm(a, "gerose", "ger"); // f. loc. pl.
  }

  public void testAdjII() throws IOException {
    // adj. decl II
    checkOneTerm(a, "gra??us", "gra??"); // m. nom. sing.
    checkOneTerm(a, "gra????s", "gra??"); // m. nom. pl.
    checkOneTerm(a, "gra??aus", "gra??"); // m. gen. sg.
    checkOneTerm(a, "gra??i??", "gra??"); // m. gen. pl.
    checkOneTerm(a, "gra??iam", "gra??"); // m. dat. sg.
    checkOneTerm(a, "gra??iems", "gra??"); // m. dat. pl.
    checkOneTerm(a, "gra????", "gra??"); // m. acc. sg.
    checkOneTerm(a, "gra??ius", "gra??"); // m. acc. pl.
    checkOneTerm(a, "gra??iu", "gra??"); // m. ins. sg.
    checkOneTerm(a, "gra??iais", "gra??"); // m. ins. pl.
    checkOneTerm(a, "gra??iame", "gra??"); // m. loc. sg.
    checkOneTerm(a, "gra??iuose", "gra??"); // m. loc. pl.

    checkOneTerm(a, "gra??i", "gra??"); // f. nom. sing.
    checkOneTerm(a, "gra??ios", "gra??"); // f. nom. pl.
    checkOneTerm(a, "gra??ios", "gra??"); // f. gen. sg.
    checkOneTerm(a, "gra??i??", "gra??"); // f. gen. pl.
    checkOneTerm(a, "gra??iai", "gra??"); // f. dat. sg.
    checkOneTerm(a, "gra??ioms", "gra??"); // f. dat. pl.
    checkOneTerm(a, "gra??i??", "gra??"); // f. acc. sg.
    checkOneTerm(a, "gra??ias", "gra??"); // f. acc. pl.
    checkOneTerm(a, "gra??ia", "gra??"); // f. ins. sg.
    checkOneTerm(a, "gra??iomis", "gra??"); // f. ins. pl.
    checkOneTerm(a, "gra??ioje", "gra??"); // f. loc. sg.
    checkOneTerm(a, "gra??iose", "gra??"); // f. loc. pl.
  }

  public void testAdjIII() throws IOException {
    // adj. decl III
    checkOneTerm(a, "vidutinis", "vidutin"); // m. nom. sing.
    checkOneTerm(a, "vidutiniai", "vidutin"); // m. nom. pl.
    checkOneTerm(a, "vidutinio", "vidutin"); // m. gen. sg.
    checkOneTerm(a, "vidutini??", "vidutin"); // m. gen. pl.
    checkOneTerm(a, "vidutiniam", "vidutin"); // m. dat. sg.
    checkOneTerm(a, "vidutiniams", "vidutin"); // m. dat. pl.
    checkOneTerm(a, "vidutin??", "vidutin"); // m. acc. sg.
    checkOneTerm(a, "vidutinius", "vidutin"); // m. acc. pl.
    checkOneTerm(a, "vidutiniu", "vidutin"); // m. ins. sg.
    checkOneTerm(a, "vidutiniais", "vidutin"); // m. ins. pl.
    checkOneTerm(a, "vidutiniame", "vidutin"); // m. loc. sg.
    checkOneTerm(a, "vidutiniuose", "vidutin"); // m. loc. pl.

    checkOneTerm(a, "vidutin??", "vidutin"); // f. nom. sing.
    checkOneTerm(a, "vidutin??s", "vidutin"); // f. nom. pl.
    checkOneTerm(a, "vidutin??s", "vidutin"); // f. gen. sg.
    checkOneTerm(a, "vidutini??", "vidutin"); // f. gen. pl.
    checkOneTerm(a, "vidutinei", "vidutin"); // f. dat. sg.
    checkOneTerm(a, "vidutin??ms", "vidutin"); // f. dat. pl.
    checkOneTerm(a, "vidutin??", "vidutin"); // f. acc. sg.
    checkOneTerm(a, "vidutines", "vidutin"); // f. acc. pl.
    checkOneTerm(a, "vidutine", "vidutin"); // f. ins. sg.
    checkOneTerm(a, "vidutin??mis", "vidutin"); // f. ins. pl.
    checkOneTerm(a, "vidutin??je", "vidutin"); // f. loc. sg.
    checkOneTerm(a, "vidutin??se", "vidutin"); // f. loc. pl.
  }

  /** test some high frequency terms from corpora to look for anything crazy */
  public void testHighFrequencyTerms() throws IOException {
    checkOneTerm(a, "ir", "ir");
    checkOneTerm(a, "kad", "kad");
    checkOneTerm(a, "??", "??");
    checkOneTerm(a, "tai", "tai");
    checkOneTerm(a, "su", "su");
    checkOneTerm(a, "o", "o");
    checkOneTerm(a, "i??", "i??");
    checkOneTerm(a, "kaip", "kaip");
    checkOneTerm(a, "bet", "bet");
    checkOneTerm(a, "yra", "yr");
    checkOneTerm(a, "buvo", "buv");
    checkOneTerm(a, "tik", "tik");
    checkOneTerm(a, "ne", "ne");
    checkOneTerm(a, "taip", "taip");
    checkOneTerm(a, "ar", "ar");
    checkOneTerm(a, "dar", "dar");
    checkOneTerm(a, "jau", "jau");
    checkOneTerm(a, "savo", "sav");
    checkOneTerm(a, "apie", "ap");
    checkOneTerm(a, "kai", "kai");
    checkOneTerm(a, "a??", "a??");
    checkOneTerm(a, "per", "per");
    checkOneTerm(a, "nuo", "nuo");
    checkOneTerm(a, "po", "po");
    checkOneTerm(a, "jis", "jis");
    checkOneTerm(a, "kas", "kas");
    checkOneTerm(a, "d", "d");
    checkOneTerm(a, "labai", "lab");
    checkOneTerm(a, "man", "man");
    checkOneTerm(a, "d??l", "d??l");
    checkOneTerm(a, "ta??iau", "tat");
    checkOneTerm(a, "nes", "nes");
    checkOneTerm(a, "u??", "u??");
    checkOneTerm(a, "to", "to");
    checkOneTerm(a, "jo", "jo");
    checkOneTerm(a, "iki", "ik");
    checkOneTerm(a, "k??", "k??");
    checkOneTerm(a, "mano", "man");
    checkOneTerm(a, "met??", "met");
    checkOneTerm(a, "nors", "nor");
    checkOneTerm(a, "jei", "jei");
    checkOneTerm(a, "bus", "bus");
    checkOneTerm(a, "j??", "j??");
    checkOneTerm(a, "??ia", "??ia");
    checkOneTerm(a, "dabar", "dabar");
    checkOneTerm(a, "Lietuvos", "Lietuv");
    checkOneTerm(a, "net", "net");
    checkOneTerm(a, "nei", "nei");
    checkOneTerm(a, "gali", "gal");
    checkOneTerm(a, "daug", "daug");
    checkOneTerm(a, "prie", "prie");
    checkOneTerm(a, "ji", "ji");
    checkOneTerm(a, "jos", "jos");
    checkOneTerm(a, "pat", "pat");
    checkOneTerm(a, "jie", "jie");
    checkOneTerm(a, "kur", "kur");
    checkOneTerm(a, "gal", "gal");
    checkOneTerm(a, "ant", "ant");
    checkOneTerm(a, "tiek", "tiek");
    checkOneTerm(a, "be", "be");
    checkOneTerm(a, "b??ti", "b??t");
    checkOneTerm(a, "bei", "bei");
    checkOneTerm(a, "daugiau", "daug");
    checkOneTerm(a, "turi", "tur");
    checkOneTerm(a, "prie??", "prie??");
    checkOneTerm(a, "vis", "vis");
    checkOneTerm(a, "b??t??", "b??t");
    checkOneTerm(a, "jog", "jog");
    checkOneTerm(a, "reikia", "reik");
    checkOneTerm(a, "m??s??", "m??s");
    checkOneTerm(a, "metu", "met");
    checkOneTerm(a, "galima", "galim");
    checkOneTerm(a, "n??ra", "n??r");
    checkOneTerm(a, "arba", "arb");
    checkOneTerm(a, "mes", "mes");
    checkOneTerm(a, "kurie", "kur");
    checkOneTerm(a, "tikrai", "tikr");
    checkOneTerm(a, "tod??l", "tod");
    checkOneTerm(a, "ten", "ten");
    checkOneTerm(a, "??iandien", "??iandien");
    checkOneTerm(a, "vienas", "vien");
    checkOneTerm(a, "visi", "vis");
    checkOneTerm(a, "kuris", "kur");
    checkOneTerm(a, "tada", "tad");
    checkOneTerm(a, "kiek", "kiek");
    checkOneTerm(a, "tuo", "tuo");
    checkOneTerm(a, "gerai", "ger");
    checkOneTerm(a, "nieko", "niek");
    checkOneTerm(a, "j??", "j??");
    checkOneTerm(a, "kol", "kol");
    checkOneTerm(a, "viskas", "visk");
    checkOneTerm(a, "mane", "man");
    checkOneTerm(a, "kart??", "kart");
    checkOneTerm(a, "m", "m");
    checkOneTerm(a, "tas", "tas");
    checkOneTerm(a, "sak??", "sak");
    checkOneTerm(a, "??moni??", "??mon");
    checkOneTerm(a, "tu", "tu");
    checkOneTerm(a, "dien??", "dien");
    checkOneTerm(a, "??mon??s", "??mon");
    checkOneTerm(a, "metais", "met");
    checkOneTerm(a, "vien??", "vien");
    checkOneTerm(a, "v??l", "v??l");
    checkOneTerm(a, "na", "na");
    checkOneTerm(a, "t??", "t??");
    checkOneTerm(a, "tiesiog", "tiesiog");
    checkOneTerm(a, "toks", "tok");
    checkOneTerm(a, "pats", "pat");
    checkOneTerm(a, "ko", "ko");
    checkOneTerm(a, "Lietuvoje", "Lietuv");
    checkOneTerm(a, "pagal", "pagal");
    checkOneTerm(a, "jeigu", "jeig");
    checkOneTerm(a, "visai", "vis");
    checkOneTerm(a, "viena", "vien");
    checkOneTerm(a, "????", "????");
    checkOneTerm(a, "metus", "met");
    checkOneTerm(a, "jam", "jam");
    checkOneTerm(a, "kod??l", "kod");
    checkOneTerm(a, "lit??", "lit");
    checkOneTerm(a, "j??", "j??");
    checkOneTerm(a, "kuri", "kur");
    checkOneTerm(a, "darbo", "darb");
    checkOneTerm(a, "tarp", "tarp");
    checkOneTerm(a, "juk", "juk");
    checkOneTerm(a, "laiko", "laik");
    checkOneTerm(a, "juos", "juos");
    checkOneTerm(a, "vis??", "vis");
    checkOneTerm(a, "kurios", "kur");
    checkOneTerm(a, "tam", "tam");
    checkOneTerm(a, "pas", "pas");
    checkOneTerm(a, "visk??", "visk");
    checkOneTerm(a, "Europos", "Eur");
    checkOneTerm(a, "atrodo", "atrod");
    checkOneTerm(a, "tad", "tad");
    checkOneTerm(a, "bent", "bent");
    checkOneTerm(a, "kit??", "kit");
    checkOneTerm(a, "??is", "??is");
    checkOneTerm(a, "Vilniaus", "Viln");
    checkOneTerm(a, "beveik", "bevei");
    checkOneTerm(a, "proc", "proc");
    checkOneTerm(a, "tokia", "tok");
    checkOneTerm(a, "??iuo", "??iuo");
    checkOneTerm(a, "du", "du");
    checkOneTerm(a, "kartu", "kart");
    checkOneTerm(a, "visada", "visad");
    checkOneTerm(a, "kuo", "kuo");
  }
}
