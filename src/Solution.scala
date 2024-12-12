package org.caotin

trait Solution[OutputType1, OutputType2]:
  def solve1(inputFile: Iterator[String]): OutputType1
  def solve2(inputFile: Iterator[String]): OutputType2