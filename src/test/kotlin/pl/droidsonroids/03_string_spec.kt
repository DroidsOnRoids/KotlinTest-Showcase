@file:Suppress("EXPERIMENTAL_API_USAGE")

package pl.droidsonroids

import arrow.core.left
import arrow.core.right
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BottleRandomStringSpec : StringSpec({
  "random values fulfill contract" {
    assertAll(ULongGenerator, ULongGenerator) { capacity, amount ->
      val bottle = Bottle.create(capacity, amount)

      if (capacity >= amount) bottle shouldBe Bottle(capacity, amount).right()
      else bottle shouldBe Overflow(capacity, amount).left()
    }
  }
})

private object ULongGenerator : Gen<ULong> {
  override fun constants() = listOf(ULong.MIN_VALUE, ULong.MAX_VALUE)

  override fun random() = generateSequence {
    Gen.long().random().first().toULong()
  }
}