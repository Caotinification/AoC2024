package org.caotin

import Days.Day1

@main def main(): Unit = {
  val source = scala.io.Source.fromFile("Inputs/day1.txt")
  val lines = source.getLines()
  println(Day1.solve(lines))
  source.close()
}