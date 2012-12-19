import org.opencv.highgui.Highgui
import org.opencv.features2d.DescriptorExtractor
import org.opencv.features2d.Features2d
import org.opencv.core.MatOfKeyPoint
import org.opencv.core.Mat
import org.opencv.features2d.FeatureDetector
import org.opencv.features2d.DescriptorMatcher
import org.opencv.core.MatOfDMatch

object CorrespondenceMatchingDemo {
  def run() {
    def detectAndExtract(mat: Mat) = {
      val keyPoints = new MatOfKeyPoint
      // We're using the SURF detector.
      val detector = FeatureDetector.create(FeatureDetector.SURF)
      detector.detect(mat, keyPoints)

      println("There were %s KeyPoints detected".format(keyPoints.toArray.size))

      // Let's just use the best KeyPoints.   
      val sorted = keyPoints.toArray.sortBy(_.response).reverse.take(50)
      val bestKeyPoints: MatOfKeyPoint = new MatOfKeyPoint(sorted: _*)

      // We're using the SURF descriptor.
      val extractor = DescriptorExtractor.create(DescriptorExtractor.SURF)
      val descriptors = new Mat
      extractor.compute(mat, bestKeyPoints, descriptors)

      println("%s descriptors were extracted, each with dimension %s".format(descriptors.rows, descriptors.cols))

      (bestKeyPoints, descriptors)
    }

    // Load the images from the |resources| directory. 
    val leftImage = Highgui.imread(getClass.getResource("/img1.bmp").getPath)
    val rightImage = Highgui.imread(getClass.getResource("/img2.bmp").getPath)

    // Detect KeyPoints and extract descriptors.
    val (leftKeyPoints, leftDescriptors) = detectAndExtract(leftImage)
    val (rightKeyPoints, rightDescriptors) = detectAndExtract(rightImage)

    // Match the descriptors.
    val matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE)
    val dmatches = new MatOfDMatch
    matcher.`match`(leftDescriptors, rightDescriptors, dmatches)

    // Visualize the matches and save the visualization.
    val correspondenceImage = new Mat
    Features2d.drawMatches(leftImage, leftKeyPoints, rightImage, rightKeyPoints, dmatches, correspondenceImage)
    assert(Highgui.imwrite("correspondences.png", correspondenceImage))
  }
}