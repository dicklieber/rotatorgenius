package com.wa9nnn.rotator

import com.typesafe.scalalogging.LazyLogging
import com.wa9nnn.rotator.CommandLine.{defaultRotatorGeniusPort, defaultRotctldPort}
import com.wa9nnn.util.HostAndPort

/**
 * What we got from the command line.
 *
 * @param controllerHostAndPort netwoerk address of Rotator Genius
 * @param rotctldPort   rotctld port to listen on.
 * @param verbose       lots of outout.
 * @param debug         show some stuff.
 */
case class CommandLine(
                        controllerHostAndPort: HostAndPort = HostAndPort("not defined", defaultRotatorGeniusPort),
                        rotctldPort: Int = defaultRotctldPort,
                        verbose: Boolean = false,
                        debug: Boolean = false
                      ) extends LazyLogging {
  def ifVerbose(c: Unit): Unit = {
    c
  }

  def ifDebug(c: Unit): Unit = {
    c
  }
}

object CommandLine {
  val defaultRotatorGeniusPort = 9006
  val defaultRotctldPort = 4533
}