package cc.kevinlee.skala.math

import java.math.MathContext

import org.scalatest.Matchers._
import org.scalatest.WordSpec

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
class BigIntsSpec extends WordSpec {

  "BigInts.sqrt" when {
    "sqrt(BigInt(-1))" should {
      "throw java.lang.IllegalArgumentException" in {
        val number: BigInt = -1
        val thrown = intercept[IllegalArgumentException] {
          BigInts.sqrt(number)
        }
        assert(thrown.getMessage contains "sqrt can handle only non-negative numbers")
      }
    }
    "sqrt(BigInt(1))" should {
      "return BigDecimal(1)" in {
        val number: BigInt = 1
        val expected: BigDecimal = 1
        val actual = BigInts.sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(BigInt(0))" should {
      "return BigDecimal(0)" in {
        val number: BigInt = 0
        val expected: BigDecimal = 0
        val actual = BigInts.sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(BigInt(2))" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
        val number: BigInt = 2
        val expected = BigDecimal("1.414213562373095")
        val actual = BigInts.sqrt(number)
        assert(actual === (expected +- 0.000000000000001))
        assert((actual * actual) === number)
      }
    }
    "sqrt(BigInt(9))" should {
      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
        val number: BigInt = 9
        val expected: BigDecimal = 3
        val actual = BigInts.sqrt(number)
        assert(actual === expected)
        assert((actual * actual) === number)
      }
    }
    "sqrt(BigInt(10))" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === BigInt(10)" in {
        val number: BigInt = 10
        val expected = BigDecimal("3.162277660168379")
        val actual = BigInts.sqrt(number)
        assert(actual === (expected +- 0.000000000000001))
        assert((actual * actual) === (BigDecimal(number) +- 0.00000000000000000000000000000001))
      }
    }
    "sqrt(BigInt(89479223372))" should {
      "return BigDecimal(299130.779713489...) and actual * actual === BigInt(89479223372)" in {
        val number: BigInt = 89479223372L

        val expected = BigDecimal("299130.779713489")
        val actual = BigInts.sqrt(number)
        assert(actual === (expected +- 0.000000001))
        assert((actual * actual) === (BigDecimal(number) +- 0.0000000000000000000001))
      }
    }
  }
  "BigInts.findSqrt" when {
    "findSqrt(BigInt(-1))" should {
      "return None" in {
        val number: BigInt = -1
        val expected = None
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigInt(1))" should {
      "return BigDecimal(1)" in {
        val number: BigInt = 1
        val expected = Option[BigDecimal](1)
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigInt(0))" should {
      "return BigDecimal(0)" in {
        val number: BigInt = 0
        val expected = Option[BigDecimal](0)
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigInt(2))" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
        val number: BigInt = 2
        val expected = Option[BigDecimal](BigInts.sqrt(number))
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === number)
      }
    }
    "findSqrt(BigInt(9))" should {
      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
        val number: BigInt = 9
        val expected = Option[BigDecimal](3)
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === number)
      }
    }
    "findSqrt(BigInt(10))" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === BigInt(10)" in {
        val number: BigInt = 10
        val expected = Option[BigDecimal](BigInts.sqrt(number))
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === (BigDecimal(number) +- 0.00000000000000000000000000000001))
      }
    }
    "findSqrt(BigInt(89479223372))" should {
      "return BigDecimal(299130.779713489...) and actual * actual === BigInt(89479223372)" in {
        val number: BigInt = 10
        val expected = Option[BigDecimal](BigInts.sqrt(number))
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === (BigDecimal(number) +- 0.0000000000000000000001))
      }
    }
  }
  "BigInts.mean" when {
    val emptyList = List.empty[BigInt]
    s"BigInts.mean($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.mean(emptyList)
        assert(actual === expected)
      }
    }
    s"BigInts.mean(0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.mean(List(0))
        assert(actual === expected)
      }
    }
    s"BigInts.mean(999)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigInts.mean(List(999))
        assert(actual === expected)
      }
    }
    val numbers = List[BigInt](1, 2, 2, 3, 3, 3, 4, 6, 10)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = BigDecimal(numbers.sum) / numbers.length
      s"return $expected" in {
        val actual = BigInts.mean(numbers)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 1, 1, 1, 1)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = 1
      s"return $expected" in {
        val actual = BigInts.mean(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = 2
      s"return $expected" in {
        val actual = BigInts.mean(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](1, 2, 2, 1)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigInts.mean(numbers4)
        assert(actual === expected)
      }
    }
  }

  "BigInts.median" when {
    val emptyList = Nil
    s"median($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.median(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigInt](0)
    s"median($numbers0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.median(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigInt](999)
    s"median($numbers1)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigInts.median(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 2)
    s"median($numbers2)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigInts.median(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"median($numbers3)" should {
      val expected: BigDecimal = 2
      s"return $expected" in {
        val actual = BigInts.median(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](1, 2, 3, 4)
    s"median($numbers4)" should {
      val expected: BigDecimal = BigDecimal(numbers4(1) + numbers4(2)) / 2
      s"return $expected" in {
        val actual = BigInts.median(numbers4)
        assert(actual === expected)
      }
    }
    val numbers4unordered = List[BigInt](2, 4, 3, 1)
    s"median($numbers4unordered)" should {
      val numbers4ordered = numbers4unordered.sorted
      val expected: BigDecimal = BigDecimal(numbers4ordered(1) + numbers4ordered(2)) / 2
      s"return $expected" in {
        val actual = BigInts.median(numbers4unordered)
        assert(actual === expected)
      }
    }
    val numbers5 = List[BigInt](1, 2, 3, 4, 5)
    s"median($numbers5)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigInts.median(numbers5)
        assert(actual === expected)
      }
    }
    val numbers5unordered = List[BigInt](2, 3, 5, 4, 1)
    s"median($numbers5unordered)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigInts.median(numbers5unordered)
        assert(actual === expected)
      }
    }
    val numbers10 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    s"median($numbers10)" should {
      val expected: BigDecimal = BigDecimal(numbers10(4) + numbers10(5)) / 2
      s"return $expected" in {
        val actual = BigInts.median(numbers10)
        assert(actual === expected)
      }
    }
    val numbers11 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    s"median($numbers11)" should {
      val expected: BigDecimal = 6
      s"return $expected" in {
        val actual = BigInts.median(numbers11)
        assert(actual === expected)
      }
    }
  }


  "BigInts.mode" when {
    val emptyList = List.empty[BigInt]
    s"mode($emptyList)" should {
      val expected = List.empty[BigInt]
      s"return $expected" in {
        val actual = BigInts.mode(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigInt](0)
    s"mode($numbers0)" should {
      val expected = List[BigInt](0)
      s"return $expected" in {
        val actual = BigInts.mode(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigInt](999)
    s"mode($numbers1)" should {
      val expected = List[BigInt](999)
      s"return $expected" in {
        val actual = BigInts.mode(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 2)
    s"mode($numbers2)" should {
      val expected = List[BigInt](1, 2)
      s"return $expected" in {
        val actual = BigInts.mode(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"mode($numbers3)" should {
      val expected = List[BigInt](1, 2, 3)
      s"return $expected" in {
        val actual = BigInts.mode(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](3, 7, 5, 13, 20, 23, 39, 23, 40, 23, 14, 12, 56, 23, 29)
    s"mode($numbers4)" should {
      val expected = List[BigInt](23)
      s"return $expected" in {
        val actual = BigInts.mode(numbers4)
        assert(actual === expected)
      }
    }
    val numbers5 = List[BigInt](1, 3, 3, 3, 4, 4, 6, 6, 6, 9)
    s"mode($numbers5)" should {
      val expected = List[BigInt](3, 6)
      s"return $expected" in {
        val actual = BigInts.mode(numbers5)
        assert(actual === expected)
      }
    }
    val numbers6 = List[BigInt](1, 1, 2, 3, 3, 3, 3, 5, 5, 7, 7, 7, 7, 100, 101, 101, 101, 101, 8)
    s"mode($numbers6)" should {
      val expected = List[BigInt](3, 7, 101)
      s"return $expected" in {
        val actual = BigInts.mode(numbers6)
        assert(actual === expected)
      }
    }
    val bigIntOfLongMax = BigInt(Long.MaxValue)
    val longMaxX2 = bigIntOfLongMax * 2
    val longMaxX3 = bigIntOfLongMax * 3
    val numbers7 = List[BigInt](1, 1, 2, longMaxX3, longMaxX3, longMaxX3, longMaxX3, longMaxX3, 5, 5, 7, 7, 7, 7, longMaxX2, longMaxX2, longMaxX2, longMaxX2, longMaxX2, 8)
    s"mode($numbers7)" should {
      val expected = List[BigInt](longMaxX2, longMaxX3)
      s"return $expected" in {
        val actual = BigInts.mode(numbers7)
        assert(actual === expected)
      }
    }
  }

  "BigInts.stdev" when {
    val emptyList = List.empty[BigInt]
    s"stdev($emptyList)" should {
      val expected = BigDecimal(0)
      s"return $expected" in {
        val actual = BigInts.stdev(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigInt](0)
    s"stdev($numbers0)" should {
      val expected = BigDecimal(0)
      s"return $expected" in {
        val actual = BigInts.stdev(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigInt](999)
    s"stdev($numbers1)" should {
      val expected = BigDecimal(0)
      s"return $expected" in {
        val actual = BigInts.stdev(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 2)
    s"stdev($numbers2)" should {
      val expected = BigDecimals.sqrt(BigDecimal("0.25"))
      s"return $expected" in {
        val actual = BigInts.stdev(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"stdev($numbers3)" should {
      val expected = BigDecimals.sqrt(BigDecimal(2) / 3)
      s"return $expected" in {
        val actual = BigInts.stdev(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](9, 2, 5, 4, 12, 7, 8, 11, 9, 3, 7, 4, 12, 5, 4, 10, 9, 6, 9, 4)
    s"stdev($numbers4)" should {
      val expected = BigDecimal("2.9832")
      s"return $expected" in {
        val actual = BigInts.stdev(numbers4)
        assert(actual === (expected +- 0.0001))
      }
    }
  }


  "BigInts.toOrdinal" when {
    "toOrdinal(1)" should {
      val number: BigInt = 1
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(2)" should {
      val number = 2
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(3)" should {
      val number = 3
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(4)" should {
      val number = 4
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(5)" should {
      val number = 5
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(10)" should {
      val number = 10
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(11)" should {
      val number = 11
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(12)" should {
      val number = 12
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(13)" should {
      val number = 13
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(14)" should {
      val number = 14
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(20)" should {
      val number = 20
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(21)" should {
      val number = 21
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(22)" should {
      val number = 22
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(23)" should {
      val number = 23
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(24)" should {
      val number = 24
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = BigInts.toOrdinal(number)
        assert(actual === expected)
      }
    }

  }

  "BigInts.findOrdinal" when {

    "findOrdinal(-1)" should {
      val number = -1
      val expected = None
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(0)" should {
      val number = 0
      val expected = None
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(1)" should {
      val number = 1
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(2)" should {
      val number = 2
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(3)" should {
      val number = 3
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(4)" should {
      val number = 4
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(5)" should {
      val number = 5
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(10)" should {
      val number = 10
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(11)" should {
      val number = 11
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(12)" should {
      val number = 12
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(13)" should {
      val number = 13
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(14)" should {
      val number = 14
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(20)" should {
      val number = 20
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(21)" should {
      val number = 21
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(22)" should {
      val number = 22
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(23)" should {
      val number = 23
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(24)" should {
      val number = 24
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = BigInts.findOrdinal(number)
        assert(actual === expected)
      }
    }

  }
}
