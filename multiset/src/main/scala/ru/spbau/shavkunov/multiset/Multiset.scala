package ru.spbau.shavkunov.multiset

import scala.collection.{GenTraversableOnce, mutable}

class Multiset[T](elements: T*) {
  private val hashMap: mutable.Map[T, Int] = new mutable.HashMap[T, Int]()

  elements.foreach(element => add(element))

  def size = hashMap.size

  def add(element: T, count: Int = 1) =
    hashMap.update(element, this.count(element) + count)

  def asSeq(): Seq[T] =
    hashMap.keys.toList.flatMap(element => List.fill(hashMap(element))(element))

  def filter(pred: T => Boolean): Multiset[T] =
    new Multiset(asSeq().filter(pred): _*)

  def withFilter(pred: (T) => Boolean): Multiset[T] = filter(pred)

  def find(element: T): Option[T] = {
    if (hashMap.contains(element)) {
      return Some(element)
    } else {
      return None
    }
  }

  def contains(element: T): Boolean = find(element).isDefined

  def map[B](fun: T => B): Multiset[B] =
    new Multiset(asSeq().map(fun): _*)

  def flatMap[B](fun: T => GenTraversableOnce[B]): Multiset[B] =
    new Multiset(asSeq().flatMap(fun): _*)

  def count(element: T): Int = hashMap.getOrElse(element, 0)

  def apply(element: T): Int = count(element)

  def &(anotherMultiset: Multiset[T]): Multiset[T] = {
    val list = hashMap.keys.toList.filter(element => anotherMultiset.contains(element)).flatMap(element => {
      val firstCount: Int = count(element)
      val secondCount: Int = anotherMultiset.count(element)
      val intersection = Math.min(firstCount, secondCount)

      List.fill(intersection)(element)
    })

    return new Multiset(list: _*)
  }

  def |(anotherMultiset: Multiset[T]): Multiset[T] = {
    val result = new Multiset[T](asSeq(): _*)

    for ((element, count) <- anotherMultiset.hashMap) {
      result.add(element, count)
    }

    return result
  }

  override def equals(that: scala.Any): Boolean = {
    that match {
      case that: Multiset[T] => hashMap == that.hashMap
      case _ => false
    }
  }
}

object Multiset {
  def unapplySeq[T](multiset: Multiset[T]): Option[Seq[T]] = Some(multiset.asSeq())

  def *[T](multiset: Multiset[T]): Option[Seq[T]] = unapplySeq(multiset)

  def apply[T](elements: T*): Multiset[T] = new Multiset[T](elements: _*)
}