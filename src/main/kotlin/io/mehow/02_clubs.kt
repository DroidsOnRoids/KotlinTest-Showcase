package io.mehow

data class Player(
  val id: Long,
  val name: String,
  val totalScore: Long
)

data class Club(
  val name: String,
  val playerIds: List<Long>,
  val minPlayerScore: Long
)

object Data {
  private val players = listOf(
      Player(0, "a", 80),
      Player(1, "b", 111),
      Player(2, "c", 531),
      Player(3, "d", 672),
      Player(4, "e", 863),
      Player(5, "f", 980),
      Player(6, "g", 421),
      Player(7, "h", 3084),
      Player(8, "i", 193),
      Player(9, "j", 1034),
      Player(10, "k", 18),
      Player(11, "l", 507),
      Player(12, "m", 507)
  )
  val clubs = listOf(
      Club("First", listOf(1, 2, 3, 4, 5, 6), 50),
      Club("Second", listOf(7, 8, 9, 10, 11, 12), 10)
  )

  fun findPlayer(id: Long) = players.firstOrNull { it.id == id }
}