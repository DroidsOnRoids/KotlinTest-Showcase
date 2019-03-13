package io.mehow

import io.kotlintest.matchers.collections.shouldNotContain
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.matchers.string.shouldNotContain
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.AbstractDescribeSpec
import io.kotlintest.specs.DescribeSpec

class Test : DescribeSpec({
  describe("clubs") {
    val clubs = Data.clubs

    it("have capital names") {
      for (club in clubs) {
        club.name[0] should { firstChar ->
          Character.isUpperCase(firstChar)
        }
      }
    }

    it("have same player count") {
      val clubsWithSamePlayerCount = clubs
          .groupingBy { it.playerIds.size }
          .eachCount()
          .size
      clubsWithSamePlayerCount shouldBe 1
    }

    for (club in clubs) {
      testPlayers(club)
    }
  }
})

private suspend fun AbstractDescribeSpec.DescribeScope.testPlayers(club: Club) {
  context("players of club ${club.name}") {
    val players = club.playerIds.map { Data.findPlayer(it) }

    it("all exists") {
      players shouldNotContain null
    }

    val filteredPlayers = players.filterNotNull()


    for (player in filteredPlayers) {
      context("player ${player.name}") {
        it("does not have numbers in name") {
          player.name shouldNotContain Regex("[0-9]")
        }

        it("matches club min score requirement") {
          player.totalScore shouldBeGreaterThanOrEqual club.minPlayerScore
        }
      }
    }
  }
}
