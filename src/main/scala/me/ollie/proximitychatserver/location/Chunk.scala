package me.ollie.proximitychatserver.location

import me.ollie.proximitychatserver.location.Direction.Direction
import me.ollie.proximitychatserver.location.Location.World

object Chunk {

  val chunkSize = 33

  private def locationToChunk(value: Double): Int = (value / chunkSize).floor.intValue

  def fromLocation(location: Location): Chunk = new Chunk(location.world, locationToChunk(location.x), locationToChunk(location.y), locationToChunk(location.z))

  def from(world: World, tuple: (Int, Int, Int)): Chunk = new Chunk(world, tuple._1, tuple._2, tuple._3)
}

class Chunk(val world: World, val x: Int, val y: Int, val z: Int) {

  def shift(direction: Direction): Chunk = {
    val shift: Vector3D = direction.vector()
    new Chunk(world, x + shift.x.intValue, y + shift.y.intValue, z + shift.z.intValue)
  }

}
