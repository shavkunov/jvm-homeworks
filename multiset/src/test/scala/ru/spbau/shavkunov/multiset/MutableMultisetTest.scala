package ru.spbau.shavkunov.multiset

import org.scalatest.FunSuite

class MutableMultisetTest extends FunSuite {

  test("add element") {
    val multiSet = new Multiset[Int]()

    assert(multiSet.count(5) == 0)
    assert(multiSet.size == 0)
    multiSet.add(5)
    assert(multiSet.count(5) == 1)
    assert(multiSet.size == 1)
  }

  test("add same elements") {
    val multiSet = new Multiset[Int](1, 2, 3)

    assert(multiSet.count(5) == 0)
    multiSet.add(5)
    assert(multiSet.count(5) == 1)
    multiSet.add(5)
    assert(multiSet.count(5) == 2)
  }

  test("as seq test") {
    val multiSet = new Multiset[Int](1, 2, 2, 2, 3, 3)
    assert(multiSet.asSeq().sorted == Seq(1, 2, 2, 2, 3, 3))
  }

  test("filter test") {
    val multiSet = new Multiset[Int](1, 2, 2, 2, 3, 3)
    val odd = multiSet.filter(elem => elem % 2 != 0)
    val even = multiSet.filter(elem => elem % 2 == 0)

    val correctOdd = new Multiset[Int](1, 3, 3)
    val correctEven = new Multiset[Int](2, 2, 2)
    assert(odd == correctOdd)
    assert(even == correctEven)
  }

  test("find test") {
    val multiSet = new Multiset[Int](1, 2, 2, 2, 3, 3)
    assert(multiSet.find(1).contains(1))
    assert(multiSet.find(3).contains(3))
    assert(multiSet.find(5).isEmpty)
  }

  test("map test") {
    val multiSet = new Multiset[Int](1, 2, 3)
    assert(multiSet.map(x => x*2) == new Multiset[Int](2, 4, 6))
    assert(multiSet.map(_ => "Test") == new Multiset[String]("Test", "Test", "Test"))
  }

  test("flatmap test") {
    val multiSet = new Multiset[Int](1, 2, 3)
    val flatted = multiSet.flatMap(x => List.fill(3)(x))
    val correct = List(1, 1, 1, 2, 2, 2, 3, 3, 3)

    assert(correct == flatted.asSeq().sorted)
  }

  test("intersection test") {
    val first = new Multiset[Int](1, 2, 2, 3, 3, 3, 4)
    val second = new Multiset[Int](2, 3, 3, 3, 3, 3)
    val intersect1 = first & second
    val intersect2 = second & first

    val correct = new Multiset[Int](2, 3, 3, 3)
    assert(intersect1 == correct)
    assert(intersect2 == correct)
  }

  test("union test") {
    val first = new Multiset[Int](1, 2, 3)
    val second = new Multiset[Int](1, 2, 2, 3, 3, 3, 5)
    val union1 = first | second
    val union2 = second | first

    val correct = new Multiset[Int](1, 1, 2, 2, 2, 3, 3, 3, 3, 5)
    assert(union1 == correct)
    assert(union2 == correct)
  }

  test("pattern matching") {
    val multiSet = Multiset(1, 2, 3)
    multiSet match {
      case Multiset(a, b, c) => assert(Multiset(a, b, c) == multiSet)
      case _ => fail("not matched")
    }
  }

  test("for comprehension map") {
    val multiSet = Multiset(1, 2, 3)
    val result =
      for {
        x <- multiSet
      } yield x * 2

    assert(result == Multiset(2, 4, 6))
  }

  test("for comprehension flatmap") {
    val first = Multiset[Int](1, 2, 3)
    val second = List[Int](3, 4, 5)
    val result: Multiset[Int] =
      for {
        element1: Int <- first
        element2: Int <- second
      } yield element1 + element2

    val expected = new Multiset[Int](4, 5, 6, 5, 6, 7, 6, 7, 8)
    assert(result == expected)
  }

}