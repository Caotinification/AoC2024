package org.caotin

import Days._

@main def main(): Unit = {
  val source = scala.io.Source.fromFile("inputs/day3.txt")
  val lines = source.getLines()
  println(Day3.solve(lines))
  source.close()
}