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

/*word histogram */
val wordMap:Map[String,Vector[String]] = wordVec.groupBy(w => w)
val quantMap:Map[String,Int] = wordMap.map(m => (m._1, m._2.size))
val mapVec:Vector[(String,Int)] = quantMap.toVector
val wordHisto = mapVec.sortBy(_._2).reverse
val uniqueWords:Vector[String] = wordHisto.map(_._1)

println(s"\n\n-------\nThere are ${wordHisto.size} unique words.\n------\n")


/*how to compare percentage
println()

val uniquePrecent = wordHisto.filter(_._2 == 1).size.toDouble / wordVec.size.toDouble * 100


println(s"\n\n-------\nThere are ${uniquePrecent} percentage of unique words in your document.\n-------\n") */

/*Vector: [String, Int] -> Vector:String
  wordHisto.map(_._1)*/


/* Spell Check!
val dictpath:String = "/vagrant/NT_John/words.txt"
val lexEntries:Vector[String] = Source.fromFile(dictpath).getLines.toVector.filter(_.size > 0)

val badWords:Vector[String] = uniqueWords.filter( w => {
  lexEntries.contains(w.toLowerCase) == false &
  lexEntries.contains(w)
})

for (w <- badWords) {
  println(w)
} */

val newVec:Vector[String] = wordVec
val mySlided:Vector[Vector[String]] = newVec.sliding(3,1).toVector
val grouped:Map[Vector[String],Vector[Vector[String]]] = mySlided.groupBy(w => w)
val madeIntoAVectorBecauseMapsAreHard:Vector[(Vector[String], Vector[Vector[String]])] = grouped.toVector
// rename this to make it shorter ;-)
val madeVec = madeIntoAVectorBecauseMapsAreHard

val ng:Vector[(String, Int)] = madeVec.map(mv => {
    val s:String = mv._1.mkString(" ")
    val i:Int = mv._2.size
    (s,i)
}).sortBy(_._2)

// See the results
for (n <- ng) {
  println(s""" "${n._1}" occurs ${n._2}""")
}
