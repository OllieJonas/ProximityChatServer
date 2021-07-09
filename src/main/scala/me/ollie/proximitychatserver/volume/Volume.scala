package me.ollie.proximitychatserver.volume


object Volume {

  trait Calculation {
    def volume(distance: Double, maxDistance: Double): Long
  }

  case class Constant() extends Calculation {
    override def volume(distance: Double, maxDistance: Double): Long = Math.round(Math.min(distance / maxDistance, 0)) * 100
  }
}

