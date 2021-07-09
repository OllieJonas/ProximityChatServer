package me.ollie.proximitychatserver.models

import me.ollie.proximitychatserver.volume.Volume

class ServerConfiguration(val maxDistance: Double = 31,
                          volumeCalculation: Volume.Calculation = Volume.Constant()) {

  def volume(distance: Double): Long = volumeCalculation.volume(distance, maxDistance)
}
