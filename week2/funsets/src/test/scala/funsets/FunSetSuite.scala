package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val u = union(union(s1, s2), s3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet contains") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton s1")
      assert(contains(s2, 2), "Singleton s2")
      assert(contains(s3, 3), "Singleton s3")
      assert(!contains(s1, 2), "Singleton s1 != 2")
      assert(!contains(s1, 3), "Singleton s1 != 3")

    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")

      assert(contains(u, 1), "Union 1")
      assert(contains(u, 2), "Union 2")
      assert(contains(u, 3), "Union 3")
      assert(!contains(u, 4), "Union 4")
    }
  }

  test("intersect contains only common elements of each set") {
    new TestSets {
      val s = intersect(s1, u)
      assert(contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "! intersect 2")
      assert(!contains(s, 3), "! intersect 3")

      val s_ = intersect(u, s1)
      assert(contains(s_, 1), "intersect 1")
      assert(!contains(s_, 2), "! intersect 2")
      assert(!contains(s_, 3), "! intersect 3")
    }
  }

  test("diff contains only common elements of each set") {
    new TestSets {
      val s = diff(u, s1)
      assert(!contains(s, 1), "!intersect 1")
      assert(contains(s, 2), "intersect 2")
      assert(contains(s, 3), "intersect 3")

      val s_ = diff(s1, u)
      assert(!contains(s_, 1), "!intersect 1")
      assert(!contains(s_, 2), "!intersect 2")
      assert(!contains(s_, 3), "!intersect 3")
    }
  }

  test("filter ") {
    new TestSets {
      val s = filter(u, x => x == 1)
      assert(contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "! intersect 2")
      assert(!contains(s, 3), "! intersect 3")
    }
  }

  test("forall ") {
    new TestSets {
      val s = union(singletonSet(99),union(singletonSet(-11), singletonSet(5)))
      def odd(x:Int) = {
        x % 2 != 0
      }

      val s_ = union(s, singletonSet(80))
      assert(forall(s, odd), "forall: odd")
      assert(!forall(s_, odd), "forall: ! odd")
    }
  }

  test("exists ") {
    new TestSets {
      val s = union(singletonSet(99),union(singletonSet(-11), singletonSet(5)))
      def even(x:Int) = {
        x % 2 == 0
      }

      val s_ = union(s, singletonSet(80))
      assert(!exists(s, even), "! exists")
      assert(exists(s_, even), "exists")
    }
  }

  test("map ") {
    new TestSets {
      val s = map(u, x => x * 2)

      assert(contains(s, 2), "map: 2 ")
      assert(contains(s, 4), "map: 4 ")
      assert(contains(s, 6), "map: 6 ")
      assert(!contains(s, 8), "! map: 8")
    }
  }


}
