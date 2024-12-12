package org.caotin
package Days

object Day2 extends Solution[Int, Int]:
  def getDifference(sequence: Seq[Int]): Seq[Int] =
    sequence.sliding(2).map(pair => pair.tail.head - pair.head).toSeq

  def isStrictlyMonotonic(sequence: Seq[Int]): Boolean =
    val diffSeq = getDifference(sequence)
    diffSeq.forall(n => n > 0) || diffSeq.forall(n => n < 0)

  // only valid if strictly monotonic, and rate is at most 3
  def isValid(sequence: Seq[Int]): Boolean =
    isStrictlyMonotonic(sequence) && getDifference(sequence).forall(Math.abs(_) <= 3)

  // can this invalid sequence be valid if for one removal
  def isPartiallyValid(invalidSeq: Seq[Int]): Boolean =
    invalidSeq.zipWithIndex.map((x, idx) =>
      isValid(invalidSeq.take(idx) ++ invalidSeq.drop(idx + 1)) // check for validity by removing one element each time
    ).find(p => p).getOrElse(false) // valid if any subsequence is valid

  override def solve1(inputFile: Iterator[String]): Int =
    val sequences = inputFile.map(
      str => str.split(" ").map(x => x.toInt).toSeq // conversion to integer sequence
    ).toVector

    // Part 1 & 2
    val (validSequences, invalids) = sequences.partition(isValid)
    val (partials, fullInvalids) = invalids.partition(isPartiallyValid)
    Utils.collectionToFile(fullInvalids)
    validSequences.length // part 1
    
    // validSequences.length + partials.length // part 2
    
  // this won't ever be implemented. 
  override def solve2(inputFile: Iterator[String]): Int = ???
