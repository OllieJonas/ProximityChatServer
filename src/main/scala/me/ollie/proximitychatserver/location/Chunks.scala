package me.ollie.proximitychatserver.location

import me.ollie.proximitychatserver.location.Chunk.{chunkSize, fromLocation}
import me.ollie.proximitychatserver.location.Direction.Direction

object Chunks {

  def inRadius(location: Location, radius: Int): Iterable[Chunk] = inRadius(fromLocation(location), radius)

  def inRadius(chunk: Chunk, radius: Int): Iterable[Chunk] = {
    val range = radius / chunkSize + 1
    List.fill(3)(-range to range)
      .flatten
      .combinations(3)
      .flatMap(_.permutations)
      .map(l => new Chunk(chunk.world, l.head + chunk.x, l(1) + chunk.y, l(2) + chunk.z))
      .iterator.to(Set)
  }

  def immediateNeighbourDirection(from: Location, to: Location): Option[Direction] = {
    (from - to).map(vector => vector)
    None
  }

  def shift(chunks: Iterable[Chunk], direction: Direction): Iterable[Chunk] = chunks.map(c => c.shift(direction))
}
