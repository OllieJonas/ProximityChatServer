package me.ollie.proximitychatserver.actors

import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import me.ollie.proximitychatserver.location.Location
import me.ollie.proximitychatserver.models.ServerConfiguration
import org.slf4j.{Logger, LoggerFactory}

object LocationUserActor {

  sealed trait LocationUserCommand

  val logger: Logger = LoggerFactory.getLogger(getClass)


  def key(serverId: String, uuid: String): ServiceKey[LocationUserCommand] = ServiceKey[LocationUserCommand]("server-" + serverId + "&user-" + uuid)

  case class UpdateLocation(location: Location) extends LocationUserCommand
  case class UpdateOtherLocation(uuid: String, location: Location) extends LocationUserCommand
  case class UpdateConfiguration(configuration: ServerConfiguration) extends LocationUserCommand
  case class ReportLocation(requester: ActorRef[LocationUserCommand]) extends LocationUserCommand

  case object Shutdown extends LocationUserCommand

  class State(val serverId: String, val uuid: String, val location: Location,
              val volumes: Map[String, Long] = Map(), val configuration: ServerConfiguration) {

    def copy(location: Location): State = new State(serverId, uuid, location, volumes, configuration)

    def copy(configuration: ServerConfiguration): State = new State(serverId, uuid, location, volumes, configuration)

    def copy(volumes: Map[String, Long]): State = new State(serverId, uuid, location, volumes, configuration)
  }

  def apply(state: State): Behavior[LocationUserCommand] = Behaviors.setup [LocationUserCommand] { _ =>

    def doApply(state: State): Behavior[LocationUserCommand] = Behaviors.receiveMessage {

      case UpdateLocation(location) =>
        doApply(state.copy(location))

      case UpdateOtherLocation(newUuid, newLocation) =>
        doApply(state.copy(state.volumes.updated(newUuid, state.configuration.volume(state.location -> newLocation))))

      case UpdateConfiguration(configuration) =>
        doApply(state.copy(configuration))

      case ReportLocation(requester) =>
        requester ! UpdateOtherLocation(state.uuid, state.location)
        Behaviors.same

      case Shutdown =>
        logger.debug("Shutting down user actor " + state.uuid)
        Behaviors.stopped
    }

    doApply(state)
  }

}


