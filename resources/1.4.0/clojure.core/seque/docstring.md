  Creates a queued seq on another (presumably lazy) seq s. The queued
  seq will produce a concrete seq in the background, and can get up to
  n items ahead of the consumer. n-or-q can be an integer n buffer
  size, or an instance of java.util.concurrent BlockingQueue. Note
  that reading from a seque can block if the reader gets ahead of the
  producer.