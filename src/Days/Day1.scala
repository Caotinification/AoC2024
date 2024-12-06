package org.caotin
package Days

// 2430334
object Day1 extends Solution[Int]:
  override def solve(input: Iterator[String]): Int = {
    val pairs = input.map(str => str.split(" {3}")).toVector
    val leftList = pairs.map(array => array(0).toInt).sorted
    val rightList = pairs.map(array => array(1).toInt).sorted
    val distances = leftList.zip(rightList).map((l,r) => Math.abs(l - r))
    distances.sum
  }
