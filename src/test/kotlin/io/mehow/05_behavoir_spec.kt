@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package io.mehow

import arrow.core.flatMap
import arrow.core.left
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class BottleBehaviorSpec : BehaviorSpec({
  val bottle = Bottle.create(10UL, 5UL)

  Given("A bottle") {
    When("I pour the missing amount in") {
      val filledBottle = bottle.flatMap { it.pourIn(5UL) }
      Then("it is full") {
        filledBottle shouldBe Bottle.filled(10UL)
      }
    }

    When("I pour the amount out") {
      val emptiedBottle = bottle.flatMap { it.pourOut(5UL) }
      Then("it is empty") {
        emptiedBottle shouldBe Bottle.emptied(10UL)
      }
    }

    When("I pour too much in") {
      val overflow = bottle.flatMap { it.pourIn(6UL) }
      Then("it is overflown") {
        overflow shouldBe Overflow(10UL, 11UL).left()
      }
    }

    When("I pour too much out") {
      val underflow = bottle.flatMap { it.pourOut(6UL) }
      Then("it is undeflown") {
        underflow shouldBe Underflow(5UL, 6UL).left()
      }
    }
  }
})
