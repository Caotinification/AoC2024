package org.caotin

import Days._

@main def main(): Unit = {
  val source = scala.io.Source.fromFile("inputs/day4.txt")
  val lines = source.getLines()
  println(Day4.solve1(lines))
  source.close()
}