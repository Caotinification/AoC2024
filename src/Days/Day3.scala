package org.caotin
package Days

import scala.annotation.tailrec
import scala.language.implicitConversions

private val PARENTHESES = "()"
private val MUL = "mul("
private val DO = "do" + PARENTHESES
private val DONT = "don't" + PARENTHESES
private val LONGEST_MUL = MUL.length + 7 + PARENTHESES.length - 1 // longest possible mul instruction, that is mul(abc,def)

object Day3 extends Solution[Int, Int]:
  private implicit def asInt(bool: Boolean): Int = if bool then 1 else 0
  private val mulOperandsPattern = """mul\((\d+),(\d+)\)""".r // Capture the operands

  override def solve1(inputFile: Iterator[String]): Int =
    val mulOperands = for operands <- inputFile.flatMap(mulOperandsPattern.findAllMatchIn(_))
      yield (operands.group(1).toInt, operands.group(2).toInt) // extract operands into pairs
    mulOperands.map((l, r) => l * r).sum

  override def solve2(inputFile: Iterator[String]): Int =
    @tailrec
    def reducer(charList: List[Char], isMul: Boolean = true, acc: Int = 0): Int =
      if charList.nonEmpty then
        charList.head match
          case 'm' =>
            if (charList.take(MUL.length) sameElements MUL) && isMul then {
              val target = ("^" + mulOperandsPattern).r.findAllIn(charList.take(LONGEST_MUL).mkString)
              val (op1, op2): (Int, Int) = if target.hasNext
                then (target.group(1).toInt, target.group(2).toInt)
                else (0,0)
              reducer(charList.tail, isMul, acc + op1 * op2 * isMul)
            }
            else
              reducer(charList.tail, isMul, acc)
          case 'd' =>
            if charList.take(DO.length) sameElements DO then {
              reducer(charList.tail, true, acc)
            } else if charList.take(DONT.length) sameElements DONT then {
              reducer(charList.tail, false, acc)
            }
            else
              reducer(charList.tail, isMul, acc)
          case _ => reducer(charList.tail, isMul, acc)
      else
        acc
    reducer(inputFile.flatten.toList)
