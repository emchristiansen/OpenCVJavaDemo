object Main extends App {
  // We must load the native library before using any OpenCV functions.
  // You must load this library _exactly once_ per Java invocation.
  // If you load it more than once, you will get a java.lang.UnsatisfiedLinkError.
  System.loadLibrary("opencv_java")

  CorrespondenceMatchingDemo.run()
  DetectFaceDemo.run()
}
