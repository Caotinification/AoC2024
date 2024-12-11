package org.caotin

import Days._

@main def main(): Unit = {
  val source = scala.io.Source.fromFile("inputs/day2.txt")
  val lines = source.getLines()
  println(Day2.solve(lines))
  source.close()
}