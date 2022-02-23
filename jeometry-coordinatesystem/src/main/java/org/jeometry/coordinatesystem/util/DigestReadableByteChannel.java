package org.jeometry.coordinatesystem.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.security.MessageDigest;

public class DigestReadableByteChannel extends AbstractInterruptibleChannel
  implements ReadableByteChannel, MessageDigestProxy {

  private static final int BUF_SIZE = 1024;

  private MessageDigest digest;

  private ReadableByteChannel channel;

  private byte[] buf = new byte[BUF_SIZE];

  public DigestReadableByteChannel(ReadableByteChannel channel, MessageDigest digest) {
    this.channel = channel;
    this.digest = digest;
  }

  @Override
  public MessageDigest getMessageDigest() {
    return digest;
  }

  @Override
  protected void implCloseChannel() throws IOException {
  }

  @Override
  public int read(ByteBuffer dst) throws IOException {
    int readCount = channel.read(dst);
    if (readCount > 0) {
      dst.flip();
      while (dst.hasRemaining()) {
        int remaining = dst.remaining();
        int count = Math.min(remaining, BUF_SIZE);
        dst.get(buf, 0, count);
        digest.update(buf, 0, count);
      }
    }
    return readCount;
  }
}
