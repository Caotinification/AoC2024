package org.caotin
package Days

import scala.annotation.{tailrec, targetName}
import scala.collection.mutable.Map as MMap

private val XMAS = "XMAS"
private type Position = (Int, Int)

extension (p: Position)
  @targetName("times")
  def *(s: Int): Position = (p._1 * s, p._2 * s)
  @targetName("plus")
  def +(q: Position): Position = (p._1 + q._1, p._2 + q._2)
  @targetName("minus")
  def -(q: Position): Position = p + (q * -1)

object Day4 extends Solution[Int, Int]:
  private val treadedPaths: MMap[IndexedSeq[Position], Boolean] = MMap.empty
  private val cardinals = IndexedSeq(N, E, S, W, NE, NW, SE, SW)
  private var dimensions = (0, 0)

  override def solve1(inputFile: Iterator[String]): Int =
    val xmasMatrix = inputFile.map(_.toVector).toVector
    dimensions = (xmasMatrix.length, xmasMatrix(0).length) // bad design lol
    val xmasMinefield = xmasMatrix.zipWithIndex.flatMap(
      (row, ridx) =>
        row.zipWithIndex.flatMap((char, cidx) => findXmas((ridx, cidx), xmasMatrix))
    )
    Utils.collectionToFile(treadedPaths.keys)
    xmasMinefield.count(p => p)

  override def solve2(inputFile: Iterator[String]): Int = ???

  private def NE(pos: Position) = N(E(pos))

  private def NW(pos: Position) = N(W(pos))

  private def N(pos: Position) = getValidPos(pos + (0, 1))

  private def SE(pos: Position) = S(E(pos))

  private def E(pos: Position) = getValidPos(pos + (1, 0))

  private def SW(pos: Position) = S(W(pos))

  private def S(pos: Position) = getValidPos(pos - (0, 1))

  private def W(pos: Position) = getValidPos(pos - (1, 0))

  private def getValidPos(pos: Position): Position =
    (Math.floorMod(pos._1, dimensions._1), Math.floorMod(pos._2, dimensions._2)) // Get the remainder

  @tailrec
  private def compose(count: Int, pos: Position)(direction: Position => Position): Position =
    if count == 0 then pos else compose(count - 1, direction(pos))(direction)

  private def getValueAt[A](pos: Position, matrix: Vector[Vector[A]]): A = matrix(pos._1)(pos._2)

  private def doesPathExist(path: IndexedSeq[Position]): Boolean =
    val wasAdded = treadedPaths.getOrElse(path, false) || treadedPaths.getOrElse(path.reverse, false)
    treadedPaths += (if wasAdded then IndexedSeq((-1, -1)) else path) -> true
    wasAdded

  private def generatePath(pos: Position)(direction: Position => Position): IndexedSeq[Position] =
    (0 until XMAS.length).map(i => compose(i, pos)(direction))

  // see if XMAS is present at all 8 cardinal directions
  private def findXmas(pos: Position, matrix: Vector[Vector[Char]]): IndexedSeq[Boolean] =
    cardinals.map(direction => {
      val newPath = generatePath(pos)(direction)
      val xmas = newPath.map(pos => getValueAt(pos, matrix))
      xmas.mkString == XMAS && !doesPathExist(newPath) // path must spell xmas, and not already exist
    })
