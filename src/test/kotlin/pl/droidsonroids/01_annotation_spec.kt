@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package pl.droidsonroids

import arrow.core.flatMap
import arrow.core.left
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class BottleAnnotationSpec : AnnotationSpec() {
  private val bottle = Bottle.create(10UL, 5UL)

  @Test fun `bottle can be filled`() {
    bottle.flatMap { it.pourIn(5UL) } shouldBe Bottle.filled(10UL)
  }

  @Test fun `bottle can be emptied`() {
    bottle.flatMap { it.pourOut(5UL) } shouldBe Bottle.emptied(10UL)
  }

  @Test fun `pouring too much to a bottle overflows it`() {
    bottle.flatMap { it.pourIn(6UL) } shouldBe Overflow(10UL, 11UL).left()
  }

  @Test fun `pouring too much from a bottle underflows it`() {
    bottle.flatMap { it.pourOut(6UL) } shouldBe Underflow(5UL, 6UL).left()
  }
}
