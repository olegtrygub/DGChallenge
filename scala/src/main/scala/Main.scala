import scala.io.Source

object Main {
  type Fragment = (String, Int)

  def getSequence(fragments: Set[Fragment]):String = fragments.find(_._2 != -1) match {
    case Some((leftFragment, leftMergePoint)) => {

      val mergePart = leftFragment.takeRight(leftMergePoint)

      val searchFragments = fragments - ((leftFragment, leftMergePoint))
      val sequences = for {
        (rightFragment, rightMergePoint) <- searchFragments
        ind = rightFragment.indexOf(mergePart)
        if ind != -1
        if leftFragment.substring(leftFragment.length - leftMergePoint - ind, leftFragment.length - leftMergePoint) == rightFragment.substring(0, ind)
        mergedFragment = (leftFragment + rightFragment.substring(ind + leftMergePoint), rightMergePoint)
        sequence = getSequence(searchFragments - ((rightFragment, rightMergePoint)) + mergedFragment)
        if !sequence.isEmpty
      } yield sequence

      sequences.headOption match {
        case None => getSequence(searchFragments + ((leftFragment, -1)))
        case Some(sequence) => sequence
      }
    }
    case None => fragments.find(_ => true).get._1
  }

  def loadFragmentsFromFile(path: String):Set[Fragment] = {
    val lines = Source.fromFile(path).getLines.toList
    def getFragments(acc: List[Fragment], lines: Seq[String]):List[Fragment] = lines match {
      case Nil => acc
      case head :: tail => {
        val split = tail.span(_(0) != '>')
        val newFragment = split._1.fold("")((stacc: String, st:String) => stacc + st.trim)
        getFragments((newFragment, newFragment.length / 2)::acc, split._2)
      }
    }
    getFragments(Nil, lines).toSet
  }

  def main(args: Array[String]): Unit = {
    print(getSequence(loadFragmentsFromFile(args(0))))
  }
}
