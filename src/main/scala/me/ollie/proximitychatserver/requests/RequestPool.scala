package me.ollie.proximitychatserver.requests

import akka.actor.typed.{Behavior, SupervisorStrategy}
import akka.actor.typed.scaladsl.{Behaviors, Routers}

object RequestPool {

  def apply(configuration: PoolConfiguration): Behavior[Unit] = Behaviors.setup { context =>
    val pool = Routers.pool(poolSize = configuration.poolSize) {
      Behaviors.supervise(RequestWorker()).onFailure[Exception](SupervisorStrategy.restart)
    }

    Behaviors.empty
  }

}
