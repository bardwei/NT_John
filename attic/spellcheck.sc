/*Data to Analyze*/
import scala.io.Source
import edu.holycross.shot.cite

val filepath:String = "/vagrant/NT_John/NT_RVR_1858.txt"
val myBook:Vector[String] = Source.fromFile(filepath).getLines.toVector.filter(_.size > 0)

val wordVec:Vector[String] = {
  val bigString:String = myBook.mkString(" ")
  val noPunc:String = bigString.replaceAll("""[\[\]¿¡,.?;:!"]""","").replaceAll(" +", " ")
  val tokenized:Vector[String] = noPunc.split(" ").toVector.filter( _.size > 0)
  tokenized
}

/*Word Histogram/Unique Words*/

val wordMap:Map[String,Vector[String]] = wordVec.groupBy(w => w)
val quantMap:Map[String,Int] = wordMap.map(m => (m._1, m._2.size))
val mapVec:Vector[(String,Int)] = quantMap.toVector
val wordHisto = mapVec.sortBy(_._2).reverse
val uniqueWords:Vector[String] = wordHisto.map(_._1)


/*Spell Checker*/
val dictpath:String = "/vagrant/NT_John/words.txt"
val lexEntries:Vector[String] = Source.fromFile(dictpath).getLines.toVector.filter(_.size > 0)

val badWords:Vector[String] = uniqueWords.filter( w => {
  val lcOkay:Boolean = lexEntries.contains(w.toLowerCase)
  val regOkay:Boolean = lexEntries.contains(w)
  val okay:Boolean = (lcOkay | regOkay )
  // val specialOkay:Boolean = ianLexEntries.contains(w)
  okay != true
})

for (w <- badWords) {
  println(w)
}

val badWordPercent = badWords.size.toDouble / uniqueWords.size.toDouble * 100

println(s"\n\n-------\n ${badWordPercent} percent of words in your document are mispelled.\n-------\n")
