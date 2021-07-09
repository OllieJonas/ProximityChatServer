package me.ollie.proximitychatserver

import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.scaladsl.Behaviors
import me.ollie.proximitychatserver.models.ServerConfiguration
import me.ollie.proximitychatserver.services.ServerService

object Guardian {

  def apply(): Behavior[Nothing] = {
    Behaviors.setup [Receptionist.Listing] { context  =>
      val serverService = context.spawnAnonymous(ServerService())
      serverService.tell(ServerService.SpawnServer("localhost", new ServerConfiguration(), null))
      context.system.receptionist ! Receptionist.Subscribe(ServerService.Key, context.self)
      Behaviors.same
    }
      .narrow
  }

}
