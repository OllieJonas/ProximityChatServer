package me.ollie.proximitychatserver.location

import me.ollie.proximitychatserver.location.Direction.East

object Direction {

  private val eightCardinalDirections: Seq[Verticality] = List[Verticality](East, NorthEast, North, NorthWest, West, SouthWest, South, SouthEast)

  /**
   * Taken from:
   * https://gamedev.stackexchange.com/questions/49290/whats-the-best-way-of-transforming-a-2d-vector-into-the-closest-8-way-compass-d
   *
   * @param vector
   * @return
   */
  def fromVector(vector: Vector3D): Direction = {
    val horizontalAngle: Double = Math.atan2(vector.z, vector.x)
    val octant: Int = (Math.round(8 * horizontalAngle / (2 * Math.PI) + 8) % 8).toInt
    val cardinalDirection = eightCardinalDirections(octant)

    if (vector.y >= 0.33) cardinalDirection.up()
    else if (vector.y <= -0.33) cardinalDirection.down()
    else cardinalDirection.asInstanceOf[Direction]
  }

  trait Direction {
    def vector(): Vector3D
  }

  trait Verticality {
    def up(): Direction

    def down(): Direction
  }


    // Directions
    object North extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(1, 0, 0)

      override def up(): Direction = NorthUp

      override def down(): Direction = NorthDown
    }

    object East extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(0, 0, 1)

      override def up(): Direction = EastUp

      override def down(): Direction = EastDown
    }

    object South extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(-1, 0, 0)

      override def up(): Direction = SouthUp

      override def down(): Direction = SouthDown
    }

    object West extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(0, 0, -1)

      override def up(): Direction = WestUp

      override def down(): Direction = WestDown
    }

    object Up extends Direction {
      override def vector(): Vector3D = new Vector3D(0, 1, 0)
    }

    object Down extends Direction {
      override def vector(): Vector3D = new Vector3D(0, -1, 0)
    }

    object NorthEast extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(1, 0, 1)

      override def up(): Direction = NorthEastUp

      override def down(): Direction = NorthEastDown
    }

    object NorthWest extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(1, 0, -1)

      override def up(): Direction = NorthWestUp

      override def down(): Direction = NorthWestDown
    }

    object SouthEast extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(-1, 0, 1)

      override def up(): Direction = SouthEastUp

      override def down(): Direction = SouthEastDown
    }

    object SouthWest extends Direction with Verticality {
      override def vector(): Vector3D = new Vector3D(-1, 0, -1)

      override def up(): Direction = SouthWestUp

      override def down(): Direction = SouthWestDown
    }

    object NorthUp extends Direction {
      override def vector(): Vector3D = new Vector3D(1, 1, 0)
    }

    object EastUp extends Direction {
      override def vector(): Vector3D = new Vector3D(0, 1, 1)
    }

    object SouthUp extends Direction {
      override def vector(): Vector3D = new Vector3D(-1, 1, 0)
    }

    object WestUp extends Direction {
      override def vector(): Vector3D = new Vector3D(0, 1, -1)
    }

    object NorthEastUp extends Direction {
      override def vector(): Vector3D = new Vector3D(1, 1, 1)
    }

    object NorthWestUp extends Direction {
      override def vector(): Vector3D = new Vector3D(1, 1, -1)
    }

    object SouthEastUp extends Direction {
      override def vector(): Vector3D = new Vector3D(-1, 1, 1)
    }

    object SouthWestUp extends Direction {
      override def vector(): Vector3D = new Vector3D(-1, 1, -1)
    }

    object NorthDown extends Direction {
      override def vector(): Vector3D = new Vector3D(1, -1, 0)
    }

    object EastDown extends Direction {
      override def vector(): Vector3D = new Vector3D(0, -1, 1)
    }

    object SouthDown extends Direction {
      override def vector(): Vector3D = new Vector3D(-1, -1, 0)
    }

    object WestDown extends Direction {
      override def vector(): Vector3D = new Vector3D(0, -1, -1)
    }

    object NorthEastDown extends Direction {
      override def vector(): Vector3D = new Vector3D(1, -1, 1)
    }

    object NorthWestDown extends Direction {
      override def vector(): Vector3D = new Vector3D(1, -1, -1)
    }

    object SouthEastDown extends Direction {
      override def vector(): Vector3D = new Vector3D(-1, -1, 1)
    }

    object SouthWestDown extends Direction {
      override def vector(): Vector3D = new Vector3D(-1, -1, -1)
    }
}
