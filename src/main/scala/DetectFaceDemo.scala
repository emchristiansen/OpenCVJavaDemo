import org.opencv.core.Core
import org.opencv.core.MatOfRect
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.highgui.Highgui
import org.opencv.objdetect.CascadeClassifier

object DetectFaceDemo {
  def run() {
    // Create a face detector from the cascade file.
    val faceDetector = new CascadeClassifier(getClass.getResource("/lbpcascade_frontalface.xml").getPath)
    val image = Highgui.imread(getClass.getResource("/AverageMaleFace.jpg").getPath)

    // Detect faces in the image.
    val faceDetections = new MatOfRect
    faceDetector.detectMultiScale(image, faceDetections)

    println("Detected %s faces".format(faceDetections.toArray.size))

    // Draw a bounding box around each face.
    for (rect <- faceDetections.toArray) {
      Core.rectangle(
        image,
        new Point(rect.x, rect.y),
        new Point(rect.x + rect.width, rect.y + rect.height),
        new Scalar(0, 255, 0))
    }

    // Save the visualized detection.
    assert(Highgui.imwrite("faceDetection.png", image))
  }
}