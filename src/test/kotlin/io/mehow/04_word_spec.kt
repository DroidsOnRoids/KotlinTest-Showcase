@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package io.mehow

import arrow.core.flatMap
import arrow.core.left
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class BottleWordSpec : WordSpec({
  val bottle = Bottle.create(10UL, 5UL)

  "A bottle" should {
    "be filled" {
      bottle.flatMap { it.pourIn(5UL) } shouldBe Bottle.filled(10UL)
    }

    "be emptied" {
      bottle.flatMap { it.pourOut(5UL) } shouldBe Bottle.emptied(10UL)
    }

    "not contain too much" {
      bottle.flatMap { it.pourIn(6UL) } shouldBe Overflow(10UL, 11UL).left()
    }

    "not give more than it contains" {
      bottle.flatMap { it.pourOut(6UL) } shouldBe Underflow(5UL, 6UL).left()
    }
  }
})

