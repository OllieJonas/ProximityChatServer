package me.ollie.proximitychatserver.location

import me.ollie.proximitychatserver.location.Location.World

object Location {
  type World = String
}

class Location(val world: World, val x: Double, val y: Double, val z: Double) {

  def chunk(): Chunk = Chunk.fromLocation(this)
  /**
   * Distance between two location models
   * @param other The other location
   * @return
   */
  def ->(other: Location): Double = {
    val dSquared = ->^(other)

    if (dSquared == Double.MaxValue) Double.MaxValue else Math.sqrt(dSquared)
  }

  def ->^(other: Location): Double = {
    if (other.world != world) return Double.MaxValue
    val square: Function[Double, Double] = x => x * x
    square(x - other.x) + square(y - other.y) + square(z - other.z)
  }

  def -(other: Location): Option[Vector3D] = {
    if (world != other.world) return None
    Some(new Vector3D(Math.abs(x - other.x), Math.abs(y - other.y), Math.abs(z - other.z)))
  }
}
