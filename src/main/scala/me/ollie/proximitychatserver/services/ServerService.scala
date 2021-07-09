package me.ollie.proximitychatserver.services

import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import me.ollie.proximitychatserver.actors.LocationServerActor
import me.ollie.proximitychatserver.actors.LocationServerActor.LocationServerCommand
import me.ollie.proximitychatserver.models.ServerConfiguration
import me.ollie.proximitychatserver.util.Error

object ServerService {

  sealed trait Request

  case class SpawnServer(id: String, configuration: ServerConfiguration, replyTo: ActorRef[Response]) extends Request
  case class DeleteServer(id: String, replyTo: ActorRef[Response]) extends Request

  sealed trait Response

  case class ServerNotFound(id: String) extends Response with Error {
    override def message(): String = "Server not found!"
  }

  val Key: ServiceKey[Request] = ServiceKey[Request]("serverService")

  def apply(servers: Map[String, ActorRef[LocationServerCommand]] = Map()): Behavior[Request] = Behaviors.receive { (context, message) =>

    message match {
      case SpawnServer(id, configuration, replyTo) =>
        val actor = context.spawn(LocationServerActor.apply(id, configuration), "server-" + id)
        apply(servers + (id -> actor))

      case DeleteServer(id, replyTo) =>
        servers.get(id).foreach(ref => ref.tell(LocationServerActor.Shutdown))
        apply(servers - id)
    }
  }
}
