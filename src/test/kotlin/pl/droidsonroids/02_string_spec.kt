@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS", "EXPERIMENTAL_API_USAGE")

package pl.droidsonroids

import arrow.core.flatMap
import arrow.core.left
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BottleStringSpec : StringSpec({
  val bottle = Bottle.create(10UL, 5UL)

  "bottle can be filled" {
    bottle.flatMap { it.pourIn(5UL) } shouldBe Bottle.filled(10UL)
  }

  "bottle can be emptied" {
    bottle.flatMap { it.pourOut(5UL) } shouldBe Bottle.emptied(10UL)
  }

  "pouring too much to a bottle overflows it" {
    bottle.flatMap { it.pourIn(6UL) } shouldBe Overflow(10UL, 11UL).left()
  }

  "pouring too much from a bottle underflows it" {
    bottle.flatMap { it.pourOut(6UL) } shouldBe Underflow(5UL, 6UL).left()
  }
})
