package org.caotin

object Utils:
  import java.io._
  def collectionToFile[A](collection: Iterable[A], fileName: String = "output.txt"): Unit = {
    val writer = BufferedWriter(FileWriter("outputs/" + fileName))
    collection.foreach(item => writer.write(item.toString + "\n"))
    writer.close()
  }
