package me.ollie.proximitychatserver.actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import me.ollie.proximitychatserver.actors.LocationUserActor.LocationUserCommand
import me.ollie.proximitychatserver.location.Location
import me.ollie.proximitychatserver.models.ServerConfiguration
import org.slf4j.{Logger, LoggerFactory}

object LocationServerActor {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  sealed trait LocationServerCommand

  case class CreateUser(uuid: String, location: Location) extends LocationServerCommand
  case class RemoveUser(uuid: String) extends LocationServerCommand
  case class UpdateUser(uuid: String, location: Location) extends LocationServerCommand
  case object Shutdown extends LocationServerCommand

  def apply(serverId: String, configuration: ServerConfiguration): Behavior[LocationServerCommand] = Behaviors.setup { context =>

  def message(serverId: String, users: Map[String, ActorRef[LocationUserCommand]], configuration: ServerConfiguration): Behavior[LocationServerCommand] = Behaviors.receiveMessage {
      case CreateUser(uuid, location) =>
        val behaviour = LocationUserActor.apply(new LocationUserActor.State(serverId, uuid, location, Map(), configuration))
        val actorRef: ActorRef[LocationUserCommand] = context.spawn(behaviour, uuid)

        users.values.foreach(ref => ref.tell(LocationUserActor.ReportLocation(actorRef))) // get all other users to report their location to new actor.
        message(serverId, users.updated(uuid, actorRef), configuration)

      case RemoveUser(uuid) =>
        val user: Option[ActorRef[LocationUserCommand]] = users.get(uuid)
        user.foreach(ref => ref.tell(LocationUserActor.Shutdown))
        message(serverId, users.removed(uuid), configuration)

      case UpdateUser(uuid, location) =>
        users.get(uuid).foreach(ref => ref.tell(LocationUserActor.UpdateLocation(location)))
        users.filter(tuple => tuple._1 != uuid).values.foreach(ref => ref.tell(LocationUserActor.UpdateOtherLocation(uuid, location)))
        message(serverId, users, configuration)

      case Shutdown =>
        logger.debug("Shutting down server " + serverId)
        Behaviors.stopped
    }

    message(serverId, Map(), configuration)
  }
}
