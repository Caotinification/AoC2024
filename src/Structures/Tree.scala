package org.caotin
package Structures

case class Tree[A](data: A, left: Tree[A] = null, right: Tree[A] = null)
  
