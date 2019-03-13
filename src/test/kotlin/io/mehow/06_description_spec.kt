@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package io.mehow

import arrow.core.flatMap
import arrow.core.left
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class BottleDescriptionSpec : DescribeSpec({
  val bottle = Bottle.create(10UL, 5UL)

  describe("A bottle") {
    it("can be filled") {
      bottle.flatMap { it.pourIn(5UL) } shouldBe Bottle.filled(10UL)
    }

    it("can be emptied") {
      bottle.flatMap { it.pourOut(5UL) } shouldBe Bottle.emptied(10UL)
    }

    it("cannot contain too much") {
      bottle.flatMap { it.pourIn(6UL) } shouldBe Overflow(10UL, 11UL).left()
    }

    it("cannot give more than it contains") {
      bottle.flatMap { it.pourOut(6UL) } shouldBe Underflow(5UL, 6UL).left()
    }
  }
})
