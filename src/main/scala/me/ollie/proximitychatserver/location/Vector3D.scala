package me.ollie.proximitychatserver.location

import me.ollie.proximitychatserver.location.Location.World

class Vector3D(val x: Double, val y: Double, val z: Double) {

  def toLocation(world: World): Location = new Location(world, x, y, z)

}
