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
package org.apache.griffin.measure.configuration.enums

import scala.util.matching.Regex

/**
  * the normalize strategy to collect metric
  */
sealed trait NormalizeType {
  val regex: Regex
  val desc: String
}

object NormalizeType {
  private val normalizeTypes: List[NormalizeType] = List(DefaultNormalizeType, EntriesNormalizeType, ArrayNormalizeType, MapNormalizeType)
  def apply(ptn: String): NormalizeType = {
    normalizeTypes.find(tp => ptn match {
      case tp.regex() => true
      case _ => false
    }).getOrElse(DefaultNormalizeType)
  }
  def unapply(pt: NormalizeType): Option[String] = Some(pt.desc)
}

/**
  * default normalize strategy
  * metrics contains n rows -> normalized metric json map
  * n = 0: { }
  * n = 1: { "col1": "value1", "col2": "value2", ... }
  * n > 1: { "arr-name": [ { "col1": "value1", "col2": "value2", ... }, ... ] }
  * all rows
  */
final case object DefaultNormalizeType extends NormalizeType {
  val regex: Regex = "".r
  val desc: String = "default"
}

/**
  * metrics contains n rows -> normalized metric json map
  * n = 0: { }
  * n >= 1: { "col1": "value1", "col2": "value2", ... }
  * the first row only
  */
final case object EntriesNormalizeType extends NormalizeType {
  val regex: Regex = "^(?i)entries$".r
  val desc: String = "entries"
}

/**
  * metrics contains n rows -> normalized metric json map
  * n = 0: { "arr-name": [ ] }
  * n >= 1: { "arr-name": [ { "col1": "value1", "col2": "value2", ... }, ... ] }
  * all rows
  */
final case object ArrayNormalizeType extends NormalizeType {
  val regex: Regex = "^(?i)array|list$".r
  val desc: String = "array"
}

/**
  * metrics contains n rows -> normalized metric json map
  * n = 0: { "map-name": { } }
  * n >= 1: { "map-name": { "col1": "value1", "col2": "value2", ... } }
  * the first row only
  */
final case object MapNormalizeType extends NormalizeType {
  val regex: Regex = "^(?i)map$".r
  val desc: String = "map"
}