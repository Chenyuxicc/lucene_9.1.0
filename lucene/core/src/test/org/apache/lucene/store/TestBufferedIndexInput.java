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
package org.apache.lucene.store;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;
import org.apache.lucene.tests.util.LuceneTestCase;
import org.apache.lucene.util.ArrayUtil;

public class TestBufferedIndexInput extends LuceneTestCase {

  private static final long TEST_FILE_LENGTH = 100 * 1024;

  // Call readByte() repeatedly, past the buffer boundary, and see that it
  // is working as expected.
  // Our input comes from a dynamically generated/ "file" - see
  // MyBufferedIndexInput below.
  public void testReadByte() throws Exception {
    MyBufferedIndexInput input = new MyBufferedIndexInput();
    for (int i = 0; i < BufferedIndexInput.BUFFER_SIZE * 10; i++) {
      assertEquals(input.readByte(), byten(i));
    }
  }

  // Call readBytes() repeatedly, with various chunk sizes (from 1 byte to
  // larger than the buffer size), and see that it returns the bytes we expect.
  // Our input comes from a dynamically generated "file" -
  // see MyBufferedIndexInput below.
  public void testReadBytes() throws Exception {
    MyBufferedIndexInput input = new MyBufferedIndexInput();
    runReadBytes(input, BufferedIndexInput.BUFFER_SIZE, random());
  }

  private void runReadBytes(IndexInput input, int bufferSize, Random r) throws IOException {

    int pos = 0;
    // gradually increasing size:
    for (int size = 1; size < bufferSize * 10; size = size + size / 200 + 1) {
      checkReadBytes(input, size, pos);
      pos += size;
      if (pos >= TEST_FILE_LENGTH) {
        // wrap
        pos = 0;
        input.seek(0L);
      }
    }
    // wildly fluctuating size:
    for (long i = 0; i < 100; i++) {
      final int size = r.nextInt(10000);
      checkReadBytes(input, 1 + size, pos);
      pos += 1 + size;
      if (pos >= TEST_FILE_LENGTH) {
        // wrap
        pos = 0;
        input.seek(0L);
      }
    }
    // constant small size (7 bytes):
    for (int i = 0; i < bufferSize; i++) {
      checkReadBytes(input, 7, pos);
      pos += 7;
      if (pos >= TEST_FILE_LENGTH) {
        // wrap
        pos = 0;
        input.seek(0L);
      }
    }
  }

  private byte[] buffer = new byte[10];

  private void checkReadBytes(IndexInput input, int size, int pos) throws IOException {
    // Just to see that "offset" is treated properly in readBytes(), we
    // add an arbitrary offset at the beginning of the array
    int offset = size % 10; // arbitrary
    buffer = ArrayUtil.grow(buffer, offset + size);
    assertEquals(pos, input.getFilePointer());
    long left = TEST_FILE_LENGTH - input.getFilePointer();
    if (left <= 0) {
      return;
    } else if (left < size) {
      size = (int) left;
    }
    input.readBytes(buffer, offset, size);
    assertEquals(pos + size, input.getFilePointer());
    for (int i = 0; i < size; i++) {
      assertEquals("pos=" + i + " filepos=" + (pos + i), byten(pos + i), buffer[offset + i]);
    }
  }

  // This tests that attempts to readBytes() past an EOF will fail, while
  // reads up to the EOF will succeed. The EOF is determined by the
  // BufferedIndexInput's arbitrary length() value.
  public void testEOF() throws Exception {
    MyBufferedIndexInput input = new MyBufferedIndexInput(1024);
    // see that we can read all the bytes at one go:
    checkReadBytes(input, (int) input.length(), 0);
    // go back and see that we can't read more than that, for small and
    // large overflows:
    int pos = (int) input.length() - 10;
    input.seek(pos);
    checkReadBytes(input, 10, pos);
    input.seek(pos);
    // block read past end of file
    expectThrows(
        IOException.class,
        () -> {
          checkReadBytes(input, 11, pos);
        });

    input.seek(pos);

    // block read past end of file
    expectThrows(
        IOException.class,
        () -> {
          checkReadBytes(input, 50, pos);
        });

    input.seek(pos);

    // block read past end of file
    expectThrows(
        IOException.class,
        () -> {
          checkReadBytes(input, 100000, pos);
        });
  }

  // byten emulates a file - byten(n) returns the n'th byte in that file.
  // MyBufferedIndexInput reads this "file".
  private static byte byten(long n) {
    return (byte) (n * n % 256);
  }

  private static class MyBufferedIndexInput extends BufferedIndexInput {
    private long pos;
    private long len;

    public MyBufferedIndexInput(long len) {
      super("MyBufferedIndexInput(len=" + len + ")", BufferedIndexInput.BUFFER_SIZE);
      this.len = len;
      this.pos = 0;
    }

    public MyBufferedIndexInput() {
      // an infinite file
      this(Long.MAX_VALUE);
    }

    @Override
    protected void readInternal(ByteBuffer b) throws IOException {
      while (b.hasRemaining()) {
        b.put(byten(pos++));
      }
    }

    @Override
    protected void seekInternal(long pos) throws IOException {
      this.pos = pos;
    }

    @Override
    public void close() throws IOException {}

    @Override
    public long length() {
      return len;
    }

    @Override
    public IndexInput slice(String sliceDescription, long offset, long length) throws IOException {
      throw new UnsupportedOperationException();
    }
  }
}
