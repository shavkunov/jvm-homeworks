package ru.spbau.shavkunov.multiset

import scala.collection.{GenTraversableOnce, mutable}

class Multiset[T](elements: T*) {
  private val hashMap: mutable.Map[T, Int] = new mutable.HashMap[T, Int]()

  elements.foreach(element => add(element))

  def size = hashMap.size

  def add(element: T) = {
    val amount = hashMap.getOrElse(element, 0)

    if (amount != 0) {
      hashMap -= element
    }

    hashMap += ((element, amount + 1))
  }

  def delete(element: T) = {
    if (hashMap.contains(element)) {
      val amount = hashMap(element)

      hashMap -= element

      if (amount != 1) {
        hashMap += ((element, amount - 1))
      }
    }
  }

  def asSeq(): Seq[T] = {
    return hashMap.keys.flatMap(element => List.fill(hashMap(element))(element)).toList
  }

  def filter(pred: T => Boolean): Multiset[T] = {
    return new Multiset(asSeq().filter(pred): _*)
  }

  def find(element: T): Option[T] = {
    if (hashMap.contains(element)) {
      return Some(element)
    } else {
      return None
    }
  }

  def contains(element: T): Boolean = {
    find(element).isDefined
  }

  def map[B](fun: T => B): Multiset[B] = {
    return new Multiset(asSeq().map(fun): _*)
  }

  def flatmap[B](fun: T => GenTraversableOnce[B]): Multiset[B] = {
    return new Multiset(asSeq().flatMap(fun): _*)
  }

  def count(element: T) = hashMap(element)

  def apply(element: T) = count(element)

  private def setOperation(anotherMultiset: Multiset[T], operation: (Int, Int) => Int): Multiset[T] = {
    val list = hashMap.keys.filter(element => anotherMultiset.contains(element)).flatMap(element => {
      val firstCount: Int = count(element)
      val secondCount: Int = anotherMultiset.count(element)
      val intersection = operation(firstCount, secondCount)

      List.fill(intersection)(element)
    }).toList

    return new Multiset(list: _*)
  }

  def &(anotherMultiset: Multiset[T]) = setOperation(anotherMultiset, Math.min)

  def |(anotherMultiset: Multiset[T]) = setOperation(anotherMultiset, Math.max)
}

object MultiSet {
  def unapplySeq[T](multiset: Multiset[T]): Option[Seq[T]] = Some(multiset.asSeq())

  def *[T](multiset: Multiset[T]): Option[Seq[T]] = unapplySeq(multiset)

  def apply[T](elements: T*): Multiset[T] = new Multiset[T](elements: _*)
}