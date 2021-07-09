package me.ollie.proximitychatserver.requests

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object RequestWorker {

  sealed trait Request

  def apply(): Behavior[Request] = Behaviors.receiveMessage { message =>
    Behaviors.same
  }


}
