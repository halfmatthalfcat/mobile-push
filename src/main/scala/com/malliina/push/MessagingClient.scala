package com.malliina.push

import com.malliina.concurrent.FutureOps
import com.malliina.util.Log
import org.asynchttpclient.Response

import scala.concurrent.{ExecutionContext, Future}

/**
  * @tparam T type of device
  */
trait MessagingClient[T] extends Log {
  def send(dest: T): Future[Response]

  def sendLogged(dest: T)(implicit ec: ExecutionContext): Future[Unit] = send(dest)
    .map(r => log info s"Sent message to: $dest. Response: ${r.getStatusText}")
    .recoverAll(t => log.warn(s"Unable to send message to: $dest", t))
}
