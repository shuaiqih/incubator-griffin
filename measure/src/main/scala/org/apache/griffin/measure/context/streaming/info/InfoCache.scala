/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/
package org.apache.griffin.measure.context.streaming.info

import org.apache.griffin.measure.Loggable
import org.apache.griffin.measure.context.streaming.lock.CacheLock

trait InfoCache extends Loggable with Serializable {

  def init(): Unit
  def available(): Boolean
  def close(): Unit

  def cacheInfo(info: Map[String, String]): Boolean
  def readInfo(keys: Iterable[String]): Map[String, String]
  def deleteInfo(keys: Iterable[String]): Unit
  def clearInfo(): Unit

  def listKeys(path: String): List[String]

  def genLock(s: String): CacheLock

}
