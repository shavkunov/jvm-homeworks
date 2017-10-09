package ru.spbau.shavkunov.multiset

import scala.collection.{GenTraversableOnce, mutable}

/**
  * Mutable Multiset implementation
  * @param elements multiset of this sequence will be creator
  * @tparam T type of the elements
  */
sealed class Multiset[T](elements: T*) {
  /**
    * Storing elemens with element and it's amount.
    */
  private val hashMap: mutable.Map[T, Int] = new mutable.HashMap[T, Int]()

  elements.foreach(element => add(element))

  def size = hashMap.size

  /**
    * Adding element to multiset
    * @param element element
    * @param count default is 1, if need to add same element several times then specify the count.
    */
  def add(element: T, count: Int = 1) =
    hashMap.update(element, this.count(element) + count)

  /**
    * Sequence representation of multiset
    * @return sequence
    */
  def asSeq(): Seq[T] =
    hashMap.keys.toList.flatMap(element => List.fill(hashMap(element))(element))

  /**
    * filtering elements
    * @param pred predicate using for filter
    * @return new multiset with appropriate elements
    */
  def filter(pred: T => Boolean): Multiset[T] =
    new Multiset(asSeq().filter(pred): _*)

  def withFilter(pred: (T) => Boolean): Multiset[T] = filter(pred)

  /**
    * Returns Some(element) if multiset contains element and otherwise it's None
    * @param element search for this element
    * @return specified option
    */
  def find(element: T): Option[T] = {
    if (hashMap.contains(element)) {
      return Some(element)
    } else {
      return None
    }
  }

  /**
    * Same as find, but with Boolean return type
    * @param element search for this element
    * @return true, if element in multiset and false otherwise
    */
  def contains(element: T): Boolean = find(element).isDefined

  /**
    * Maps function to all elements of the Multiset and returns new resulting multiset
    */
  def map[B](fun: T => B): Multiset[B] =
    new Multiset(asSeq().map(fun): _*)

  /**
    * Maps function to all elements of the Multiset and returns new resulting multiset
    */
  def flatMap[B](fun: T => GenTraversableOnce[B]): Multiset[B] =
    new Multiset(asSeq().flatMap(fun): _*)

  /**
    * Returns amount of element in multiset
    */
  def count(element: T): Int = hashMap.getOrElse(element, 0)

  def apply(element: T): Int = count(element)

  /**
    * Returns new multiset which representing intersection with another multiset
    */
  def &(anotherMultiset: Multiset[T]): Multiset[T] = {
    val list = hashMap.keys.toList.filter(element => anotherMultiset.contains(element)).flatMap(element => {
      val firstCount: Int = count(element)
      val secondCount: Int = anotherMultiset.count(element)
      val intersection = Math.min(firstCount, secondCount)

      List.fill(intersection)(element)
    })

    return new Multiset(list: _*)
  }

  /**
    * Returns new multiset which representing union with another multiset
    */
  def |(anotherMultiset: Multiset[T]): Multiset[T] = {
    val result = new Multiset[T](asSeq(): _*)

    for ((element, count) <- anotherMultiset.hashMap) {
      result.add(element, count)
    }

    return result
  }

  /**
    * Multiset comparison
    */
  override def equals(anotherMultiset: scala.Any): Boolean = {
    anotherMultiset match {
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