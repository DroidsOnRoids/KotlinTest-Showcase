@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package pl.droidsonroids

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right

fun main() {
  val bottle = Bottle.create(10UL, 5UL)
  println(bottle)

  val filledBottle = bottle.flatMap { it.pourIn(5UL) }
  println(filledBottle)

  val emptiedBottle = bottle.flatMap { it.pourOut(5UL) }
  println(emptiedBottle)

  val overflow = bottle.flatMap { it.pourIn(6UL) }
  println(overflow)

  val underflow = bottle.flatMap { it.pourOut(6UL) }
  println(underflow)

  val stillOverflow = overflow.flatMap { it.pourOut(20UL) }
  println(stillOverflow)
}

sealed class PouringProblem

data class Overflow(val capacity: ULong, val amount: ULong) : PouringProblem() {
  override fun toString(): String {
    return "Do $capacity nijak nie wlezie $amount!"
  }
}

data class Underflow(val amount: ULong, val expectedAmount: ULong) : PouringProblem() {
  override fun toString(): String {
    return "Z $amount nijak nie wyczarujesz $expectedAmount!"
  }
}

data class Bottle(val capacity: ULong, val amount: ULong) {
  init {
    require(amount <= capacity) { "Pojemność to $capacity a chcesz włożyć $amount!" }
  }

  companion object {
    fun create(capacity: ULong, amount: ULong): Either<PouringProblem, Bottle> {
      return if (amount > capacity) Overflow(capacity, amount).left()
      else Bottle(capacity, amount).right()
    }

    fun filled(capacity: ULong) = create(capacity, capacity)

    fun emptied(capacity: ULong) = create(capacity, 0UL)
  }

  fun pourIn(amount: ULong) = create(capacity,
      this.amount + amount)

  fun pourOut(amount: ULong): Either<PouringProblem, Bottle> {
    return if (this.amount >= amount) Bottle(capacity, this.amount - amount).right()
    else Underflow(this.amount, amount).left()
  }
}
