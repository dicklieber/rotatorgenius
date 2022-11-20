/*
 *   Copyright (C) 2022  Dick Lieber, WA9NNN
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.wa9nnn.rotator.arco

import com.wa9nnn.rotator.ui.RotatorPanel
import com.wa9nnn.rotator.{AppConfig, ConfigManager, RotatorConfig}
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableHashMap

import java.util.UUID
import javax.inject.Inject
import scala.collection.concurrent.TrieMap


class ArcoCoordinator @Inject()(configManager: ConfigManager) {
  val rotatorMap = new TrieMap[UUID, RotatorStuff]()

  val selectedRotator: Option[UUID] = None

  def updateRouterState(rotatorState: RotatorState): Unit = {
    val state: RotatorStuff = rotatorMap(rotatorState.id)
    state.value = rotatorState
  }

  def moveSelected(targetAzimuth: Int): Unit = {
    throw new NotImplementedError() //todo
  }

  def currentSelectedState: RotatorState = {
    val uuid: UUID = selectedRotator.getOrElse(rotatorMap.head._1)
    rotatorMap(uuid).value
  }


  configManager.onChange {
    (_, _, is: AppConfig) =>
      setup(is.rotators)
  }
  setup(configManager.value.rotators)


  def setup(rotators: Iterable[RotatorConfig]): Unit = {
    rotatorMap.values.foreach(_.stop())
    rotatorMap.clear()
    rotators.foreach {
      rc =>
        rotatorMap.put(rc.id, new RotatorStuff(rc))
    }
  }
}

/**
 * Holds everthing we know about aa Rotator
 */
class RotatorStuff(rotatorConfig: RotatorConfig) extends ObjectProperty[RotatorState]() {

  private val rotatorPanel = new RotatorPanel(this)

  private val arcoInterface: ArcoInterface = new ArcoInterface(rotatorConfig, this)

  def stop(): Unit = {
    rotatorPanel.stop()
    arcoInterface.stop()
  }

}