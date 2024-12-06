package org.caotin

trait Solution[OutputType]:
  def solve(inputFile: Iterator[String]): OutputType
